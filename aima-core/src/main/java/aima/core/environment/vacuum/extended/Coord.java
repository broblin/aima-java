package aima.core.environment.vacuum.extended;

/**
 *
 * Created by benoit on 14/07/2018.
 */
public class Coord {
    int x;
    int y;

    /**
     * dans l'exercice, l'aspirateur ne peut pas aller en diagonale
     * @param otherx
     * @param othery
     * @return
     */
    int evaluateDistance(int otherx, int othery){
        return Math.abs(x-otherx) + Math.abs(y - othery);
    }
}
