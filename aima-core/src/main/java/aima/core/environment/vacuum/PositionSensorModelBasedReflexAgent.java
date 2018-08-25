package aima.core.environment.vacuum;

import aima.core.agent.impl.aprog.PositionSensorModelBasedReflexAgentProgram;
import aima.core.agent.impl.aprog.simplerule.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static aima.core.agent.impl.aprog.PositionSensorModelBasedReflexAgentProgram.VISITED_LOCATIONS;

/**
 * Created by benoit on 19/08/2018.
 */
public class PositionSensorModelBasedReflexAgent extends PositionSensorSimpleReflexAgent {

    public PositionSensorModelBasedReflexAgent() {
    }

    public PositionSensorModelBasedReflexAgent(List<Coord> allLocations) {
        super();
        configure(allLocations);
    }

    @Override
    public void configure(List<Coord> allLocations){
        this.allLocations = allLocations;
        program = new PositionSensorModelBasedReflexAgentProgram(getRuleSet());
    }


    protected Set<Rule> getRuleSet() {
        Set<Rule> rules = new LinkedHashSet<Rule>();

        rules.add(new Rule(new EQUALCondition(LocalVacuumEnvironmentPercept.ATTRIBUTE_STATE,
                VacuumEnvironment.LocationState.Dirty),
                VacuumEnvironment.ACTION_SUCK));
        rules.add(new Rule( new ANDCondition(
                new CanGoUpCondition(
                        LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                        allLocations), new HasNotBeenVisitedCondition(VISITED_LOCATIONS,
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,VacuumEnvironment.ACTION_GO_UP)),
                VacuumEnvironment.ACTION_GO_UP));
        rules.add(new Rule( new ANDCondition(
                new CanMoveRightCondition(
                        LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                        allLocations),new HasNotBeenVisitedCondition(VISITED_LOCATIONS,
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,VacuumEnvironment.ACTION_MOVE_RIGHT)),
                VacuumEnvironment.ACTION_MOVE_RIGHT));
        rules.add(new Rule( new ANDCondition(
                new CanGoDownCondition(
                        LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                        allLocations),new HasNotBeenVisitedCondition(VISITED_LOCATIONS,
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,VacuumEnvironment.ACTION_GO_DOWN)),
                VacuumEnvironment.ACTION_GO_DOWN));
        rules.add(new Rule(new ANDCondition(
                new CanMoveLeftCondition(
                        LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                        allLocations),new HasNotBeenVisitedCondition(VISITED_LOCATIONS,
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,VacuumEnvironment.ACTION_MOVE_LEFT)),
                VacuumEnvironment.ACTION_MOVE_LEFT));

        return rules;
    }
}
