package game;

public class Scoring {
	
    private int[][] scoredVectors = new int[256][6];
    private int scoredCount = 0;

    public Sequence[] sequences(char[] array) {
        if (array.length == 0) {
            return new Sequence[0];
        }
        Sequence[] sequences;
        int start = 0;
        int end = 0;
        int count = 0;

        while (end < array.length) {
            if (array[start] == array[end]) {
                end++;
            } else {
                count++;
                start = end;
            }
        }

        sequences = new Sequence[count + 1];

        start = 0;
        end = 0;
        count = 0;

        while (end < array.length) {
            if (array[start] == array[end]) {
                end++;
            }else {
                
            	sequences[count++] = new Sequence(start, end - 1, array[start]);
                start = end;
            }
        }

        sequences[count++] = new Sequence(start, end - 1, array[start]);
        return sequences;
    }

    public int calculatePoints(Board board, char token) {

        int points = 0;
        char[] array;

        for (int targetLength = 5;
             targetLength >= 3;
             targetLength--) {

            for (int row = 0; row < 8; row++) {
                
            	array = new char[8];
               
                for (int col = 0; col < 8; col++) {
                    array[col] = board.getPosition(col, row);
                }
                points += scoreSequences(array, token, row, 0, 0, 1, targetLength);
            }

            for (int col = 0; col < 8; col++) {
                
            	array = new char[8];
                
                for (int row = 0; row < 8; row++) {
                    array[row] = board.getPosition(col, row);
                }
                points += scoreSequences(array, token, 0, col, 1, 0, targetLength);
            }

            for (int col = 0; col < 8; col++) {
                points += checkDiagonal(board, 0, col, 1, 1, token, targetLength);
            }
            for (int row = 1; row < 8; row++) {
                points += checkDiagonal(board, row, 0, 1, 1, token, targetLength);
            }
            
            for (int col = 0; col < 8; col++) {
                points += checkDiagonal(board, 0, col, 1, -1, token, targetLength);
            }
            for (int row = 1; row < 8; row++) {
                points += checkDiagonal(board, row, 7, 1, -1, token, targetLength);
            }
        }
        return points;
    }

    
    public int checkDiagonal(Board board, int row, int col, int rowChange, int colChange, char token, int targetLength) {

        char[] array = new char[8];
        int count = 0;

        int startRow = row;
        int startCol = col;

        while (row >= 0 && row < 8 &&
               col >= 0 && col < 8) {
            array[count] = board.getPosition(col, row);
            count++;
            row += rowChange;
            col += colChange;
        }
        char[] diagonal = new char[count];
        for (int i = 0; i < count; i++) {
            diagonal[i] = array[i];
        }
        return scoreSequences(
            diagonal, token, startRow, startCol,
            rowChange, colChange, targetLength);
    }

    
    public int scoreSequences(char[] array, char token, int startRow, int startCol, int rowChange, int colChange, int targetLength) {
        Sequence[] found = sequences(array);
        int points = 0;
        for (int i = 0; i < found.length; i++) {
            int length = found[i].length;
            boolean correctLength =
                (targetLength == 5 && length >= 5) ||
                length == targetLength;
            if (found[i].ch == token && correctLength) {
                int row = startRow + found[i].start * rowChange;
                int col = startCol + found[i].start * colChange;
                int newPoints = getPoints(length);
                int[] current = {row, col, rowChange, colChange,length, newPoints};
                int overlapCount = 0;
                int previousIndex = -1;
                
                int j;
         
                for (j = 0; j < scoredCount; j++) {
                    if (overlaps(current, scoredVectors[j])) {
                        overlapCount++;
                        previousIndex = j;
                    }
                }

                if (overlapCount == 0) {
                    points += newPoints;
                    if (scoredCount < scoredVectors.length) {
                        scoredVectors[scoredCount] = current;
                        scoredCount++;
                    }
                } else if (overlapCount == 1) {
                    int[] previous = scoredVectors[previousIndex];
                    if (isExtension(current, previous)) {
                        int extraPoints = newPoints - previous[5];
                        if (extraPoints > 0) {
                            points += extraPoints;
                            scoredVectors[previousIndex] =
                                current;
                        }
                    }
                }
            }
        }

        return points;
    }

    
    
    public int getPoints(int length) {

        if (length == 3) {
            return 4;
        } else if (length == 4) {
            return 6;
        } else if (length >= 5) {
            return 10;
        }

        return 0;
    }

    
    public boolean overlaps(int[] a, int[] b) {
        for (int i = 0; i < a[4]; i++) {
            int rowA = a[0] + i * a[2];
            int colA = a[1] + i * a[3];
            for (int j = 0; j < b[4]; j++) {
                int rowB = b[0] + j * b[2];
                int colB = b[1] + j * b[3];
                if (rowA == rowB && colA == colB) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isExtension(int[] current, int[] previous) {
        if (current[2] != previous[2] || current[3] != previous[3]) {
            return false;
        }
        for (int i = 0; i < previous[4]; i++) {
            int row = previous[0] + i * previous[2];
            int col = previous[1] + i * previous[3];
            boolean found = false;
            
            for (int j = 0; j < current[4]; j++) {
                int newRow = current[0] + j * current[2];
                int newCol = current[1] + j * current[3];
                if (row == newRow && col == newCol) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                return false;
            }
        }
        return true;
    }
}