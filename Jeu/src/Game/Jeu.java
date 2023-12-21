package Game;
import Maze.Affichage;

public class Jeu {
    static String fileName;

    public static void main(String[] args) {
        int niveau = Affichage.choix();
        switch (niveau){
            case 1:
                fileName = "src/assets/level1.txt";
                break;
            case 2:
                fileName = "src/assets/level2.txt";
                break;
            case 3:
                fileName = "src/assets/level3.txt";
                break;
        }

        char[][] maze = Maze.Maze.loadMaze(fileName);
        new Affichage(maze, niveau);
    }
}