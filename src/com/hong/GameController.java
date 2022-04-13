package com.hong;

import com.hong.Enums.SpotState;

public class GameController {

    public static GameController gameController;
    private final GameBoard gameBoard = new GameBoard();
    private final PlayerInput player = new PlayerInput(gameBoard);
    private final ComputerPlayer computer = new ComputerPlayer(gameBoard);

    private enum TurnState {
        PLAYER,
        COMPUTER
    }
    private TurnState turnState = TurnState.PLAYER;

    public void init() {
        gameController = this;

        gameBoard.init();
        gameBoard.displayBoard();

        player.start();
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
        gameBoard.displayBoard();
        System.out.println("\n Tie!");
    }

    private void runWinnerAction(SpotState winner) {
        gameBoard.displayBoard();
        System.out.println("\n" + winner.value + "wins!");
    }

    private void runTurnStateMachine() {
        switch (turnState) {
            case PLAYER:
                turnState = TurnState.COMPUTER;
                computer.start();
                break;
            case COMPUTER:
                turnState = TurnState.PLAYER;
                gameBoard.displayBoard();
                player.start();
                break;
        }
    }

}
