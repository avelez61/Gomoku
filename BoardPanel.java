import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;

class BoardPanel extends JPanel {
	static final int SQUARE_SIZE = 32;
    private GameState gameState;

	public BoardPanel() {
		gameState = new GameState();
        final int BOARD_SIZE = gameState.BOARD_SIZE;
        setPreferredSize(new Dimension(BOARD_SIZE * SQUARE_SIZE, BOARD_SIZE * SQUARE_SIZE));
        
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (gameState.getWinState() != 0) { return; }
				int col = (int) Math.floor((e.getX() / SQUARE_SIZE));
				int row = (int) Math.floor((e.getY() / SQUARE_SIZE));
				gameState.makeMove(row, col);
                repaint();
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
        
        String[][] board = gameState.getBoard();
        final String PLAYER_1_PIECE = gameState.PLAYER_1_PIECE;
        final String PLAYER_2_PIECE = gameState.PLAYER_2_PIECE;
        final int BOARD_SIZE = gameState.BOARD_SIZE;
        
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
}