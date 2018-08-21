package aima.core.agent.impl.aprog.simplerule;

import aima.core.agent.impl.ObjectWithDynamicAttributes;
import aima.core.environment.vacuum.Coord;

import java.util.List;

/**
 * Created by benoit on 21/08/2018.
 */
public abstract class CanMoveCondition extends Condition {

    protected Object currentLocationKey;

    protected List<Coord> allLocations;

    public CanMoveCondition(Object currentLocationKey, List<Coord> allLocations) {
        this.currentLocationKey = currentLocationKey;
        this.allLocations = allLocations;
    }

    @Override
    public boolean evaluate(ObjectWithDynamicAttributes p) {

        Coord currentLocation = (Coord) p.getAttribute(currentLocationKey);
        return allLocations.contains(findNextCoord(currentLocation));
    }

    public abstract Coord findNextCoord(Coord currentLocation);

    @Override
    public String toString() {
        return "CanMoveCondition{" +
                "currentLocationKey=" + currentLocationKey +
                ", allLocations=" + allLocations +
                '}';
    }
}
