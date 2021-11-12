package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	public ClueGame() {


		
	}
	
	public static void main(String[] args) {
		
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
			
		JFrame frame = new JFrame();
		ClueGame center	= new ClueGame();  
		
		GameControlPanel panel = new GameControlPanel(); 
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
		

		
		
	}
}
