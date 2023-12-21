package Game;

import java.util.*;

public class A_star {
    public static class Point {
        public int row;
        public int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    // Check if a cell (row, col) is a valid cell or not
    static boolean isValid(char[][] maze, int row, int col) {
        int rows = maze.length, cols = maze[0].length;
        return (row >= 1) && (row < rows) && (col >= 1) && (col < cols) && ((maze[row][col] == '!' || maze[row][col] == 'M' || maze[row][col] == 'E' || maze[row][col] == 'F'));
    } // AND (&&) OR (||)

    // Find the shortest path in binary maze from source to destination using BFS
    public static List<Point> shortestPathBFS(char[][] maze, Point src, Point dest) {
        int rows = maze.length, cols = maze[0].length;

        // Array to store the distance of each cell from the source
        int[][] dist = new int[rows][cols];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // Array to store the parent of each cell
        Point[][] parent = new Point[rows][cols];

        // The source cell has distance of 0 from itself
        dist[src.row][src.col] = 0;

        // Create a queue for BFS
        Queue<Point> q = new LinkedList<>();
        q.add(src); // Enqueue the source cell

        // Arrays to get row and column numbers of 4 neighbors of a cell
        int[] rowNum = {-1, 0, 1, 0};
        int[] colNum = {0, -1, 0, 1};

        // Perform BFS
        while (!q.isEmpty()) {
            Point curr = q.poll();

            // If we have reached the destination cell, we are done
            if (curr.row == dest.row && curr.col == dest.col) {
                List<Point> path = new ArrayList<>();
                Point node = curr;
                while (node != null) {
                    path.add(node);
                    node = parent[node.row][node.col];
                }
                Collections.reverse(path);
                return path;
            }

            // Visit all neighbors of the current cell
            for (int i = 0; i < 4; i++) {
                int row = curr.row + rowNum[i];
                int col = curr.col + colNum[i];

                // If the adjacent cell is valid, has a path, and not visited yet, enqueue it
                if (isValid(maze, row, col) && dist[row][col] > dist[curr.row][curr.col] + 1) {
                    // Update the distance of the neighbor cell from the source
                    dist[row][col] = dist[curr.row][curr.col] + 1;
                    parent[row][col] = curr; // Set the parent of the neighbor cell
                    q.add(new Point(row, col)); // Enqueue the neighbor cell
                }
            }
        }

        // Destination cannot be reached
        return null;
    }
}
