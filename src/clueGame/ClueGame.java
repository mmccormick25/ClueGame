package clueGame;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame {

	public static JFrame frame;
	
	static boolean gameWon = false;
	private static boolean nextPressed = false;
	public static GameControlPanel controlPanel = new GameControlPanel(); 
	public static GameSidePanel sidePanel;
	
	public ClueGame() {
		super();
	}
	
	public static void main(String[] args) {
		
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		sidePanel = new GameSidePanel();
			
		frame = new JFrame("Clue Game");
		
		JOptionPane.showMessageDialog(frame,"You are " + board.players.get(0).getName() + "\nCan you find the solution \nbefore the computer players?");
		
		controlPanel.setTurn(board.players.get(0), 5);
		
		sidePanel.setDisplayPlayer(board.players.get(0));
		
		frame.setSize(800, 600);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		frame.setLayout(new BorderLayout());
		frame.add(board,BorderLayout.CENTER);
	
		frame.add(controlPanel,BorderLayout.SOUTH);
		frame.add(sidePanel,BorderLayout.EAST);
		frame.pack();
		
		// Running first turn, this is usually done by pressing next button
		board.runTurn();
	}

}
