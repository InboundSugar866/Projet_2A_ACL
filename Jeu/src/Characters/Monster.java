package Characters;

import javax.swing.*;
import java.util.*;


import Game.Tests;
import Maze.Maze;
import Maze.Affichage;
import Game.A_star;

public class Monster extends Characters{
    private final JLabel[][] mazeCells;
    private final JPanel spotlightPanel;

    public Monster(JLabel[][] mazeCells, JPanel spotlightPanel) {
        super(mazeCells);
        this.mazeCells = mazeCells;
        this.spotlightPanel = spotlightPanel;
    }

    public void monster(char[][] maze, ImageIcon floorIcon, ImageIcon monsterIcon, ImageIcon monsterIconR) {

        int[] posE = new int[2];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 'E' || maze[i][j] == 'F') { // Assuming 'E' and 'F' are the targets
                    posE[0] = j;
                    posE[1] = i;
                }
            }
        }

        List<A_star.Point> positions = new ArrayList<>();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 'M' || maze[i][j] == 'N') {
                    positions.add(new A_star.Point(i, j)); // Note that we switch the order of i and j here because Point takes column index first and row index second.
                }
            }
        }

        for (A_star.Point position : positions) {
            int dxM = 0, dyM = 0; // Initialize dxM and dyM
            int posXM = position.col, posYM = position.row; // Get the current position of 'M' or 'N'
            boolean Etat = maze[posYM][posXM] == 'M'; // Check if it's 'M' or 'N'

            A_star.Point source = position;
            A_star.Point dest = new A_star.Point(posE[1], posE[0]); // Assuming posE is the destination for all 'M's and 'N's

            List<A_star.Point> path = A_star.shortestPathBFS(maze, source, dest); // Call the BFS algorithm

            if (path == null) {
                System.out.println("Destination is unreachable");
            } else {
                A_star.Point curr = path.get(0);
                A_star.Point next = path.get(1);
                if (next.row < curr.row) {
                    //System.out.println("Move up");
                    dxM = 0;
                    dyM = -1;
                } else if (next.row > curr.row) {
                    //System.out.println("Move down");
                    dxM = 0;
                    dyM = 1;
                } else if (next.col < curr.col) {
                    //System.out.println("Move left");
                    dxM = -1;
                    dyM = 0;
                    Etat = false;
                } else if (next.col > curr.col) {
                    //System.out.println("Move right");
                    dxM = 1;
                    dyM = 0;
                    Etat = true;
                }

                int[] newPositionM = Maze.setPosition(dxM, dyM, posXM, posYM, maze.length, maze[0].length);

                // If the new position is valid and not the player's position, update the maze and draw the monster
                if (maze[newPositionM[1]][newPositionM[0]] != '#' && maze[newPositionM[1]][newPositionM[0]] != 'E' && maze[newPositionM[1]][newPositionM[0]] != 'F' && maze[newPositionM[1]][newPositionM[0]] != 'T' && maze[newPositionM[1]][newPositionM[0]] != 'S' && maze[newPositionM[1]][newPositionM[0]] != 'M' && maze[newPositionM[1]][newPositionM[0]] != 'N') {
                    maze[posYM][posXM] = '!';
                    mazeCells[posYM][posXM].setIcon(floorIcon);
                    posXM = newPositionM[0];
                    posYM = newPositionM[1];
                    if(Etat) {
                        maze[posYM][posXM] = 'M';
                        mazeCells[posYM][posXM].setIcon(monsterIcon);
                    } else {
                        maze[posYM][posXM] = 'N';
                        mazeCells[posYM][posXM].setIcon(monsterIconR);
                    }
                    Affichage.spotlightPanel.repaint();
                } else if(maze[newPositionM[1]][newPositionM[0]] == 'E' || maze[newPositionM[1]][newPositionM[0]] == 'F'){
                    Affichage.deathdisplay();
                    Tests.restartGame();
                }
            }
        }
    }
}