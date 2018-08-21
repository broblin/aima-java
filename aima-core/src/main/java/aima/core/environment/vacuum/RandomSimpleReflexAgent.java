package aima.core.environment.vacuum;

import aima.core.agent.impl.aprog.simplerule.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by benoit on 17/08/2018.
 */
public class RandomSimpleReflexAgent extends PositionSensorSimpleReflexAgent {

    public RandomSimpleReflexAgent() {
        super();
    }

    public RandomSimpleReflexAgent(List<Coord> allLocations) {
        super(allLocations);
    }


    //

    // PRIVATE METHODS
    //
    protected Set<Rule> getRuleSet() {
        // Note: Using a LinkedHashSet so that the iteration order (i.e. implied
        // precedence) of rules can be guaranteed.
        Set<Rule> rules = new LinkedHashSet<Rule>();

        rules.add(new Rule(new EQUALCondition(LocalVacuumEnvironmentPercept.ATTRIBUTE_STATE,
                VacuumEnvironment.LocationState.Dirty),
                VacuumEnvironment.ACTION_SUCK));
        rules.add(new Rule( new ANDCondition(
                new CanGoUpCondition(
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                        allLocations), new RandomCondition()),
                VacuumEnvironment.ACTION_GO_UP));
        rules.add(new Rule( new ANDCondition(
                new CanMoveRightCondition(
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                        allLocations),new RandomCondition()),
                VacuumEnvironment.ACTION_MOVE_RIGHT));
        rules.add(new Rule( new ANDCondition(
                new CanGoDownCondition(
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                        allLocations),new RandomCondition()),
                VacuumEnvironment.ACTION_GO_DOWN));
        rules.add(new Rule(new ANDCondition(
                new CanMoveLeftCondition(
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                        allLocations),new RandomCondition()),
                VacuumEnvironment.ACTION_MOVE_LEFT));

        return rules;
    }
}
