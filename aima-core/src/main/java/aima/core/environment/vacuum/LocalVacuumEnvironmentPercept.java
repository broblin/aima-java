package aima.core.environment.vacuum;

import aima.core.agent.impl.DynamicPercept;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a local percept in the vacuum environment (i.e. details the
 * agent's location and the state of the square the agent is currently at).
 * 
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 * @author Mike Stampone
 * @author Andrew Brown
 */
public class LocalVacuumEnvironmentPercept extends DynamicPercept {

	public static final String ATTRIBUTE_AGENT_LOCATION = "agentLocation";
	public static final String IS_CHOCK_DETECTED = "chockPerception";
	public static final String ATTRIBUTE_STATE = "state";
	public static final String LAST_MOVEMENT_ACTION = "lastMovementAction";
	public static final String VISITED_LOCATIONS = "visitedLocations";

	/**
	 * Construct a vacuum environment percept from the agent's perception of the
	 * current location and state.
	 * 
	 * @param agentLocation
	 *            the agent's perception of the current location.
	 * @param state
	 *            the agent's perception of the current state.
	 */
	public LocalVacuumEnvironmentPercept(Coord agentLocation,
			VacuumEnvironment.LocationState state) {
		setAttribute(ATTRIBUTE_AGENT_LOCATION, agentLocation);
		setAttribute(ATTRIBUTE_STATE, state);
	}

	public LocalVacuumEnvironmentPercept(Coord agentLocation,
										 VacuumEnvironment.LocationState state,
										 Boolean isChockDetected) {
		setAttribute(ATTRIBUTE_AGENT_LOCATION, agentLocation);
		setAttribute(ATTRIBUTE_STATE, state);
		setAttribute(IS_CHOCK_DETECTED, isChockDetected);
		setAttribute(VISITED_LOCATIONS,new ArrayList<Coord>(){{add(agentLocation);}});
	}

	/**
	 * Return the agent's perception of the current location, which is either A
	 * or B.
	 * 
	 * @return the agent's perception of the current location, which is either A
	 *         or B.
	 */
	public Coord getAgentLocation() {
		return (Coord) getAttribute(ATTRIBUTE_AGENT_LOCATION);
	}

	/**
	 * Return the agent's perception of the current state, which is either
	 * <em>Clean</em> or <em>Dirty</em>.
	 * 
	 * @return the agent's perception of the current state, which is either
	 *         <em>Clean</em> or <em>Dirty</em>.
	 */
	public VacuumEnvironment.LocationState getLocationState() {
		return (VacuumEnvironment.LocationState) getAttribute(ATTRIBUTE_STATE);
	}

	public Boolean isChockDetected() {
		return (Boolean) getAttribute(IS_CHOCK_DETECTED);
	}

	/**
	 * Return string representation of this percept.
	 *
	 * @return a string representation of this percept.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(getAgentLocation());
		sb.append(", ");
		sb.append(getLocationState());
		sb.append("]");
		return sb.toString();
	}
}
