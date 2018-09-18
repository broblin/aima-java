package aima.core.environment.CannibalsAndMissionaries;

import java.util.ArrayList;
import java.util.List;

import static aima.core.environment.CannibalsAndMissionaries.CannibalsAndMissionariesAction.*;

/**
 *
 * Created by benoit on 14/09/2018.
 */
public class CannibalsAndMissionariesModel {
    int nbCannibalsInRightRiver=0;
    int nbCannibalsInLeftRiver=3;
    int nbCannibalsInBoat=0;

    int nbMissionariesInRightRiver=0;
    int nbMissionariesInLeftRiver=3;
    int nbMissionariesInBoat=0;

    boolean boatOnTheLeft = true;

    CannibalsAndMissionariesModel previousState;

    public boolean isAForbiddenState(){
        boolean cannibalMajorityOnRightRiver = nbMissionariesInRightRiver < nbCannibalsInRightRiver;
        boolean cannibalMajorityOnLeftRiver = nbMissionariesInLeftRiver < nbCannibalsInLeftRiver;
        boolean tooManyPersonsOnBoat = nbCannibalsInBoat + nbMissionariesInBoat > 2;
        boolean negativeValue = nbCannibalsInRightRiver < 0 || nbCannibalsInLeftRiver < 0 || nbCannibalsInBoat <0 ||
                nbMissionariesInRightRiver < 0 || nbMissionariesInLeftRiver < 0 || nbMissionariesInBoat <0;
        return cannibalMajorityOnRightRiver || 
                cannibalMajorityOnLeftRiver ||
                tooManyPersonsOnBoat ||
                negativeValue;
    }

    public boolean isProblemSolved(){
        return nbCannibalsInRightRiver == 3 && nbMissionariesInRightRiver == 3;
    }
    
    public List<CannibalsAndMissionariesAction> findAllowedActionFromBoatPosition(){
        if(boatOnTheLeft){
            return new ArrayList<CannibalsAndMissionariesAction>(){{
                add(PUT_ONE_MISSIONARY_FROM_LEFT_RIVER_TO_BOAT);
                add(PUT_ONE_MISSIONARY_FROM_BOAT_TO_LEFT_RIVER);
                add(PUT_ONE_CANNIBAL_FROM_LEFT_RIVER_TO_BOAT);
                add(PUT_ONE_CANNIBAL_FROM_BOAT_TO_LEFT_RIVER);
                add(MOVE_BOAT);
            }};
        }else{
            return new ArrayList<CannibalsAndMissionariesAction>(){{
                add(PUT_ONE_MISSIONARY_FROM_RIGHT_RIVER_TO_BOAT);
                add(PUT_ONE_MISSIONARY_FROM_BOAT_TO_RIGHT_RIVER);
                add(PUT_ONE_CANNIBAL_FROM_RIGHT_RIVER_TO_BOAT);
                add(PUT_ONE_CANNIBAL_FROM_BOAT_TO_RIGHT_RIVER);
                add(MOVE_BOAT);
            }};
        }
    }
    
    public CannibalsAndMissionariesModel clone(){
        CannibalsAndMissionariesModel model = new CannibalsAndMissionariesModel();
        model.nbCannibalsInBoat = nbCannibalsInBoat;
        model.nbCannibalsInRightRiver = nbCannibalsInRightRiver;
        model.nbCannibalsInLeftRiver = nbCannibalsInLeftRiver;
        return model;
    }
    
