package Characters;

import javax.swing.*;
import java.util.Random;

import Game.Menus;
import Maze.Maze;
import Maze.Affichage;

public class Ghost extends Characters{
    private final JLabel[][] mazeCells;
    private final JPanel spotlightPanel;

    public Ghost(JLabel[][] mazeCells, JPanel spotlightPanel) {
        super(mazeCells);
        this.mazeCells = mazeCells;
        this.spotlightPanel = spotlightPanel;
    }
    
    // method to make the ghost move somewhat randomly
    public int[] ghost(char[][] maze, ImageIcon floorIcon, ImageIcon monsterIcon, ImageIcon monsterIconR, ImageIcon monsterWallIcon, ImageIcon monsterWallIconR,ImageIcon WallIcon) {
        int dxM, dyM;
        boolean Etat = true;
        boolean Wall = false;

        int[] posM = new int[2];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[1].length; j++) {
                if (maze[i][j] == 'G') {
                    posM[0] = j;
                    posM[1] = i;
                    Etat = true;
                    Wall = false;
                } else if (maze[i][j] == 'H') {
                    posM[0] = j;
                    posM[1] = i;
                    Etat = false;
                    Wall = false;
                } else if (maze[i][j] == 'R') {
                    posM[0] = j;
                    posM[1] = i;
                    Etat = true;
                    Wall = true;
                } else if (maze[i][j] == 'B') {
                    posM[0] = j;
                    posM[1] = i;
                    Etat = false;
                    Wall = true;
                }
            }
        }
        posXG = posM[0];
        posYG = posM[1];

        Random r = new Random();
        int n = r.nextInt(4);
        if(n==0){
            dxM = 1;
            dyM = 0;
            Etat = true;
        }else if(n==1){
            dxM = 0;
            dyM = 1;
        }else if(n==2){
            dxM = -1;
            dyM = 0;
            Etat = false;
        }else{
            dxM = 0;
            dyM = -1;
        }
        // Calculate the new position
        int[] newPositionM = Maze.setPosition(dxM, dyM, posXG, posYG, maze.length, maze[0].length);

        if(maze[newPositionM[1]][newPositionM[0]] == 'E' || maze[newPositionM[1]][newPositionM[0]] == 'F'){
            Affichage.deathdisplay();
            Menus.restartGame();
        }
        // If the new position is valid and not the player's position, update the maze and draw the monster
        if ((maze[newPositionM[1]][newPositionM[0]] != 'E' || maze[newPositionM[1]][newPositionM[0]] != 'F') && maze[newPositionM[1]][newPositionM[0]] != 'T' && maze[newPositionM[1]][newPositionM[0]] != 'S') {
            if (Wall) {
                maze[posYG][posXG] = '#';
                mazeCells[posYG][posXG].setIcon(WallIcon);
            } else {
                maze[posYG][posXG] = '!';
                mazeCells[posYG][posXG].setIcon(floorIcon);
            }
            posXG = newPositionM[0];
            posYG = newPositionM[1];
            if (maze[newPositionM[1]][newPositionM[0]] == '#') {
                if (Etat) {
                    maze[posYG][posXG] = 'R';
                    mazeCells[posYG][posXG].setIcon(monsterWallIcon);
                } else {
                    maze[posYG][posXG] = 'B';
                    mazeCells[posYG][posXG].setIcon(monsterWallIconR);
                }
            }else {
                if (Etat) {
                    maze[posYG][posXG] = 'G';
                    mazeCells[posYG][posXG].setIcon(monsterIcon);
                } else {
                    maze[posYG][posXG] = 'H';
                    mazeCells[posYG][posXG].setIcon(monsterIconR);
                }
            }
            spotlightPanel.repaint();
        }

        return new int[]{posYG, posXG};
    }
}
