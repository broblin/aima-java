package aima.core.environment.nqueens;

import java.time.LocalDateTime;

/**
 * Created by benoit on 11/03/2019.
 */
public class NQueensAlgorithmLauncher {

    static final int HILL_CLIMBING_BY_FIRST_CHOICE=0;
    static final int HILL_CLIMBING_BY_RANDOM_CHOICE=1;
    static final int SIMULATED_ANNEALING=2;
    static final int SIMULATED_ANNEALING_PARAMETER = 50;

    public static void main(String[] args){
        int chosenAlgorithm = HILL_CLIMBING_BY_RANDOM_CHOICE;
        GameArea gameArea = new GameArea();
        gameArea.generateState();
        GameArea solution=null;
        long startTime = System.currentTimeMillis();
        System.out.println(LocalDateTime.now());
        switch(chosenAlgorithm){
            case HILL_CLIMBING_BY_FIRST_CHOICE:
                HillClimbingByFirstChoiceAlgorithm algorithm = new HillClimbingByFirstChoiceAlgorithm();
                solution = algorithm.goNextStepWithHillClimbing(gameArea);
                break;
            case HILL_CLIMBING_BY_RANDOM_CHOICE:
                HillClimbingByRandomChoiceAlgorithm algorithm1 = new HillClimbingByRandomChoiceAlgorithm();
                solution = algorithm1.goNextStepWithHillClimbing(gameArea);
                break;
            case SIMULATED_ANNEALING:
                SimulatedAnnealingAlgorithm algorithm2 = new SimulatedAnnealingAlgorithm(SIMULATED_ANNEALING_PARAMETER);
                solution = algorithm2.goNextStep(gameArea);

        }
        long stopTime = System.currentTimeMillis();

        if(solution != null) System.out.println(String.format("solution trouvée : %s",solution.toStringInChessCoordLabel()));
        System.out.println(String.format("temps passé en ms : %d",stopTime-startTime));
    }
}
