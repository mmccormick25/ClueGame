package clueGame;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	static boolean gameWon = false;
	private static boolean nextPressed = false;
	static GameControlPanel panel = new GameControlPanel(); 
	
	public ClueGame() {

		
	}
	
	public static void main(String[] args) {
		
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
			
		JFrame frame = new JFrame();
		ClueGame center	= new ClueGame(); 
		
		JOptionPane.showMessageDialog(frame,"You are " + board.players.get(0).getName() + "\nCan you find the solution \nbefore the computer players?");
		
		panel.setTurn(board.players.get(0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
		
		GameSidePanel side= new GameSidePanel();
		
		frame.setSize(800, 700);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		frame.setLayout(new BorderLayout());
		frame.add(board,BorderLayout.CENTER);
	
		frame.add(panel,BorderLayout.SOUTH);
		frame.add(side,BorderLayout.EAST);
		
		side.setSize(frame.WIDTH-board.WIDTH,board.HEIGHT);
		
		board.calcTargets(board.grid[6][6], 3);
		
		board.runTurn();
	}

}
