package aima.test.core.unit.environment.vacuum;

import aima.core.agent.impl.SimpleActionTracker;
import aima.core.environment.vacuum.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by benoit on 21/08/2018.
 */
public class PositionSensorModelBasedReflexAgentTest {

    private PositionSensorSimpleReflexAgent agent;
    private PositionSensorModelBasedReflexAgent modelBasedReflexAgent;
    private SimpleActionTracker actionTracker;
    private SimpleActionTracker modelBasedActionTracker;

    @Before
    public void setUp() {
        List<Coord> allLocations = Arrays.asList(VacuumEnvironment.LOCATION_A, VacuumEnvironment.LOCATION_B);
        agent = new PositionSensorSimpleReflexAgent(allLocations);
        modelBasedReflexAgent = new PositionSensorModelBasedReflexAgent(allLocations);
        actionTracker = new SimpleActionTracker();
        modelBasedActionTracker = new SimpleActionTracker();
    }

    @Test
    public void testModelBasedVsSimpleReflexAgent(){
        VacuumEnvironment tve = new VacuumEnvironment(
                VacuumEnvironment.LocationState.Clean,
                VacuumEnvironment.LocationState.Dirty);
        VacuumEnvironment tveForModelBasedAgent = new VacuumEnvironment(
                VacuumEnvironment.LocationState.Clean,
                VacuumEnvironment.LocationState.Dirty);

        tve.addAgent(agent, VacuumEnvironment.LOCATION_A);
        tveForModelBasedAgent.addAgent(modelBasedReflexAgent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);
        tveForModelBasedAgent.addEnvironmentView(modelBasedActionTracker);

        //when : X steps
        tve.step(10);
        tveForModelBasedAgent.step(10);

        System.out.println(actionTracker.getActions());
        System.out.println(modelBasedActionTracker.getActions());
        System.out.println("agent performance : "+tve.getPerformanceMeasure(agent)+" agent avec état: "+tveForModelBasedAgent.getPerformanceMeasure(modelBasedReflexAgent));
        Assert.assertTrue(tve.getPerformanceMeasure(agent) < tveForModelBasedAgent.getPerformanceMeasure(modelBasedReflexAgent));
    }

    @Test
    public void testModelBasedVsSimpleReflexAgent2(){
        ArrayList<Coord> tveDims = new ArrayList<Coord>() {{
            add(new Coord(1,1));
            add(new Coord(1,2));
            add(new Coord(2,2));
            add(new Coord(2,1));
            add(new Coord(3,1));
            add(new Coord(3,2));
        }};
        VacuumEnvironment.LocationState[] locationStates = new  VacuumEnvironment.LocationState[] {VacuumEnvironment.LocationState.Clean,
                VacuumEnvironment.LocationState.Dirty,
                VacuumEnvironment.LocationState.Dirty,
                VacuumEnvironment.LocationState.Dirty,
                VacuumEnvironment.LocationState.Clean,
                VacuumEnvironment.LocationState.Clean};


        //given : an 3x2 environement with the coord 2,1 dirty
        VacuumEnvironment tve = new VacuumEnvironment(
                tveDims,
                locationStates);
        VacuumEnvironment tveForModelBasedAgent = new VacuumEnvironment(
                tveDims,
                locationStates);
        agent.configure(tveDims);
        modelBasedReflexAgent.configure(tveDims);

        tve.addAgent(agent, VacuumEnvironment.LOCATION_A);
        tveForModelBasedAgent.addAgent(modelBasedReflexAgent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);
        tveForModelBasedAgent.addEnvironmentView(modelBasedActionTracker);

        //when : X steps
        tve.step(10);
        tveForModelBasedAgent.step(10);

        System.out.println(actionTracker.getActions());
        System.out.println(modelBasedActionTracker.getActions());
        System.out.println("agent performance : "+tve.getPerformanceMeasure(agent)+" agent avec état: "+tveForModelBasedAgent.getPerformanceMeasure(modelBasedReflexAgent));
        Assert.assertTrue(tve.getPerformanceMeasure(agent) < tveForModelBasedAgent.getPerformanceMeasure(modelBasedReflexAgent));
    }

    @Test
    public void testModelBasedVsSimpleReflexAgent3(){
        ArrayList<Coord> tveDims = new ArrayList<Coord>() {{
            add(new Coord(1,1));
            add(new Coord(2,1));
            add(new Coord(3,1));
            add(new Coord(4,1));
            add(new Coord(5,1));
            add(new Coord(6,1));
            add(new Coord(7,1));
            add(new Coord(8,1));
        }};
        VacuumEnvironment.LocationState[] locationStates = new  VacuumEnvironment.LocationState[] {VacuumEnvironment.LocationState.Dirty,
                VacuumEnvironment.LocationState.Dirty,
                VacuumEnvironment.LocationState.Dirty,
                VacuumEnvironment.LocationState.Dirty,
                VacuumEnvironment.LocationState.Dirty,
                VacuumEnvironment.LocationState.Dirty,
                VacuumEnvironment.LocationState.Dirty,
                VacuumEnvironment.LocationState.Dirty};


        //given : an 8x1 environement with the all coord dirty
        VacuumEnvironment tve = new VacuumEnvironment(
                tveDims,
                locationStates);
        VacuumEnvironment tveForModelBasedAgent = new VacuumEnvironment(
                tveDims,
                locationStates);
        agent.configure(tveDims);
        modelBasedReflexAgent.configure(tveDims);

        tve.addAgent(agent, VacuumEnvironment.LOCATION_A);
        tveForModelBasedAgent.addAgent(modelBasedReflexAgent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);
        tveForModelBasedAgent.addEnvironmentView(modelBasedActionTracker);

        //when : X steps
        tve.step(17);
        tveForModelBasedAgent.step(17);

        //the coord 2,1 is still dirty and the target finish to produce the 2 sames actions at infinite

        System.out.println(actionTracker.getActions());
        System.out.println(modelBasedActionTracker.getActions());
        System.out.println("agent performance : "+tve.getPerformanceMeasure(agent)+" agent avec état: "+tveForModelBasedAgent.getPerformanceMeasure(modelBasedReflexAgent));
        Assert.assertTrue(tve.getPerformanceMeasure(agent) <= tveForModelBasedAgent.getPerformanceMeasure(modelBasedReflexAgent));
    }
}
