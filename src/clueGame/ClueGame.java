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
	public static GameControlPanel panel = new GameControlPanel(); 
	public static GameSidePanel side= new GameSidePanel();
	public ClueGame() {

		
	}
	
	public static void main(String[] args) {
		
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
			
		frame = new JFrame("Welcome to Clue Game");
		
		JOptionPane.showMessageDialog(frame,"You are " + board.players.get(0).getName() + "\nCan you find the solution \nbefore the computer players?");
		
		
		panel.setTurn(board.players.get(0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
		
		
		frame.setSize(800, 700);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		frame.setLayout(new BorderLayout());
		frame.add(board,BorderLayout.CENTER);
	
		frame.add(panel,BorderLayout.SOUTH);
		frame.add(side,BorderLayout.EAST);
		frame.pack();
		
		// Running first turn, this is usually done by pressing next button
		board.runTurn();
	}

}
