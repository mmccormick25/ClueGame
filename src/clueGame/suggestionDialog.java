package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class suggestionDialog extends JDialog{
	JMenuBar personChoice;
	JMenuBar weaponChoice;
	JButton submit,cancel;
	JTextField roomName;
	
	public void setRoomName(String name) {
		roomName.setText(name);
		
	}
	
	public suggestionDialog() {
		JDialog suggestion = new JDialog(ClueGame.frame,"Make a suggestion");
		 submit= new JButton("Submit");
		cancel = new JButton("Cancel");
		JLabel room = new JLabel("Current Room");
		
		// display the room name in JTextField
		roomName = new JTextField();
		
		
		
		JLabel person = new JLabel("Person");
		JLabel weapon = new JLabel("Weapon");
		 personChoice = new JMenuBar();
		JMenu personC = new JMenu("choose player");
		
		// create click for menus.
		Clicklistener click = new Clicklistener();
		
		
		// add the drop-down menu for players
		for(Player player:Board.players) {
			if(!player.getName().equals(ClueGame.panel.getTurn())) {
				
				JMenuItem playerName = new JMenuItem(player.getName());
				personC.add(playerName);
				playerName.addActionListener(click); 
			} else {
				BoardCell temp = Board.getInstance().getGrid()[player.getRow()][player.getColumn()];
				setRoomName(Board.getInstance().getRoom(temp).getName());
			}
		}
		personChoice.add(personC);
		
		// add the drop-down menu for weapons. 
		weaponChoice = new JMenuBar();
		JMenu weaponC = new JMenu("Choose weapon");
		for(String w:Board.weapons) {
				JMenuItem weaponName = new JMenuItem(w);
				weaponC.add(weaponName);
				weaponName.addActionListener(click); 
			
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

	
	private class Clicklistener implements ActionListener {
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == personChoice)
			{ 
				
			}
			if (e.getSource() == weaponChoice)
			{
				
			}
			if (e.getSource() == submit)
			{
				
			}
			if (e.getSource() == cancel)
			{
				
			}
			
		}
	}
	


}
