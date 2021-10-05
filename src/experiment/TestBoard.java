package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	public Set<TestBoardCell> testCells = new HashSet<>();
	TestBoardCell testCell = new TestBoardCell(-1, -1);
	final static int COLS = 4;
	final static int ROWS = 4;
	public TestBoard() {
		for (int c = 0; c < COLS; c++) {
			for (int r = 0; r < ROWS; r++) {
				testCells.add(new TestBoardCell(r, c));
			}
		}
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		
	}
	
	public Set<TestBoardCell> getTargets() {
		// Adding incorrect cell to set
		testCells.add(testCell);
		return testCells;
		
	}
	
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell cell = testCell;
		for (TestBoardCell c : testCells) {
			if (c.col == col && c.row == row) {
				cell = c;
			}
		}
		return cell;
	}
	
	public void printBoard() {
		for (TestBoardCell c : testCells) {
			System.out.println(c.row + " " + c.col);
		}
	}
}
