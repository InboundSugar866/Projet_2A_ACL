import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;

public class test {
    private static final char[][] maze = new char[20][50];

    public static void main(String[] args) {
        // Example usage
        String fileName = "src/assets/maze3.txt";
        //File file = new File("maze3.txt");
        try {
            String readline;

            FileReader fileReader =
                    new FileReader(fileName);

            BufferedReader br =
                    new BufferedReader(fileReader);

            int line = 0;
            while((readline = br.readLine()) != null) {
                //System.out.println(readline); //loads the maze

                char[] charArr = readline.toCharArray();
                maze[line] = charArr;  // error here

                line++;
            }

            br.close();
        }

        // errors while reading the file
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");

        }
        char[] arr = {'#', '!', '#', '!', '!'};
        createPanels(maze);
    }

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
}
