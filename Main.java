import java.util.Scanner;

public class Main {
	// Constants
	static final String EMPTY = "*";
	static final String PLAYER_1_PIECE = "@";
	static final String PLAYER_2_PIECE = "O";
	static final String[] PLAYER_PIECES = {PLAYER_1_PIECE, PLAYER_2_PIECE};
	static final int PLAYER_1 = 0;
	static final int PLAYER_2 = 1;
	static final int BOARD_SIZE = 15;
	
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		
		// Init Board
		String[][] board = new String[BOARD_SIZE][BOARD_SIZE];
		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				board[r][c] = EMPTY;
			}
		}
		int turn = PLAYER_1;
		
		boolean gameOver = false;
		while (!gameOver) {
			// Print Board
			for (int r = 0; r < BOARD_SIZE; r++) {
				for (int c = 0; c < BOARD_SIZE; c++) {
					System.out.print(board[r][c] + " ");
				}
				System.out.println();
			}
			
			// Get Player Move
			System.out.print("Enter a row to place a piece: ");
			int row = Integer.parseInt(scanner.nextLine());
			
			System.out.print("Enter a col to place a piece: ");
			int col = Integer.parseInt(scanner.nextLine());
			
			// Validate and Execute Player Move
			if (inBounds(row, col) && board[row][col] == EMPTY) {
				board[row][col] = PLAYER_PIECES[turn];
				turn = turn == PLAYER_1 ? PLAYER_2 : PLAYER_1;
			}
		}
	}
	
	static boolean inBounds(int row, int col) {
		return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE; 
	}
}