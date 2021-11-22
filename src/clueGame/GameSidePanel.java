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

	// Creating test player to display cards in hand and seen cards for
	static Player displayPlayer;

	Board board = Board.getInstance();

	public GameSidePanel() {

		displayPlayer = board.players.get(0);

		// set the whole panel to top, bottom, and middle
		setLayout(new GridLayout(3,1,30,10));

		// Creating array lists that will hold different kind of cards in player hands
		ArrayList<Card> cards = displayPlayer.getCards();
		ArrayList<Card> playerCards = new ArrayList<Card>();
		ArrayList<Card> roomCards = new ArrayList<Card>();
		ArrayList<Card> weaponCards = new ArrayList<Card>();

		// Sorting cards in players hand by type
		for (Card c : cards) {
			if (c.getCardType() == Card.CardType.PERSON) {
				playerCards.add(c);
			} else if (c.getCardType() == Card.CardType.ROOM) {
				roomCards.add(c);
			} else if (c.getCardType() == Card.CardType.WEAPON) {
				weaponCards.add(c);
			}
		}

		// Getting list of seen cards from player
		ArrayList<Card> playersSeen = displayPlayer.getSeenPersons();
		// Creating list of text fields based on cards
		ArrayList<JTextField> seenPlayerFields = getTextFields(playersSeen);
		ArrayList<JTextField> handPlayerFields = getTextFields(playerCards);

		ArrayList<Card> roomsSeen = displayPlayer.getSeenRooms();
		ArrayList<JTextField> seenRoomFields = getTextFields(roomsSeen);
		ArrayList<JTextField> handRoomFields = getTextFields(roomCards);

		ArrayList<Card> weaponsSeen = displayPlayer.getSeenWeapons();
		ArrayList<JTextField> seenWeaponFields = getTextFields(weaponsSeen);
		ArrayList<JTextField> handWeaponFields = getTextFields(weaponCards);

		// Creating three panels
		JPanel top = new JPanel(new GridLayout(2 + seenPlayerFields.size() + handPlayerFields.size(),0,5,1));
		JPanel middle = new JPanel(new GridLayout(2 + seenRoomFields.size() + handRoomFields.size(),0,5,1));
		JPanel bottom = new JPanel(new GridLayout(2 + seenWeaponFields.size() + handWeaponFields.size(),0,5,1));

		// Filling in top panel
		peopleLabelHand = new JLabel("In Hand:");
		peopleLabelSeen= new JLabel("Seen:");
		top.setBorder(new TitledBorder(new EtchedBorder(),"People"));

		top.add(peopleLabelHand);
		if(handPlayerFields.size() ==0) {
			JTextField field = new JTextField("None");
			top.add(field);
		}else {
			for (JTextField field : handPlayerFields) {
				top.add(field);
			}
		}

		top.add(peopleLabelSeen);
		if(seenPlayerFields.size() ==0) {
			JTextField field = new JTextField("None");
			top.add(field);
		}else {
			for (JTextField field : seenPlayerFields) {
				top.add(field);
			}
		}
		add(top);

		// Filling in middle panel
		roomLabelHand = new JLabel("In Hand:");
		roomLabelSeen = new JLabel("Seen:");
		middle.setBorder(new TitledBorder(new EtchedBorder(),"Rooms"));
		middle.add(roomLabelHand);
		if(handRoomFields.size() ==0) {
			JTextField field = new JTextField("None");
			middle.add(field);
		}else {
			for (JTextField field : handRoomFields) {
				middle.add(field);
			}
		}
		middle.add(roomLabelSeen);
		if(seenRoomFields.size() ==0) {
			JTextField field = new JTextField("None");
			middle.add(field);
		}else {
			for (JTextField field : seenRoomFields) {
				middle.add(field);
			}	
		}
		add(middle);

		// Filling in bottom panel
		weaponLabelHand = new JLabel("In Hand:");
		weaponLabelSeen = new JLabel("Seen:");
		bottom.add(weaponLabelHand);
		if(handWeaponFields.size() ==0) {
			JTextField field = new JTextField("None");
			bottom.add(field);
		}else {
			for (JTextField field : handWeaponFields) {
				bottom.add(field);
			}
		}
		bottom.add(weaponLabelSeen);
		if(seenWeaponFields.size() ==0) {
			JTextField field = new JTextField("None");
			bottom.add(field);
		}else {
			for (JTextField field : seenWeaponFields) {
				bottom.add(field);
			}	
		}
		bottom.setBorder(new TitledBorder(new EtchedBorder(),"Weapons"));
		add(bottom);
		setBorder(new TitledBorder(new EtchedBorder(),"Known Cards"));

	}

	// Generates array of JTextFields that will display card names from array of cards
	public ArrayList<JTextField> getTextFields(ArrayList<Card> persons) {
		ArrayList<JTextField> fields = new ArrayList<JTextField>();
		for (Card c:persons) {
			JTextField cSeen = new JTextField();
			cSeen.setText(c.getCardName());
			fields.add(cSeen);
		}

		return fields;

	}

	public void setDisplayPlayer(Player player) {
		displayPlayer = (HumanPlayer) player;
	}


	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Filling in hand and seen cards for test player
		displayPlayer.addSeenCard(new Card("player one", Card.CardType.PERSON));
		displayPlayer.addSeenCard(new Card("player two", Card.CardType.PERSON));
		displayPlayer.addSeenCard(new Card("room one", Card.CardType.ROOM));
		displayPlayer.addSeenCard(new Card("room two", Card.CardType.ROOM));
		displayPlayer.addSeenCard(new Card("weapon one", Card.CardType.WEAPON));
		displayPlayer.addSeenCard(new Card("weapon two", Card.CardType.WEAPON));

		displayPlayer.updatehand(new Card("player three", Card.CardType.PERSON));
		displayPlayer.updatehand(new Card("player four", Card.CardType.PERSON));
		displayPlayer.updatehand(new Card("room three", Card.CardType.ROOM));
		displayPlayer.updatehand(new Card("room four", Card.CardType.ROOM));
		displayPlayer.updatehand(new Card("weapon three", Card.CardType.WEAPON));
		displayPlayer.updatehand(new Card("weapon four", Card.CardType.WEAPON));

		GameSidePanel panel = new GameSidePanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(200, 700);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

	}


}