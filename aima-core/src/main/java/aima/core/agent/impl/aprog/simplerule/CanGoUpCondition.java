package aima.core.agent.impl.aprog.simplerule;

import aima.core.agent.impl.ObjectWithDynamicAttributes;
import aima.core.environment.vacuum.Coord;

import java.util.List;

/**
 * Created by benoit on 05/08/2018.
 */
public class CanGoUpCondition extends CanMoveCondition {

    @Override
    public Coord findNextCoord(Coord currentLocation) {
        return new Coord(currentLocation.getX(),currentLocation.getY()+1);
    }

    public CanGoUpCondition(Object currentLocationKey, List<Coord> allLocations) {
        super(currentLocationKey, allLocations);
    }
}
