package tests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class ComputerAITest {
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
	public void selectTargetsCheck() {
		// test the situation of no room in the target list
		ComputerPlayer aiOne = new ComputerPlayer("test player","red",7,9);
		BoardCell targetCell = aiOne.selectTargets(board.getCell(7, 9), 2);
		assert targetCell != board.getCell(3, 3);
		assert targetCell != board.getCell(9, 3);
		
		// test the situation of one room in the target list
		ComputerPlayer aiTwo = new ComputerPlayer("test player","yello",5,19);
		BoardCell targetCellTwo = aiTwo.selectTargets(board.getCell(5, 19), 3);
		assert targetCellTwo == board.getCell(5, 24);
		
		// test the situation for room in the target list and has been seen. 
		ComputerPlayer aiThree = new ComputerPlayer("test player","black",13,20);
		ArrayList<Card> testSeenCards = new ArrayList<Card>();
		aiThree.setSeenRooms(bathroomCard);
		testSeenCards = aiThree.getSeenRooms();
		BoardCell targetCellThree = aiThree.selectTargets(board.getCell(13, 20), 3);
		assert targetCellThree != board.getCell(14, 19);
		
		
		
	}
}
