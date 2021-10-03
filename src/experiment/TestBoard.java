package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	// Creating test set and TestBoardCell object to avoid errors
	public Set<TestBoardCell> testCells = new HashSet<>();
	public TestBoardCell testCell = new TestBoardCell(-1, -1);
	
	public TestBoard() {
		
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		
	}
	
	public Set<TestBoardCell> getTargets() {
		// Adding incorrect cell to set
		testCells.add(testCell);
		return testCells;
		
	}
	
	public TestBoardCell getCell(int row, int col) {
		// Returning incorrect cell
		return testCell;
	}
}
