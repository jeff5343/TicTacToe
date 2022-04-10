package com.hong;

import java.util.Arrays;

public class ComputerPlayer {

    private GameBoard gameBoard;

    public ComputerPlayer(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void start() {
        int[] scoreXy = gameBoard.getBlockSpotCoordinate(SpotState.X);
        int[] blockXy = gameBoard.getBlockSpotCoordinate(SpotState.O);
        int[] xy = gameBoard.getRandomUnoccupiedSpot();

        System.out.println(Arrays.toString(blockXy));

        if(scoreXy != null) {
            gameBoard.changeSpot(scoreXy[0], scoreXy[1], SpotState.X);
        } else if(blockXy != null) {
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
