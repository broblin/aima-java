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
        Optional<CustomEightPuzzleModel> nextCustomEightPuzzleModel = Optional.ofNullable(customEightPuzzleModel);
        int nbLoop = 0;
        int temperature=0;

        while(nextCustomEightPuzzleModel.isPresent() && !nextCustomEightPuzzleModel.get().isSolutionFound()){
            System.out.println(String.format("item: %d %s",nbLoop,nextCustomEightPuzzleModel.get().gameArea.toString()));
            temperature = schema(nbLoop);

            lastNotNullEightPuzzleModel =  nextCustomEightPuzzleModel.get();

            Optional<GameArea> bestGameArea = nextCustomEightPuzzleModel.get().findNextPiecesPosition().stream()
                    .min((gameArea1, gameArea2) -> customEightPuzzleModel.heuristicFunction(gameArea1.piecesPosition) - customEightPuzzleModel.heuristicFunction(gameArea2.piecesPosition));
            nextCustomEightPuzzleModel = Optional.of(new CustomEightPuzzleModel(customEightPuzzleModel,bestGameArea.get()));
            nbLoop++;
        }

        if(!nextCustomEightPuzzleModel.isPresent()){
            System.out.println(String.format("pas de solution trouvée en %d iterations, dernier item: %s",nbLoop,lastNotNullEightPuzzleModel.gameArea.toString()));
            return null;
        }
        return nextCustomEightPuzzleModel.get();
    }

    private int schema(int t){
        return t >= parameter ? 1 : parameter - t;
    }


    private Optional<CustomEightPuzzleModel> chooseNextCustomEightPuzzleModel(Optional<CustomEightPuzzleModel> current){
        //TODO choisir au hasard
        current.get().findNextPiecesPosition();
        return null;
    }
}
