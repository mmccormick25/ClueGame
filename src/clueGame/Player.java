package clueGame;

import java.util.ArrayList;
import java.util.Collections;

abstract public class Player {
	private String name;
	private String color;
	private int row, column;
	protected ArrayList<Card> cards = new ArrayList<Card>();
	
	public Player(String name, String color, int row, int column) {
		this.name = name;
		this.setColor(color);
		this.row = row;
		this.column = column;
	}
	
	abstract public void updatehand(Card card);
	
	public Card checkSuggestion(Solution suggestion) {
		// Shuffling cards so random matching card is returned
		Collections.shuffle(cards);
		for (Card playerCard : cards) {
			for (Card suggestionCard : suggestion.getSolution()) {
				if (playerCard == suggestionCard) {
					return playerCard;
				}
			}
		}
		return null;
		
	}
	
	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
	

}
