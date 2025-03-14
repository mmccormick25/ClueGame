package clueGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	
	private Solution accusation;

	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}
	
	public BoardCell selectTarget(BoardCell currentLocation, int dice) {
		BoardCell target = null;
		Board.getInstance().calcTargets(currentLocation, dice);
		Set<BoardCell> targets = new HashSet<BoardCell>();
		targets = Board.getInstance().getTargets();
		List<BoardCell> targetsList = new ArrayList<>();
		targetsList.addAll(targets);
		// Logic to try and select a room the computer hasn't seen before
		for(BoardCell c: targetsList) {
			String roomName = Board.getInstance().getRoom(c).getName();
			boolean isSeenCards = seenRooms.contains(new Card(roomName,CardType.ROOM));
			if(c.inRoom && (isSeenCards == false)) {
				target = c;
				break;
			}else {
				Random rdm = new Random(123);
				int index = rdm.nextInt(targetsList.size());
				target = targetsList.get(index);
			}
		}	

		return target;
	}
	
	public Solution createSuggestion(BoardCell currentLocation) {
		Room room = Board.getInstance().getRoom(currentLocation);
		// Empty variables that will hold ai suggestion
		Card roomCard = null;
		Card weaponCard = null;
		Card personCard = null;
		
		ArrayList<Card> unseenWeapons = new ArrayList<Card>();
		ArrayList<Card> unseenPersons = new ArrayList<Card>();
		
		// List of all weapons and players from board
		ArrayList<String> weapons = (ArrayList<String>) Board.getInstance().weapons.clone();
		ArrayList<Player> players = (ArrayList<Player>) Board.getInstance().players.clone();
		
		if(currentLocation.inRoom) {
			roomCard = new Card(room.getName(), CardType.ROOM);
		}
		
		// Checking if each card in deck has been seen, if not it is added to unseen list to pick from for a suggestion
		for (Card card : Board.getInstance().deck) {
			if (card != null) {
				boolean found = false;
				if (card.getCardType().equals(CardType.WEAPON)) {
					for (Card seenWeapon : seenWeapons) {
						if (seenWeapon.equals(card)) {
							found = true;
						}
					}
					if (!found) {
						unseenWeapons.add(card);
					}
				} else if (card.getCardType().equals(CardType.PERSON)) {
					for (Card seenPerson : seenPersons) {
						if (seenPerson.equals(card)) {
							found = true;
						}
					}
					if (!found) {
						unseenPersons.add(card);
					}
				}
			}
		}
		
		// Logic for picking random unseen card or random card
		if (unseenWeapons.size() > 0) {
			Collections.shuffle(unseenWeapons);
			weaponCard = unseenWeapons.get(0);
		} else {
			Collections.shuffle(weapons);			
			weaponCard = new Card(weapons.get(0), CardType.WEAPON);
		}
		
		if (unseenPersons.size() > 0) {
			Collections.shuffle(unseenPersons);
			personCard = unseenPersons.get(0);
		} else {
			Collections.shuffle(players);
			personCard = new Card(players.get(0).getName(), CardType.WEAPON);
		}
		
		return new Solution(roomCard, weaponCard, personCard);
	}

	public Solution getAccusation() {
		return accusation;
	}

	public void setAccusation(Solution accusation) {
		this.accusation = accusation;
	}
	
	@Override
	public void updatehand(Card card) {
		super.cards.add(card);
		addSeenCard(card);
	}
	
}
