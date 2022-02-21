package tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Game {
    private Grid grid;
    private BufferedReader reader;

    public Game() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void playGame() throws GameException, Grid.GridException {
        startEmptyGame();
        int gameControl;
        do {
            makeMove();
            gameControl = grid.analyzeGrid();
        } while (gameControl == 0);

        switch(gameControl) {
            case 1:
                System.out.println("Draw");
                break;
            case 2:
                System.out.println("X wins");
                break;
            case 3:
                System.out.println("O wins");
                break;
            default:
                throw new GameException("Unknown error (gameControl variable is (" + gameControl + ")!");
        }
    }

    private void startEmptyGame() throws Grid.GridException {
        grid = new Grid(3, 3, 'X');
        grid.printGrid('|', '-');
    }

    private void startSpecificGame() throws Grid.GridException {
        System.out.print("Enter cells: ");
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        try {
            s = reader.readLine();
            grid = new Grid(3, 3, s, 'X');
            grid.printGrid('|', '-');
        } catch (IOException e) {
            System.out.println(e.getMessage());
            try {
                reader.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /*public Game(Grid g) {
        grid = g;
    }*/

    private void makeMove() throws GameException, Grid.GridException {
        Integer x = null;
        Integer y = null;

        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        String[] coordinates = {};
        try {
            do {
                System.out.print("Enter the coordinates: ");
                s = reader.readLine();
                coordinates = s.trim().split("\\s+");
                /*if (coordinates.length != 2) {
                    throw new IllegalInputCoordinatesException("Incorrect coordinates count!");
                */
                try {
                    x = Integer.parseInt(coordinates[0]);
                    y = Integer.parseInt(coordinates[1]);
                    grid.fillCell(x.intValue() - 1, y.intValue() - 1);
                } catch (NumberFormatException ex){
                    System.out.println("You should enter numbers!");
                    x = null;
                } catch (Grid.CoordinatesOutOfGridException ex){
                    System.out.println("Coordinates should be from 1 to 3!");
                    x = null;
                } catch (Grid.OccupiedCellException ex){
                    System.out.println("This cell is occupied! Choose another one!");
                    x = null;
                }
            } while(x == null || y == null);

            grid.printGrid('|', '-');
        } catch (IOException e) {
            System.out.println(e.getMessage());
            try {
                reader.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } /*finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }*/
    }

    static class GameException extends Exception {
        GameException(String msg) {
            super(msg);
        }
    }

    static class IllegalInputCoordinatesException extends GameException {
        IllegalInputCoordinatesException(String msg) {
            super(msg);
        }
    }
}
