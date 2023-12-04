package Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Characters.Monster;

public class Affichage {
    private JLabel[][] mazeCells;

    private int posX, posY;
    private int posXM, posYM;
    private int finishX, finishY;
    private JPanel spotlightPanel;

    // Method that displays and draws the maze
    public Affichage(char[][] maze) {
        JFrame frame = initializeFrame(maze);
        ImageIcon[] icons = loadIcons();

        placeTraps(maze,10);
        populateMaze(maze, frame, icons);

        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e, maze, icons[1], icons[2],icons[4]);
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

        //startFrameRefresh(frame);

        return frame;
    }

    private void startFrameRefresh(JFrame frame) {
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.repaint();
            }
        });
        timer.start();
    }

    // Method to load the textures
    private ImageIcon[] loadIcons() {
        ImageIcon[] icons = new ImageIcon[6];
        icons[0] = new ImageIcon("src/assets/rock-wall2.png");
        icons[1] = new ImageIcon("src/assets/empty-cell.png");
        icons[2] = new ImageIcon("src/assets/character2.png");
        icons[3] = new ImageIcon("src/assets/end.png");
        icons[4] = new ImageIcon("src/assets/monster.png");
        icons[5] = new ImageIcon("src/assets/trap1.png");
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
        }else if (maze[i][j] == 'T') {
            cell.setIcon(icons[5]);
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
            } else if (maze[i][j] == 'M') {
                cell.setIcon(icons[4]);
                int[] monsterPosition = Maze.getPosition(maze, 'M', i, j);
                posXM = monsterPosition[0];
                posYM = monsterPosition[1];
            }
        }
    }

    // Method to make keyboard input
    private void handleKeyPress(KeyEvent e, char[][] maze, ImageIcon floorIcon, ImageIcon characterIcon, ImageIcon monsterIcon) {
        int dx = 0, dy = 0;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                dy = -1;
                break;
            case KeyEvent.VK_DOWN:
                dy = 1;
                break;
            case KeyEvent.VK_LEFT:
                dx = -1;
                break;
            case KeyEvent.VK_RIGHT:
                dx = 1;
                break;
        }
        int[] newPosition = Maze.setPosition(dx, dy, posX, posY, maze.length, maze[0].length);
        if (maze[newPosition[1]][newPosition[0]] != '#') {
            mazeCells[posY][posX].setIcon(floorIcon);
            posX = newPosition[0];
            posY = newPosition[1];
            mazeCells[posY][posX].setIcon(characterIcon);
            spotlightPanel.repaint();
            int[] newPlayerPosition = handleTrap(maze, posX, posY, floorIcon, characterIcon);
            posX = newPlayerPosition[0];
            posY = newPlayerPosition[1];
        }
        int[] posM = monster(maze, floorIcon, monsterIcon);
        if (Game.Tests.haswon(maze,posX,posY)){
            victorydisplay();
        }

        if (Game.Tests.hasdeath(maze,posX,posY, posM)){
            deathdisplay();
        }

    }



    private int[] monster(char[][] maze, ImageIcon floorIcon, ImageIcon monsterIcon) {
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


    /*
    // Method to return the character to the initial position after falling into the trap1
    private void trap1(char[][] maze, ImageIcon floorIcon, ImageIcon characterIcon, ImageIcon trapIcon) {
        for (int i = 0; i < 10; i++){
            int n = nombre();
            int[] newPositionM = Maze.setPosition(n, n, posXM, posYM, maze.length, maze[0].length);
            mazeCells[posYM][posXM].setIcon(floorIcon);
            posXM = newPositionM[0];
            posYM = newPositionM[1];
            System.out.println(posXM);
            System.out.println(posYM);
            mazeCells[posYM][posXM].setIcon(trapIcon);	// mettre trapIcon identique à floorIcon
            spotlightPanel.repaint();
            maze[newPositionM[1]][newPositionM[0]] = 'T';

        }

        int[] newPosition = Maze.setPosition(0, 0, 0, 0, maze.length, maze[0].length);
        if (maze[posY][posX] == 'T') {
            mazeCells[posY][posX].setIcon(floorIcon);
            posX = newPosition[0];
            posY = newPosition[1];
            mazeCells[posY][posX].setIcon(characterIcon);
            spotlightPanel.repaint();
        }
    }

    public static int nombre() {
        //return (int)((Math.random()*((30-1)+1))+1);
        return 12;
    }
     */

    private void placeTraps(char[][] maze, int numberOfTraps) {
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

    private int[] handleTrap(char[][] maze, int playerX, int playerY, ImageIcon floorIcon, ImageIcon characterIcon) {
        // Check if the player has hit a trap
        if (maze[playerY][playerX] == 'T') {
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
            if (!availablePositions.isEmpty()) {
                int randomIndex = rand.nextInt(availablePositions.size());
                int[] newPosition = availablePositions.get(randomIndex);
                // Move the player to the new position
                maze[playerY][playerX] = '!';
                mazeCells[playerY][playerX].setIcon(floorIcon);
                playerX = newPosition[1];
                playerY = newPosition[0];
                maze[playerY][playerX] = 'P';
                mazeCells[playerY][playerX].setIcon(characterIcon);
                spotlightPanel.repaint();
            }
        }
        return new int[]{playerX, playerY};
    }

    public static void victorydisplay () {

        JFrame f = new JFrame("VICTOIRE");
        f.setSize(400,200);

        JPanel pannel = new JPanel();
        JLabel jLabel1 =new JLabel("VICTOIRE");
        pannel.add(jLabel1);

        ImageIcon icone = new ImageIcon("book.gif");
        JLabel jLabel2 =new JLabel(icone);
        pannel.add(jLabel2);

        JLabel jLabel3 =new JLabel("Vous avez gagné",icone,SwingConstants.LEFT);
        pannel.add(jLabel3);

        f.getContentPane().add(pannel);
        f.setVisible(true);
    }

    public static void deathdisplay () {

        JFrame f = new JFrame("DEFAITE");
        f.setSize(400,200);

        JPanel pannel = new JPanel();
        JLabel jLabel1 =new JLabel("DEFAITE");
        pannel.add(jLabel1);

        ImageIcon icone = new ImageIcon("book.gif");
        JLabel jLabel2 =new JLabel(icone);
        pannel.add(jLabel2);

        JLabel jLabel3 =new JLabel("Vous avez perdu",icone,SwingConstants.LEFT);
        pannel.add(jLabel3);

        f.getContentPane().add(pannel);
        f.setVisible(true);
    }
}