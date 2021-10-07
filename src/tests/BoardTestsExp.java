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
		TestBoardCell cell = board.getCell(1,1);
		Set<TestBoardCell> testList = cell.getAdjList(board);
		Assert.assertTrue(testList.contains(board.getCell(1,0)));
		Assert.assertTrue(testList.contains(board.getCell(0,1)));
		Assert.assertTrue(testList.contains(board.getCell(1,2)));
		Assert.assertTrue(testList.contains(board.getCell(2,1)));
		Assert.assertEquals(4, testList.size());
		
}
	@Test
	public void testTargetsMixed() {
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setIsRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		System.out.println("target in Mixed:" + targets.size());
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));

}
	@Test
	public void testTargetsNormal() {
		TestBoardCell cell = board.getCell(0, 0);
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
	}
	
	@Test
	public void testInRoom() {
		TestBoardCell cell = board.getCell(3,3);
		cell.setIsRoom(true);
		Assert.assertTrue(cell.isRoom());
	}
	
	@Test
	public void testIsOccupied() {
		TestBoardCell cell = board.getCell(2,1);
		cell.setOccupied(true);
		Assert.assertTrue(cell.getOccupied());
	}
}

