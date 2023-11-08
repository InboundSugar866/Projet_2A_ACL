import java.io.*;

public class Maze {

    private static char[][] maze = new char[20][20];
    //private static char[][] maze;
    private static int rows = 0;
    private static int columns = 0;


    public static void print () {

        System.out.println();

        for (int r=0; r < maze.length; r++) {
            for (int c=0; c< maze[r].length; c++)
                System.out.print (maze[r][c]);
            System.out.println();
        }

        System.out.println();

    }  // method print_maze


    public static void main(String[] args) throws IOException  {

        String fileName = "maze.txt";

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
        print();
    }
}