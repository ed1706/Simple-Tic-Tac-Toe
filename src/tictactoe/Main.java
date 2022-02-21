package tictactoe;

public class Main {
    public static void main(String[] args) throws Exception {
        FifthStage ob = new FifthStage();
        ob.doStage();
    }
}

/*class SecondStage {
    public void start() throws Grid.GridException {
        Grid grid = new Grid(3, 3);
        grid.initializeGrid();
        grid.printGrid('|', '-');
    }
}*/

/*class ThirdStage {
    public void start() throws Grid.GridException {
        Grid grid = new Grid(3, 3);
        grid.initializeGrid();
        grid.printGrid('|', '-');
        System.out.println(grid.analyzeGrid());
    }
}*/

/*class FourthStage {
    public void doStage() throws Grid.GridException, Game.GameException {
        Game game = new Game();
        game.startSpecificGame();
        game.makeMove('X');
    }
}*/


class FifthStage {
    public void doStage() throws Grid.GridException, Game.GameException {
        Game game = new Game();
        game.playGame();
    }
}