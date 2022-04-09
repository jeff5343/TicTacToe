package com.hong;

public class GameController {

    public static GameController gameController;
    private final GameBoard gameBoard = new GameBoard();
    private final PlayerInput player = new PlayerInput(gameBoard);
    private final ComputerPlayer computer = new ComputerPlayer(gameBoard);

    private TurnState turnState = TurnState.PLAYER;
    private enum TurnState {
        PLAYER,
        COMPUTER
    }

    public void init() {
        gameController = this;

        gameBoard.init();
        gameBoard.displayBoard();

        player.start();
    }

    public void startNextTurn() {
        if(gameBoard.getAllSpotsOccupied()) {
            gameBoard.displayBoard();
            System.out.println("Tie!");
            return;
        }

        SpotState spot = gameBoard.getMatchingSet();
        if(spot!=SpotState.NONE) {
            gameBoard.displayBoard();
            System.out.println("\n" + spot.value + "wins!");
            return;
        }

        runTurnStateMachine();
    }

    private void runTurnStateMachine() {
        switch (turnState) {
            case PLAYER -> {
                turnState = TurnState.COMPUTER;
                computer.start();
            }
            case COMPUTER -> {
                turnState = TurnState.PLAYER;
                gameBoard.displayBoard();
                player.start();
            }
        }
    }

}