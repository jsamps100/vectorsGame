package game;

import java.util.Random;

public class AI {

    private Random r;
    public AI() {

        r = new Random();
    }

    public int[] chooseMove(Board board) {
        int row = r.nextInt(8);
        int col = r.nextInt(8);

        while (!board.isValidMove(col, row)) {
            row = r.nextInt(8);
            col = r.nextInt(8);
        }

        return new int[] {row, col};
    }

}