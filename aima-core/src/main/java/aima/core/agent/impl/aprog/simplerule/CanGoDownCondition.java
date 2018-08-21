package aima.core.agent.impl.aprog.simplerule;

import aima.core.environment.vacuum.Coord;

import java.util.List;

/**
 * Created by benoit on 05/08/2018.
 */
public class CanGoDownCondition extends CanMoveCondition {


    @Override
    public Coord findNextCoord(Coord currentLocation) {
        return new Coord(currentLocation.getX(),currentLocation.getY()-1);
    }

    public CanGoDownCondition(Object currentLocationKey, List<Coord> allLocations) {
        super(currentLocationKey, allLocations);
    }
}
