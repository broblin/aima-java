package aima.test.core.unit.environment.vacuum;


import aima.core.agent.impl.SimpleActionTracker;
import aima.core.environment.vacuum.Coord;
import aima.core.environment.vacuum.PositionSensorSimpleReflexAgent;
import aima.core.environment.vacuum.VacuumEnvironment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by benoit.roblin on 06/08/18.
 */
public class PositionSensorSimpleReflexAgentTest {

    private PositionSensorSimpleReflexAgent agent;
    private SimpleActionTracker actionTracker;

    @Before
    public void setUp() {
        List<Coord> allLocations = Arrays.asList(VacuumEnvironment.LOCATION_A, VacuumEnvironment.LOCATION_B);
        agent = new PositionSensorSimpleReflexAgent(allLocations);
        actionTracker = new SimpleActionTracker();
    }

    @Test
    public void testCleanClean() {
        VacuumEnvironment tve = new VacuumEnvironment(
                VacuumEnvironment.LocationState.Clean,
                VacuumEnvironment.LocationState.Clean);
        tve.addAgent(agent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);

        tve.step(8);

        Assert.assertEquals(
                "Action[name=Right], Action[name=Left], Action[name=Right], Action[name=Left], Action[name=Right], Action[name=Left], Action[name=Right], Action[name=Left]",
                actionTracker.getActions());
    }

    @Test
    public void testCleanDirty() {
        VacuumEnvironment tve = new VacuumEnvironment(
                VacuumEnvironment.LocationState.Clean,
                VacuumEnvironment.LocationState.Dirty);
        tve.addAgent(agent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);

        tve.step(8);

        Assert.assertEquals(
                "Action[name=Right], Action[name=Suck], Action[name=Left], Action[name=Right], Action[name=Left], Action[name=Right], Action[name=Left], Action[name=Right]",
                actionTracker.getActions());
    }

    @Test
    public void testDirtyClean() {
        VacuumEnvironment tve = new VacuumEnvironment(
                VacuumEnvironment.LocationState.Dirty,
                VacuumEnvironment.LocationState.Clean);
        tve.addAgent(agent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);

        tve.step(8);

        Assert.assertEquals(
                "Action[name=Suck], Action[name=Right], Action[name=Left], Action[name=Right], Action[name=Left], Action[name=Right], Action[name=Left], Action[name=Right]",
                actionTracker.getActions());
    }

    @Test
    public void testDirtyDirty() {
        VacuumEnvironment tve = new VacuumEnvironment(
                VacuumEnvironment.LocationState.Dirty,
                VacuumEnvironment.LocationState.Dirty);
        tve.addAgent(agent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);

        tve.step(8);

        Assert.assertEquals(
                "Action[name=Suck], Action[name=Right], Action[name=Suck], Action[name=Left], Action[name=Right], Action[name=Left], Action[name=Right], Action[name=Left]",
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
        agent.configure(tveDims);
        tve.addAgent(agent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);

        tve.step(8);

        Assert.assertEquals(
                "Action[name=Up], Action[name=Suck], Action[name=Right], Action[name=Suck], Action[name=Down], Action[name=Up], Action[name=Down], Action[name=Up]",
                actionTracker.getActions());
    }

    @Test
    public void testNonRationalAgent(){
        //given : an 3x2 environement with the coord 2,1 dirty
        List<Coord> tveDims = new ArrayList<Coord>() {{
            add(new Coord(1,1));
            add(new Coord(1,2));
            add(new Coord(2,2));
            add(new Coord(2,1));
            add(new Coord(3,1));
            add(new Coord(3,2));
        }};
        VacuumEnvironment tve = new VacuumEnvironment(
                tveDims,
                new  VacuumEnvironment.LocationState[] {VacuumEnvironment.LocationState.Clean,
                        VacuumEnvironment.LocationState.Dirty,
                        VacuumEnvironment.LocationState.Dirty,
                        VacuumEnvironment.LocationState.Dirty,
                        VacuumEnvironment.LocationState.Clean,
                        VacuumEnvironment.LocationState.Clean});
        agent.configure(tveDims);
        tve.addAgent(agent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);

        //when : 10 steps
        tve.step(10);

        //the coord 2,1 is still dirty and the target finish to produce the 2 sames actions at infinite
        Assert.assertEquals(
                "Action[name=Up], Action[name=Suck], Action[name=Right], Action[name=Suck], Action[name=Right], Action[name=Down], Action[name=Up], Action[name=Down], Action[name=Up], Action[name=Down]",
                actionTracker.getActions());
        Assert.assertEquals(tve.getLocationState(new Coord(2,1)),VacuumEnvironment.LocationState.Dirty);
    }

    @Test
    public void testWithUnavailable1x2(){
        //avec un obstacle : 1,2
        //given : an 3x2 environement with the coord 2,1 dirty
        List<Coord> tveDims = new ArrayList<Coord>() {{
            add(new Coord(1,1));
            add(new Coord(2,2));
            add(new Coord(2,1));
            add(new Coord(3,1));
            add(new Coord(3,2));
        }};
        VacuumEnvironment tve = new VacuumEnvironment(
                tveDims,
                new  VacuumEnvironment.LocationState[] {VacuumEnvironment.LocationState.Clean,
                        VacuumEnvironment.LocationState.Dirty,
                        VacuumEnvironment.LocationState.Dirty,
                        VacuumEnvironment.LocationState.Dirty,
                        VacuumEnvironment.LocationState.Clean});
        agent.configure(tveDims);
        tve.addAgent(agent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);

        //when : 10 steps
        tve.step(10);

        //the coord 2,1 is still dirty and the target finish to produce the 2 sames actions at infinite
        Assert.assertEquals(
                "Action[name=Right], Action[name=Suck], Action[name=Up], Action[name=Suck], Action[name=Right], Action[name=Down], Action[name=Suck], Action[name=Up], Action[name=Down], Action[name=Up]",
                actionTracker.getActions());
        Assert.assertEquals(tve.getLocationState(new Coord(2,1)),VacuumEnvironment.LocationState.Clean);
    }

}