    public CannibalsAndMissionariesModel generateNewModelFromAction(CannibalsAndMissionariesAction action){
        CannibalsAndMissionariesModel newModel = this.clone();
        newModel.previousState = this;
        switch (action){
            case PUT_ONE_CANNIBAL_FROM_BOAT_TO_RIGHT_RIVER:
                newModel.nbCannibalsInBoat--;
                newModel.nbCannibalsInRightRiver++;
                break;
            case PUT_ONE_CANNIBAL_FROM_BOAT_TO_LEFT_RIVER:
                newModel.nbCannibalsInBoat--;
                newModel.nbCannibalsInLeftRiver++;
                break;
            case PUT_ONE_CANNIBAL_FROM_RIGHT_RIVER_TO_BOAT:
                newModel.nbCannibalsInBoat++;
                newModel.nbCannibalsInRightRiver--;
                break;
            case PUT_ONE_CANNIBAL_FROM_LEFT_RIVER_TO_BOAT:
                newModel.nbCannibalsInBoat++;
                newModel.nbCannibalsInLeftRiver--;
                break;
            case PUT_ONE_MISSIONARY_FROM_BOAT_TO_RIGHT_RIVER:
                newModel.nbMissionariesInBoat--;
                newModel.nbMissionariesInRightRiver++;
                break;
            case PUT_ONE_MISSIONARY_FROM_BOAT_TO_LEFT_RIVER:
                newModel.nbMissionariesInBoat--;
                newModel.nbMissionariesInLeftRiver++;
                break;
            case PUT_ONE_MISSIONARY_FROM_RIGHT_RIVER_TO_BOAT:
                newModel.nbMissionariesInBoat++;
                newModel.nbMissionariesInRightRiver--;
                break;
            case PUT_ONE_MISSIONARY_FROM_LEFT_RIVER_TO_BOAT:
                newModel.nbMissionariesInBoat++;
                newModel.nbMissionariesInLeftRiver--;
                break;
            default:
                newModel.boatOnTheLeft = newModel.boatOnTheLeft ? false : true;
                break;
        }
        return newModel;
    }

    public int getNbCannibalsInRightRiver() {
        return nbCannibalsInRightRiver;
    }

    public void setNbCannibalsInRightRiver(int nbCannibalsInRightRiver) {
        this.nbCannibalsInRightRiver = nbCannibalsInRightRiver;
    }

    public int getNbCannibalsInLeftRiver() {
        return nbCannibalsInLeftRiver;
    }

    public void setNbCannibalsInLeftRiver(int nbCannibalsInLeftRiver) {
        this.nbCannibalsInLeftRiver = nbCannibalsInLeftRiver;
    }

    public int getNbCannibalsInBoat() {
        return nbCannibalsInBoat;
    }

    public void setNbCannibalsInBoat(int nbCannibalsInBoat) {
        this.nbCannibalsInBoat = nbCannibalsInBoat;
    }

    public int getNbMissionariesInRightRiver() {
        return nbMissionariesInRightRiver;
    }

    public void setNbMissionariesInRightRiver(int nbMissionariesInRightRiver) {
        this.nbMissionariesInRightRiver = nbMissionariesInRightRiver;
    }

    public int getNbMissionariesInLeftRiver() {
        return nbMissionariesInLeftRiver;
    }

    public void setNbMissionariesInLeftRiver(int nbMissionariesInLeftRiver) {
        this.nbMissionariesInLeftRiver = nbMissionariesInLeftRiver;
    }

    public int getNbMissionariesInBoat() {
        return nbMissionariesInBoat;
    }

    public void setNbMissionariesInBoat(int nbMissionariesInBoat) {
        this.nbMissionariesInBoat = nbMissionariesInBoat;
    }

    public boolean isBoatOnTheLeft() {
        return boatOnTheLeft;
    }

    public void setBoatOnTheLeft(boolean boatOnTheLeft) {
        this.boatOnTheLeft = boatOnTheLeft;
    }

    public CannibalsAndMissionariesModel getPreviousState() {
        return previousState;
    }

    public void setPreviousState(CannibalsAndMissionariesModel previousState) {
        this.previousState = previousState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CannibalsAndMissionariesModel that = (CannibalsAndMissionariesModel) o;

        if (nbCannibalsInRightRiver != that.nbCannibalsInRightRiver) return false;
        if (nbCannibalsInLeftRiver != that.nbCannibalsInLeftRiver) return false;
        if (nbCannibalsInBoat != that.nbCannibalsInBoat) return false;
        if (nbMissionariesInRightRiver != that.nbMissionariesInRightRiver) return false;
        if (nbMissionariesInLeftRiver != that.nbMissionariesInLeftRiver) return false;
        if (nbMissionariesInBoat != that.nbMissionariesInBoat) return false;
        return boatOnTheLeft == that.boatOnTheLeft;

    }

    @Override
    public int hashCode() {
        int result = nbCannibalsInRightRiver;
        result = 31 * result + nbCannibalsInLeftRiver;
        result = 31 * result + nbCannibalsInBoat;
        result = 31 * result + nbMissionariesInRightRiver;
        result = 31 * result + nbMissionariesInLeftRiver;
        result = 31 * result + nbMissionariesInBoat;
        result = 31 * result + (boatOnTheLeft ? 1 : 0);
        return result;
    }
}
