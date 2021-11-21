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
				roomChoice.addItem(r.getName());

		}
		
		
		// create click for menus.
		Clicklistener click = new Clicklistener();
		submit.addActionListener(click);
		cancel.addActionListener(click);
		
		
		// add the drop-down menu for players
		for(Player player:Board.players) {
			if(!player.getName().equals(ClueGame.panel.getTurn())) {
				
				
				personChoice.addItem(player.getName());
				
			} 
		}
		
		
		// add the drop-down menu for weapons. 
		weaponChoice = new JComboBox<String>();
		
		for(String w:Board.weapons) {
				
			weaponChoice.addItem(w);
			
		}
			
		
		
		
		
		accusation.setLayout(new GridLayout(4,2));
		accusation.setSize(200, 200);
		accusation.setVisible(true);
		
		accusation.add(room);
		
		accusation.add(person);
		accusation.add(personChoice);
		
		accusation.add(weapon);
		accusation.add(weaponChoice);
		
		accusation.add(submit);
		accusation.add(cancel);
		
	}

	
	private class Clicklistener implements ActionListener {
		public void actionPerformed(ActionEvent e)
		
		{
			String playerName = personChoice.getSelectedItem().toString();
			String weaponName = weaponChoice.getSelectedItem().toString();
			String roomName = roomChoice.getSelectedItem().toString();
			Card player = new Card(playerName,Card.CardType.PERSON);
			Card weapon = new Card(weaponName,Card.CardType.WEAPON);
			Card room = new Card(roomName, Card.CardType.ROOM);
			
			
			if (e.getSource() == submit)
			{
				System.out.println("submit pressed");
				 newAccusation = new Solution(room,weapon,player);
				 ClueGame.panel.setGuess(room.getCardName() + "," + weapon.getCardName() + "," + player.getCardName());
	//			 Board.getInstance().handleSuggestion(null, newSuggestion, null);
				 Board.getInstance().one.dispose();
				 Board.getInstance().one.setVisible(false);
			}
			if (e.getSource() == cancel)
			{
				 Board.getInstance().one.dispose();
				setVisible(false);
			}
			
		}
	}
	


}
