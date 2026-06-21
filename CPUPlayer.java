import java.util.ArrayList;
import java.util.Random;

class CPUPlayer {
    private ArrayList<int[]> getMoves(String[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();
        
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c] == GameState.EMPTY) {
                    int move[] = {r, c};
                    moves.add(move);
                }
            }
        }

        return moves;
    }
    
    public int[] selectMove(String[][] board) {
        ArrayList<int []> moves = getMoves(board);
        
        Random random = new Random();
        int randomIndex = random.nextInt(moves.size());
        
        return moves.get(randomIndex);
    }
    
    private class Move {
        int row;
        int col;
        
        public Move(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}

