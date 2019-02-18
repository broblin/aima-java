package aima.core.environment.nqueens;

import java.util.Optional;

/**
 * escalade premier choix : recalcul d'un état et on continue jusqu'à ce qu'on en trouve un avec un meilleur score
 * Created by benoit on 18/02/2019.
 */
public class HillClimbingByFirstChoiceAlgorithm {

    GameArea goNextStepWithHillClimbing(GameArea gameArea){
        GameArea lastNotNullGameArea;
        Optional<GameArea> optionalGameArea = Optional.ofNullable(gameArea);
        int nbLoop = 0;

        while(optionalGameArea.isPresent() && !optionalGameArea.get().isSolutionFound()){
            System.out.println(String.format("item: %d %s",nbLoop,optionalGameArea.get().toStringInChessCoordLabel()));
            lastNotNullGameArea =  optionalGameArea.get();
            Optional<GameArea> bestGameArea = optionalGameArea.get().findNextPiecesPosition().stream()
                    .min((gameArea1, gameArea2) -> gameArea.heuristicFunction() - gameArea.heuristicFunction());
            nbLoop++;

            while(!bestGameArea.isPresent() || lastNotNullGameArea.heuristicFunction() < bestGameArea.get().heuristicFunction()){
                //System.out.println(String.format("pas de solution trouvée en %d iterations, dernier item: %s",nbLoop,lastNotNullGameArea.toStringInChessCoordLabel()));
                bestGameArea = Optional.of(new GameArea());
                bestGameArea.get().generateState();
                nbLoop++;
            }

            optionalGameArea = bestGameArea;
        }


        return optionalGameArea.get();
    }
}
