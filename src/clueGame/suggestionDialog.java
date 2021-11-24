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

public class suggestionDialog extends JDialog{
	private JComboBox<String> personChoice,weaponChoice;
	JButton submit,cancel;
	JTextField roomName;
	Solution newSuggestion;
	JDialog suggestion;
	Board board = Board.getInstance();

	public void setRoomName(String name) {
		roomName.setText(name);

	}

	public suggestionDialog() {
		suggestion = new JDialog(ClueGame.frame,"Make a suggestion");
		submit= new JButton("Submit");
		cancel = new JButton("Cancel");
		JLabel room = new JLabel("Current Room");

		// display the room name in JTextField
		roomName = new JTextField();

		JLabel person = new JLabel("Person");
		JLabel weapon = new JLabel("Weapon");
		personChoice = new JComboBox<String>();


		// create click for menus.
		Clicklistener click = new Clicklistener();
		submit.addActionListener(click);
		cancel.addActionListener(click);


		// add the drop-down menu for players
		for(Player player:Board.players) {
			if(!player.getName().equals(ClueGame.controlPanel.getTurn())) {


				personChoice.addItem(player.getName());

			} else {
				BoardCell temp = Board.getInstance().getGrid()[player.getRow()][player.getColumn()];
				setRoomName(Board.getInstance().getRoom(temp).getName());
			}
		}


		// add the drop-down menu for weapons. 
		weaponChoice = new JComboBox<String>();

		for(String w:Board.weapons) {

			weaponChoice.addItem(w);

		}



		// set the size and layout for the suggestion dialog. 

		suggestion.setLayout(new GridLayout(4,2));
		suggestion.setSize(200, 300);
		suggestion.setVisible(true);

		// add the components to the suggestion dialog. 
		suggestion.add(room);
		suggestion.add(roomName);
		suggestion.add(person);
		suggestion.add(personChoice);

		suggestion.add(weapon);
		suggestion.add(weaponChoice);

		suggestion.add(submit);
		suggestion.add(cancel);

	}


	private class Clicklistener implements ActionListener {
		public void actionPerformed(ActionEvent e)

		{	// // create variables to store the related cards
			String playerName = personChoice.getSelectedItem().toString();
			String weaponName = weaponChoice.getSelectedItem().toString();
			Card player = new Card(playerName,CardType.PERSON);
			Card weapon = new Card(weaponName,CardType.WEAPON);
			Card room = new Card(roomName.getText(), CardType.ROOM);


			if (e.getSource() == submit)
			{	// when submit is clicked, the solution is created and showed in the control panel. 
				// Then the dialog will close automatically.
				System.out.println("submit pressed");
				newSuggestion = new Solution(room,weapon,player);
				ClueGame.controlPanel.setGuess(room.getCardName() + ", " + weapon.getCardName() + ", " + player.getCardName());

				board.handleHumanSuggestion(newSuggestion);
				
				suggestion.dispose();
				suggestion.setVisible(false);
				board.suggestMade = true;
			}
			if (e.getSource() == cancel)
			{			// when cancel is clicked, the window will disappear automatically. 
				suggestion.dispose();
				suggestion.setVisible(false);
				board.suggestMade = true;
			}

		}
	}



}
