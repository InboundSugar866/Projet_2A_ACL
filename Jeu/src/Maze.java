import java.io.*;
import java.util.Scanner;

public class Maze {

    private static final char[][] maze = new char[20][50];

    public static void print () {

        System.out.println();

        for (char[] chars : maze) {
            for (char aChar : chars) System.out.print(aChar);
            System.out.println();
        }

        System.out.println();

    }  // method print_maze

    public static int[] setPosition(char direction, int posX, int posY, int maxY, int maxX) {
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

    public static void main(String[] args)  {

        Scanner niv = new Scanner(System.in);
        System.out.println("Choose the level : 1(easy), 2(medium), 3(hard) ");
        String niveau = niv.next();
        String fileName;
        switch (niveau) {
            case "1":
                fileName = "src/assets/maze1.txt";
                break;
            case "2":
                fileName = "src/assets/maze2.txt";
                break;
            case "3":
                fileName = "src/assets/maze3.txt";
                break;
            default:
                fileName = "";
                System.out.println("not in database");
                System. exit(0);
        }

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
            int[] newPosition = setPosition(direction, posX, posY, maze.length, maze[0].length);
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