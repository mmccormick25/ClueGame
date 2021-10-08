package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	final static int COLS = 4;
	final static int ROWS = 4;
	private TestBoardCell[][] testCells = new TestBoardCell[ROWS][COLS];
	private Set<TestBoardCell> targets = new HashSet<>();
	HashSet<TestBoardCell> visitedList = new HashSet<TestBoardCell>();
	
	public TestBoard() {
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				testCells[r][c] = new TestBoardCell(r, c);
			}
		}
	}
	public void findAllTargets(TestBoardCell cell, int length) {
		for (TestBoardCell adjCell: cell.getAdjList(this)) {
			if(visitedList.contains(adjCell) || adjCell.getOccupied()) {
				continue;
			}
			
				visitedList.add(adjCell);
				
			if(length == 1 || adjCell.isRoom()) {
				targets.add(adjCell);
				}else {
					findAllTargets(adjCell,length-1);
				}
			visitedList.remove(adjCell);
		}

		
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		visitedList.clear();
		visitedList.add(startCell);
		findAllTargets(startCell,pathlength);
		
	}
	
	public Set<TestBoardCell> getTargets() {
		
		for(TestBoardCell test:targets) {
			System.out.print(test);
		}
		System.out.println();
		
		return targets;
		
	}
	
	public TestBoardCell getCell(int row, int col) {
		return testCells[row][col];
	}
	

}
