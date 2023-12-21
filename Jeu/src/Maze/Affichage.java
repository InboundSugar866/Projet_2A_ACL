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

import Characters.Characters;
import Characters.Monster;
import Characters.Ghost;
import Objects.Instant;
import Characters.Hero;
import Objects.Permanent;

public class Affichage {
    public static Instant Trap;
    public static Permanent Fiole;
    private static JLabel[][] mazeCells;

    public static int posX;
    public static int posY;
    private int posXM, posYM;
    private static int finishX;
    private static int finishY;
    public static JPanel spotlightPanel;
    public static Ghost monsterMaze2;
    public static Hero Hero;
    private int NoT;
    public static int rad;
    private Instant trap1;
    public static JFrame frame;
    public static Timer timer;

    // Method that displays and draws the maze
    public Affichage(char[][] maze, int niveau) {

        switch (niveau) {
            case 1:
                NoT = 10;
                rad = 3;
                break;
            case 2:
                NoT = 20;
                rad = 4;
                break;
            case 3:
                NoT = 40;
                rad = 5;
                break;
        }

        frame = initializeFrame(maze);
        ImageIcon[] icons = loadIcons();

        createSpotlight(frame, maze, rad);

        Trap = new Instant(mazeCells, spotlightPanel);
        Trap.placeTraps(maze,NoT);

        Fiole = new Permanent(mazeCells, spotlightPanel);
        Fiole.placeFiole(maze,NoT/10);

        Hero = new Hero(mazeCells, spotlightPanel);
        Hero.placePlayer(maze, 'E');

        Characters.placeEnd(maze, 'S');

        // add as many monsters as one like
        MonsterManager monsterManager = new MonsterManager(icons[1], icons[4], icons[9]);
        monsterManager.addMonster(new Monster(mazeCells, spotlightPanel));
        monsterManager.addMonster(new Monster(mazeCells, spotlightPanel));
        monsterManager.addMonster(new Monster(mazeCells, spotlightPanel));
        monsterManager.addMonster(new Monster(mazeCells, spotlightPanel));
        monsterManager.addMonster(new Monster(mazeCells, spotlightPanel));

        monsterManager.placeMonsters(maze, 'M');

        monsterMaze2 = new Ghost(mazeCells, spotlightPanel);
        monsterMaze2.placeCharacter(maze, 'G');


        populateMaze(maze, frame, icons);

        timer = new Timer(1000,new ActionListener(){
            public void actionPerformed(ActionEvent e){
                monsterManager.moveMonsters(maze);
                monsterMaze2.ghost(maze,icons[1],icons[8],icons[6],icons[10],icons[11],icons[0]);
            }
        });
        timer.start();

        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                Hero.handleKeyPress(e, maze, icons[1], icons[2],icons[7]);
            }
        });
        finalizeFrame(frame);
    }

    // Method to initialize the frame containing the 2D-maze
    public static JFrame initializeFrame(char[][] maze) {
        JFrame frame = new JFrame("Maze Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(maze.length, maze[0].length));
        mazeCells = new JLabel[maze.length][maze[0].length];
        return frame;
    }

    // Method to load the textures
    private ImageIcon[] loadIcons() {
        ImageIcon[] icons = new ImageIcon[13];
        icons[0] = new ImageIcon("src/assets/rock-wall2.png");
        icons[1] = new ImageIcon("src/assets/empty-cell.png");
        icons[2] = new ImageIcon("src/assets/character2.png");
        icons[3] = new ImageIcon("src/assets/end.png");
        icons[4] = new ImageIcon("src/assets/Goomba.png");
        icons[5] = new ImageIcon("src/assets/trap1.png");
        icons[6] = new ImageIcon("src/assets/monsterR.png");
        icons[7] = new ImageIcon("src/assets/characterR.png");
        icons[8] = new ImageIcon("src/assets/monster.png");
        icons[9] = new ImageIcon("src/assets/GoombaR.png");
        icons[10] = new ImageIcon("src/assets/monster-rock-wall2.png");
        icons[11] = new ImageIcon("src/assets/monster-rock-wallR.png");
        icons[12] = new ImageIcon("src/assets/Potion-Lumos.png");
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
        }else if (maze[i][j] == 'L') {
            cell.setIcon(icons[12]);
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
            }else if (maze[i][j] == 'F') {
                    cell.setIcon(icons[7]);
                    int[] characterPosition = Maze.getPosition(maze, 'F', i, j);
                    posX = characterPosition[0];
                    posY = characterPosition[1];
            } else if (maze[i][j] == 'M') {
                cell.setIcon(icons[4]);
                int[] monsterPosition = Maze.getPosition(maze, 'M', i, j);
                posXM = monsterPosition[0];
                posYM = monsterPosition[1];
            }else if (maze[i][j] == 'G') {
                cell.setIcon(icons[8]);
                int[] ghostPosition = Maze.getPosition(maze, 'G', i, j);
            }
        }
    }

    // Method to make the Affichage method simpler by finalizing the frame elsewhere
    private void finalizeFrame(JFrame frame) {
        frame.pack();
        frame.setVisible(true);
        frame.requestFocusInWindow();
        frame.setResizable(false);
    }

    public static void setRad(int newRad) {
        rad = newRad;
        spotlightPanel.repaint();
    }

    // Method to create the spotlight effect around the player and the finish
    public static void createSpotlight(JFrame frame, char[][] maze, int rad) {
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
                int radius = Math.min(getWidth(), getHeight()) / rad; // Increase this value to make the circle bigger
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

    public static int choix(){
        Object[] options = {"facile", "moyen", "difficile"};
        int choix = JOptionPane.showOptionDialog(null, "Sélectionnez un niveau :", "Choix du niveau", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);;
        return choix+1;
    }

    public class MonsterManager {
        private List<Monster> monsters;
        private ImageIcon floorIcon, monsterIcon, monsterIconR;

        public MonsterManager(ImageIcon floorIcon, ImageIcon monsterIcon, ImageIcon monsterIconR) {
            this.monsters = new ArrayList<>();
            this.floorIcon = floorIcon;
            this.monsterIcon = monsterIcon;
            this.monsterIconR = monsterIconR;
        }

        public void addMonster(Monster monster) {
            this.monsters.add(monster);
        }

        public void moveMonsters(char[][] maze) {
            Monster monsterMaze = new Monster(mazeCells, spotlightPanel);
            monsterMaze.monster(maze,floorIcon,monsterIcon,monsterIconR);
        }

        public void placeMonsters(char[][] maze, char character) {
            for (Monster monster : monsters) {
                monster.placeCharacter(maze, character);
            }
        }
    }
}