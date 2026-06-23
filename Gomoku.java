import javax.swing.*;
import javax.swing.JOptionPane;

public class Main {
	public static void main(String args[]) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Gomoku");
			BoardPanel boardPanel = new BoardPanel();
			frame.add(boardPanel);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
            
            /*
            Object[] playerOptions = {"Single Player", "Multiplayer"};
            int n = JOptionPane.showOptionDialog(frame,
            "Select an Option", "Player Options", JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, playerOptions, playerOptions[1]);
            */
		});
	}
}