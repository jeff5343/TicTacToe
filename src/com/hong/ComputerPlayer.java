package com.hong;

public class ComputerPlayer {

    private GameBoard gameBoard;

    public ComputerPlayer(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void start() {
        int[] xy = gameBoard.getRandomUnoccupiedSpot();
        if(gameBoard.getSpotOccupied(xy[0],xy[1])) {
            start();
            return;
        }
        gameBoard.changeSpot(xy[0], xy[1], SpotState.X);

        GameController.gameController.startNextTurn();
    }

}
