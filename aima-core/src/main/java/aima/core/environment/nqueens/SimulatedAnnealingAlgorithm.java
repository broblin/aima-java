package aima.core.environment.nqueens;

import java.util.List;
import java.util.Optional;

/**
 * Created by benoit on 18/02/2019.
 */
public class SimulatedAnnealingAlgorithm {
    int parameter;

    public SimulatedAnnealingAlgorithm(int parameter) {
        this.parameter = parameter;
    }

    /**
     *
     * @param gameArea
     * @return
     */
    GameArea goNextStep(GameArea gameArea){
        GameArea lastNotNullGameArea=null;
        Optional<GameArea> currentGameArea = Optional.ofNullable(gameArea);
        int nbLoop = 0;
        int temperature;

        while(currentGameArea.isPresent() && !currentGameArea.get().isSolutionFound()){
            System.out.println(String.format("item: %d %s",nbLoop,currentGameArea.get().toStringInChessCoordLabel()));
            temperature = schema(nbLoop);

            lastNotNullGameArea =  currentGameArea.get();

            Optional<GameArea>  candidateNextCurrentGameArea = chooseNextGameArea(currentGameArea);
            int delta = this.calcDelta(currentGameArea,candidateNextCurrentGameArea);
            if(delta > 0 || randomOK(delta,temperature)){
                currentGameArea = candidateNextCurrentGameArea;
            }

            nbLoop++;
        }

        if(!currentGameArea.isPresent()){
            System.out.println(String.format("pas de solution trouvée en %d iterations, dernier item: %s",nbLoop,lastNotNullGameArea.toStringInChessCoordLabel()));
            return null;
        }
        return currentGameArea.get();
    }

    private int schema(int t){
        return t >= parameter ? 1 : parameter - t;
    }


    private Optional<GameArea> chooseNextGameArea(Optional<GameArea> current){
        List<GameArea> candidates = current.get().findNextPiecesPosition();
        double randomValue = Math.random();
        int chosenIndex = Double.valueOf(Math.floor(candidates.size() * randomValue)).intValue();
        GameArea chosenGameArea = candidates.get(chosenIndex);
        return Optional.of(chosenGameArea);
    }

    private int calcDelta(Optional<GameArea> currentGameArea,Optional<GameArea> nextGameArea){
        return currentGameArea.get().heuristicFunction() - nextGameArea.get().heuristicFunction();
    }

    private boolean randomOK(int delta,int temperature){
        //formula : e^delta/temperature
        double formula =  Math.exp(delta/temperature);
        return Math.random() < formula;
    }
}
