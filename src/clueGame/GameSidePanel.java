package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameSidePanel extends JPanel {
	JLabel peopleLabelHand,peopleLabelSeen,roomLabelHand,roomLabelSeen,weaponLabelHand,weaponLabelSeen;
	private JTextField peopleInHand,peopleSeen,roomInHand,roomSeen,weaponInHand,weaponSeen;
	ComputerPlayer testPlayer= new ComputerPlayer( "Test Player","orange",1, 0);
	


	public GameSidePanel() {
		// set the whole panel to top and bottom.
		setLayout(new GridLayout(3,1,30,10));
	// create the top panel by 1x4
		ArrayList<Card> playerInHand =  testPlayer.getSeenPersons();
		int length = playerInHand.size();
		JPanel top = new JPanel(new GridLayout(2+ ComputerPlayer.getSeenPersons(),0,5,20));
		JPanel middle = new JPanel(new GridLayout(4,0,5,20));
		JPanel bottom = new JPanel(new GridLayout(4,0,5,20));
		peopleLabelHand = new JLabel("In Hand:");
		peopleLabelSeen= new JLabel("Seen:");
		peopleInHand = new JTextField();
		peopleSeen = new JTextField();
		
		top.setBorder(new TitledBorder(new EtchedBorder(),"Peole"));
		top.add(peopleLabelHand);
		top.add(peopleInHand);
		top.add(peopleLabelSeen);
		top.add(peopleSeen);		
		add(top);
		
		roomLabelHand = new JLabel("In Hand:");
		roomLabelSeen= new JLabel("Seen:");
		roomInHand = new JTextField();
		roomSeen = new JTextField();
		middle.setBorder(new TitledBorder(new EtchedBorder(),"Rooms"));
		middle.add(roomLabelHand);
		middle.add(roomInHand);
		middle.add(roomLabelSeen);
		middle.add(roomSeen);		
		add(middle);
		
		weaponLabelHand = new JLabel("In Hand:");
		weaponLabelSeen= new JLabel("Seen:");
		weaponInHand = new JTextField();
		weaponSeen = new JTextField();
		bottom.add(weaponLabelHand);
		bottom.add(weaponInHand);
		bottom.add(weaponLabelSeen);
		bottom.add(weaponSeen);	
		bottom.setBorder(new TitledBorder(new EtchedBorder(),"Weapons"));
		add(bottom);
		setBorder(new TitledBorder(new EtchedBorder(),"Known Cards"));

	}
	

	
	private ArrayList<Card> getSeenPersons() {
		// TODO Auto-generated method stub
		return null;
	}



	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameSidePanel panel = new GameSidePanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(200, 700);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		
		panel.setPeopleInHand(new ComputerPlayer( "Col. Mustard","orange",0, 0));
//		panel.setGuess( "I have no guess!");
//		panel.setGuessResult( "So you have nothing?");
		
	}




// set the content of guess text box 
	public void setPeopleInHand(ComputerPlayer Player) {
		peopleInHand.setText(Player.getName());
		
	}
	public void setPeopleSeen(ArrayList<Card> persons) {
		for (Card c:persons) {
			JTextField cSeen = new JTextField();
			cSeen.setText(c.getCardName());
			
		}
		
		
	}
	
	// set the content of guess result text box 
	public void setGuessResult(String gResult) {
		guessResult.setText(gResult);
		
	}
	// set the content of computer player and roll text box 
	public void setTurn(ComputerPlayer computerPlayer, int i) {
		turn.setText(computerPlayer.getName());	
		roll.setText(String.valueOf(i));
		
	}
	
	
}