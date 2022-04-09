package com.hong;

import java.util.Arrays;

public class GameBoard {

    private final SpotState[][] spots =
            new SpotState[Constants.widthAndHeight]
                    [Constants.widthAndHeight];

    public void init() {
        for(SpotState[] sA : spots) {
            Arrays.fill(sA, SpotState.NONE);
        }
    }

    public void displayBoard() {
        startNewLine();
        for(int i=0; i< spots.length; i++) {
            printNumOnRow(i+1);
            printRow(spots[i]);
            if(i!=spots.length-1) {
                printNewLine(spots[i].length);
            }
        }
        startNewLine();
    }

    public void changeSpot(int row, int column, SpotState state) {
        spots[row][column] = state;
    }

    /**
     * @return row and column values in an array
     */
    public int[] getRandomUnoccupiedSpot() {
        return new int[]{
            getRandomNumber(),
            getRandomNumber()
        };
    }

    public boolean getAllSpotsOccupied() {
        int numberOfSpots = (int)Math.pow(Constants.widthAndHeight, 2);
        for(SpotState[] sA : spots) {
            for(SpotState s : sA) {
                if(s!=SpotState.NONE) {
                    numberOfSpots--;
                }
            }
        }
        return numberOfSpots==0;
    }

    public boolean getSpotOccupied(int row, int column) {
        return (spots[row][column] != SpotState.NONE);
    }

    public SpotState getMatchingSet() {
        SpotState equalRow = getEqualRow();
        SpotState equalColumn = getEqualColumn();
        SpotState equalDiagonal = getEqualDiagonal();

        return (equalRow==SpotState.NONE) ? ((equalColumn==SpotState.NONE) ?
                equalDiagonal : equalColumn) : equalRow;
    }

    private SpotState getEqualRow() {
        for(int i=0; i<spots.length; i++) {
            SpotState firstState = sA[0];
            if(firstState == SpotState.NONE) {
                continue;
            }
            for(int i=0; i<sA.length; i++) {
                if(sA[i] == firstState) {
                    if(i==sA.length-1) {
                        return firstState;
                    }
                    continue;
                }
                break;
                SpotState spot = getSpotArrayEquals(getRow())
            }
        }
        return SpotState.NONE;
    }

    private SpotState getEqualColumn() {
        for(int i = 0; i<Constants.widthAndHeight; i++) {
            SpotState spot = getSpotArrayEquals(getColumn(i));
            if(spot!=SpotState.NONE) {
                return spot;
            }
        }
        return SpotState.NONE;
    }

    private SpotState getEqualDiagonal() {
        for(int i=0; i<2; i++) {
            SpotState spot = getSpotArrayEquals(getDiagonal(i+1));
            if(spot!=SpotState.NONE) {
                return spot;
            }
        }
        return SpotState.NONE;
    }

    private SpotState[] getRow(int number) {
        return spots[number];
    }

    private SpotState[] getColumn(int number) {
        SpotState[] column = new SpotState[Constants.widthAndHeight];
        for(int i = 0; i<Constants.widthAndHeight; i++) {
            column[i] = spots[i][number];
        }
        return column;
    }

    private SpotState[] getDiagonal(int number) {
        SpotState[] diagonal = new SpotState[Constants.widthAndHeight];
        if(number==1) {
            for(int i=0; i<Constants.widthAndHeight; i++) {
                diagonal[i] = spots[i][i];
            }
        } else if(number==2) {
            for(int i=Constants.widthAndHeight-1; i>0; i--) {
                diagonal[i] = spots[i][i];
            }
        } else {
            Arrays.fill(diagonal, SpotState.NONE);
        }
        return diagonal;
    }

    private SpotState getSpotArrayEquals(SpotState[] spotStates) {
        SpotState firstState = spotStates[0];
        for(int j=0; j<spotStates.length; j++) {
            if(j==Constants.widthAndHeight-1) {
                return firstState;
            }
        }
        return firstState;
    }

    private void printRow(SpotState[] sA) {
        int length = sA.length;
        for(int i=0; i<length; i++) {
            System.out.print(sA[i].value + (i!=length-1 ? "|" : ""));
        }
    }

    private void printNewLine(int times) {
        startNewLine();
        for(int i=0; i<times; i++) {
            System.out.print((i==0 ? "   ——" : "  ——"));
        }
        startNewLine();
    }

    private void printNumOnRow(int num) {
        System.out.print(num + ". ");
    }

    private int getRandomNumber() {
        return (int)(Math.random()*(Constants.widthAndHeight));
    }

    private void startNewLine() {
        System.out.print("\n");
    }

}
