package tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Math.abs;

public class Grid {
    private final int width;
    private final int length;

    private char currentFillSymbol;

    private final char[][] grid;

    public Grid(int w, int l, char firstFillSymbol) throws GridException {
        if (w == l && w > 2) {
            width = w;
            length = l;

            grid = new char[length][width];
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < width; j++) {
                    grid[i][j] = '_';
                }
            }
            currentFillSymbol = firstFillSymbol;
        } else {
            throw new IllegalGridSizeException("Incorrect grid width or length");
        }
    }

    public Grid(int w, int l, String s, char firstFillSymbol) throws GridException {
        if (w == l && w > 2) {
            width = w;
            length = l;

            if (s == null) {
                throw new IllegalInputStringException("Input string is null!");
            }
            if (s.length() != width * length) {
                throw new IllegalInputStringException("Incorrect string length!");
            }
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != 'X' && s.charAt(i) != 'O' && s.charAt(i) != '_') {
                    throw new IllegalInputStringException("Incorrect symbol in input string: char index is (" + i + ")!");
                }
            }

            grid = new char[length][width];

            for (int i = 0; i < s.length(); i++) {
                grid[i / width][i % width] = s.charAt(i);
            }
            currentFillSymbol = firstFillSymbol;
        } else {
            throw new IllegalGridSizeException("Incorrect grid width or length");
        }
    }

    public void printGrid(char boardSymbol, char aboveSymbol) {
        int exWidth = (width + 2) * 2 - 1;

        for (int i = 0; i < exWidth; i++) {
            System.out.print(aboveSymbol);
        }
        System.out.println();

        for (int i = 0; i < length; i++) {
            System.out.print("" + boardSymbol + ' ');
            for (int j = 0; j < width; j++) {
                System.out.print("" + grid[i][j] + ' ');
            }
            System.out.println(boardSymbol);
        }

        for (int i = 0; i < exWidth; i++) {
            System.out.print(aboveSymbol);
        }
        System.out.println();
    }

    /*public void initializeGrid() throws GridException {
        String inputString = getInputString();

        for (int i = 0; i < inputString.length(); i++) {
            grid[i / width][i % width] = inputString.charAt(i);
        }
    }*/

    public int analyzeGrid() {
        int xCount = 0;
        int oCount = 0;
        int elementsCount = 0;
        for (int i = 0; i < length; i++) {
            for(int j = 0; j < width; j++) {
                if (grid[i][j] == 'X') {
                    xCount++;
                    elementsCount++;
                } else if (grid[i][j] == 'O') {
                    oCount++;
                    elementsCount++;
                }
            }
        }
        if (abs(xCount - oCount) > 1) {
            //return "Impossible";
            return -1;
        }
        int xWinsCount = winsCount('X');
        int oWinsCount = winsCount('O');
        /*if ((xWinsCount > 1 || oWinsCount > 1) ||
                (xWinsCount == 1 && oWinsCount == 1)) {
            //return "Impossible";
            return -1;
        }*/
        if (xWinsCount >= 1 && oWinsCount >= 1) {
            //return "Impossible";
            return -1;
        }
        if (xWinsCount >= 1) {
            //return "X wins";
            return 2;
        }
        if (oWinsCount >= 1) {
            //return "O wins";
            return 3;
        }
        if (elementsCount < (length * width)) {
            //return "Game not finished";
            return 0;
        } else {
            //return "Draw";
            return 1;
        }
    }

    public void fillCell(int x, int y) throws CoordinatesOutOfGridException, OccupiedCellException {
        if (!((x >= 0 && x < length) && (y >= 0 && y < width))) {
            throw new CoordinatesOutOfGridException("Coordinates are out of grid size:\r\n" +
                "x coordinate: " + x + "\r\n" +
                "y coordinate: " + y + "\r\n" +
                "grid size: " + length + " (length)  " + width + " (width)!");
        }
        if (grid[x][y] != '_') {
            throw new OccupiedCellException("Coordinate (" + x + "," + y + ") is occupied by " +
                grid[x][y] + " symbol!");
        }
        grid[x][y] = currentFillSymbol;
        changeFillSymbol();
    }

    private void changeFillSymbol() {
        if (currentFillSymbol == 'X') {
            currentFillSymbol = 'O';
        } else {
            currentFillSymbol = 'X';
        }
    }

    private int winsCount(char winSymbol) {
        int winsCount = 0;
        for (int i = 0; i < length && winsCount < 2; i++) {
            if (grid[i][0] == winSymbol &&
                    grid[i][1] == winSymbol &&
                    grid[i][2] == winSymbol) {
                winsCount++;
            }
            if (grid[0][i] == winSymbol &&
                    grid[1][i] == winSymbol &&
                    grid[2][i] == winSymbol) {
                winsCount++;
            }
        }
        if (winsCount >= 2) {
            return winsCount;
        }
        if (grid[0][0] == winSymbol &&
                grid[1][1] == winSymbol &&
                grid[2][2] == winSymbol) {
            winsCount++;
        }
        if (grid[0][2] == winSymbol &&
                grid[1][1] == winSymbol &&
                grid[2][0] == winSymbol) {
            winsCount++;
        }
        return winsCount;
    }

    /*private String getInputString() throws IllegalInputStringException {
        System.out.print("Enter cells: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        try {
            s = reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        if (s == null) {
            throw new IllegalInputStringException("Input string is null!");
        }
        if (s.length() != width * length) {
            throw new IllegalInputStringException("Incorrect string length!");
        }
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != 'X' && s.charAt(i) != 'O' && s.charAt(i) != '_') {
                throw new IllegalInputStringException("Incorrect symbol in input string: char index is (" + i + ")!");
            }
        }
        return s;
    }*/

    static class GridException extends Exception {
        GridException(String msg) {
            super(msg);
        }
    }

    static class IllegalGridSizeException extends GridException {
        IllegalGridSizeException(String msg) {
            super(msg);
        }
    }

    static class IllegalInputStringException extends GridException {
        IllegalInputStringException(String msg) {
            super(msg);
        }
    }

    static class CoordinatesOutOfGridException extends GridException {
        CoordinatesOutOfGridException(String msg) {
            super(msg);
        }
    }

    static class OccupiedCellException extends GridException {
        OccupiedCellException(String msg) {
            super(msg);
        }
    }
}