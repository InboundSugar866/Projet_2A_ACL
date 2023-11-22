package Game;
import Maze.Affichage;

public class Jeu {
    public static void main(String[] args) {
    String fileName = "src/assets/maze3.txt";
    char[][] maze = Maze.Maze.loadMaze(fileName);
    new Affichage(maze);
    }
}