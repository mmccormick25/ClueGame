package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	private ArrayList<Card> seenRooms = new ArrayList<Card>();
	public void setSeenRooms(Card card) {
		seenRooms.add(card);
	}
	public ArrayList<Card> getSeenRooms() {
		return seenRooms;
	}


	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}

	@Override
	public void updatehand(Card card) {
		super.cards.add(card);
	}
	public BoardCell selectTargets(BoardCell currentLocation, int dice) {
		BoardCell target = null;
		Board.getInstance().calcTargets(currentLocation, dice);
		Set<BoardCell> targets = new HashSet<BoardCell>();
		targets = Board.getInstance().getTargets();
		List<BoardCell> targetsList = new ArrayList<>();
		targetsList.addAll(targets);
		for(BoardCell c: targetsList) {
			String roomName = Board.getInstance().getRoom(c).getName();
			boolean isSeenCards = seenRooms.contains(new Card(roomName,Card.CardType.ROOM));
			if(c.inRoom && (isSeenCards == false)) {
				target = c;
				break;
			}else {
				Random rdm = new Random();
				int index = rdm.nextInt(targetsList.size());
				target = targetsList.get(index);
			}
		}
		
		
		
		return target;
	}
	
}
