package clueGame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

public class suggestionDialog extends JDialog{
	
	
	public suggestionDialog() {
		JDialog suggestion = new JDialog(ClueGame.frame,"Make a suggestion");
		JButton submit= new JButton("Submit");
		JButton cancel = new JButton("Cancel");
		JLabel room = new JLabel("Current Room");
		JTextField roomName = new JTextField();
		JLabel person = new JLabel("Person");
		JLabel weapon = new JLabel("Weapon");
		JMenuBar personChoice = new JMenuBar();
		JMenu personC = new JMenu("choose player");
		for(Player player:Board.players) {
			if(!player.getName().equals(ClueGame.panel.getTurn())) {
				JMenuItem playerName = new JMenuItem(player.getName());
				personC.add(playerName);
				playerName.addActionListener(null); // TODO
			}
		}
		personChoice.add(personC);
		
		JMenuBar weaponChoice = new JMenuBar();
		JMenu weaponC = new JMenu("Choose weapon");
		for(String w:Board.weapons) {
				JMenuItem weaponName = new JMenuItem(w);
				weaponC.add(weaponName);
				weaponName.addActionListener(null); // TODO
			
		}
			
		weaponChoice.add(weaponC);
		
		
		
		suggestion.setLayout(new GridLayout(4,2));
		suggestion.setSize(200, 300);
		suggestion.setVisible(true);
		
		suggestion.add(room);
		suggestion.add(roomName);
		suggestion.add(person);
		suggestion.add(personChoice);
		
		suggestion.add(weapon);
		suggestion.add(weaponChoice);
		
		suggestion.add(submit);
		suggestion.add(cancel);
		
	}


}
