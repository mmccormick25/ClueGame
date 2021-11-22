package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	private JLabel whoseTurn,Roll;
	private JTextField turn,roll,guess,guessResult;
	private JButton accusation;
	private static JButton next;
	public boolean isFirstPlayer;
	
	Board board = Board.getInstance();

	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		
		// set the whole panel to top and bottom.
		setLayout(new GridLayout(2,0,10,10));
	// create the top panel by 1x4
		JPanel top = new JPanel(new GridLayout(1,4,5,50));
	// create the contents for uppderleft1 panel
		JPanel upperLeft1 = new JPanel(new GridLayout(2,1));
		whoseTurn = new JLabel("Whose turn?",SwingConstants.CENTER);
		turn = new JTextField();
		upperLeft1.add(whoseTurn);
		upperLeft1.add(turn);

		// create the contents for uppderleft2 panel
		JPanel upperLeft2 = new JPanel(new GridLayout(1,2));
		Roll = new JLabel("Roll:",SwingConstants.RIGHT);
		roll = new JTextField();
		roll.setSize(new Dimension(10,20));
		upperLeft2.add(Roll);
		upperLeft2.add(roll);

		
		
		// create 2 buttons
		Clicklistener click = new Clicklistener();
		
		accusation = new JButton("Make Accusation");
		next = new JButton("NEXT!");
		
		next.addActionListener(click);
		accusation.addActionListener(click);
		
		top.add(upperLeft1);
		top.add(upperLeft2);
		top.add(accusation);
		top.add(next);
		
		// create the bottoom panel and fill the contents.
		JPanel bottom = new JPanel(new GridLayout(0,2));
		guess = new JTextField();
		guess.setBorder(new TitledBorder (new EtchedBorder(),"Guess"));
		bottom.add(guess);
	
		
		
		guessResult = new JTextField();
		guessResult.setBorder(new TitledBorder (new EtchedBorder(),"Guess Result"));
		bottom.add(guessResult);
		
		add(top);
		add(bottom);
		
		
	

	}
	
	public String getTurn() {
		return turn.getText();
	}




	public String getRoll() {
		return roll.toString();
	}

	// ClickListener listens to when next and accusation buttons are pressed
	class Clicklistener implements ActionListener {
		public accusationDialog a;
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == next)
			{
				// Checking if player hasn't moved and it is human players turn
				if (board.currentPlayerIndex == 1) {
					if (board.notMoved) {
						JOptionPane.showMessageDialog(null, "You must move before ending your turn.");	
					} else if (!board.suggestMade) {
						JOptionPane.showMessageDialog(null, "You must make a suggestion or press cancel.");
					} else if (!board.accMade) {
						JOptionPane.showMessageDialog(null, "You must make an accusation or press cancel.");
					} else {
						board.nextPressed();
					}
				} else {
					board.nextPressed();
				}
			}
			if (e.getSource() == accusation)
			{
				board.accMade = false;
				a = new accusationDialog();
			}
		}
	}
	

	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard","orange",0, 0),5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
		
		while (true) {
			if(next.getModel().isArmed()) {
		        System.out.println("the button is pressed");
			}
		}
	}




	// set the content of guess text box 
	public void setGuess(String s) {
		guess.setText(s);
	}
	
	// set the content of guess result text box 
	public void setGuessResult(String gResult) {
		guessResult.setText(gResult);
		
	}
	// set the content of computer player and roll text box 
	public void setTurn(Player player, int i) {
		turn.setText(player.getName());	
		roll.setText(String.valueOf(i));
		
	}
	
	
}