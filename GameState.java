import java.util.HashMap;

class GameState {
    static final String EMPTY = " ";
    static final String PLAYER_1_PIECE = "@";
	static final String PLAYER_2_PIECE = "O";
	static final String[] PLAYER_PIECES = {PLAYER_1_PIECE, PLAYER_2_PIECE};
    static final int BOARD_SIZE = 15;
    static final int PLAYER_1 = 0;
    static final int PLAYER_2 = 1;
    static final HashMap<String, HashMap<String, Integer>> DIRECTIONS = new HashMap<>();
    
    private String[][] board;
    private int turn;
    private int winState;
    
    public GameState() {
		DIRECTIONS.put("HORIZONTAL", new HashMap<>());
		DIRECTIONS.get("HORIZONTAL").put("ROW_CHANGE", 0);
		DIRECTIONS.get("HORIZONTAL").put("COL_CHANGE", 1);

		DIRECTIONS.put("VERTICAL", new HashMap<>());
		DIRECTIONS.get("VERTICAL").put("ROW_CHANGE", 1);
		DIRECTIONS.get("VERTICAL").put("COL_CHANGE", 0);

		DIRECTIONS.put("ANTI_DIAGONAL", new HashMap<>());
		DIRECTIONS.get("ANTI_DIAGONAL").put("ROW_CHANGE", 1);
		DIRECTIONS.get("ANTI_DIAGONAL").put("COL_CHANGE", -1);

		DIRECTIONS.put("DIAGONAL", new HashMap<>());
		DIRECTIONS.get("DIAGONAL").put("ROW_CHANGE", 1);
		DIRECTIONS.get("DIAGONAL").put("COL_CHANGE", 1);
        
        board = new String[BOARD_SIZE][BOARD_SIZE];
		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				board[r][c] = EMPTY;
			}
		}
        turn = PLAYER_1;
        winState = 0;
    }
    
    public String[][] getBoard() {
        String [][] deepCopy = new String[board.length][board[0].length];
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                deepCopy[r][c] = board[r][c];
            }
        }
        
        return deepCopy;
    }
    
    public int getWinState() {
        return winState;
    }   
    
    public void makeMove(int row, int col) {
        if (board[row][col] == EMPTY) {
            board[row][col] = PLAYER_PIECES[turn];
            winState = checkWin(board, turn);
            turn = turn == PLAYER_1 ? PLAYER_2 : PLAYER_1;

            if (winState != 0) {
                System.out.println("Win State: " + winState);
            }
        }
    }
    
    private static boolean inBounds(int row, int col) {
		return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE; 
	}

	private static int checkWin(String[][] board, int turn) {
		String playerPiece = PLAYER_PIECES[turn];
		boolean isFull = true;

		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				for (HashMap<String, Integer> direction : DIRECTIONS.values()) {
					int targetRow = r;
					int targetCol = c;
					int inARow = 0;

					while (inBounds(targetRow, targetCol) && board[targetRow][targetCol].equals(playerPiece)) {
						inARow++;
						targetRow += direction.get("ROW_CHANGE");
						targetCol += direction.get("COL_CHANGE");

						if (inARow == 5) {
							return turn + 1;
						}
					}
				}

				if (board[r][c] == EMPTY) {
					isFull = false;
				}
			}
		}
		
		if (isFull) {
			return 3;
		}
		else {
			return 0;
		}
	}
}