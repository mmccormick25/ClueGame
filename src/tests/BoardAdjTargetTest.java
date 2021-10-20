package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeAll
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, the green house that has two doors
		Set<BoardCell> testList = board.getCell(3,3).getAdjList(board);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(5, 7)));
		assertTrue(testList.contains(board.getCell(5, 8)));
		
		// now test the lab which has secret passage
		testList = board.getCell(9,2).getAdjList(board);
		assertEquals(2, testList.size());
		assertFalse(testList.contains(board.getCell(11, 5)));
		assertTrue(testList.contains(board.getCell(13, 25)));

		
		// one more room, 
		testList = board.getCell(19,9).getAdjList(board);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(15, 9)));
		assertTrue(testList.contains(board.getCell(15, 10)));
		assertTrue(testList.contains(board.getCell(5, 14)));

	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getCell(14,5).getAdjList(board);
		for (BoardCell c : testList) {
			System.out.println(c.toString());
		}
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(17, 2)));
		assertTrue(testList.contains(board.getCell(13, 5)));
		assertTrue(testList.contains(board.getCell(15, 5)));
		assertTrue(testList.contains(board.getCell(14, 6)));



		testList = board.getCell(17,23).getAdjList(board);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(17, 22)));
		assertTrue(testList.contains(board.getCell(16, 23)));
		assertTrue(testList.contains(board.getCell(19, 25)));
		
		testList = board.getCell(15,9).getAdjList(board);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(15, 8)));
		assertTrue(testList.contains(board.getCell(15, 10)));
		assertTrue(testList.contains(board.getCell(14, 9)));
		assertTrue(testList.contains(board.getCell(19, 9)));
	}
	
	// Test a variety of walkway scenarios
	@Test
	public void testAdjacencyWalkways()
	{
		// 
		Set<BoardCell> testList = board.getCell(21,5).getAdjList(board);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(20, 5)));
		
		// 
		testList = board.getCell(6,5).getAdjList(board);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(6, 4)));
		assertTrue(testList.contains(board.getCell(5, 5)));
		assertTrue(testList.contains(board.getCell(7, 5)));

		// Test adjacent to walkways
		testList = board.getCell(9,6).getAdjList(board);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(9, 5)));
		assertTrue(testList.contains(board.getCell(9, 7)));
		assertTrue(testList.contains(board.getCell(10, 6)));
		assertTrue(testList.contains(board.getCell(8, 6)));

		// Test next to closet
		testList = board.getCell(9,15).getAdjList(board);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(9, 14)));
		assertTrue(testList.contains(board.getCell(9, 16)));
		assertTrue(testList.contains(board.getCell(8, 15)));
	
	}
	
	
	// Tests out of room center, 1 and 3
	@Test
	public void testTargetsInMine() {
		// test a roll of 1
		board.calcTargets(board.getCell(5, 24), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(3, 19)));
		assertTrue(targets.contains(board.getCell(6, 20)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(5, 24), 3);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(3, 17)));
		assertTrue(targets.contains(board.getCell(5, 19)));	
		assertTrue(targets.contains(board.getCell(4, 20)));
		assertTrue(targets.contains(board.getCell(8, 20)));	
		
		
	}
	
	@Test
	public void testTargetsInBathroom() {
		// test a roll of 1
		board.calcTargets(board.getCell(13, 25), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(12, 21)));
		assertTrue(targets.contains(board.getCell(9, 2)));	
	}

	// Tests out of room center, 1 and 3
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(3, 19), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(5, 24)));
		assertTrue(targets.contains(board.getCell(3, 18)));	
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(4, 19)));	
		
		// test a roll of 2
		board.calcTargets(board.getCell(3, 19), 2);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(3, 17)));
		assertTrue(targets.contains(board.getCell(4, 18)));
		assertTrue(targets.contains(board.getCell(5, 19)));	
		assertTrue(targets.contains(board.getCell(4, 20)));

		

	
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(19, 21), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(20, 21)));
		assertTrue(targets.contains(board.getCell(18, 21)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(19, 21), 3);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertFalse(targets.contains(board.getCell(21, 21)));
		assertTrue(targets.contains(board.getCell(16, 21)));
		assertTrue(targets.contains(board.getCell(17, 22)));	
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(7, 12), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(7, 11)));
		assertTrue(targets.contains(board.getCell(8, 12)));	
		
		// test a roll of 2
		board.calcTargets(board.getCell(7, 12), 2);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(7, 10)));
		assertTrue(targets.contains(board.getCell(9, 12)));
		assertTrue(targets.contains(board.getCell(8, 11)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 2 with blocked path below
		board.getCell(3, 17).setOccupied(true);
		board.calcTargets(board.getCell(2, 17), 2);
		board.getCell(3, 17).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(1, 16)));
		assertTrue(targets.contains(board.getCell(0, 17)));
	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(5, 14).setOccupied(true);
		board.calcTargets(board.getCell(1, 14), 1);
		board.getCell(5, 14).setOccupied(false);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(5, 14)));	
		assertTrue(targets.contains(board.getCell(0, 14)));	
		assertTrue(targets.contains(board.getCell(1, 15)));	
		
		// check leaving a room with a blocked doorway
		
	
		board.getCell(15, 9).setOccupied(true);
		board.calcTargets(board.getCell(19, 9), 2);
		board.getCell(15, 9).setOccupied(false);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(15,11)));
		assertTrue(targets.contains(board.getCell(5, 14)));	
		assertTrue(targets.contains(board.getCell(14, 10)));

	}


	
	
}
