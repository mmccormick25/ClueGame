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
				if (playerCard == suggestionCard) {
					return playerCard;
				}
			}
		}
		return null;
		
	}
	
	public void addSeenCard(Card card) {
		if (card.getCardType() == Card.CardType.ROOM) {
			seenRooms.add(card);
		} else if (card.getCardType() == Card.CardType.WEAPON) {
			seenWeapons.add(card);
		} if (card.getCardType() == Card.CardType.PERSON) {
			seenPersons.add(card);
		}
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

	public void draw(int cellDim, Graphics g) {
		for (Player p : board.players) {
			if (p.getRow() == this.getRow() && p.getColumn() == this.getColumn() && p != this) {
				
			}
		}
		
		g.setColor(MyColor.getColor(color));
		g.fillOval(cellDim * column, cellDim * row, cellDim, cellDim);
	}
	

}
