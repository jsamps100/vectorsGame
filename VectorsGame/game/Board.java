package game;


public class Board {

    private playerId[][] grid;
    public enum playerId {

        human('X'), comp('C'), unowned(' ');

        char id;

        playerId(char id) {
            this.id = id;
        }
    }

    public Board() {

        grid = new playerId[8][8];
        int row;
        int col;

        for (row = 0; row < 8; row++) {
            for (col = 0; col < 8; col++) {
                grid[row][col] = playerId.unowned;
            }
        }
    }

    public void displayBoard() {

        System.out.println("  0   1   2   3   4   5   6   7");
        System.out.println("---------------------------------");
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                System.out.printf("| %c ", grid[row][col].id);

            }

            System.out.println("| " + row);
            System.out.println("---------------------------------");
        }
    }

    public boolean isValidMove(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return false;
        }
        if (grid[y][x] == playerId.unowned) {
            return true;
        }

        return false;
    }

    public void placeMove(int x, int y, playerId isHuman) {
        if (isValidMove(x, y)) {
            grid[y][x] = isHuman;
        } 
    }
    public void aiPlaceMove(int[] coords, playerId isComp) {
        if (isValidMove(coords[0], coords[1])) {
            grid[coords[1]][coords[0]] = isComp;
        }
    }
    public char getPosition(int x, int y) {
        return grid[y][x].id;
    }
}