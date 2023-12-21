package Objects;

import Maze.Affichage;

import javax.swing.*;

public class Permanent extends Objects {
    private final JLabel[][] mazeCells;
    private JPanel spotlightPanel;

    public Permanent(JLabel[][] mazeCells, JPanel spotlightPanel) {
        super(mazeCells, spotlightPanel);
        this.mazeCells = mazeCells; // add this line
        this.spotlightPanel = spotlightPanel;
    }

    public void handleFiole(char[][] maze, int playerX, int playerY){
        //Affichage.setRad(8);
        Affichage.spotlightPanel = null;
        //Affichage.createSpotlight(Affichage.frame, maze, 3);
        //Affichage.spotlightPanel.repaint();
    }
}
