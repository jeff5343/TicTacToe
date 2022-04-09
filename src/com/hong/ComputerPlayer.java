package com.hong;

public class ComputerPlayer {

    private GameBoard gameBoard;

    public ComputerPlayer(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void start() {
        int[] blockXy = gameBoard.getBlockSpotCoordinate();
        int[] xy = gameBoard.getRandomUnoccupiedSpot();

        if(blockXy != null) {
            gameBoard.changeSpot(blockXy[0], blockXy[1], SpotState.X);
        } else if(gameBoard.getSpotOccupied(xy[0],xy[1])) {
            start();
            return;
        } else {
            gameBoard.changeSpot(xy[0], xy[1], SpotState.X);
        }

        GameController.gameController.startNextTurn();
    }

}
