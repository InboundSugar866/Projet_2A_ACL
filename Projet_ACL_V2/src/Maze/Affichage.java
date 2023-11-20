package Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Affichage {
    private final JLabel[][] mazeCells;
    private int posX, posY;
    private static final char[][] maze = new char[20][50];

    public Affichage(char[][] maze) {
        JFrame frame = new JFrame("Maze Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(maze.length, maze[0].length));

        ImageIcon wallIcon = new ImageIcon("src/assets/rock-wall2.png");
        ImageIcon floorIcon = new ImageIcon("src/assets/empty-cell.png");
        ImageIcon characterIcon = new ImageIcon("src/assets/character2.png");
        ImageIcon finishIcon = new ImageIcon("src/assets/end.png");

        mazeCells = new JLabel[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                mazeCells[i][j] = new JLabel();
                if (maze[i][j] == '#') {
                    mazeCells[i][j].setIcon(wallIcon);
                }
                else if(maze[i][j] == 'S') {
                    mazeCells[i][j].setIcon(finishIcon);
                } else {
                    mazeCells[i][j].setIcon(floorIcon);
                    if (maze[i][j] == 'E') {
                        posX = j;
                        posY = i;
                        mazeCells[i][j].setIcon(characterIcon);
                    }
                }
                frame.add(mazeCells[i][j]);
            }
        }

        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int dx = 0, dy = 0;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:    dy = -1; break;
                    case KeyEvent.VK_DOWN:  dy =  1; break;
                    case KeyEvent.VK_LEFT:  dx = -1; break;
                    case KeyEvent.VK_RIGHT: dx =  1; break;
                }
                int[] newPosition = Maze.setPosition(dx, dy, posX, posY, maze.length, maze[0].length);
                if (maze[newPosition[1]][newPosition[0]] != '#') {
                    mazeCells[posY][posX].setIcon(floorIcon);
                    posX = newPosition[0];
                    posY = newPosition[1];
                    mazeCells[posY][posX].setIcon(characterIcon);
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
        frame.requestFocusInWindow();
    }
}
