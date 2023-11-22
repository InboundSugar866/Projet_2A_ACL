package Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Affichage {
    private JLabel[][] mazeCells;
    private int posX, posY;
    private int finishX, finishY;
    private JPanel spotlightPanel;

    // Method that displays and draws the maze
    public Affichage(char[][] maze) {
        JFrame frame = initializeFrame(maze);
        ImageIcon[] icons = loadIcons();

        populateMaze(maze, frame, icons);

        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e, maze, icons[1], icons[2]);
            }
        });

        createSpotlight(frame, maze);
        finalizeFrame(frame);
    }

    // Method to initialize the frame containing the 2D-maze
    private JFrame initializeFrame(char[][] maze) {
        JFrame frame = new JFrame("Maze Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(maze.length, maze[0].length));
        mazeCells = new JLabel[maze.length][maze[0].length];
        return frame;
    }

    // Method to load the textures
    private ImageIcon[] loadIcons() {
        ImageIcon[] icons = new ImageIcon[4];
        icons[0] = new ImageIcon("src/assets/rock-wall2.png");
        icons[1] = new ImageIcon("src/assets/empty-cell.png");
        icons[2] = new ImageIcon("src/assets/character2.png");
        icons[3] = new ImageIcon("src/assets/end.png");
        return icons;
    }

    // Method to draw the maze with the appropriate texture
    private void populateMaze(char[][] maze, JFrame frame, ImageIcon[] icons) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                JLabel cell = new JLabel();
                mazeCells[i][j] = cell;
                setCellIcon(maze, icons, i, j, cell);
                frame.add(cell);
            }
        }
    }

    // Method used to correlate the texture to the txt file
    private void setCellIcon(char[][] maze, ImageIcon[] icons, int i, int j, JLabel cell) {
        if (maze[i][j] == '#') {
            cell.setIcon(icons[0]);
        } else if (maze[i][j] == 'S') {
            cell.setIcon(icons[3]);
            int[] finishPosition = Maze.getPosition(maze, 'S', i, j);
            finishX = finishPosition[0];
            finishY = finishPosition[1];
        } else {
            cell.setIcon(icons[1]);
            if (maze[i][j] == 'E') {
                cell.setIcon(icons[2]);
                int[] characterPosition = Maze.getPosition(maze, 'E', i, j);
                posX = characterPosition[0];
                posY = characterPosition[1];
            }
        }
    }

    // Method to make keyboard input
    private void handleKeyPress(KeyEvent e, char[][] maze, ImageIcon floorIcon, ImageIcon characterIcon) {
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
            spotlightPanel.repaint();
        }
    }

    // Method to make the Affichage method simpler by finalizing the frame elsewhere
    private void finalizeFrame(JFrame frame) {
        frame.pack();
        frame.setVisible(true);
        frame.requestFocusInWindow();
        frame.setResizable(false);
    }

    // Method to create the spotlight effect around the player and the finish
    private void createSpotlight(JFrame frame, char[][] maze) {
        spotlightPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                // Create a BufferedImage for the spotlight effect
                BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D gImg = img.createGraphics();

                // Draw a black layer on the BufferedImage
                gImg.setColor(Color.BLACK);
                gImg.fillRect(0, 0, getWidth(), getHeight());

                // Clear a circle at the spotlight location
                gImg.setComposite(AlphaComposite.Clear);
                int radius = Math.min(getWidth(), getHeight()) / 5; // Increase this value to make the circle bigger
                gImg.fill(new Ellipse2D.Double(posX * getWidth() / maze[0].length + getWidth() / (2 * maze[0].length) - radius, posY * getHeight() / maze.length + getHeight() / (2 * maze.length) - radius, 2 * radius, 2 * radius));
                gImg.fill(new Ellipse2D.Double(finishX * getWidth() / maze[0].length + getWidth() / (2 * maze[0].length) - radius, finishY * getHeight() / maze.length + getHeight() / (2 * maze.length) - radius, 2 * radius, 2 * radius));

                // Draw the rest of the image black
                Area outer = new Area(new Rectangle(0, 0, img.getWidth(), img.getHeight()));
                int x = (img.getWidth() / 4);
                int y = (img.getHeight() / 4);
                Rectangle2D.Double inner = new Rectangle2D.Double(0, 0, img.getWidth(), img.getHeight());
                outer.subtract(new Area(inner)); // remove the ellipse from the original area
                gImg.fill(outer);

                gImg.dispose();

                // Draw the BufferedImage on the panel
                g2d.drawImage(img, 0, 0, null);
            }
        };
        spotlightPanel.setOpaque(false);
        frame.setGlassPane(spotlightPanel);
        spotlightPanel.setVisible(true);
    }
}



