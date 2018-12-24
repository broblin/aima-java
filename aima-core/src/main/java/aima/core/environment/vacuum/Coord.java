package aima.core.environment.vacuum;

/**
 *
 * Created by benoit on 14/07/2018.
 */
public class Coord {
    int x;
    int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * dans l'exercice, l'aspirateur ne peut pas aller en diagonale
     * @param otherx
     * @param othery
     * @return
     */
    public int evaluateDistance(int otherx, int othery){
        return Math.abs(x-otherx) + Math.abs(y - othery);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coord coord = (Coord) o;

        if (x != coord.x) return false;
        return y == coord.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
