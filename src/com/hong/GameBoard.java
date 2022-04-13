package com.hong;

import java.util.Arrays;
import java.util.HashMap;

import com.hong.Enums.LineType;
import com.hong.Enums.SpotState;

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
        printNumOnColumns();
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
    public int[] getRandomSpot() {
        int[] randomSpot =  new int[] {getRandomNumber(), getRandomNumber()};
        if(getSpotOccupied(randomSpot[0], randomSpot[1])) {
            return getRandomSpot();
        }
        return randomSpot;
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
     * returns xy coordinate of blocking spot,
     * returns null if no spots are found
     */
    public int[] getBlockSpotCoordinate(SpotState findSpot) {
        HashMap<LineType,int[]> missingSpot = findMissingSpot(findSpot);
        int[] values = getIntArrayValueOfHashMap(missingSpot);
        if(!Arrays.equals(values, new int[] {-1, -1})) {
            LineType line = getLineKeyOfHashMap(missingSpot);
            return getCoordinateOfLine(line, values);
        }
        return null;
    }

    /**
     * @return
     * returns xy coordinate of next spot,
     * returns null if no spots are found
     */
    public int[] getNextSpotCoordinate(SpotState findSpot, ComputerPlayer player) {
        HashMap<LineType,int[]> nextSpot = findNextSpot(findSpot, null);
        int[] values = getIntArrayValueOfHashMap(nextSpot);
        if(!Arrays.equals(values, new int[] {-1, -1})) {
            LineType line = getLineKeyOfHashMap(nextSpot);
            player.setCurrentLineType(line);
            return getCoordinateOfLine(line, values);
        }
        return null;
    }

    /**
     * @return
     * returns xy coordinate of next spot,
     * returns null if no spots are found
     */
    public int[] getNextSpotCoordinateWithLine(SpotState findSpot, LineType lineToFollow) {
        if(lineToFollow==null) {
            return null;
        }
        HashMap<LineType,int[]> nextSpot = findNextSpot(findSpot, lineToFollow);
        int[] values = getIntArrayValueOfHashMap(nextSpot);
        if(!Arrays.equals(values, new int[] {-1, -1})) {
            LineType line = getLineKeyOfHashMap(nextSpot);
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

    private HashMap<LineType, int[]> findMissingSpot(SpotState findSpot) {
        HashMap<LineType, int[]> spot = new HashMap<>();
        for(int i=0; i<Constants.widthAndHeight; i++) {
            int r = getMissingSpotOfArray(getRow(i), findSpot);
            int c = getMissingSpotOfArray(getColumn(i), findSpot);
            int d = getMissingSpotOfArray(getDiagonal(i), findSpot);
            if(r!=-1) {
                spot.put(LineType.ROW, new int[] {i, r});
                return spot;
            }
            if(c!=-1) {
                spot.put(LineType.COLUMN, new int[] {i, c});
                return spot;
            }
            if(d!=-1) {
                spot.put(LineType.DIAGONAL, new int[] {i, d});
                return spot;
            }
        }
        spot.put(LineType.ROW, new int[] {-1,-1});
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

    private HashMap<LineType, int[]> findNextSpot(SpotState findSpot, LineType lineType) {
        HashMap<LineType, int[]> spot = new HashMap<>();
        for(int i=0; i<Constants.widthAndHeight; i++) {
            int r = getNextSpotOfArray(getRow(i), findSpot);
            int c = getNextSpotOfArray(getColumn(i), findSpot);
            int d = getNextSpotOfArray(getDiagonal(i), findSpot);
            if(r!=-1 && (lineType==null || lineType==LineType.ROW)) {
                spot.put(LineType.ROW, new int[] {i, r});
                return spot;
            }
            if(c!=-1 && (lineType==null || lineType==LineType.COLUMN)) {
                spot.put(LineType.COLUMN, new int[] {i, c});
                return spot;
            }
            if(d!=-1 && (lineType==null || lineType==LineType.DIAGONAL)) {
                spot.put(LineType.DIAGONAL, new int[] {i, d});
                return spot;
            }
        }
        spot.put(LineType.ROW, new int[] {-1,-1});
        return spot;
    }

    /**
     * @return
     * returns -1 if there is no next spots available
     */
    private int getNextSpotOfArray(SpotState[] spots, SpotState findSpot) {
        SpotState oppositeSpot = (findSpot==SpotState.X) ? SpotState.O : SpotState.X;
        for(int i=0; i<spots.length; i++) {
            if(spots[i] == findSpot) {
                if(i<spots.length-1 && !getSpotArrayHasSpot(spots, oppositeSpot)) {
                    if(spots[i+1]==SpotState.NONE) {
                        return i+1;
                    }
                }
                if(i>0 && !getSpotArrayHasSpot(spots, oppositeSpot)) {
                    if(spots[i-1]==SpotState.NONE) {
                        return i-1;
                    }
                }
            }
        }
        return -1;
    }

    private boolean getSpotArrayHasSpot(SpotState[] spots, SpotState spot) {
        for(SpotState s : spots) {
            if(s==spot) {
                return true;
            }
        }
        return false;
    }

    private int[] getCoordinateOfLine(LineType line, int[] number) {
        if(line == LineType.ROW) {
            return number;
        } else if(line == LineType.COLUMN) {
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

    private int[] getIntArrayValueOfHashMap(HashMap<LineType,int[]> map) {
        for(int[] i : map.values()) {
            return i;
        }
        return new int[] {-1, -1};
    }

    private LineType getLineKeyOfHashMap(HashMap<LineType,int[]> map) {
        for(LineType l : map.keySet()) {
            return l;
        }
        return LineType.ROW;
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

    private void printNumOnColumns() {
        System.out.print("    ");
        for(int i=0; i<Constants.widthAndHeight; i++) {
            System.out.print(i+1+".  ");
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
