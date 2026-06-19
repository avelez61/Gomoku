import java.util.Scanner;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main extends JPanel {
	// Constants
	static final String EMPTY = "*";
	static final String PLAYER_1_PIECE = "@";
	static final String PLAYER_2_PIECE = "O";
	static final String[] PLAYER_PIECES = {PLAYER_1_PIECE, PLAYER_2_PIECE};
	static final int PLAYER_1 = 0;
	static final int PLAYER_2 = 1;
	static final int BOARD_SIZE = 15;	
	static final HashMap<String, HashMap<String, Integer>> DIRECTIONS = new HashMap<>();

	public static void main(String args[]) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Gomoku");
			BoardPanel boardPanel = new BoardPanel();
			frame.add(boardPanel);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(BOARD_SIZE * 32, BOARD_SIZE * 32);
			frame.setVisible(true);
		});

		Scanner scanner = new Scanner(System.in);
		
		// Set Direction Values
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

		// Init Board
		String[][] board = new String[BOARD_SIZE][BOARD_SIZE];
		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				board[r][c] = EMPTY;
			}
		}
		int turn = PLAYER_1;
		
		int winState = 0;
		while (winState == 0) {
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
				winState = checkWin(board, turn);
				turn = turn == PLAYER_1 ? PLAYER_2 : PLAYER_1;
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

	static boolean inBounds(int row, int col) {
		return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE; 
	}
	
	static int checkWin(String[][] board, int turn) {
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

class BoardPanel extends JPanel {
	public BoardPanel() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int col = Math.round((e.getX() / (float) 32));
				int row = Math.round((e.getY() / (float) 32));

				System.out.println("ROW: " + row);
				System.out.println("COL: " + col);
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
	}
}