package clueGame;

import javax.swing.JFrame;

public class ClueGame extends JFrame {
	public ClueGame() {
		
	}
	
	public static void main(String[] args) {
		
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		ClueGame frame = new ClueGame();  // create the frame 
		frame.setContentPane(Board.getInstance()); // put the panel in the frame
		frame.setSize(1000, 700);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
}
