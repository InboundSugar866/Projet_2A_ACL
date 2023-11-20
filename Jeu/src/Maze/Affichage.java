package Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Affichage {
    public static void createPanels(char[][] arr) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(arr.length, arr[0].length));

        for (char[] row : arr) {
            for (char c : row) {
                if (c == '#') {
                    JPanel panel = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            Image wall = new ImageIcon("src/assets/rock-wall2.png").getImage();;
                            g.drawImage(wall, 0, 0, 32, 32, null);
                        }
                    };
                    panel.setPreferredSize(new Dimension(32, 32));
                    frame.add(panel);
                } else if (c == '!') {
                    JPanel panel = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            Image wall = new ImageIcon("src/assets/empty-cell.png").getImage();;
                            g.drawImage(wall, 0, 0, 32, 32, null);
                        }
                    };
                    panel.setPreferredSize(new Dimension(32, 32));
                    frame.add(panel);
                } else if (c == 'E') {
                    JPanel panel = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            Image wall = new ImageIcon("src/assets/character2.png").getImage();;
                            g.drawImage(wall, 0, 0, 32, 32, null);
                        }
                    };
                    panel.setPreferredSize(new Dimension(32, 32));
                    frame.add(panel);
                } else if (c == 'S') {
                    JPanel panel = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            Image wall = new ImageIcon("src/assets/end.png").getImage();;
                            g.drawImage(wall, 0, 0, 32, 32, null);
                        }
                    };
                    panel.setPreferredSize(new Dimension(32, 32));
                    frame.add(panel);
                } else if (c == ' '){
                    //do nothing
                }
            }
        }
        frame.pack();
        frame.setVisible(true);
    }
    public static JButton createButton(final JFrame frame,final char [][] Maze) {
        JButton button = new JButton("Start");

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createPanels(Maze);
                frame.dispose();
            }
        });

        return button;
    }
}
