package aima.test.core.unit.environment.vacuum;

import aima.core.agent.impl.SimpleActionTracker;
import aima.core.environment.vacuum.Coord;
import aima.core.environment.vacuum.PositionSensorSimpleReflexAgent;
import aima.core.environment.vacuum.RandomSimpleReflexAgent;
import aima.core.environment.vacuum.VacuumEnvironment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by benoit on 17/08/2018.
 */
public class RandomSimpleReflexAgentTest {

    private PositionSensorSimpleReflexAgent agent;
    private RandomSimpleReflexAgent randomAgent;
    private SimpleActionTracker actionTracker;
    private SimpleActionTracker randomActionTracker;

    @Before
    public void setUp() {
        List<Coord> allLocations = Arrays.asList(VacuumEnvironment.LOCATION_A, VacuumEnvironment.LOCATION_B);
        agent = new PositionSensorSimpleReflexAgent(allLocations);
        randomAgent = new RandomSimpleReflexAgent(allLocations);
        actionTracker = new SimpleActionTracker();
        randomActionTracker = new SimpleActionTracker();
    }

    @Test
    public void testRandomVsSimpleReflexAgent(){
        VacuumEnvironment tve = new VacuumEnvironment(
                VacuumEnvironment.LocationState.Clean,
                VacuumEnvironment.LocationState.Dirty);
        VacuumEnvironment tveForRandomAgent = new VacuumEnvironment(
                VacuumEnvironment.LocationState.Clean,
                VacuumEnvironment.LocationState.Dirty);

        tve.addAgent(agent, VacuumEnvironment.LOCATION_A);
        tveForRandomAgent.addAgent(randomAgent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);
        tveForRandomAgent.addEnvironmentView(randomActionTracker);

        //when : X steps
        tve.step(10);
        tveForRandomAgent.step(10);

        //the coord 2,1 is still dirty and the target finish to produce the 2 sames actions at infinite

        //Assert.assertEquals(
        //        "Action[name=Up], Action[name=Suck], Action[name=Right], Action[name=Suck], Action[name=Right], Action[name=Down], Action[name=Up], Action[name=Down], Action[name=Up], Action[name=Down]",
        //        actionTracker.getActions());
        System.out.println(actionTracker.getActions());
        System.out.println(randomActionTracker.getActions());
        System.out.println("agent performance : "+tve.getPerformanceMeasure(agent)+" agent avec hasard: "+tveForRandomAgent.getPerformanceMeasure(randomAgent));
        Assert.assertTrue(tve.getPerformanceMeasure(agent) < tveForRandomAgent.getPerformanceMeasure(randomAgent));
    }

    @Test
    public void testRandomVsSimpleReflexAgent2(){
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
        VacuumEnvironment tveForRandomAgent = new VacuumEnvironment(
                tveDims,
                locationStates);
        agent.configure(tveDims);
        randomAgent.configure(tveDims);

        tve.addAgent(agent, VacuumEnvironment.LOCATION_A);
        tveForRandomAgent.addAgent(randomAgent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);
        tveForRandomAgent.addEnvironmentView(randomActionTracker);

        //when : X steps
        tve.step(10);
        tveForRandomAgent.step(10);

        //the coord 2,1 is still dirty and the target finish to produce the 2 sames actions at infinite

        //Assert.assertEquals(
        //        "Action[name=Up], Action[name=Suck], Action[name=Right], Action[name=Suck], Action[name=Right], Action[name=Down], Action[name=Up], Action[name=Down], Action[name=Up], Action[name=Down]",
        //        actionTracker.getActions());
        System.out.println(actionTracker.getActions());
        System.out.println(randomActionTracker.getActions());
        System.out.println("agent performance : "+tve.getPerformanceMeasure(agent)+" agent avec hasard: "+tveForRandomAgent.getPerformanceMeasure(randomAgent));
        Assert.assertTrue(tve.getPerformanceMeasure(agent) < tveForRandomAgent.getPerformanceMeasure(randomAgent));
    }

    @Test
    public void testBadPerformanceRandomSimpleReflexAgent(){
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
        VacuumEnvironment tveForRandomAgent = new VacuumEnvironment(
                tveDims,
                locationStates);
        agent.configure(tveDims);
        randomAgent.configure(tveDims);

        tve.addAgent(agent, VacuumEnvironment.LOCATION_A);
        tveForRandomAgent.addAgent(randomAgent, VacuumEnvironment.LOCATION_A);

        tve.addEnvironmentView(actionTracker);
        tveForRandomAgent.addEnvironmentView(randomActionTracker);

        //when : X steps
        tve.step(10);
        tveForRandomAgent.step(10);

        //the coord 2,1 is still dirty and the target finish to produce the 2 sames actions at infinite

        //Assert.assertEquals(
        //        "Action[name=Up], Action[name=Suck], Action[name=Right], Action[name=Suck], Action[name=Right], Action[name=Down], Action[name=Up], Action[name=Down], Action[name=Up], Action[name=Down]",
        //        actionTracker.getActions());
        System.out.println(actionTracker.getActions());
        System.out.println(randomActionTracker.getActions());
        System.out.println("agent performance : "+tve.getPerformanceMeasure(agent)+" agent avec hasard: "+tveForRandomAgent.getPerformanceMeasure(randomAgent));
        Assert.assertTrue(tve.getPerformanceMeasure(agent) > tveForRandomAgent.getPerformanceMeasure(randomAgent));
    }
}
