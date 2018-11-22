package aima.core.environment.eightpuzzle;

import aima.core.environment.vacuum.Coord;

import java.util.List;
import java.util.Optional;

/**
 * with euristic-function
 * Created by benoit on 22/11/2018.
 */
public class CustomEightPuzzleAlgorithm {

    CustomEightPuzzleModel goNextStep(CustomEightPuzzleModel customEightPuzzleModel){
        List<GameArea> nextGameArea = customEightPuzzleModel.findNextPiecesPosition();
        Optional<GameArea> bestGameArea = nextGameArea.stream()
                .min((gameArea1, gameArea2) -> customEightPuzzleModel.heuristicFunction(gameArea1.piecesPosition) - customEightPuzzleModel.heuristicFunction(gameArea2.piecesPosition));
        CustomEightPuzzleModel nextCustomEightPuzzleModel = new CustomEightPuzzleModel(customEightPuzzleModel,bestGameArea.get());
        if(nextCustomEightPuzzleModel.isSolutionFound()){
            return nextCustomEightPuzzleModel;
        }else{
            return goNextStep(nextCustomEightPuzzleModel);
        }

    }

    public static void main(String[] args){
        //TODO : données initials
        int dim = 3;
        Coord[] initialPosition = new Coord[dim*dim-1];
        initialPosition[0] = new Coord(3,3);
    }
}
