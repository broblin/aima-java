package aima.core.environment.nqueens;

import aima.core.environment.vacuum.Coord;

/**
 * Created by benoit on 18/02/2019.
 */
@FunctionalInterface
public interface QueenScoreCalculator {
    long calc(Coord[] coords,int pos);
}
