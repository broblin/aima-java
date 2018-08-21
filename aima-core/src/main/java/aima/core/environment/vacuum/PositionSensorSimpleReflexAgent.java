package aima.core.environment.vacuum;

import aima.core.agent.impl.AbstractAgent;
import aima.core.agent.impl.aprog.SimpleReflexAgentProgram;
import aima.core.agent.impl.aprog.simplerule.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by benoit on 04/08/2018.
 */
public class PositionSensorSimpleReflexAgent extends AbstractAgent {


    List<Coord> allLocations;

    public PositionSensorSimpleReflexAgent() {
        super();
    }

    public PositionSensorSimpleReflexAgent(List<Coord> allLocations) {
        super();
        configure(allLocations);
    }

    public void configure(List<Coord> allLocations){
        this.allLocations = allLocations;
        program = new SimpleReflexAgentProgram(getRuleSet());
    }

    //
    // PROTECTED METHODS
    //
    protected Set<Rule> getRuleSet() {
        // Note: Using a LinkedHashSet so that the iteration order (i.e. implied
        // precedence) of rules can be guaranteed.
        Set<Rule> rules = new LinkedHashSet<Rule>();

        // Rules based on REFLEX-VACUUM-AGENT:
        // Artificial Intelligence A Modern Approach (3rd Edition): Figure 2.8,
        // page 48.

        rules.add(new Rule(new EQUALCondition(LocalVacuumEnvironmentPercept.ATTRIBUTE_STATE,
                VacuumEnvironment.LocationState.Dirty),
                VacuumEnvironment.ACTION_SUCK));
        rules.add(new Rule(new CanGoUpCondition(
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                allLocations),
                VacuumEnvironment.ACTION_GO_UP));
        rules.add(new Rule(new CanMoveRightCondition(
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                allLocations),
                VacuumEnvironment.ACTION_MOVE_RIGHT));
        rules.add(new Rule(new CanGoDownCondition(
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                allLocations),
                VacuumEnvironment.ACTION_GO_DOWN));
        rules.add(new Rule(new CanMoveLeftCondition(
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                allLocations),
                VacuumEnvironment.ACTION_MOVE_LEFT));

        return rules;
    }
}
