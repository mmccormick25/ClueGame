package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public enum CardType {
		ROOM,PERSON,WEAPON;
	}
	
	public Card(String cardName, CardType cardType) {
		this.cardName = cardName;
		this.cardType = cardType;
	}

	public boolean equals(Card card) {
		if (this.cardName.equals(card.cardName)) {
			return true;
		}
		return false;
		
	}
	
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public CardType getCardType() {
		return cardType;
	}

}
