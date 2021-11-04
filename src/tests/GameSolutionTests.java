package tests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
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
		greenHouseCard = new Card("Green Hourse",Card.CardType.ROOM);
		mineCard = new Card("Mine",Card.CardType.ROOM);
		infirmaryCard = new Card("Infirmary",Card.CardType.ROOM);
		cafeteriaCard = new Card("Cafeteria",Card.CardType.ROOM);
		officeCard = new Card("Office",Card.CardType.ROOM);
		quartersCard = new Card("Quarters",Card.CardType.ROOM);
		storageCard = new Card("Storage",Card.CardType.ROOM);
		labCard = new Card("Lab",Card.CardType.ROOM);
		bathroomCard = new Card("Bathroom",Card.CardType.ROOM);
		
		// cards for weapons 
		laserGunCard = new Card("Laser Gun",Card.CardType.WEAPON);
		moonRockCard = new Card("Moon Rock",Card.CardType.WEAPON);
		lightsaberCard = new Card("Lightsaber",Card.CardType.WEAPON);
		wrenchCard = new Card("Wrench",Card.CardType.WEAPON);
		liveWireCard = new Card("Live Wire",Card.CardType.WEAPON);
		knifeCard = new Card("Knife",Card.CardType.WEAPON);
		
		// six people
		yellowCard = new Card("Dr. Dandelion",Card.CardType.PERSON);
		brownCard = new Card("Bishop Brown",Card.CardType.PERSON);
		grayCard = new Card("Sir Silver",Card.CardType.PERSON);
		orangeCard = new Card("Officer Orange",Card.CardType.PERSON);
		maroonCard = new Card("Mrs. Maroon",Card.CardType.PERSON);
		blueCard = new Card("Professor Prizmarine",Card.CardType.PERSON);
		
		
	}
	@Test
	public void checkAccsationTests() {
		board.setSolution(bathroomCard, laserGunCard, grayCard);
		assert board.checkAccusation(new Solution(labCard,laserGunCard, grayCard))==false;
		assert board.checkAccusation(new Solution(bathroomCard,laserGunCard, blueCard))==false;
		assert board.checkAccusation(new Solution(bathroomCard,knifeCard, grayCard))==false;
		
	}
}
