package tests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.Player;

public class GameSetupTests {
	
	private static Board board;
	
	@BeforeAll
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}
	
	
	
	@Test
	public void testWeaponSetup() {
		ArrayList<String> a = board.weapons;
		assert board.weapons.size() == 6;
		assert board.weapons.contains("Wrench");
		assert board.weapons.contains("Laser Gun");
		assert board.weapons.contains("Knife");
	}
	
	@Test
	public void testPlayerSetup() {
		ArrayList<Player> players = board.players;
		assert board.players.size() == 6;
		assert board.players.get(0).getName().equals("Dr. Dandelion");
		assert board.players.get(2).getColor().equals("Gray");
		assert board.players.get(5).getRow() == 0;
	}
	
	@Test
	public void testDeckSetup() {
		ArrayList<Card> d = board.deck;
		assert board.deck.size() == 21;
		boolean foundWrench = false;
		boolean foundMine = false;
		boolean foundOfficerOrange = false;
		for (Card card : board.deck) {
			if (card.getCardName().equals("Wrench")) {
				foundWrench = true;
			}
			if (card.getCardName().equals("Mine")) {
				foundMine = true;
			}
			if (card.getCardName().equals("Officer Orange")) {
				foundOfficerOrange = true;
			}
		}
		assert foundWrench;
		assert foundMine;
		assert foundOfficerOrange;
	}
	
	@Test
	public void testDeal() {
		ArrayList<Player> players = board.players;
		for (Player p : board.players) {
			assert p.getCards().size() == 3;
		}
		assert board.getSolution().getSolution().length == 3;
	}
	
}
