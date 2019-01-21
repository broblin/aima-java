package aima.core.environment.eightpuzzle;

import java.util.List;
import java.util.Optional;

/**
 * recuit simulé
 * Created by benoit on 16/01/2019.
 */
public class SimulatedAnnealingAlgorithm {

    int parameter;

    public SimulatedAnnealingAlgorithm(int parameter) {
        this.parameter = parameter;
    }

    /**
     *
     * @param customEightPuzzleModel
     * @return
     */
    CustomEightPuzzleModel goNextStep(CustomEightPuzzleModel customEightPuzzleModel){
        CustomEightPuzzleModel lastNotNullEightPuzzleModel=null;
        Optional<CustomEightPuzzleModel> currentCustomEightPuzzleModel = Optional.ofNullable(customEightPuzzleModel);
        int nbLoop = 0;
        int temperature;

        while(currentCustomEightPuzzleModel.isPresent() && !currentCustomEightPuzzleModel.get().isSolutionFound()){
            System.out.println(String.format("item: %d %s",nbLoop,currentCustomEightPuzzleModel.get().gameArea.toString()));
            temperature = schema(nbLoop);

            lastNotNullEightPuzzleModel =  currentCustomEightPuzzleModel.get();

            Optional<CustomEightPuzzleModel>  candidateNextCurrentCustomEightPuzzleModel = chooseNextCustomEightPuzzleModel(currentCustomEightPuzzleModel);
            int delta = this.calcDelta(currentCustomEightPuzzleModel,candidateNextCurrentCustomEightPuzzleModel);
            if(delta > 0 || randomOK(delta,temperature)){
                currentCustomEightPuzzleModel = candidateNextCurrentCustomEightPuzzleModel;
            }

            nbLoop++;
        }

        if(!currentCustomEightPuzzleModel.isPresent()){
            System.out.println(String.format("pas de solution trouvée en %d iterations, dernier item: %s",nbLoop,lastNotNullEightPuzzleModel.gameArea.toString()));
            return null;
        }
        return currentCustomEightPuzzleModel.get();
    }

    private int schema(int t){
        return t >= parameter ? 1 : parameter - t;
    }


    private Optional<CustomEightPuzzleModel> chooseNextCustomEightPuzzleModel(Optional<CustomEightPuzzleModel> current){
        List<GameArea> candidates = current.get().findNextPiecesPosition();
        int chosenIndex = Long.valueOf(Math.round(candidates.size() * Math.random())).intValue();
        GameArea chosenGameArea = candidates.get(chosenIndex);
        return Optional.of(new CustomEightPuzzleModel(current.get(),chosenGameArea));
    }

    private int calcDelta(Optional<CustomEightPuzzleModel> currentCustomEightPuzzleModel,Optional<CustomEightPuzzleModel> nextCustomEightPuzzleModel){
        return currentCustomEightPuzzleModel.get().heuristicFunction() - nextCustomEightPuzzleModel.get().heuristicFunction();
    }

    private boolean randomOK(int delta,int temperature){
        //formula : e^delta/temperature
        double formula =  Math.exp(delta/temperature);
        return Math.random() < formula;
    }
}
