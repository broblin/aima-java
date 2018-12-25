package aima.core.environment.eightpuzzle;

import aima.core.environment.vacuum.Coord;

import java.util.*;
import java.util.stream.Stream;

/**
 * exploration gloutonne par le meilleur d'abord : incomplète pour le troisième exemple
 * puis algorithme A etoile
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

    Map<GameArea,Integer> modelsWithMinimumCost = new HashMap<>();
    Map<CustomEightPuzzleModel,Integer> frontierModels = new HashMap<>();

    List<GameArea> greedyExplorationPreviousPositions = new ArrayList<>();

    /**
     *
     * @param customEightPuzzleModel
     * @return
     */
    CustomEightPuzzleModel goNextStepWithGreedyExploration(CustomEightPuzzleModel customEightPuzzleModel){
        Stream<GameArea> nextGameArea = customEightPuzzleModel.findNextPiecesPosition().stream().filter(gameArea -> !greedyExplorationPreviousPositions.contains(gameArea));
        Optional<GameArea> bestGameArea = nextGameArea
                .min((gameArea1, gameArea2) -> customEightPuzzleModel.heuristicFunction(gameArea1.piecesPosition) - customEightPuzzleModel.heuristicFunction(gameArea2.piecesPosition));
        if(!bestGameArea.isPresent()){
            System.out.println("pas de solution trouvée, dernier item: "+customEightPuzzleModel.gameArea.toString());
            return null;
        }
        CustomEightPuzzleModel nextCustomEightPuzzleModel = new CustomEightPuzzleModel(customEightPuzzleModel,bestGameArea.get());
        greedyExplorationPreviousPositions.add(bestGameArea.get());
        if(nextCustomEightPuzzleModel.isSolutionFound()){
            return nextCustomEightPuzzleModel;
        }else{
            return goNextStepWithGreedyExploration(nextCustomEightPuzzleModel);
        }

    }

    /**
     *
     * @param customEightPuzzleModel
     * @param cost
     * @return
     */
    CustomEightPuzzleModel goNextStepWithAStar(CustomEightPuzzleModel customEightPuzzleModel,int cost){
        frontierModels.remove(customEightPuzzleModel);
        List<GameArea> nextGameArea = customEightPuzzleModel.findNextPiecesPosition();
        int nextCost = cost++;

        Stream<GameArea> addToFrontierModel = nextGameArea.stream().filter(gameArea -> !modelsWithMinimumCost.containsKey(gameArea) || nextCost < modelsWithMinimumCost.get(gameArea));

        addToFrontierModel.forEach(gameArea -> {
            CustomEightPuzzleModel frontierEightPuzzleModel = new CustomEightPuzzleModel(customEightPuzzleModel,gameArea);
            modelsWithMinimumCost.put(gameArea,nextCost);
            frontierModels.put(frontierEightPuzzleModel,nextCost);
        });


        Optional<CustomEightPuzzleModel> bestCustomEightPuzzleModel = frontierModels.keySet().stream()
                .min((frontierModel1, frontierModel2) -> frontierModels.get(frontierModel1) + customEightPuzzleModel.heuristicFunction(frontierModel1.gameArea.piecesPosition) -
                        (frontierModels.get(frontierModel2) + customEightPuzzleModel.heuristicFunction(frontierModel2.gameArea.piecesPosition)));
        if(!bestCustomEightPuzzleModel.isPresent()){
            System.out.println("pas de solution trouvée, dernier item: "+customEightPuzzleModel.gameArea.toString());
            return null;
        }
        //System.out.println("etape: "+nextCustomEightPuzzleModel.gameArea.emptyCase.toString());
        if(bestCustomEightPuzzleModel.get().isSolutionFound()){
            return bestCustomEightPuzzleModel.get();
        }else{
            return goNextStepWithAStar(bestCustomEightPuzzleModel.get(),nextCost);
        }
    }

    public static void main(String[] args){
        int dim = 3;
        /**
         *  7,2,4    1,4,2     1,2,3
         *  5, ,6 ou 3,5,8 ou  4,5,6
         *  8,3,1    6,7,      7,8
         *
         *  solution:
         *   ,1,2
         *  3,4,5
         *  6,7,8
         */
        Coord[] initialPosition = new Coord[dim*dim-1];
        int position = 1;
        boolean useAStarAlgorithm = true;

        if(position == 1){
            initialPosition[UN] = new Coord(3,3);
            initialPosition[DEUX] = new Coord(2,1);
            initialPosition[TROIS] = new Coord(2,3);
            initialPosition[QUATRE] = new Coord(3,1);
            initialPosition[CINQ] = new Coord(1,2);
            initialPosition[SIX] = new Coord(3,2);
            initialPosition[SEPT] = new Coord(1,1);
            initialPosition[HUIT] = new Coord(1,3);
        } else if(position == 2){
            initialPosition[UN] = new Coord(1,1);
            initialPosition[DEUX] = new Coord(3,1);
            initialPosition[TROIS] = new Coord(1,2);
            initialPosition[QUATRE] = new Coord(2,1);
            initialPosition[CINQ] = new Coord(2,2);
            initialPosition[SIX] = new Coord(1,3);
            initialPosition[SEPT] = new Coord(2,3);
            initialPosition[HUIT] = new Coord(3,2);
        }else{
            initialPosition[UN] = new Coord(1,1);
            initialPosition[DEUX] = new Coord(2,1);
            initialPosition[TROIS] = new Coord(3,1);
            initialPosition[QUATRE] = new Coord(1,2);
            initialPosition[CINQ] = new Coord(2,2);
            initialPosition[SIX] = new Coord(3,2);
            initialPosition[SEPT] = new Coord(1,3);
            initialPosition[HUIT] = new Coord(2,3);
        }

        CustomEightPuzzleModel customEightPuzzleModel = new CustomEightPuzzleModel(3);
        customEightPuzzleModel.initializePiecesPosition(initialPosition);
        CustomEightPuzzleAlgorithm customEightPuzzleAlgorithm = new CustomEightPuzzleAlgorithm();

        CustomEightPuzzleModel solution = useAStarAlgorithm ? customEightPuzzleAlgorithm.goNextStepWithAStar(customEightPuzzleModel,0) : customEightPuzzleAlgorithm.goNextStepWithGreedyExploration(customEightPuzzleModel);

        CustomEightPuzzleModel step = solution;
        List<CustomEightPuzzleModel> steps = new ArrayList<>();
        while(step != null){
            steps.add(step);
            step = step.previousState;
        }
        for(int i=steps.size()-1;i>=0;i--){
            System.out.println(String.format("etape %d: %s ",steps.size()-i,steps.get(i).gameArea.emptyCase.toString()));
        }

    }
}
