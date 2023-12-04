package Characters;

import Maze.Maze;

import javax.swing.*;
import java.util.Random;


public class Monster {
    private static JLabel[][] mazeCells;
    private static JPanel spotlightPanel;
    private static int posXM;
    private static int posYM;

    public static int[] monster(char[][] maze, ImageIcon floorIcon, ImageIcon monsterIcon) {
        int dxM = 0, dyM = 0;
        int[] posM = new int[2];
        Random r = new Random();
        int n = r.nextInt(4);
        if(n==0){
            dxM = 1;
            dyM = 0;
        }else if(n==1){
            dxM = 0;
            dyM = 1;
        }else if(n==2){
            dxM = -1;
            dyM = 0;
        }else{
            dxM = 0;
            dyM = -1;
        }
        int[] newPositionM = Maze.setPosition(dxM, dyM, posXM, posYM, maze.length, maze[0].length);
        if (maze[newPositionM[1]][newPositionM[0]] != '#') {
            mazeCells[posYM][posXM].setIcon(floorIcon);
            posXM = newPositionM[0];
            posYM = newPositionM[1];
            maze[posYM][posXM] = 'M';
            posM[0] = posYM;
            posM[1] = posXM;
            mazeCells[posYM][posXM].setIcon(monsterIcon);
            spotlightPanel.repaint();
        }
        return posM;
    }
}