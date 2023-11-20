package Game;
import Maze.Maze;

public class Jeu {
    public static void main(String[] args) {
    Maze game = new Maze();
    String fileName = game.getFileName();
    game.loadMaze(fileName);
    game.playGame();
    }
}
