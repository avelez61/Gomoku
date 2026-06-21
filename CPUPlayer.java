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
    
    public int[] selectMove(GameState gameState) {
        ArrayList<int []> moves = getMoves(gameState.getBoard());
        ArrayList<int []> candidateMoves = new ArrayList<>();
        
        GameState newState = gameState.deepCopy();
        newState.switchTurn();
        for (int[] move : moves) {
            newState.makeMove(move[0], move[1]);
            if (newState.getWinState() != 0) {
                candidateMoves.add(move);
            }
            newState.unMakeMove(move[0], move[1]);
        }
        
        Random random = new Random();
        if (candidateMoves.size() > 0) {
            int randomIndex = random.nextInt(candidateMoves.size());
            return candidateMoves.get(randomIndex);
        }
        else {
            int randomIndex = random.nextInt(moves.size());
            return moves.get(randomIndex);
        }
    }
}

