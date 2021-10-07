// Authors: Matthew McCormick and Zhen Liu

package tests;
 
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach
	public void setUp() {
		this.board = new TestBoard();
	}
	
	@Test
	public void testAdjacency() {
<<<<<<< HEAD
		TestBoardCell cell = board.getCell(1,1);
		Set<TestBoardCell> testList = cell.getAdjList(board);
		Assert.assertTrue(testList.contains(board.getCell(1,0)));
		Assert.assertTrue(testList.contains(board.getCell(0,1)));
		Assert.assertTrue(testList.contains(board.getCell(1,2)));
		Assert.assertTrue(testList.contains(board.getCell(2,1)));
=======
		// Testing adjacency for top left corner
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList(board);
		Assert.assertTrue(testList.contains(board.getCell(1,0)));
		Assert.assertTrue(testList.contains(board.getCell(0,1)));
		Assert.assertEquals(2, testList.size());
		// Testing adjacency for bottom right corner
		cell = board.getCell(3,3);
		testList = cell.getAdjList(board);
		Assert.assertTrue(testList.contains(board.getCell(2,3)));
		Assert.assertTrue(testList.contains(board.getCell(3,2)));
		Assert.assertEquals(2, testList.size());
		// Testing adjacency for right edge
		cell = board.getCell(2,3);
		testList = cell.getAdjList(board);
		Assert.assertTrue(testList.contains(board.getCell(2,2)));
		Assert.assertTrue(testList.contains(board.getCell(1,3)));
		Assert.assertTrue(testList.contains(board.getCell(3,3)));
		Assert.assertEquals(3, testList.size());
		// Testing adjacency for left edge
		cell = board.getCell(1,0);
		testList = cell.getAdjList(board);
		Assert.assertTrue(testList.contains(board.getCell(0,0)));
		Assert.assertTrue(testList.contains(board.getCell(1,1)));
		Assert.assertTrue(testList.contains(board.getCell(2,0)));
		Assert.assertEquals(3, testList.size());
		// Testing adjacency for middle
		cell = board.getCell(2,1);
		testList = cell.getAdjList(board);
		Assert.assertTrue(testList.contains(board.getCell(2,0)));
		Assert.assertTrue(testList.contains(board.getCell(1,1)));
		Assert.assertTrue(testList.contains(board.getCell(3,1)));
		Assert.assertTrue(testList.contains(board.getCell(2,2)));
>>>>>>> b81b5328fcc0205e3a7b2aa28973f1ddd7488532
		Assert.assertEquals(4, testList.size());
		
	}
	
	@Test
	public void testTargetsMixed() {
		// Setting up occupied cells
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setIsRoom(true);
<<<<<<< HEAD
=======
		// Testing from cell (0, 3) with roll of 3
>>>>>>> b81b5328fcc0205e3a7b2aa28973f1ddd7488532
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		System.out.println("target in Mixed:" + targets.size());
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
<<<<<<< HEAD
=======
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		// Testing from cell (0, 0) with roll of 2
		cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		// Testing from cell (3, 2) with roll of 4
		cell = board.getCell(3, 2);
		board.calcTargets(cell, 4);
		targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
>>>>>>> b81b5328fcc0205e3a7b2aa28973f1ddd7488532

	}
	@Test
	public void testTargetsNormal() {
<<<<<<< HEAD
		TestBoardCell cell = board.getCell(0, 0);
=======
		// Testing from cell (1, 4) with roll of 3
		TestBoardCell cell = board.getCell(1, 4);
>>>>>>> b81b5328fcc0205e3a7b2aa28973f1ddd7488532
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		System.out.println("target in Normal:" + targets.size());
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		// Testing from cell (0, 0) with roll of 2
		cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
	}
	
	@Test
	public void testInRoom() {
		// Testing if set room and get room functions work properly
		TestBoardCell cell = board.getCell(4,4);
		cell.setIsRoom(true);
		Assert.assertTrue(cell.isRoom());
	}
	
	@Test
	public void testIsOccupied() {
		// Testing if set occupied and get occupied functions work properly
		TestBoardCell cell = board.getCell(2,1);
		cell.setOccupied(true);
		Assert.assertTrue(cell.getOccupied());
	}
}