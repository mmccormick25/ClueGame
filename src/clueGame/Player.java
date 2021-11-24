package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.text.Format.Field;
import java.util.ArrayList;
import java.util.Collections;

abstract public class Player {
	protected ArrayList<Card> seenRooms = new ArrayList<Card>();
	protected ArrayList<Card> seenWeapons = new ArrayList<Card>();
	protected ArrayList<Card> seenPersons = new ArrayList<Card>();
	
	private String name;
	private String color;
	private int row, column;
	protected ArrayList<Card> cards = new ArrayList<Card>();
	
	Board board = Board.getInstance();
	
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
				if (playerCard.equals(suggestionCard)) {
					return playerCard;
				}
			}
		}
		return null;
		
	}
	
	// Adds card to correct array list based on card type
	public void addSeenCard(Card card) {
		if (card.getCardType() == CardType.ROOM && !seenRooms.contains(card)) {
			seenRooms.add(card);
		} else if (card.getCardType() == CardType.WEAPON && !seenWeapons.contains(card)) {
			seenWeapons.add(card);
		} if (card.getCardType() == CardType.PERSON && !seenPersons.contains(card)) {
			seenPersons.add(card);
		}
	}
	
	public void draw(int cellDim, int offSet, Graphics g) {
		g.setColor(MyColor.getColor(color));
		g.fillOval(cellDim * column + offSet, cellDim * row, cellDim, cellDim);
	}
	
	public ArrayList<Card> getSeenWeapons() {
		return seenWeapons;
	}
	
	public ArrayList<Card> getSeenPersons() {
		return seenPersons;
	}
	
	public ArrayList<Card> getSeenRooms() {
		return seenRooms;
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
	
	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
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
