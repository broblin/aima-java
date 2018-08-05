package aima.core.environment.vacuum;

import aima.core.agent.impl.AbstractAgent;
import aima.core.agent.impl.aprog.SimpleReflexAgentProgram;
import aima.core.agent.impl.aprog.simplerule.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by benoit on 04/08/2018.
 */
public class PositionSensorSimpleReflexAgent extends AbstractAgent {

    int xEnvironmentDimension;
    int yEnvironmentDimension;

    public PositionSensorSimpleReflexAgent() {
        super();
    }

    public PositionSensorSimpleReflexAgent(int xEnvironmentDimension,int yEnvironmentDimension) {
        super();
        configure(xEnvironmentDimension,yEnvironmentDimension);
    }

    public void configure(int xEnvironmentDimension,int yEnvironmentDimension){
        this.xEnvironmentDimension = xEnvironmentDimension;
        this.yEnvironmentDimension = yEnvironmentDimension;
        program = new SimpleReflexAgentProgram(getRuleSet());
    }

    //
    // PRIVATE METHODS
    //
    private Set<Rule> getRuleSet() {
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
                getyEnvironmentDimension()),
                VacuumEnvironment.ACTION_GO_UP));
        rules.add(new Rule(new CanMoveRightCondition(
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                getxEnvironmentDimension()),
                VacuumEnvironment.ACTION_MOVE_RIGHT));
        rules.add(new Rule(new CanGoDownCondition(
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                1),
                VacuumEnvironment.ACTION_GO_DOWN));
        rules.add(new Rule(new CanMoveLeftCondition(
                LocalVacuumEnvironmentPercept.ATTRIBUTE_AGENT_LOCATION,
                1),
                VacuumEnvironment.ACTION_MOVE_LEFT));

        return rules;
    }

    public int getxEnvironmentDimension() {
        return xEnvironmentDimension;
    }

    public void setxEnvironmentDimension(int xEnvironmentDimension) {
        this.xEnvironmentDimension = xEnvironmentDimension;
    }

    public int getyEnvironmentDimension() {
        return yEnvironmentDimension;
    }

    public void setyEnvironmentDimension(int yEnvironmentDimension) {
        this.yEnvironmentDimension = yEnvironmentDimension;
    }
}
