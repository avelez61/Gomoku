import java.util.HashMap;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;

public class Main extends JPanel {
	public static void main(String args[]) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Gomoku");
			BoardPanel boardPanel = new BoardPanel();
			frame.add(boardPanel);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
		});
	}
}

class BoardPanel extends JPanel {
	// Constants
	static final String EMPTY = "*";
	static final String PLAYER_1_PIECE = "@";
	static final String PLAYER_2_PIECE = "O";
	static final String[] PLAYER_PIECES = {PLAYER_1_PIECE, PLAYER_2_PIECE};
	static final int PLAYER_1 = 0;
	static final int PLAYER_2 = 1;
	static final int BOARD_SIZE = 15;
	static final int SQUARE_SIZE = 32;
	static final HashMap<String, HashMap<String, Integer>> DIRECTIONS = new HashMap<>();
	private String[][] board;
	private int turn;
	private int winState;

	public BoardPanel() {
		setPreferredSize(new Dimension(BOARD_SIZE * SQUARE_SIZE, BOARD_SIZE * SQUARE_SIZE));

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

		board = new String[BOARD_SIZE][BOARD_SIZE];
		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				board[r][c] = EMPTY;
			}
		}
		turn = PLAYER_1;		
		winState = 0;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (winState != 0) { return; }

				int col = (int) Math.floor((e.getX() / SQUARE_SIZE));
				int row = (int) Math.floor((e.getY() / SQUARE_SIZE));

				if (board[row][col] == EMPTY) {
					board[row][col] = PLAYER_PIECES[turn];
					winState = checkWin(board, turn);
					turn = turn == PLAYER_1 ? PLAYER_2 : PLAYER_1;

					repaint();

					if (winState != 0) {
						System.out.println("Win State: " + winState);
					}
				}
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;

		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				if (board[r][c].equals(PLAYER_1_PIECE)) {
					graphics.setColor(Color.BLACK);
					graphics.fillOval(c * SQUARE_SIZE, r * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
				}
				else if (board[r][c].equals(PLAYER_2_PIECE)) {
					graphics.setColor(Color.WHITE);
					graphics.fillOval(c * SQUARE_SIZE, r * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
				}
				
			}
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