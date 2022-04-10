package com.hong;

import java.util.Arrays;
import java.util.HashMap;

public class GameBoard {

    private final SpotState[][] spots =
            new SpotState[Constants.widthAndHeight]
                    [Constants.widthAndHeight];

    public enum Lines {
        ROW, COLUMN, DIAGONAL
    };

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

    /**
     * @return
     * returns xy coordinate of blocking spot
     * returns null if no spots are found
     */
    public int[] getBlockSpotCoordinate(SpotState findSpot) {
        HashMap<Lines,int[]> missingSpot = findMissingSpots(findSpot);
        int[] values = new int[] {-1,-1};
        for(int[] i : missingSpot.values()) {
            values = i;
            break;
        }
        if(!Arrays.equals(values, new int[] {-1, -1})) {
            Lines line = Lines.ROW;
            for(Lines l : missingSpot.keySet()) {
                line = l;
                break;
            }
            return getCoordinateOfLine(line, values);
        }
        return null;
    }

    private SpotState getEqualRow() {
        for(int i=0; i<spots.length; i++) {
            SpotState spot = getSpotArrayEquals(getRow(i));
            if(spot!=SpotState.NONE) {
                return spot;
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
            int j=Constants.widthAndHeight-1;
            for(int i=0; i<Constants.widthAndHeight; i++) {
                diagonal[i] = spots[i][j];
                j--;
            }
        } else {
            Arrays.fill(diagonal, SpotState.NONE);
        }
        return diagonal;
    }

    private SpotState getSpotArrayEquals(SpotState[] spotStates) {
        SpotState firstState = spotStates[0];
        for(int j=0; j<spotStates.length; j++) {
            if(firstState == spotStates[j]) {
                if(j==Constants.widthAndHeight-1) {
                    return firstState;
                }
                continue;
            }
            break;
        }
        return SpotState.NONE;
    }

    private HashMap<Lines, int[]> findMissingSpots(SpotState findSpot) {
        HashMap<Lines, int[]> spot = new HashMap<>();
        spot.put(Lines.ROW, new int[] {-1,-1});
        for(int i=0; i<Constants.widthAndHeight; i++) {
            int r = getMissingSpotOfArray(getRow(i), findSpot);
            int c = getMissingSpotOfArray(getColumn(i), findSpot);
            int d = getMissingSpotOfArray(getDiagonal(i), findSpot);
            if(r!=-1) {
                spot.remove(Lines.ROW);
                spot.put(Lines.ROW, new int[] {i, r});
                return spot;
            }
            if(c!=-1) {
                spot.remove(Lines.ROW);
                spot.put(Lines.COLUMN, new int[] {i, c});
                return spot;
            }
            if(d!=-1) {
                spot.remove(Lines.ROW);
                spot.put(Lines.DIAGONAL, new int[] {i, d});
                return spot;
            }
        }
        return spot;
    }

    /**
     * @return returns -1 if there is no missing spot
     */
    private int getMissingSpotOfArray(SpotState[] spots, SpotState spot) {
        int numberOfSpots = 0;
        int numberOfOppositeSpots = 0;
        int missingSpot=0;
        for(int i=0; i<spots.length; i++) {
            if(spots[i] == spot) {
                numberOfSpots++;
                continue;
            }
            if((spot==SpotState.X) ? spots[i]==SpotState.O : spots[i]==SpotState.X) {
                numberOfOppositeSpots++;
                continue;
            }
            if(spots[i] == SpotState.NONE) {
                missingSpot=i;
            }
        }
        return ((numberOfSpots==Constants.widthAndHeight-1) ? (numberOfOppositeSpots<1) ? missingSpot : -1 : -1);
    }

    private int[] getCoordinateOfLine(Lines line, int[] number) {
        if(line == Lines.ROW) {
            return number;
        } else if(line == Lines.COLUMN) {
            return getCoordinateOfColumn(number);
        } else {
            return getCoordinateOfDiagonal(number);
        }

    }

    private int[] getCoordinateOfColumn(int[] number) {
        return new int[] {
            number[1],
            number[0]
        };
    }

    private int[] getCoordinateOfDiagonal(int[] number) {
        if(number[0]==1) {
            return new int[] {number[1], number[1]};
        } else if(number[0]==2) {
            int j=Constants.widthAndHeight-1;
            for(int i=0; i<Constants.widthAndHeight; i++) {
                if(number[1] == i) {
                    return new int[] {number[1], j};
                }
                j--;
            }
        }
        return new int[] {-1,-1};
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
