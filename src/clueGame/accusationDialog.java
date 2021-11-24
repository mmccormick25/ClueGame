package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class accusationDialog extends JDialog{
	private JComboBox<String> roomChoice,personChoice,weaponChoice;
	JButton submit,cancel;
	Solution newAccusation;
	JDialog accusation;
	Board board = Board.getInstance();

	// constructor for accusation dialog 
	public accusationDialog() {
		accusation = new JDialog(ClueGame.frame,"Make an Accusation");
		 submit= new JButton("Submit");
		cancel = new JButton("Cancel");
		JLabel room = new JLabel("Room");		
		JLabel person = new JLabel("Person");
		JLabel weapon = new JLabel("Weapon");
		roomChoice = new JComboBox<String>();
		personChoice = new JComboBox<String>();
		for(Room r: Board.getInstance().rooms.values()) {		
			System.out.println(r.getName());
				roomChoice.addItem(r.getName());

		}
		
		// create click for buttons
		Clicklistener click = new Clicklistener();
		submit.addActionListener(click);
		cancel.addActionListener(click);
		
		
		// add the drop-down menu for players
		for(Player player:Board.players) {
			if(!player.getName().equals(ClueGame.controlPanel.getTurn())) {
				
				
				personChoice.addItem(player.getName());
				
			} 
		}
		
		
		// add the drop-down menu for weapons. 
		weaponChoice = new JComboBox<String>();
		
		for(String w:Board.weapons) {
				
			weaponChoice.addItem(w);
			
		}
			
		
		
		
		// create the skeleton for the accusation dialog 
		accusation.setLayout(new GridLayout(4,2));
		accusation.setSize(200, 200);
		accusation.setVisible(true);
		
		// add the components of room
		accusation.add(room);
		accusation.add(roomChoice);
		
		// add the components of person
		accusation.add(person);
		accusation.add(personChoice);
		
		// add the components of weapons
		accusation.add(weapon);
		accusation.add(weaponChoice);
		
		// add the two buttons 
		accusation.add(submit);
		accusation.add(cancel);
		
	}

	private class Clicklistener implements ActionListener {
		public void actionPerformed(ActionEvent e)
		
		{   // create variables to store the related cards
			String playerName = personChoice.getSelectedItem().toString();
			String weaponName = weaponChoice.getSelectedItem().toString();
			String roomName = roomChoice.getSelectedItem().toString();
			Card player = new Card(playerName,CardType.PERSON);
			Card weapon = new Card(weaponName,CardType.WEAPON);
			Card room = new Card(roomName, CardType.ROOM);
			
			// when submit is clicked, the solution is created and showed in the control panel. 
			// Then the dialog will close automatically.
			if (e.getSource() == submit)
			{
				System.out.println("submit pressed");
				 newAccusation = new Solution(room,weapon,player);
				 ClueGame.controlPanel.setGuess(room.getCardName() + "," + weapon.getCardName() + "," + player.getCardName());
				 accusation.dispose();
				 accusation.setVisible(false);
				 board.accMade = true;
				 board.handleHumanAccusation(newAccusation);
		
			}
			
			// when cancel is clicked, the window will disappear automatically. 
			if (e.getSource() == cancel)
			{
				accusation.dispose();
				accusation.setVisible(false);
				board.accMade = true;
			}
			
		}
	}
	


}
