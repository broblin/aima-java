package aima.core.environment.eightpuzzle;

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
        int temperature=0;

        while(currentCustomEightPuzzleModel.isPresent() && !currentCustomEightPuzzleModel.get().isSolutionFound()){
            System.out.println(String.format("item: %d %s",nbLoop,currentCustomEightPuzzleModel.get().gameArea.toString()));
            //Optional<CustomEightPuzzleModel> currentCustomEightPuzzleModel = currentCustomEightPuzzleModel;
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
        //TODO choisir au hasard
        current.get().findNextPiecesPosition();
        /*Optional<GameArea> bestGameArea = nextCustomEightPuzzleModel.get().findNextPiecesPosition().stream()
                    .min((gameArea1, gameArea2) -> customEightPuzzleModel.heuristicFunction(gameArea1.piecesPosition) - customEightPuzzleModel.heuristicFunction(gameArea2.piecesPosition));
        nextCustomEightPuzzleModel = Optional.of(new CustomEightPuzzleModel(customEightPuzzleModel,bestGameArea.get()));
        */
        return null;
    }

    private int calcDelta(Optional<CustomEightPuzzleModel> currentCustomEightPuzzleModel,Optional<CustomEightPuzzleModel> nextCustomEightPuzzleModel){
        //TODO
        return -1;
    }

    private boolean randomOK(int delta,int temperature){
        //TODO
        //formula : e^delta/temperature
        return false;
    }
}
