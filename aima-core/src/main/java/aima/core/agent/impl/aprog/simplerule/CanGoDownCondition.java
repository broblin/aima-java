package aima.core.agent.impl.aprog.simplerule;

import aima.core.agent.impl.ObjectWithDynamicAttributes;
import aima.core.environment.vacuum.Coord;

/**
 * Created by benoit on 05/08/2018.
 */
public class CanGoDownCondition extends Condition {

    private Object key;

    private int minY;

    public CanGoDownCondition(Object key, int minY){
        this.key = key;
        this.minY = minY;
    }

    @Override
    public boolean evaluate(ObjectWithDynamicAttributes p) {
        return  ((Coord) p.getAttribute(key)).getY() > minY;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        return sb.append(key).append(".y >").append(minY).toString();
    }
}
