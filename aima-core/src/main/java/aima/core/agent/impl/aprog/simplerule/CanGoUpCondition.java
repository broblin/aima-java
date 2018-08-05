package aima.core.agent.impl.aprog.simplerule;

import aima.core.agent.impl.ObjectWithDynamicAttributes;
import aima.core.environment.vacuum.Coord;

/**
 * Created by benoit on 05/08/2018.
 */
public class CanGoUpCondition extends Condition {

    private Object key;

    private int maxY;

    public CanGoUpCondition(Object key, int maxY){
        this.key = key;
        this.maxY = maxY;
    }

    @Override
    public boolean evaluate(ObjectWithDynamicAttributes p) {
        return ((Coord) p.getAttribute(key)).getY() < maxY;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        return sb.append(key).append(".y <").append(maxY).toString();
    }


}
