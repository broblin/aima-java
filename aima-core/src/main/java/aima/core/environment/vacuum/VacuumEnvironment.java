package aima.core.environment.vacuum;

import aima.core.agent.Action;
import aima.core.agent.Agent;
import aima.core.agent.EnvironmentState;
import aima.core.agent.Percept;
import aima.core.agent.impl.AbstractEnvironment;
import aima.core.agent.impl.DynamicAction;
import aima.core.search.agent.NondeterministicSearchAgent;
import aima.core.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): pg 58.<br>
 * <br>
 * Let the world contain just two locations. Each location may or may not
 * contain dirt, and the agent may be in one location or the other. There are 8
 * possible world states, as shown in Figure 3.2. The agent has three possible
 * actions in this version of the vacuum world: <em>Left</em>, <em>Right</em>,
 * and <em>Suck</em>. Assume for the moment, that sucking is 100% effective. The
 * goal is to clean up all the dirt.
 * 
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 * @author Mike Stampone
 * @author Ruediger Lunde
 */
public class VacuumEnvironment extends AbstractEnvironment {
	// Allowable Actions within the Vacuum Environment
	public static final Action ACTION_MOVE_LEFT = new DynamicAction("Left");
	public static final Action ACTION_GO_UP = new DynamicAction("Up");
	public static final Action ACTION_MOVE_RIGHT = new DynamicAction("Right");
	public static final Action ACTION_GO_DOWN = new DynamicAction("Down");
	public static final Action ACTION_SUCK = new DynamicAction("Suck");
	public static final Action ACTION_KEEP_SAME_DIRECTION = new DynamicAction("keepMovement");
	//it begins by 1 (not 0 like java index)
	public static final Coord LOCATION_A = new Coord(1,1);
	public static final Coord LOCATION_B = new Coord(2,1);

	int xDimension;
	int yDimension;

	public enum LocationState {
		Clean, Dirty
	}

    protected final List<Coord> locations;
	protected VacuumEnvironmentState envState = null;
	protected boolean isDone = false;

	/**
	 * Constructs a vacuum environment with two locations A and B, in which dirt is
	 * placed at random.
	 */
	public VacuumEnvironment() {
		this(Util.randomBoolean() ? LocationState.Clean : LocationState.Dirty,
				Util.randomBoolean() ? LocationState.Clean : LocationState.Dirty);
	}

	/**
	 * Constructs a vacuum environment with two locations A and B, in which dirt is
	 * placed as specified.
	 * 
	 * @param locAState
	 *            the initial state of location A, which is either
	 *            <em>Clean</em> or <em>Dirty</em>.
	 * @param locBState
	 *            the initial state of location B, which is either
	 *            <em>Clean</em> or <em>Dirty</em>.
	 */
	public VacuumEnvironment(LocationState locAState, LocationState locBState) {
		this(Arrays.asList(LOCATION_A, LOCATION_B), locAState, locBState);
	}

	/**
	 * Constructor which allows subclasses to define a vacuum environment with an arbitrary number
	 * of squares. Two-dimensional grid environments can be defined by additionally overriding
	 * {@link #getXDimension()} and {@link #getYDimension()}.
	 */
	public VacuumEnvironment(List<Coord> locations, LocationState... locStates) {
		this.locations = locations;
		xDimension = locations.stream().max((coord,coord2) -> Integer.compare(coord.x,coord2.x)).get().x;
		yDimension = locations.stream().max((coord,coord2) -> Integer.compare(coord.y,coord2.y)).get().y;

		envState = new VacuumEnvironmentState();
		for (int i = 0; i < locations.size() && i < locStates.length; i++)
			envState.setLocationState(locations.get(i), locStates[i]);
	}

	public List<Coord> getLocations() {
		return locations;
	}

	public EnvironmentState getCurrentState() {
		return envState;
	}

	public LocationState getLocationState(Coord location) {
		return envState.getLocationState(location);
	}

	public Coord getAgentLocation(Agent a) {
		return envState.getAgentLocation(a);
	}

	@Override
	public void addAgent(Agent a) {
		int idx = new Random().nextInt(locations.size());
		envState.setAgentLocation(a, locations.get(idx));
		super.addAgent(a);
	}

	public void addAgent(Agent a, Coord location) {
		// Ensure the agent state information is tracked before
		// adding to super, as super will notify the registered
		// EnvironmentViews that is was added.
		envState.setAgentLocation(a, location);
		super.addAgent(a);
	}

	@Override
	public Percept getPerceptSeenBy(Agent anAgent) {
		if (anAgent instanceof NondeterministicSearchAgent) {
			// This agent expects a fully observable environment. It gets a clone of the environment state.
			return envState.clone();
		}
		// Other agents get a local percept.
		Coord loc = envState.getAgentLocation(anAgent);
		return new LocalVacuumEnvironmentPercept(loc, envState.getLocationState(loc),envState.isChockDetected());
	}

	@Override
	public void executeAction(Agent a, Action action) {
		Coord loc = getAgentLocation(a);
		envState.setChockDetected(false);
		if (ACTION_MOVE_RIGHT == action) {
			int x = getX(loc);
			if (x < getXDimension())
				envState.setAgentLocation(a, getLocation(x + 1, getY(loc)));
			else
				envState.setChockDetected(true);
			updatePerformanceMeasure(a, -1);
		} else if (ACTION_MOVE_LEFT == action) {
			int x = getX(loc);
			if (x > 1)
				envState.setAgentLocation(a, getLocation(x - 1, getY(loc)));
			else
				envState.setChockDetected(true);
			updatePerformanceMeasure(a, -1);
		} else if (ACTION_GO_DOWN == action) {
			int y = getY(loc);
			if (y > 1)
				envState.setAgentLocation(a, getLocation(getX(loc), y - 1));
			else
				envState.setChockDetected(true);
			updatePerformanceMeasure(a, -1);
		} else if (ACTION_GO_UP == action) {
			int y = getY(loc);
			if (y < getYDimension())
				envState.setAgentLocation(a, getLocation(getX(loc), y + 1));
			else
				envState.setChockDetected(true);
			updatePerformanceMeasure(a, -1);
		} else if (ACTION_SUCK == action) {
			if (LocationState.Dirty == envState.getLocationState(envState
					.getAgentLocation(a))) {
				envState.setLocationState(envState.getAgentLocation(a),
						LocationState.Clean);
				updatePerformanceMeasure(a, 10);
			}
		} else if (action.isNoOp()) {
			// In the Vacuum Environment we consider things done if
			// the agent generates a NoOp.
			isDone = true;
		}
	}

	@Override
	public boolean isDone() {
		return super.isDone() || isDone;
	}


	// Information for grid views...

	public int getXDimension() {
		return xDimension;
	}

	public int getYDimension() {
		return yDimension;
	}

	// 1 means left
	public int getX(Coord location) {
		return location.x;
	}

	// 1 means bottom
	public int getY(Coord location) {
		return location.y;
	}

	// (1, 1) is bottom left
	public Coord getLocation(int x, int y) {
		return locations.stream().filter(coord -> coord.x == x && coord.y == y).findFirst().get();
	}
}