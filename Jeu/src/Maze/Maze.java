package Maze;

import java.io.*;

public class Maze {
    private static final char[][] maze = new char[20][50];

    // Method to set the position of a character in the maze
    public static int[] setPosition(int dx, int dy, int posX, int posY, int mazeHeight, int mazeWidth) {
        int newX = posX + dx;
        int newY = posY + dy;
        if (newX >= 0 && newX < mazeWidth && newY >= 0 && newY < mazeHeight) {
            return new int[]{newX, newY};
        } else {
            return new int[]{posX, posY};  // Return the original position if the new position is out of bounds
        }
    }

    // Method to get the position of a character in the maze
    public static int[] getPosition(char[][] maze, char character, int i, int j) {
        int[] position = new int[2];
        if (maze[i][j] == character) {
            position[0] = j;
            position[1] = i;
        }
        return position;
    }

    // Method to load the maze from a txt file
    public static char[][] loadMaze(String fileName) {
        try {
            String readline;

            FileReader fileReader =
                    new FileReader(fileName);

            BufferedReader br =
                    new BufferedReader(fileReader);

            int line = 0;
            while ((readline = br.readLine()) != null) {
                char[] charArr = readline.toCharArray();
                maze[line] = charArr;
                line++;
            }

            br.close();
        }

        // errors while reading the file
        catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");

        }
        return maze;
    }
}