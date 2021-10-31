package clueGame;

import java.util.ArrayList;

abstract public class Player {
	private String name;
	private String color;
	private int row, column;
	private ArrayList<Card> cards;
	
	abstract public void updatehand(Card card);
	
	
	

}
