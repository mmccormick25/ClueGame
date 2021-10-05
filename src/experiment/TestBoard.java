package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	TestBoardCell testCell = new TestBoardCell(-1, -1);
	final static int COLS = 4;
	final static int ROWS = 4;
	public TestBoardCell[][] testCells = new TestBoardCell[COLS][ROWS];
	public Set<TestBoardCell> targets = new HashSet<>();
	public TestBoard() {
		for (int c = 0; c < COLS; c++) {
			for (int r = 0; r < ROWS; r++) {
				testCells[r][c] = new TestBoardCell(r, c);
			}
		}
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
		
	}
	
	public TestBoardCell getCell(int row, int col) {
		return testCells[row][col];
	}
}
