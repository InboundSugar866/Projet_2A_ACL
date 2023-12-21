package Characters;

import Game.Tests;
import Maze.Maze;

import javax.swing.*;
import java.awt.event.KeyEvent;

import Maze.Affichage;
import Objects.Permanent;

public class Hero extends Characters{
    private static JLabel[][] mazeCells;

    private static JPanel spotlightPanel;

    public Hero(JLabel[][] mazeCells, JPanel spotlightPanel) {
        super(mazeCells);
        Hero.mazeCells = mazeCells;
        Hero.spotlightPanel = spotlightPanel;
    }

    // method to move the player according to the keyboard inputs
    public static void handleKeyPress(KeyEvent e, char[][] maze, ImageIcon floorIcon, ImageIcon characterIcon, ImageIcon characterIconR) {
        int dx = 0, dy = 0;
        boolean Etat = true;

        int[] pos = new int[2];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[1].length; j++) {
                if (maze[i][j] == 'E') {
                    pos[0] = j;
                    pos[1] = i;
                } else if (maze[i][j] == 'F') {
                    pos[0] = j;
                    pos[1] = i;
                    Etat = false;
                }
            }
        }
        int posX = pos[0];
        int posY = pos[1];

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                dy = -1;
                break;
            case KeyEvent.VK_DOWN:
                dy = 1;
                break;
            case KeyEvent.VK_LEFT:
                dx = -1;
                Etat = false;
                break;
            case KeyEvent.VK_RIGHT:
                dx = 1;
                Etat = true;
                break;
        }

        // check all the loose and win cases
        int[] newPosition = Maze.setPosition(dx, dy, posX, posY, maze.length, maze[0].length);
        if (maze[newPosition[1]][newPosition[0]] != '#') {
            if (maze[newPosition[1]][newPosition[0]] == '!' || maze[newPosition[1]][newPosition[0]] == 'L') {
                if (maze[newPosition[1]][newPosition[0]] == 'L') {
                Affichage.Fiole.handleFiole(maze, posX, posY);
                }
                maze[posY][posX] = '!';
                mazeCells[posY][posX].setIcon(floorIcon);
                posX = newPosition[0];
                Affichage.posX = newPosition[0];
                posY = newPosition[1];
                Affichage.posY = newPosition[1];
                if(Etat) {
                    maze[posY][posX] = 'E';
                    mazeCells[posY][posX].setIcon(characterIcon);
                } else {
                    maze[posY][posX] = 'F';
                    mazeCells[posY][posX].setIcon(characterIconR);
                }
                Affichage.spotlightPanel.repaint();
            } else if (Tests.haswon(maze, newPosition[0], newPosition[1])) {
                Affichage.victorydisplay();
                Tests.restartGame();
            } else if (maze[newPosition[1]][newPosition[0]] == 'M' || maze[newPosition[1]][newPosition[0]] == 'N' || maze[newPosition[1]][newPosition[0]] == 'G' || maze[newPosition[1]][newPosition[0]] == 'H') {
                Affichage.deathdisplay();
                Tests.restartGame();
            } else if (maze[newPosition[1]][newPosition[0]] == 'T') {
                int[] newPlayerPosition = Affichage.Trap.handleTrap(maze, posX, posY, floorIcon, characterIcon);
                posX = newPlayerPosition[0];
                posY = newPlayerPosition[1];
                Affichage.posX = posX;
                Affichage.posY = posY;
            }
        }

        //double distanceM = calculateDistance(posX, posY, posXM, posYM);
        //System.out.println("distance to monster "+distanceM);

        //double distanceG = calculateDistance(posX, posY, posXG, posYG);
        //System.out.println("distance to ghost "+distanceG);
    }
}
