package Characters;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Characters {
    final JLabel[][] mazeCells;
    protected static int posXM;
    protected static int posYM;
    protected static int posXG;
    protected static int posYG;

    public Characters(JLabel[][] mazeCells) {
        this.mazeCells = mazeCells;
    }

    // method to place a character somewhere randomly on the maze
    public static void placeCharacter(char[][] maze, char character) {
        List<int[]> availablePositions = new ArrayList<>();
        // Find all positions with '!'
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == '!') {
                    availablePositions.add(new int[]{i, j});
                }
            }
        }

        // Randomly select a position from the available ones and place an 'M'
        Random rand = new Random();
        if (!availablePositions.isEmpty()) {
            int randomIndex = rand.nextInt(availablePositions.size());
            int[] selectedPosition = availablePositions.get(randomIndex);
            maze[selectedPosition[0]][selectedPosition[1]] = character;
            availablePositions.remove(randomIndex); // Remove the selected position from available positions
        }
    }

    // method to place the player somewhere randomly on the upper left corner of the maze
    public static void placePlayer(char[][] maze, char character) {
        List<int[]> availablePositions = new ArrayList<>();
        // Find all positions with '!'
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (maze[i][j] == '!') {
                    availablePositions.add(new int[]{i, j});
                }
            }
        }

        // Randomly select a position from the available ones and place an 'M'
        Random rand = new Random();
        if (!availablePositions.isEmpty()) {
            int randomIndex = rand.nextInt(availablePositions.size());
            int[] selectedPosition = availablePositions.get(randomIndex);
            maze[selectedPosition[0]][selectedPosition[1]] = character;
            availablePositions.remove(randomIndex); // Remove the selected position from available positions
        }
    }

    // method to place the end flag somewhere randomly on the lower right corner of the maze
    public static void placeEnd(char[][] maze, char character) {
        List<int[]> availablePositions = new ArrayList<>();
        // Find all positions with '!'
        for (int i = 15; i < maze.length; i++) {
            for (int j = 30; j < maze[0].length; j++) {
                if (maze[i][j] == '!') {
                    availablePositions.add(new int[]{i, j});
                }
            }
        }

        // Randomly select a position from the available ones and place an 'M'
        Random rand = new Random();
        if (!availablePositions.isEmpty()) {
            int randomIndex = rand.nextInt(availablePositions.size());
            int[] selectedPosition = availablePositions.get(randomIndex);
            maze[selectedPosition[0]][selectedPosition[1]] = character;
            availablePositions.remove(randomIndex); // Remove the selected position from available positions
        }
    }

    public static double calculateDistance(int charX, int charY, int monsterX, int monsterY) {
        int dx = charX - monsterX;
        int dy = charY - monsterY;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
