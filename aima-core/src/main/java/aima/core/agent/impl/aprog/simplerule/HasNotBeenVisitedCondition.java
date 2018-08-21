package aima.core.agent.impl.aprog.simplerule;

import aima.core.agent.Action;
import aima.core.agent.impl.ObjectWithDynamicAttributes;
import aima.core.environment.vacuum.Coord;
import aima.core.environment.vacuum.VacuumEnvironment;

import java.util.List;

/**
 * vérifie si la prochaine position cible d'une action donnée a été visité
 * Created by benoit on 19/08/2018.
 */
public class HasNotBeenVisitedCondition extends Condition {

    Object visitedLocationsKey;
    Object currentLocationKey;
    Action nextAction;

    public HasNotBeenVisitedCondition(Object visitedLocationsKey, Object currentLocationKey, Action nextAction) {
        this.visitedLocationsKey = visitedLocationsKey;
        this.currentLocationKey = currentLocationKey;
        this.nextAction = nextAction;
    }

    @Override
    public boolean evaluate(ObjectWithDynamicAttributes p) {
        Coord currentLocation = (Coord) p.getAttribute(currentLocationKey);
        return !((List<Coord>)p.getAttribute(visitedLocationsKey)).contains(findNextCoord(currentLocation));
    }

    protected Coord findNextCoord(Coord currentLocation){
        if(nextAction.equals(VacuumEnvironment.ACTION_MOVE_LEFT)){
            return new Coord(currentLocation.getX()-1,currentLocation.getY());
        }else if(nextAction.equals(VacuumEnvironment.ACTION_GO_DOWN)){
            return new Coord(currentLocation.getX(),currentLocation.getY()-1);
        }else if(nextAction.equals(VacuumEnvironment.ACTION_MOVE_RIGHT)){
            return new Coord(currentLocation.getX()+1,currentLocation.getY());
        }else if(nextAction.equals(VacuumEnvironment.ACTION_GO_UP)){
            return new Coord(currentLocation.getX(),currentLocation.getY()+1);
        }
        return null;
    }

    @Override
    public String toString() {
        return "HasNotBeenVisitedCondition{" +
                "visitedLocationsKey=" + visitedLocationsKey +
                ", currentLocationKey=" + currentLocationKey +
                ", nextAction=" + nextAction +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        HasNotBeenVisitedCondition that = (HasNotBeenVisitedCondition) o;

        if (visitedLocationsKey != null ? !visitedLocationsKey.equals(that.visitedLocationsKey) : that.visitedLocationsKey != null)
            return false;
        if (currentLocationKey != null ? !currentLocationKey.equals(that.currentLocationKey) : that.currentLocationKey != null)
            return false;
        return nextAction != null ? nextAction.equals(that.nextAction) : that.nextAction == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (visitedLocationsKey != null ? visitedLocationsKey.hashCode() : 0);
        result = 31 * result + (currentLocationKey != null ? currentLocationKey.hashCode() : 0);
        result = 31 * result + (nextAction != null ? nextAction.hashCode() : 0);
        return result;
    }
}
