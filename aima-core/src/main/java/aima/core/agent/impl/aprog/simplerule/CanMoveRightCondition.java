package aima.core.agent.impl.aprog.simplerule;

import aima.core.agent.impl.ObjectWithDynamicAttributes;
import aima.core.environment.vacuum.Coord;

import java.util.List;

/**
 * Created by benoit on 05/08/2018.
 */
public class CanMoveRightCondition extends CanMoveCondition {

    @Override
    public Coord findNextCoord(Coord currentLocation) {
        return new Coord(currentLocation.getX()+1,currentLocation.getY());
    }

    public CanMoveRightCondition(Object currentLocationKey, List<Coord> allLocations) {
        super(currentLocationKey, allLocations);
    }
}
