package aima.core.agent.impl.aprog.simplerule;

import aima.core.agent.impl.ObjectWithDynamicAttributes;
import aima.core.environment.vacuum.Coord;

/**
 * Created by benoit on 05/08/2018.
 */
public class CanMoveLeftCondition extends Condition {
    private Object key;

    private int minX;

    public CanMoveLeftCondition(Object key, int minX){
        this.key = key;
        this.minX = minX;
    }

    @Override
    public boolean evaluate(ObjectWithDynamicAttributes p) {
        return ((Coord) p.getAttribute(key)).getX() > minX;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        return sb.append(key).append(".x >").append(minX).toString();
    }

}
