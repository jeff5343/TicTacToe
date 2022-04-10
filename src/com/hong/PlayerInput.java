package com.hong;

import java.util.Scanner;

public class PlayerInput {

    private final Scanner scanner = new Scanner(System.in);
    private final GameBoard gameBoard;

    private int selectedColumn;
    private int selectedRow;

    public PlayerInput(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void start() {
        selectedColumn = 0;
        selectedRow = 0;

        askForColumn();
    }

    private void askForColumn() {
        System.out.println(
                "\n" +
                "Enter a column number (left to right)"
        );
        setColumnWithInput();
    }

    private void setColumnWithInput() {
        int input = getPlayerNumberInput();
        if(input!=-1) {
            selectedColumn = input;
            askForRow();
            return;
        }
        askForColumn();
    }

    private void askForRow() {
        System.out.println(
                "Enter a row number (top to bottom)"
        );
        setRowWithInput();
    }

    private void setRowWithInput() {
        int input = getPlayerNumberInput();
        if(input!=-1) {
            selectedRow = input;
            finishSelection();
            return;
        }
        askForRow();
    }

    private void finishSelection() {
        if(gameBoard.getSpotOccupied(selectedRow,selectedColumn)) {
            System.out.println("Spot already occupied!");
            start();
            return;
        }
        gameBoard.changeSpot(selectedRow,selectedColumn,SpotState.O);

        GameController.gameController.startNextTurn();
    }

    /**
     * @return returns -1 if the input cannot be turned into an int
     */
    private int getPlayerNumberInput() {
        String line = scanner.nextLine();
        if(isStringNumber(line)) {
            int i = Integer.parseInt(line);
            if(isNumberValid(i)) {
                // arrays in java are -1 so yes
                return i-1;
            }
        }
        return -1;
    }

    private boolean isStringNumber(String value) {
        return value.matches("([0-9])");
    }

    private boolean isNumberValid(int num) {
        return num <= Constants.widthAndHeight;
    }

}
