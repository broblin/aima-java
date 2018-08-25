package aima.core.agent.impl.aprog;

import aima.core.agent.Action;
import aima.core.agent.Model;
import aima.core.agent.Percept;
import aima.core.agent.impl.DynamicState;
import aima.core.agent.impl.aprog.simplerule.Rule;
import aima.core.environment.vacuum.Coord;
import aima.core.environment.vacuum.LocalVacuumEnvironmentPercept;
import aima.core.environment.vacuum.VacuumEnvironment;

import java.util.*;

/**
 * Created by benoit on 19/08/2018.
 */
public class PositionSensorModelBasedReflexAgentProgram extends ModelBasedReflexAgentProgram {
    public static final String VISITED_LOCATIONS = "visitedLocations";
    public static final String FRONTIER_LOCATIONS = "frontierLocations";

    public PositionSensorModelBasedReflexAgentProgram(Set<Rule> ruleSet){
        super();
        configure(ruleSet);
    }


    @Override
    protected void init() {

    }

    public void configure(Set<Rule> ruleSet){
        setState(new DynamicState());
        setRules(ruleSet);
    }

    @Override
    protected DynamicState updateState(DynamicState state, Action action, Percept percept, Model model) {
        LocalVacuumEnvironmentPercept vep = (LocalVacuumEnvironmentPercept) percept;

        state.setAttribute(LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                vep.getAgentLocation());
        state.setAttribute(LocalVacuumEnvironmentPercept.ATTRIBUTE_STATE,
                vep.getLocationState());

        if(state.getAttribute(VISITED_LOCATIONS) == null){
            state.setAttribute(VISITED_LOCATIONS,new ArrayList<Coord>());
        }
        List<Coord> visitedLocations = (List<Coord>) state.getAttribute(VISITED_LOCATIONS);
        if(vep.getLocationState().equals(VacuumEnvironment.LocationState.Clean)){
            visitedLocations.add(vep.getAgentLocation());
        }

        return state;
    }
}
