package aima.core.agent.impl.aprog.simplerule;

import aima.core.agent.impl.ObjectWithDynamicAttributes;

import java.util.Random;

/**
 * Created by benoit on 17/08/2018.
 */
public class RandomCondition extends Condition {

    Random random = new Random();

    @Override
    public boolean evaluate(ObjectWithDynamicAttributes p) {
        return random.nextBoolean();
    }

    @Override
    public String toString() {
        return random.toString();
    }
}
