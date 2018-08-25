package aima.core.environment.vacuum;

import aima.core.agent.impl.AbstractAgent;
import aima.core.agent.impl.aprog.ChockSensorModelBasedReflexAgentProgram;
import aima.core.agent.impl.aprog.SimpleReflexAgentProgram;
import aima.core.agent.impl.aprog.simplerule.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by benoit on 23/08/2018.
 */
public class ChockSensorModelBasedReflexAgent  extends AbstractAgent {
    public static final String VISITED_LOCATIONS = "visitedLocations";
    public static final String FRONTIER_LOCATIONS = "frontierLocations";

    public ChockSensorModelBasedReflexAgent() {
        super();
        program = new ChockSensorModelBasedReflexAgentProgram(getRuleSet());
    }

    //
    // PROTECTED METHODS
    //
    protected Set<Rule> getRuleSet() {
        Set<Rule> rules = new LinkedHashSet<Rule>();

        rules.add(new Rule(new EQUALCondition(LocalVacuumEnvironmentPercept.ATTRIBUTE_STATE,
                VacuumEnvironment.LocationState.Dirty),
                VacuumEnvironment.ACTION_SUCK));
        rules.add(new Rule(new ANDCondition(new ANDCondition(
                    new EQUALCondition(LocalVacuumEnvironmentPercept.IS_CHOCK_DETECTED,
                        Boolean.FALSE),
                    new HasNotBeenVisitedCondition(VISITED_LOCATIONS,
                        LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                        LocalVacuumEnvironmentPercept.LAST_MOVEMENT_ACTION)),
                    new HasNotBeenVisitedCondition(FRONTIER_LOCATIONS,
                        LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                        LocalVacuumEnvironmentPercept.LAST_MOVEMENT_ACTION)),
                VacuumEnvironment.ACTION_KEEP_SAME_DIRECTION));
        rules.add(new Rule(new ANDCondition(
                new HasNotBeenVisitedCondition(VISITED_LOCATIONS,
                        LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,VacuumEnvironment.ACTION_GO_UP),
                new HasNotBeenVisitedCondition(FRONTIER_LOCATIONS,
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,VacuumEnvironment.ACTION_GO_UP)),
                VacuumEnvironment.ACTION_GO_UP));
        rules.add(new Rule( new ANDCondition(
                new HasNotBeenVisitedCondition(VISITED_LOCATIONS,
                        LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,VacuumEnvironment.ACTION_MOVE_RIGHT),
                new HasNotBeenVisitedCondition(FRONTIER_LOCATIONS,
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,VacuumEnvironment.ACTION_MOVE_RIGHT)),
                VacuumEnvironment.ACTION_MOVE_RIGHT));
        rules.add(new Rule( new ANDCondition(
                new HasNotBeenVisitedCondition(VISITED_LOCATIONS,
                        LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,VacuumEnvironment.ACTION_GO_DOWN),
                new HasNotBeenVisitedCondition(FRONTIER_LOCATIONS,
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,VacuumEnvironment.ACTION_GO_DOWN)),
                VacuumEnvironment.ACTION_GO_DOWN));
        rules.add(new Rule(new ANDCondition(
                new HasNotBeenVisitedCondition(VISITED_LOCATIONS,
                        LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,VacuumEnvironment.ACTION_MOVE_LEFT),
                new HasNotBeenVisitedCondition(FRONTIER_LOCATIONS,
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,VacuumEnvironment.ACTION_MOVE_LEFT)),
                VacuumEnvironment.ACTION_MOVE_LEFT));

        return rules;
    }
}
