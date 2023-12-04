package Game;

public class Tests {
    public static boolean haswon (char[][] maze, int posX, int posY){
        return maze[posY][posX]=='S';
    }

    public static boolean hasdeath (char[][] maze, int posX, int posY, int[] posM) {
        int posYM = posM[0];
        int posXM = posM[1];
        if (posX == posXM && posY == posYM) {
            return true;
        }
        else {
            return false;
        }
    }
}
