package tests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;

public class gameSetupTests {
	
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
		assert board.weapons.size() == 6;
		assert board.weapons.contains("Wrench");
		assert board.weapons.contains("Laser Gun");
		assert board.weapons.contains("Knife");
	}
	
	@Test
	public void testPlayerSetup() {
		assert board.players.size() == 6;
		assert board.players.get(0).getName().equals("Dr. Dandelion");
		assert board.players.get(2).getColor().equals("Gray");
		assert board.players.get(5).getRow() == 0;
	}
	
	@Test
	public void testDeckSetup() {
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
	
}
