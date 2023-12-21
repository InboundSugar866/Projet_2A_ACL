package Objects;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Instant extends Objects{
    private final JLabel[][] mazeCells;
    private final JPanel spotlightPanel;

    public Instant(JLabel[][] mazeCells, JPanel spotlightPanel) {
        super(mazeCells, spotlightPanel);
        this.mazeCells = mazeCells; // add this line
        this.spotlightPanel = spotlightPanel;
    }

    // method to move the character somewhere anywhere
    public int[] handleTrap(char[][] maze, int playerX, int playerY, ImageIcon floorIcon, ImageIcon characterIcon) {
        // Check if the player has hit a trap
        int[] newPosition = new int[2];
        List<int[]> availablePositions = new ArrayList<>();
        // Find all available positions
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == '!') {
                    availablePositions.add(new int[]{i, j});
                }
            }
        }
        // Randomly select a position from the available ones
        Random rand = new Random();

        int randomIndex = rand.nextInt(availablePositions.size());
        newPosition = availablePositions.get(randomIndex);
        // Move the player to the new position
        maze[playerY][playerX] = '!';
        mazeCells[playerY][playerX].setIcon(floorIcon);
        playerX = newPosition[1];
        playerY = newPosition[0];
        maze[playerY][playerX] = 'E';
        mazeCells[playerY][playerX].setIcon(characterIcon);
        spotlightPanel.repaint();

        return new int[]{newPosition[1], newPosition[0]};
    }
}
