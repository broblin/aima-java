package aima.core.environment.eightpuzzle;

import aima.core.environment.vacuum.Coord;

import java.time.LocalDateTime;
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
    static int NEUF=8;
    static int DIX=9;
    static int ONZE=10;
    static int DOUZE=11;
    static int TREIZE=12;
    static int QUATORZE=13;
    static int QUINZE=14;

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
     * @param initialCost
     * @return
     */
    CustomEightPuzzleModel goNextStepWithAStar(CustomEightPuzzleModel customEightPuzzleModel,int initialCost){
        Optional<CustomEightPuzzleModel> bestCustomEightPuzzleModel = Optional.ofNullable(customEightPuzzleModel);
        int nbLoop = 0;

        while(bestCustomEightPuzzleModel.isPresent() && !bestCustomEightPuzzleModel.get().isSolutionFound()){
            nbLoop++;
            final CustomEightPuzzleModel currentCustomEightPuzzleModel = bestCustomEightPuzzleModel.get();
            Integer cost = frontierModels.containsKey(currentCustomEightPuzzleModel) ? frontierModels.remove(currentCustomEightPuzzleModel) : initialCost;
            List<GameArea> nextGameArea = currentCustomEightPuzzleModel.findNextPiecesPosition();
            cost++;
            final int nextCost = cost;

            Stream<GameArea> addToFrontierModel = nextGameArea.stream().filter(gameArea -> !modelsWithMinimumCost.containsKey(gameArea) || nextCost < modelsWithMinimumCost.get(gameArea));

            addToFrontierModel.forEach(gameArea -> {
                CustomEightPuzzleModel frontierEightPuzzleModel = new CustomEightPuzzleModel(currentCustomEightPuzzleModel,gameArea);
                modelsWithMinimumCost.put(gameArea,nextCost);
                frontierModels.put(frontierEightPuzzleModel,nextCost);
            });

            bestCustomEightPuzzleModel = frontierModels.keySet().stream()
                    .min((frontierModel1, frontierModel2) -> frontierModels.get(frontierModel1) + frontierModel1.heuristicFunction() -
                            (frontierModels.get(frontierModel2) + frontierModel2.heuristicFunction()));
        }

        System.out.println(String.format("nb itérations : %d taille modelsWithMinimumCost : %d taille frontierModels : %d ",nbLoop,modelsWithMinimumCost.size(),frontierModels.size()));

        if(bestCustomEightPuzzleModel.isPresent()){
            System.out.println(String.format("coût pour arriver à la solution : %d ",frontierModels.get(bestCustomEightPuzzleModel.get())));
            return bestCustomEightPuzzleModel.get();
        }else{
            System.out.println("pas de solution trouvée, dernier item: "+customEightPuzzleModel.gameArea.toString());
            return null;
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
        long startTime = System.currentTimeMillis();
        System.out.println(LocalDateTime.now());
        Coord[] initialPosition = new Coord[dim*dim-1];
        int position = 4;
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
        }else if(position == 3){
            initialPosition[UN] = new Coord(1,1);
            initialPosition[DEUX] = new Coord(2,1);
            initialPosition[TROIS] = new Coord(3,1);
            initialPosition[QUATRE] = new Coord(1,2);
            initialPosition[CINQ] = new Coord(2,2);
            initialPosition[SIX] = new Coord(3,2);
            initialPosition[SEPT] = new Coord(1,3);
            initialPosition[HUIT] = new Coord(2,3);
        }else{
            //dim 4
            dim=4;
            initialPosition = new Coord[dim*dim-1];
            initialPosition[UN] = new Coord(1,1);
            initialPosition[DEUX] = new Coord(2,1);
            initialPosition[TROIS] = new Coord(4,1);
            initialPosition[QUATRE] = new Coord(2,2);
            initialPosition[CINQ] = new Coord(1,2);
            initialPosition[SIX] = new Coord(4,2);
            initialPosition[SEPT] = new Coord(3,2);
            initialPosition[HUIT] = new Coord(2,3);
            initialPosition[NEUF] = new Coord(1,3);
            initialPosition[DIX] = new Coord(4,3);
            initialPosition[ONZE] = new Coord(3,3);
            initialPosition[DOUZE] = new Coord(1,4);
            initialPosition[TREIZE] = new Coord(2,4);
            initialPosition[QUATORZE] = new Coord(3,4);
            initialPosition[QUINZE] = new Coord(4,4);
        }

        CustomEightPuzzleModel customEightPuzzleModel = new CustomEightPuzzleModel(dim);
        customEightPuzzleModel.initializePiecesPosition(initialPosition);
        CustomEightPuzzleAlgorithm customEightPuzzleAlgorithm = new CustomEightPuzzleAlgorithm();

        CustomEightPuzzleModel solution = useAStarAlgorithm ? customEightPuzzleAlgorithm.goNextStepWithAStar(customEightPuzzleModel,0) : customEightPuzzleAlgorithm.goNextStepWithGreedyExploration(customEightPuzzleModel);

        long stopTime = System.currentTimeMillis();
        System.out.println(String.format("temps passé en ms : %d",stopTime-startTime));

        CustomEightPuzzleModel step = solution;
        List<CustomEightPuzzleModel> steps = new ArrayList<>();
        while(step != null){
            steps.add(step);
            step = step.previousState;
        }
        for(int i=steps.size()-1;i>=0;i--){
            System.out.println(String.format("etape %d: %s ",steps.size()-i-1,steps.get(i).gameArea.emptyCase.toString()));
        }

    }
}
