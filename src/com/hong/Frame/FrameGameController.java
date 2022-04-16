package com.hong.Frame;

import com.hong.ComputerPlayer;
import com.hong.Enums.SpotState;
import com.hong.Enums.TurnState;
import com.hong.GameBoard;
import com.hong.PlayerInput;

public class FrameGameController {

    public static FrameGameController frameGameController;
    private final GameBoard gameBoard = new GameBoard();
    private final ComputerPlayer computer = new ComputerPlayer(gameBoard);
    private final GameFrame gameFrame = new GameFrame(gameBoard);

    private TurnState turnState = TurnState.PLAYER;

    public void init() {
        frameGameController = this;

        gameBoard.init();
    }

    public void startNextTurn() {
        if(gameBoard.getAllSpotsOccupied()) {
            runTieAction();
            return;
        }
        SpotState winner = gameBoard.getMatchingSet();
        if(winner!=SpotState.NONE) {
            runWinnerAction(winner);
            return;
        }
        runTurnStateMachine();
    }

    private void runTieAction() {
        gameFrame.changeBottomLabel("Tie!");
        gameFrame.disableButtons();
    }

    private void runWinnerAction(SpotState winner) {
        gameFrame.changeBottomLabel(winner.value + "wins!");
        gameFrame.changeButtonTexts(gameBoard.getSpots());
        gameFrame.disableButtons();
    }

    private void runTurnStateMachine() {
        switch (turnState) {
            case PLAYER:
                turnState = TurnState.COMPUTER;
                computer.start();
                break;
            case COMPUTER:
                turnState = TurnState.PLAYER;
                gameFrame.changeButtonTexts(gameBoard.getSpots());
                break;
        }
    }

}
