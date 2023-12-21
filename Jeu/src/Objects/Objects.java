package Objects;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Objects {
    final JLabel[][] mazeCells;
    private final JPanel spotlightPanel;

    public Objects(JLabel[][] mazeCells, JPanel spotlightPanel) {
        this.mazeCells = mazeCells;
        this.spotlightPanel = spotlightPanel;
    }

    // method to place the traps randomly
    public static void placeTraps(char[][] maze, int numberOfTraps) {
        List<int[]> availablePositions = new ArrayList<>();
        // Find all positions with '!'
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == '!') {
                    availablePositions.add(new int[]{i, j});
                }
            }
        }

        // Randomly select a position from the available ones and place a 'T'
        Random rand = new Random();
        for (int i = 0; i < numberOfTraps; i++) {
            if (!availablePositions.isEmpty()) {
                int randomIndex = rand.nextInt(availablePositions.size());
                int[] selectedPosition = availablePositions.get(randomIndex);
                maze[selectedPosition[0]][selectedPosition[1]] = 'T';
                availablePositions.remove(randomIndex); // Remove the selected position from available positions
            }
        }
    }

    // method to place the fiole randomly
    public static void placeFiole(char[][] maze, int numberOfTraps) {
        List<int[]> availablePositions = new ArrayList<>();
        // Find all positions with '!'
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == '!') {
                    availablePositions.add(new int[]{i, j});
                }
            }
        }
        Random rand = new Random();
        for (int i = 0; i < numberOfTraps; i++) {
            if (!availablePositions.isEmpty()) {
                int randomIndex = rand.nextInt(availablePositions.size());
                int[] selectedPosition = availablePositions.get(randomIndex);
                maze[selectedPosition[0]][selectedPosition[1]] = 'L';
                availablePositions.remove(randomIndex); // Remove the selected position from available positions
            }
        }
    }
}
