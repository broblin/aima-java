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
 * Created by benoit on 25/08/2018.
 */
public class ChockSensorModelBasedReflexAgentTest {

    private ChockSensorModelBasedReflexAgent chockSensorModelBasedReflexAgent;
    private PositionSensorModelBasedReflexAgent positionSensorModelBasedReflexAgent;
    private SimpleActionTracker actionTracker;
    private SimpleActionTracker modelBasedActionTracker;

    @Before
    public void setUp() {
        List<Coord> allLocations = Arrays.asList(VacuumEnvironment.LOCATION_A, VacuumEnvironment.LOCATION_B);
        chockSensorModelBasedReflexAgent = new ChockSensorModelBasedReflexAgent();
        positionSensorModelBasedReflexAgent = new PositionSensorModelBasedReflexAgent(allLocations);
        actionTracker = new SimpleActionTracker();
        modelBasedActionTracker = new SimpleActionTracker();
    }

    @Test
    public void testCleanClean() {
        VacuumEnvironment tve = new VacuumEnvironment(
                VacuumEnvironment.LocationState.Clean,
                VacuumEnvironment.LocationState.Clean);
        tve.addAgent(chockSensorModelBasedReflexAgent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);

        tve.step(8);

        Assert.assertEquals(
                "Action[name=Up], Action[name=Right], Action[name=Right], Action[name=Up], Action[name=Down], Action[name=NoOp], Action[name=NoOp], Action[name=NoOp]",
                actionTracker.getActions());
    }

    @Test
    public void testCleanDirty() {
        VacuumEnvironment tve = new VacuumEnvironment(
                VacuumEnvironment.LocationState.Clean,
                VacuumEnvironment.LocationState.Dirty);
        tve.addAgent(chockSensorModelBasedReflexAgent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);

        tve.step(8);

        Assert.assertEquals(
                "Action[name=Up], Action[name=Right], Action[name=Suck], Action[name=Right], Action[name=Up], Action[name=Down], Action[name=NoOp], Action[name=NoOp]",
                actionTracker.getActions());
    }

    @Test
    public void testDirtyClean() {
        VacuumEnvironment tve = new VacuumEnvironment(
                VacuumEnvironment.LocationState.Dirty,
                VacuumEnvironment.LocationState.Clean);
        tve.addAgent(chockSensorModelBasedReflexAgent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);

        tve.step(8);

        Assert.assertEquals(
                "Action[name=Suck], Action[name=Up], Action[name=Right], Action[name=Right], Action[name=Up], Action[name=Down], Action[name=NoOp], Action[name=NoOp]",
                actionTracker.getActions());
    }

    @Test
    public void testDirtyDirty() {
        VacuumEnvironment tve = new VacuumEnvironment(
                VacuumEnvironment.LocationState.Dirty,
                VacuumEnvironment.LocationState.Dirty);
        tve.addAgent(chockSensorModelBasedReflexAgent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);

        tve.step(8);

        Assert.assertEquals(
                "Action[name=Suck], Action[name=Up], Action[name=Right], Action[name=Suck], Action[name=Right], Action[name=Up], Action[name=Down], Action[name=NoOp]",
                actionTracker.getActions());
    }

    @Test
    public void testCleanDirtyDirtyClean() {
        List<Coord> tveDims = new ArrayList<Coord>() {{
            add(new Coord(1,1));
            add(new Coord(1,2));
            add(new Coord(2,2));
            add(new Coord(2,1));
        }};
        VacuumEnvironment tve = new VacuumEnvironment(
                tveDims,
                new  VacuumEnvironment.LocationState[] {VacuumEnvironment.LocationState.Clean,
                        VacuumEnvironment.LocationState.Dirty,
                        VacuumEnvironment.LocationState.Dirty,
                        VacuumEnvironment.LocationState.Clean});
        tve.addAgent(chockSensorModelBasedReflexAgent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);

        tve.step(8);

        Assert.assertEquals(
                "Action[name=Up], Action[name=Suck], Action[name=Up], Action[name=Right], Action[name=Suck], Action[name=Right], Action[name=Up], Action[name=Down]",
                actionTracker.getActions());
    }


