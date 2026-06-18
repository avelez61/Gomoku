public class Main {
	public static void main(String args[]) {
		final String EMPTY = "*";
		final String PLAYER_1_PIECE = "@";
		final String PLAYER_2_PIECE = "O";
		final int BOARD_SIZE = 15;
		
		// Init Board
		String[][] board = new String[BOARD_SIZE][BOARD_SIZE];
		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				board[r][c] = EMPTY;
			}
		}
		
		// Print Board
		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				System.out.print(board[r][c] + " ");
			}
			System.out.println();
		}
	}
}