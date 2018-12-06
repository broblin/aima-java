package aima.core.environment.eightpuzzle;

import aima.core.environment.vacuum.Coord;

import java.util.ArrayList;
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
        int dim = 3;
        /**
         *  7,2,4     ,8,4
         *  5, ,6 ou 7,6,3
         *  8,3,1    1,2,5
         *
         *  solution:
         *   ,1,2
         *  3,4,5
         *  6,7,8
         */
        Coord[] initialPosition = new Coord[dim*dim-1];
        /*initialPosition[0] = new Coord(3,3);
        initialPosition[1] = new Coord(2,1);
        initialPosition[2] = new Coord(2,3);
        initialPosition[3] = new Coord(3,1);
        initialPosition[4] = new Coord(1,2);
        initialPosition[5] = new Coord(3,2);
        initialPosition[6] = new Coord(1,1);
        initialPosition[7] = new Coord(1,3);*/

        initialPosition[0] = new Coord(1,2);
        initialPosition[1] = new Coord(2,3);
        initialPosition[2] = new Coord(3,2);
        initialPosition[3] = new Coord(3,1);
        initialPosition[4] = new Coord(1,2);
        initialPosition[5] = new Coord(3,2);
        initialPosition[6] = new Coord(1,1);
        initialPosition[7] = new Coord(1,3);

        CustomEightPuzzleModel customEightPuzzleModel = new CustomEightPuzzleModel(3);
        customEightPuzzleModel.initializePiecesPosition(initialPosition);
        CustomEightPuzzleAlgorithm customEightPuzzleAlgorithm = new CustomEightPuzzleAlgorithm();
        CustomEightPuzzleModel solution = customEightPuzzleAlgorithm.goNextStep(customEightPuzzleModel);

        CustomEightPuzzleModel step = solution;
        List<CustomEightPuzzleModel> steps = new ArrayList<>();
        while(step != null){
            steps.add(step);
            step = step.previousState;
        }
        for(int i=steps.size()-1;i>=0;i--){
            System.out.println("etape: "+steps.get(i).toString());
        }

    }
}
