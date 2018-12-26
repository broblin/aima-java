package aima.core.environment.eightpuzzle;

import aima.core.environment.vacuum.Coord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by benoit on 07/11/2018.
 */
public class CustomEightPuzzleModel {

    Coord[] solutions;

    GameArea gameArea = new GameArea();
    
    int dim;

    CustomEightPuzzleModel previousState;

    public CustomEightPuzzleModel(int dim) {
        this.dim = dim;
        int size = dim*dim-1;
        this.solutions = new Coord[size];
        for (int i=0;i<size;i++){
           solutions[i] = findCoordSolutionFromPosition(i+1,dim);
        }
    }

    public CustomEightPuzzleModel(CustomEightPuzzleModel previousState,GameArea gameArea) {
        this.dim = previousState.getDim();
        this.solutions = previousState.getSolutions();
        this.previousState=previousState;
        this.gameArea = gameArea;
    }

    public void initializePiecesPosition(Coord[] piecesPosition){
        this.gameArea.piecesPosition = piecesPosition;
        List<Coord> allCaseList = findAllCaseList();
        Stream<Coord> streamPiecesPosition = Arrays.asList(piecesPosition).stream();
        //TODO : piecesPosition ne doit pas avoir de doublon
        if(piecesPosition.length != solutions.length){
            throw new RuntimeException("piecesPosition n'a pas la bonne longueur");
        }
        boolean isAllCoordOnCaseList = streamPiecesPosition.allMatch(coord -> allCaseList.contains(coord));
        
        if(!isAllCoordOnCaseList){
            throw new RuntimeException("piecesPosition a des coordonnées en dehors de l'aire de jeu");
        }
        this.gameArea.emptyCase = findEmptyCaseFromAllCaseList(allCaseList);
        
    }
    
    private List<Coord> findAllCaseList(){
        List<Coord> allCaseList = new ArrayList();
        for(int i=1;i<=dim;i++){
            for(int j=1;j<=dim;j++){
                allCaseList.add(new Coord(i,j));
            }
        }
        
        return allCaseList;
    }
    
    private Coord findEmptyCaseFromAllCaseList(List<Coord> allCaseList){
        for (int i=0;i<gameArea.piecesPosition.length;i++){
            Coord occupiedCase = gameArea.piecesPosition[i];
            allCaseList.remove(occupiedCase);
        }
        return allCaseList.get(0);
    }

    private Coord findCoordSolutionFromPosition(int pos,int dim){
        int x = pos%dim+1;
        int y = pos/dim+1;
        return new Coord(x,y);
    }
    
    public List<GameArea> findNextPiecesPosition(){
        List<GameArea> resultList = new ArrayList<>();
        Coord upperEmptyCase = findUpperEmptyCase();
        Coord bottomEmptyCase = findBottomEmptyCase();
        Coord leftEmptyCase = findLeftEmptyCase();
        Coord rightEmptyCase = findRightEmptyCase();
        if(upperEmptyCase != null) resultList.add(replaceEmptyCaseByPiecePosition(upperEmptyCase));
        if(bottomEmptyCase != null) resultList.add(replaceEmptyCaseByPiecePosition(bottomEmptyCase));
        if(leftEmptyCase != null) resultList.add(replaceEmptyCaseByPiecePosition(leftEmptyCase));
        if(rightEmptyCase != null) resultList.add(replaceEmptyCaseByPiecePosition(rightEmptyCase));
        
        return resultList;
    }

    public int heuristicFunction(Coord[] nextPiecesPosition){
        if(nextPiecesPosition.length != solutions.length){
            throw new RuntimeException("nextPiecesPosition n'a pas la bonne longueur");
        }
        int totalDistance=0;
        for (int i=0;i<solutions.length;i++){
            totalDistance += solutions[i].evaluateDistance(nextPiecesPosition[i].getX(),nextPiecesPosition[i].getY());
        }
        return totalDistance;
    }

    public int heuristicFunction(){
        return heuristicFunction(gameArea.piecesPosition);
    }
    
    public boolean isSolutionFound(){
        return Arrays.equals(gameArea.piecesPosition,solutions);
    }
    
    private GameArea replaceEmptyCaseByPiecePosition(Coord coord){
        Coord[] nextPiecesPosition = this.gameArea.piecesPosition.clone();
        GameArea nextGameArea = new GameArea();
        for (int i=0;i<this.gameArea.piecesPosition.length;i++){
            if(nextPiecesPosition[i].equals(coord)){
                nextGameArea.emptyCase = nextPiecesPosition[i];
                nextPiecesPosition[i] = gameArea.emptyCase;
                break;
            }
        }
        nextGameArea.piecesPosition = nextPiecesPosition;
        return nextGameArea;
    }
    
    private Coord findBottomEmptyCase(){
        if(gameArea.emptyCase.getY() > 1){
            return new Coord(gameArea.emptyCase.getX(),gameArea.emptyCase.getY()-1);
        }else{
            return null;
        }
    }

    private Coord findUpperEmptyCase(){
        if(gameArea.emptyCase.getY() < dim){
            return new Coord(gameArea.emptyCase.getX(),gameArea.emptyCase.getY()+1);
        }else{
            return null;
        }
    }

    private Coord findLeftEmptyCase(){
        if(gameArea.emptyCase.getX() > 1){
            return new Coord(gameArea.emptyCase.getX()-1,gameArea.emptyCase.getY());
        }else{
            return null;
        }
    }

    private Coord findRightEmptyCase(){
        if(gameArea.emptyCase.getX() < dim){
            return new Coord(gameArea.emptyCase.getX()+1,gameArea.emptyCase.getY());
        }else{
            return null;
        }
    }

    public Coord[] getSolutions() {
        return solutions;
    }

    public int getDim() {
        return dim;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomEightPuzzleModel that = (CustomEightPuzzleModel) o;

        if (dim != that.dim) return false;
        return gameArea != null ? gameArea.equals(that.gameArea) : that.gameArea == null;

    }

    @Override
    public int hashCode() {
        int result = gameArea != null ? gameArea.hashCode() : 0;
        result = 31 * result + dim;
        return result;
    }
}
