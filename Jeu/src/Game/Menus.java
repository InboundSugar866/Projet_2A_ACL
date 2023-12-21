package Game;

import Maze.Affichage;

import javax.swing.*;

public class Menus {
    // method to check if the player has won
    public static boolean haswon (char[][] maze, int posX, int posY){
        return maze[posY][posX]=='S';
    }

    // method to check if the player has lost
    public static boolean hasdeath (int posX, int posY, int[] posM) {
        int posYM = posM[0];
        int posXM = posM[1];
        if (posX == posXM && posY == posYM) {
            return true;
        }
        else {
            return false;
        }
    }

    // method to ask the player to restart the game
    public static int restartGameQuestion() {
        int choixRestart = JOptionPane.showConfirmDialog(
                null,
                "Voulez-vous recommencer une partie ?",
                "Recommencer la partie",
                JOptionPane.YES_NO_OPTION

        );
        // Le choix sera 0 si l'utilisateur clique sur oui et 1 si l'utilisateur clique sur non
        return choixRestart;
    }

    // method to actually restart the game
    public static void restartGame(){
        int choixRestart=restartGameQuestion();
        if (choixRestart==0) {
            Game.Jeu.main(new String[0]);
            Affichage.timer.stop();
        }
        else{
            Affichage.frame.dispose();
            System.exit(1);
        }
    }
}
