package com.hong;

import com.hong.Enums.LineType;

public class ComputerPlayer {

    private final GameBoard gameBoard;
    private LineType currentLineType;

    public ComputerPlayer(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void start() {
        int[] scoreXy = gameBoard.getBlockSpotCoordinate(Constants.computerSpot);
        int[] blockXy = gameBoard.getBlockSpotCoordinate(Constants.playerSpot);
        int[] nextXyWithLine = gameBoard.getNextSpotCoordinateWithLine(Constants.computerSpot, currentLineType);
        int[] nextXy = gameBoard.getNextSpotCoordinate(Constants.computerSpot, this);
        int[] xy = gameBoard.getRandomSpot();

        if (scoreXy != null) {
            gameBoard.changeSpot(scoreXy[0], scoreXy[1], Constants.computerSpot);
        } else if (blockXy != null) {
            gameBoard.changeSpot(blockXy[0], blockXy[1], Constants.computerSpot);
        } else if (nextXyWithLine != null) {
            gameBoard.changeSpot(nextXyWithLine[0], nextXyWithLine[1], Constants.computerSpot);
        } else if (nextXy != null) {
            gameBoard.changeSpot(nextXy[0], nextXy[1], Constants.computerSpot);
        } else {
            gameBoard.changeSpot(xy[0], xy[1], Constants.computerSpot);
        }

        GameController.gameController.startNextTurn();
    }

    public void setCurrentLineType(LineType type) {
        currentLineType = type;
    }

}