    @Test
    public void testPositionVsChockAgent(){
        VacuumEnvironment tve = new VacuumEnvironment(
                VacuumEnvironment.LocationState.Clean,
                VacuumEnvironment.LocationState.Dirty);
        VacuumEnvironment tveForModelBasedAgent = new VacuumEnvironment(
                VacuumEnvironment.LocationState.Clean,
                VacuumEnvironment.LocationState.Dirty);

        tve.addAgent(chockSensorModelBasedReflexAgent, VacuumEnvironment.LOCATION_A);
        tveForModelBasedAgent.addAgent(positionSensorModelBasedReflexAgent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);
        tveForModelBasedAgent.addEnvironmentView(modelBasedActionTracker);

        //when : X steps
        tve.step(10);
        tveForModelBasedAgent.step(10);

        System.out.println(actionTracker.getActions());
        System.out.println(modelBasedActionTracker.getActions());
        System.out.println("chockSensorModelBasedReflexAgent performance : "+tve.getPerformanceMeasure(chockSensorModelBasedReflexAgent)+" positionSensorModelBasedReflexAgent avec état: "+tveForModelBasedAgent.getPerformanceMeasure(positionSensorModelBasedReflexAgent));
        Assert.assertTrue(tve.getPerformanceMeasure(chockSensorModelBasedReflexAgent) < tveForModelBasedAgent.getPerformanceMeasure(positionSensorModelBasedReflexAgent));
    }

    @Test
    public void testPositionVsChockAgent2(){
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
        positionSensorModelBasedReflexAgent.configure(tveDims);

        tve.addAgent(chockSensorModelBasedReflexAgent, VacuumEnvironment.LOCATION_A);
        tveForModelBasedAgent.addAgent(positionSensorModelBasedReflexAgent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);
        tveForModelBasedAgent.addEnvironmentView(modelBasedActionTracker);

        //when : X steps
        tve.step(10);
        tveForModelBasedAgent.step(10);

        System.out.println(actionTracker.getActions());
        System.out.println(modelBasedActionTracker.getActions());
        System.out.println("chockSensorModelBasedReflexAgent performance : "+tve.getPerformanceMeasure(chockSensorModelBasedReflexAgent)+" positionSensorModelBasedReflexAgent avec état: "+tveForModelBasedAgent.getPerformanceMeasure(positionSensorModelBasedReflexAgent));
        Assert.assertTrue(tve.getPerformanceMeasure(chockSensorModelBasedReflexAgent) < tveForModelBasedAgent.getPerformanceMeasure(positionSensorModelBasedReflexAgent));
    }

    @Test
    public void testPositionVsChockAgent3(){
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
        positionSensorModelBasedReflexAgent.configure(tveDims);

        tve.addAgent(chockSensorModelBasedReflexAgent, VacuumEnvironment.LOCATION_A);
        tveForModelBasedAgent.addAgent(positionSensorModelBasedReflexAgent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);
        tveForModelBasedAgent.addEnvironmentView(modelBasedActionTracker);

        //when : X steps
        tve.step(17);
        tveForModelBasedAgent.step(17);

        //the coord 2,1 is still dirty and the target finish to produce the 2 sames actions at infinite

        System.out.println(actionTracker.getActions());
        System.out.println(modelBasedActionTracker.getActions());
        System.out.println("chockSensorModelBasedReflexAgent performance : "+tve.getPerformanceMeasure(chockSensorModelBasedReflexAgent)+" positionSensorModelBasedReflexAgent avec état: "+tveForModelBasedAgent.getPerformanceMeasure(positionSensorModelBasedReflexAgent));
        Assert.assertTrue(tve.getPerformanceMeasure(chockSensorModelBasedReflexAgent) <= tveForModelBasedAgent.getPerformanceMeasure(positionSensorModelBasedReflexAgent));
    }
}
