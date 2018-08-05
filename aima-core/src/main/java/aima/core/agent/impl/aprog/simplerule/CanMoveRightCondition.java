package aima.core.agent.impl.aprog.simplerule;

import aima.core.agent.impl.ObjectWithDynamicAttributes;
import aima.core.environment.vacuum.Coord;

/**
 * Created by benoit on 05/08/2018.
 */
public class CanMoveRightCondition extends Condition {
    private Object key;

    private int maxX;

    public CanMoveRightCondition(Object key, int maxX){
        this.key = key;
        this.maxX = maxX;
    }

    @Override
    public boolean evaluate(ObjectWithDynamicAttributes p) {
        return ((Coord) p.getAttribute(key)).getX() < maxX;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        return sb.append(key).append(".x <").append(maxX).toString();
    }
}
