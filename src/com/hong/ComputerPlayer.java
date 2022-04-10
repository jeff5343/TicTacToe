package com.hong;

public class ComputerPlayer {

    private final GameBoard gameBoard;

    public ComputerPlayer(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void start() {
        int[] scoreXy = gameBoard.getBlockSpotCoordinate(Constants.computerSpot);
        int[] blockXy = gameBoard.getBlockSpotCoordinate(Constants.playerSpot);
        int[] nextXy = gameBoard.getNextSpotCoordinate(Constants.computerSpot);
        int[] xy = gameBoard.getRandomSpot();

        if (scoreXy != null) {
            gameBoard.changeSpot(scoreXy[0], scoreXy[1], Constants.computerSpot);
        } else if (blockXy != null) {
            gameBoard.changeSpot(blockXy[0], blockXy[1], Constants.computerSpot);
        } else if (nextXy != null) {
            gameBoard.changeSpot(nextXy[0], nextXy[1], Constants.computerSpot);
        } else {
            if(gameBoard.getSpotOccupied(xy[0], xy[1])) {
                start();
                return;
            }
            gameBoard.changeSpot(xy[0], xy[1], Constants.computerSpot);
        }

        GameController.gameController.startNextTurn();
    }

}
