import java.io.*;
import java.util.Scanner;

public class Maze {

    private static char[][] maze = new char[20][50];
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

    public static int[] moveCharacter(char direction, int posX, int posY, int maxY, int maxX) {
        switch (direction) {
            case 'z':
                posY = Math.max(0, posY - 1);
                break;
            case 's':
                posY = Math.min(maxY - 1, posY + 1);
                break;
            case 'q':
                posX = Math.max(0, posX - 1);
                break;
            case 'd':
                posX = Math.min(maxX - 1, posX + 1);
                break;
        }
        return new int[]{posX, posY};
    }  // method move_character

    public static int[] getPosition(char[][] file, char target) {
        for (int y = 0; y < file.length; y++) {
            for (int x = 0; x < file[y].length; x++) {
                if (file[y][x] == target) {
                    return new int[]{x, y};
                }
            }
        }
        return new int[]{-1, -1};  // Return -1, -1 if target is not found
    }  // method getPosition


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

        print();


        // Get the initial position of 'X'
        int[] position = getPosition(maze, 'E');
        int posX = position[0], posY = position[1];

        Scanner scanner = new Scanner(System.in);
        while (maze[posY][posX] != 'S') {
            System.out.println("Enter direction (z for up, s for down, q for left, d for right): ");
            char direction = scanner.next().charAt(0);

            // Clear current position
            maze[posY][posX] = '!';

            // Move the character
            int[] newPosition = moveCharacter(direction, posX, posY, maze.length, maze[0].length);
            // Check if the new position is occupied by '#'
            if (maze[newPosition[1]][newPosition[0]] == '#') {
                System.out.println("Not possible");
                // Set 'X' back to its original position
               maze[posY][posX] = 'E';
            } else if (maze[newPosition[1]][newPosition[0]] == 'S') {
                System.out.println("You've reached 'S'!");
                break;
            } else {
                maze[posY][posX] = '!';
                posX = newPosition[0];
                posY = newPosition[1];
                // Set new position
                maze[posY][posX] = 'E';
            }

            print();
        }
    }
}