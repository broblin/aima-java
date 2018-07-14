package aima.core.environment.vacuum.extended;

import aima.core.agent.Action;
import aima.core.agent.Agent;
import aima.core.agent.Percept;
import aima.core.agent.impl.AbstractEnvironment;
import aima.core.agent.impl.DynamicAction;
import aima.core.environment.vacuum.VacuumEnvironment;
import aima.core.environment.vacuum.VacuumEnvironmentState;

import java.util.Map;

/**
 * Created by benoit on 14/07/2018.
 */
public class ExtendedVacuumEnvironment extends VacuumEnvironment {

    int xDimension;
    int yDimension;

    public ExtendedVacuumEnvironment(int xDimension, int yDimension, Map<Coord,LocationState> locationStateMap){
        this.xDimension = xDimension;
        this.yDimension = yDimension;

        //TODO remplir locations par rapport aux coordonnées

        //TODO : comment indiquer que telle case est sale ?

        envState = new VacuumEnvironmentState();
        //TODO
        /*
        for (int i = 0; i < locations.size() && i < locStates.length; i++)
            envState.setLocationState(locations.get(i), locStates[i]);
        */
    }

    @Override
    public Object getLocation(int x, int y) {
        return locations.get((getYDimension() - y) * getXDimension() + x - 1);
    }

    //TODO à redéfinir par rapport à la classe mère

    // 1 means left
    public int getX(String location) {
        return getLocations().indexOf(location) % getXDimension() + 1;
    }

    // 1 means bottom
    public int getY(String location) {
        return getYDimension() - getLocations().indexOf(location) / getXDimension();
    }
}
