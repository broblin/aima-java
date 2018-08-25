package aima.core.agent.impl.aprog;

import aima.core.agent.Action;
import aima.core.agent.Model;
import aima.core.agent.Percept;
import aima.core.agent.impl.DynamicState;
import aima.core.agent.impl.NoOpAction;
import aima.core.agent.impl.aprog.simplerule.Rule;
import aima.core.environment.vacuum.Coord;
import aima.core.environment.vacuum.LocalVacuumEnvironmentPercept;
import aima.core.environment.vacuum.VacuumEnvironment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static aima.core.environment.vacuum.VacuumEnvironment.*;

/**
 * the model register visited positions dynamicly (position initial = 1,1)
 * x and y postion can be < 0
 * Created by benoit on 22/08/2018.
 */
public class ChockSensorModelBasedReflexAgentProgram  extends PositionSensorModelBasedReflexAgentProgram {

    public ChockSensorModelBasedReflexAgentProgram(Set<Rule> ruleSet) {
        super(ruleSet);
    }

    @Override
    protected void init() {

    }

    @Override
    protected DynamicState updateState(DynamicState state, Action action, Percept percept, Model model) {
        LocalVacuumEnvironmentPercept vep = (LocalVacuumEnvironmentPercept) percept;

        state.setAttribute(LocalVacuumEnvironmentPercept.IS_CHOCK_DETECTED,
                vep.isChockDetected());
        state.setAttribute(LocalVacuumEnvironmentPercept.ATTRIBUTE_STATE,
                vep.getLocationState());

        registerLastMovementAction(state,action);

        if(state.getAttribute(VISITED_LOCATIONS) == null){
            state.setAttribute(VISITED_LOCATIONS,new ArrayList<Coord>(){{add(new Coord(1,1));}});
        }

        List<Coord> visitedLocations = (List<Coord>) state.getAttribute(VISITED_LOCATIONS);
        Coord lastVisitedLocation = visitedLocations.get(visitedLocations.size() - 1);
        int x = lastVisitedLocation.getX();
        int y = lastVisitedLocation.getY();

        if(!vep.isChockDetected()){

            Coord currentLocation=findCurrentLocation(action,lastVisitedLocation);

            if(currentLocation != null){
                visitedLocations.add(new Coord(x,y-1));
                state.setAttribute(LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                        vep.getAgentLocation());
            }

            if(vep.getLocationState().equals(VacuumEnvironment.LocationState.Clean)){
                visitedLocations.add(vep.getAgentLocation());
            }

        }else{
            Coord frontierLocation = findCurrentLocation(action,lastVisitedLocation);
            addNewFrontier(state,frontierLocation);
        }

        return state;
    }

    @Override
    protected Action ruleAction(Rule r) {
        return null == r ? NoOpAction.NO_OP : r.getAction() != VacuumEnvironment.ACTION_KEEP_SAME_DIRECTION ? r.getAction(): getLastMovementAction();
    }

    protected Action getLastMovementAction(){
        return (Action) getState().getAttribute(LocalVacuumEnvironmentPercept.LAST_MOVEMENT_ACTION);
    }

    protected void registerLastMovementAction(DynamicState state, Action action){
        if(action == VacuumEnvironment.ACTION_GO_UP ||
                action == VacuumEnvironment.ACTION_GO_DOWN ||
                action == VacuumEnvironment.ACTION_MOVE_RIGHT ||
                action == VacuumEnvironment.ACTION_MOVE_LEFT
                ){
            state.setAttribute(LocalVacuumEnvironmentPercept.LAST_MOVEMENT_ACTION,action);
        }
    }

    protected Coord findCurrentLocation(Action action,Coord lastVisitedLocation){
        int x = lastVisitedLocation.getX();
        int y = lastVisitedLocation.getY();
        if (ACTION_MOVE_RIGHT == action) {
            return new Coord(x+1,y);
        } else if (ACTION_MOVE_LEFT == action) {
            return new Coord(x-1,y);
        } else if (ACTION_GO_DOWN == action) {
            return new Coord(x,y-1);
        } else if (ACTION_GO_UP == action) {
            return new Coord(x,y+1);
        }else{
            return lastVisitedLocation;
        }
    }

    protected void addNewFrontier(DynamicState state,Coord frontierLocation){
        if(state.getAttribute(FRONTIER_LOCATIONS) == null){
            state.setAttribute(FRONTIER_LOCATIONS,new ArrayList<Coord>());
        }
        ((List<Coord>) state.getAttribute(FRONTIER_LOCATIONS)).add(frontierLocation);
    }
}
