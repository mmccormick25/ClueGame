package clueGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	private ArrayList<Card> seenRooms = new ArrayList<Card>();
	private ArrayList<Card> seenWeapons = new ArrayList<Card>();
	private ArrayList<Card> seenPersons = new ArrayList<Card>();
	
	public ArrayList<Card> getSeenWeapons() {
		return seenWeapons;
	}
	public void setSeenWeapons(Card c) {
		seenWeapons.add(c);
	}
	public ArrayList<Card> getSeenPersons() {
		return seenPersons;
	}
	public void setSeenPersons(Card c) {
		seenPersons.add(c);
	}

	
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
	
	public BoardCell selectTarget(BoardCell currentLocation, int dice) {
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
				Random rdm = new Random(123);
				int index = rdm.nextInt(targetsList.size());
				target = targetsList.get(index);
			}
		}	
		
		System.out.println(target);
		return target;
	}
	
	public Solution createSuggestion(BoardCell currentLocation) {
		Room room = Board.getInstance().getRoom(currentLocation);
		Card roomCard = null;
		Card weaponCard = null;
		Card personCard = null;
		ArrayList<Card> unseenWeapons = new ArrayList<Card>();
		ArrayList<Card> unseenPersons = new ArrayList<Card>();
		
		ArrayList<String> weapons = (ArrayList<String>) Board.getInstance().weapons.clone();
		ArrayList<Player> players = (ArrayList<Player>) Board.getInstance().players.clone();
		
		if(currentLocation.inRoom) {
			roomCard = new Card(room.getName(), Card.CardType.ROOM);
		}
		
		for (Card card : Board.getInstance().deck) {
			if (card != null) {
				boolean found = false;
				if (card.getCardType().equals(Card.CardType.WEAPON)) {
					for (Card seenWeapon : seenWeapons) {
						if (seenWeapon.equals(card)) {
							found = true;
						}
					}
					if (!found) {
						unseenWeapons.add(card);
					}
				} else if (card.getCardType().equals(Card.CardType.PERSON)) {
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
		
		if (unseenWeapons.size() > 0) {
			Collections.shuffle(unseenWeapons);
			weaponCard = unseenWeapons.get(0);
		} else {
			Collections.shuffle(weapons);
			
			weaponCard = new Card(weapons.get(0), Card.CardType.WEAPON);
		}
		
		if (unseenPersons.size() > 0) {
			Collections.shuffle(unseenPersons);
			personCard = unseenPersons.get(0);
		} else {
			Collections.shuffle(players);
			personCard = new Card(players.get(0).getName(), Card.CardType.WEAPON);
		}
		
		return new Solution(roomCard, weaponCard, personCard);
	}
	
}
