package clueGame;

public class Solution {
	private Card room;
	private Card person;
	private Card weapon;
	
	public Solution(Card room, Card weapon, Card person) {
		this.room = room;
		this.person = person;
		this.weapon = weapon;
	}
	
	public Card[] getSolution() {
		Card[] soln = {room, weapon,person};
		return soln;
	}
}
