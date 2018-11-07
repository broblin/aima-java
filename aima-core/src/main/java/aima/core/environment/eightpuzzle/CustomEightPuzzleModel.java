package aima.core.environment.eightpuzzle;

import aima.core.environment.vacuum.Coord;

/**
 * Created by benoit on 07/11/2018.
 */
public class CustomEightPuzzleModel {
    Coord[] piecesPosition;

    Coord[] solutions;

    public CustomEightPuzzleModel(int dim) {
        int size = dim*dim-1;
        this.piecesPosition = new Coord[size];
        this.solutions = new Coord[size];
        for (int i=0;i<size;i++){
           solutions[i] = findCoordSolutionFromPosition(i+1,dim);
        }
    }

    public void initializePiecesPosition(Coord[] piecesPosition){
        this.piecesPosition = piecesPosition;
    }

    public Coord findCoordSolutionFromPosition(int pos,int dim){
        int x = pos%dim;
        int y = pos/dim;
        return new Coord(x,y);
    }

    public int heuristicFunction(Coord[] nextPiecesPosition){
        if(nextPiecesPosition.length != solutions.length){
            //TODO : générer exception
        }
        int totalDistance=0;
        for (int i=0;i<solutions.length;i++){
            totalDistance += solutions[i].evaluateDistance(nextPiecesPosition[i].getX(),nextPiecesPosition[i].getY());
        }
        return totalDistance;
    }
}
