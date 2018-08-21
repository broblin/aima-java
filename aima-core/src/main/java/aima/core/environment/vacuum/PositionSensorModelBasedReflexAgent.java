package aima.core.environment.vacuum;

import aima.core.agent.impl.aprog.PositionSensorModelBasedReflexAgentProgram;
import aima.core.agent.impl.aprog.simplerule.*;

import java.util.LinkedHashSet;
import java.util.Set;

import static aima.core.agent.impl.aprog.PositionSensorModelBasedReflexAgentProgram.VISITED_LOCATIONS;

/**
 * Created by benoit on 19/08/2018.
 */
public class PositionSensorModelBasedReflexAgent extends PositionSensorSimpleReflexAgent {

    public PositionSensorModelBasedReflexAgent() {
    }

    public PositionSensorModelBasedReflexAgent(int xEnvironmentDimension, int yEnvironmentDimension) {
        super(xEnvironmentDimension, yEnvironmentDimension);
    }

    @Override
    public void configure(int xEnvironmentDimension,int yEnvironmentDimension){
        this.xEnvironmentDimension = xEnvironmentDimension;
        this.yEnvironmentDimension = yEnvironmentDimension;
        program = new PositionSensorModelBasedReflexAgentProgram(getRuleSet());
    }


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
                        getyEnvironmentDimension()), new HasNotBeenVisitedCondition(VISITED_LOCATIONS,
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,VacuumEnvironment.ACTION_GO_UP)),
                VacuumEnvironment.ACTION_GO_UP));
        rules.add(new Rule( new ANDCondition(
                new CanMoveRightCondition(
                        LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                        getxEnvironmentDimension()),new HasNotBeenVisitedCondition(VISITED_LOCATIONS,
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,VacuumEnvironment.ACTION_MOVE_RIGHT)),
                VacuumEnvironment.ACTION_MOVE_RIGHT));
        rules.add(new Rule( new ANDCondition(
                new CanGoDownCondition(
                        LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                        1),new HasNotBeenVisitedCondition(VISITED_LOCATIONS,
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,VacuumEnvironment.ACTION_GO_DOWN)),
                VacuumEnvironment.ACTION_GO_DOWN));
        rules.add(new Rule(new ANDCondition(
                new CanMoveLeftCondition(
                        LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                        1),new HasNotBeenVisitedCondition(VISITED_LOCATIONS,
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,VacuumEnvironment.ACTION_MOVE_LEFT)),
                VacuumEnvironment.ACTION_MOVE_LEFT));

        return rules;
    }
}
