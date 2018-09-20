package aima.core.environment.CannibalsAndMissionaries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by benoit on 14/09/2018.
 */
public class ResolutionWithUniformCostAlgorithm {

    Map<CannibalsAndMissionariesModel,Integer> modelsWithMinimumCost = new HashMap<>();

    List<CannibalsAndMissionariesModel> problemSolvedList = new ArrayList<>();

    public List<CannibalsAndMissionariesModel> goNextStep(List<CannibalsAndMissionariesModel> frontierModels){
        List<CannibalsAndMissionariesModel> nextStepModels = new ArrayList<>();
        frontierModels.forEach(model -> {
            nextStepModels.addAll(goNextStepFromFrontier(model));
        });

        if(!nextStepModels.isEmpty()){
           goNextStep(nextStepModels);
        }
        return nextStepModels;
    }

    public List<CannibalsAndMissionariesModel> goNextStepFromFrontier(CannibalsAndMissionariesModel model){
        List<CannibalsAndMissionariesModel> nextStepModels = new ArrayList<>();

        List<CannibalsAndMissionariesAction> allowedActionsFromBoatPosition = model.findAllowedActionFromBoatPosition();

        allowedActionsFromBoatPosition.stream().forEach( action -> {
            CannibalsAndMissionariesModel nextModel = model.generateNewModelFromAction(action);
            Integer previousCost = modelsWithMinimumCost.containsKey(nextModel.getPreviousState()) ? modelsWithMinimumCost.get(nextModel.getPreviousState()) : 0;
            if(!nextModel.isAForbiddenState()){
                if(modelsWithMinimumCost.containsKey(nextModel)  && isABetterWay(nextModel,previousCost)){
                    removePreviousWay(nextModel);
                    modelsWithMinimumCost.put(nextModel,previousCost+1) ;
                    if(nextModel.isProblemSolved()){
                        problemSolvedList.add(nextModel);
                    }else {
                        nextStepModels.add(nextModel);
                    }
                } else if(!modelsWithMinimumCost.containsKey(nextModel)){
                    modelsWithMinimumCost.put(nextModel,previousCost+1) ;
                    nextStepModels.add(nextModel);
                }
            }

        });
        return nextStepModels;
    }


    public boolean isABetterWay(CannibalsAndMissionariesModel nextModel,Integer previousCost){
        return previousCost < modelsWithMinimumCost.get(nextModel);
    }

    public void removePreviousWay(CannibalsAndMissionariesModel model){
        modelsWithMinimumCost.remove(model);
    }

    public static void main(String[] args){
        List<CannibalsAndMissionariesModel> initModels = new ArrayList<>();
        initModels.add(new CannibalsAndMissionariesModel());

        ResolutionWithUniformCostAlgorithm algorithm = new ResolutionWithUniformCostAlgorithm();
        algorithm.goNextStep(initModels);
        algorithm.problemSolvedList.forEach(model -> {
            System.out.println("Solution: "+model.generateWay().toString());
        });
    }

}
