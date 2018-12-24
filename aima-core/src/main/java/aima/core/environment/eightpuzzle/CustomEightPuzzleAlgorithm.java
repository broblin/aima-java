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

    static int UN = 0;
    static int DEUX=1;
    static int TROIS=2;
    static int QUATRE=3;
    static int CINQ=4;
    static int SIX=5;
    static int SEPT=6;
    static int HUIT=7;

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
         *  7,2,4    1,4,2
         *  5, ,6 ou 3,5,8
         *  8,3,1    6,7,
         *
         *  solution:
         *   ,1,2
         *  3,4,5
         *  6,7,8
         */
        Coord[] initialPosition = new Coord[dim*dim-1];
        /*initialPosition[UN] = new Coord(3,3);
        initialPosition[DEUX] = new Coord(2,1);
        initialPosition[TROIS] = new Coord(2,3);
        initialPosition[QUATRE] = new Coord(3,1);
        initialPosition[CINQ] = new Coord(1,2);
        initialPosition[SIX] = new Coord(3,2);
        initialPosition[SEPT] = new Coord(1,1);
        initialPosition[HUIT] = new Coord(1,3);*/

        initialPosition[UN] = new Coord(1,1);
        initialPosition[DEUX] = new Coord(3,1);
        initialPosition[TROIS] = new Coord(1,2);
        initialPosition[QUATRE] = new Coord(2,1);
        initialPosition[CINQ] = new Coord(2,2);
        initialPosition[SIX] = new Coord(1,3);
        initialPosition[SEPT] = new Coord(2,3);
        initialPosition[HUIT] = new Coord(3,2);

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
            System.out.println("etape: "+steps.get(i).gameArea.emptyCase.toString());
        }

    }
}
