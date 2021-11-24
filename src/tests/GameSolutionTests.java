package tests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameSolutionTests {
	private static Board board;
	private static Card greenHouseCard,mineCard,infirmaryCard,cafeteriaCard,officeCard,quartersCard,storageCard,labCard,bathroomCard;
	private static Card laserGunCard,moonRockCard,lightsaberCard,wrenchCard,liveWireCard,knifeCard;
	private static Card yellowCard,brownCard,grayCard,orangeCard,maroonCard,blueCard;
	
	@BeforeAll
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		// cards for rooms 
		greenHouseCard = new Card("Green House",CardType.ROOM);
		mineCard = new Card("Mine",CardType.ROOM);
		infirmaryCard = new Card("Infirmary",CardType.ROOM);
		cafeteriaCard = new Card("Cafeteria",CardType.ROOM);
		officeCard = new Card("Office",CardType.ROOM);
		quartersCard = new Card("Quarters",CardType.ROOM);
		storageCard = new Card("Storage",CardType.ROOM);
		labCard = new Card("Lab",CardType.ROOM);
		bathroomCard = new Card("Bathroom",CardType.ROOM);
		
		// cards for weapons 
		laserGunCard = new Card("Laser Gun",CardType.WEAPON);
		moonRockCard = new Card("Moon Rock",CardType.WEAPON);
		lightsaberCard = new Card("Lightsaber",CardType.WEAPON);
		wrenchCard = new Card("Wrench",CardType.WEAPON);
		liveWireCard = new Card("Live Wire",CardType.WEAPON);
		knifeCard = new Card("Knife",CardType.WEAPON);
		
		// six people
		yellowCard = new Card("Dr. Dandelion",CardType.PERSON);
		brownCard = new Card("Bishop Brown",CardType.PERSON);
		grayCard = new Card("Sir Silver",CardType.PERSON);
		orangeCard = new Card("Officer Orange",CardType.PERSON);
		maroonCard = new Card("Mrs. Maroon",CardType.PERSON);
		blueCard = new Card("Professor Prizmarine",CardType.PERSON);
			
	}
	@Test
	public void checkAccusationTests() {
		board.setSolution(bathroomCard, laserGunCard, grayCard);
		assert board.checkAccusation(new Solution(labCard,laserGunCard, grayCard))==false;
		assert board.checkAccusation(new Solution(bathroomCard,laserGunCard, blueCard))==false;
		assert board.checkAccusation(new Solution(bathroomCard,knifeCard, grayCard))==false;
		assert board.checkAccusation(new Solution(bathroomCard,laserGunCard, grayCard))==true;
	}
	
	@Test
	public void checkDisproveSuggestion() {
		// Creating suggestion to be tested
		Solution suggestion = new Solution(labCard, laserGunCard, grayCard);
		// Creating dummy player to add cards to hand
		ComputerPlayer testPlayer = new ComputerPlayer("testPlayer", "Black", 10, 10);
		assert (testPlayer.checkSuggestion(suggestion) == null);
		// Adding cards to player hand
		testPlayer.updatehand(mineCard);
		testPlayer.updatehand(knifeCard);
		testPlayer.updatehand(labCard);
		assert (testPlayer.checkSuggestion(suggestion) == labCard);
	}
	
	@Test
	public void checkSuggestionHandling() {
		// Creating suggestion
		Solution suggestion = new Solution(labCard, laserGunCard, grayCard);
		// Setting up players and their hands
		HumanPlayer humanPlayer = new HumanPlayer("testPlayerOne", "Black", 10, 10);
		humanPlayer.updatehand(grayCard);
		ComputerPlayer compPlayerOne = new ComputerPlayer("testPlayerTwo", "Black", 10, 10);
		compPlayerOne.updatehand(labCard);
		ComputerPlayer compPlayerTwo = new ComputerPlayer("testPlayerTwo", "Black", 10, 10);
		compPlayerTwo.updatehand(laserGunCard);
		
		ArrayList<Player> players = new ArrayList<Player>(Arrays.asList(humanPlayer, compPlayerOne, compPlayerTwo));
		
		assert (board.handleSuggestion(players, suggestion, humanPlayer) == labCard);
		assert (board.handleSuggestion(players, suggestion, compPlayerOne) == laserGunCard);
		
		// Suggestion containing cards that only compPlayerTwo has one has
		Solution suggestionTwo = new Solution(mineCard, laserGunCard, orangeCard);
		assert (board.handleSuggestion(players, suggestionTwo, compPlayerTwo) == null);
	}
}
