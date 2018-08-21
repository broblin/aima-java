package aima.core.agent.impl.aprog.simplerule;

import aima.core.environment.vacuum.Coord;

import java.util.List;

/**
 * Created by benoit on 05/08/2018.
 */
public class CanMoveLeftCondition extends CanMoveCondition {
    @Override
    public Coord findNextCoord(Coord currentLocation) {
        return new Coord(currentLocation.getX()-1,currentLocation.getY());
    }

    public CanMoveLeftCondition(Object currentLocationKey, List<Coord> allLocations) {
        super(currentLocationKey, allLocations);
    }

}
