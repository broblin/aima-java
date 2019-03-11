package aima.core.environment.nqueens;

import aima.core.environment.vacuum.Coord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Created by benoit on 18/02/2019.
 */
public class GameArea {
    public static int CHESS_SIZE = 8;
    Coord[] piecesPosition = new Coord[CHESS_SIZE];

    public GameArea(){

    }

    public void generateState(){
        for(int i=0;i<CHESS_SIZE;i++){
            piecesPosition[i] = generateCoord();
        }
    }

    Coord generateCoord(){
        Coord candidate;
        do{
            candidate = new Coord(generateIndex(),generateIndex());

        }while (alreadyOccupied(candidate));
        return candidate;
    }

    public boolean alreadyOccupied(Coord candidate){
        return Arrays.asList(piecesPosition).contains(candidate);
    }

    /**
     *
     * @return
     */
    int generateIndex(){
        double randomValue = Math.random();
        return Double.valueOf(Math.floor(CHESS_SIZE * randomValue)).intValue();
    }

    public String displayLabelX(int x){
        switch (x){
            case 7 : return "A";
            case 6 : return "B";
            case 5 : return "C";
            case 4 : return "D";
            case 3 : return "E";
            case 2 : return "F";
            case 1 : return "G";
            case 0 : return "H";
            default : return null;
        }
    }

    public String displayLabelY(int y){
        switch (y){
            case 7 : return "8";
            case 6 : return "7";
            case 5 : return "6";
            case 4 : return "5";
            case 3 : return "4";
            case 2 : return "3";
            case 1 : return "2";
            case 0 : return "1";
            default : return null;
        }
    }

    public String displayLabelCoord(Coord coord){
        return "Coord{" + displayLabelX(coord.getX()) + "," + displayLabelY(coord.getY()) + "}";
    }

    public boolean isSolutionFound(){
        return heuristicFunction() == 0;
    }

    public int heuristicFunction(){
        return calcInVertical() + calcInHorizontal() + calcInDiagonals();
    }

    public int calcInVertical(){
        return calcInLine((coords,pos) -> Arrays.stream(coords).filter(queen -> queen.getY() == pos).count());
    }

    public int calcInHorizontal(){
        return calcInLine((coords,pos) -> Arrays.stream(coords).filter(queen -> queen.getX() == pos).count());
    }

    public int calcInDiagonals(){
        int totalScore = 0;
        for(int i=0;i<CHESS_SIZE;i++){
            final Coord piece = piecesPosition[i];
            long score = Arrays.stream(piecesPosition).filter(queen -> Math.abs(queen.getX() - piece.getX()) == Math.abs(queen.getY() - piece.getY())).count();
            if(score != 1L){
                totalScore += score;
            }
        }
        return totalScore;
    }

    public int calcInLine(QueenScoreCalculator queenScoreCalculator){
        int totalScore = 0;
        for(int i=0;i<CHESS_SIZE;i++){
            final int pos = i;
            long score = queenScoreCalculator.calc(piecesPosition,pos);
            if(score != 1L){
                totalScore += score;
            }
        }
        return totalScore;
    }

    public List<GameArea> findNextPiecesPosition(){
        List<GameArea> results = new ArrayList<>();
        for(int i = 0;i< piecesPosition.length;i++){
            results.addAll(findAllNextPiecesPositionFromAQueen(i));
        }
        return results;
    }

    protected List<GameArea> findAllNextPiecesPositionFromAQueen(int index){
        List<GameArea> results = new ArrayList<>();
        for(int i=0;i<CHESS_SIZE;i++){
            for(int j=0;j<CHESS_SIZE;j++){
                Coord nextPiecePosition = new Coord(i,j);
                if(!alreadyOccupied(nextPiecePosition)){
                    Coord[] newCoords =  Arrays.copyOf(piecesPosition,piecesPosition.length);
                    newCoords[index] = nextPiecePosition;
                    GameArea gameArea = new GameArea();
                    gameArea.piecesPosition = newCoords;
                    results.add(gameArea);
                }
            }
        }
        return results;
    }

    @Override
    public String toString() {
        return "GameArea{" +
                "piecesPosition=" + Arrays.toString(piecesPosition) +
                '}';
    }

    public String toStringInChessCoordLabel(){
        StringBuilder sb = new StringBuilder();
        sb.append("GameArea{");
        sb.append("piecesPosition=");
        Arrays.stream(piecesPosition).forEach(position -> sb.append(displayLabelCoord(position)));
        sb.append("}");
        return sb.toString();
    }
}
