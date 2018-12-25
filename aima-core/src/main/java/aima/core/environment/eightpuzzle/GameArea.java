package aima.core.environment.eightpuzzle;

import aima.core.environment.vacuum.Coord;

import java.util.Arrays;

/**
 * Created by benoit on 22/11/2018.
 */
public class GameArea {
    Coord[] piecesPosition;
    Coord emptyCase;

    public Coord[] getPiecesPosition() {
        return piecesPosition;
    }

    public void setPiecesPosition(Coord[] piecesPosition) {
        this.piecesPosition = piecesPosition;
    }

    public Coord getEmptyCase() {
        return emptyCase;
    }

    public void setEmptyCase(Coord emptyCase) {
        this.emptyCase = emptyCase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameArea gameArea = (GameArea) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(piecesPosition, gameArea.piecesPosition)) return false;
        return emptyCase.equals(gameArea.emptyCase);

    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(piecesPosition);
        result = 31 * result + emptyCase.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GameArea{" +
                "piecesPosition=" + Arrays.toString(piecesPosition) +
                ", emptyCase=" + emptyCase +
                '}';
    }
}
