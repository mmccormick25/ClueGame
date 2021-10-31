package clueGame;

public class HumanPlayer extends Player{
	

	public HumanPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}

	@Override
	public void updatehand(Card card) {
		super.cards.add(card);
	}

}
