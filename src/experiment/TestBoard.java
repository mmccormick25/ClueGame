package experiment;

import java.util.Set;

public class TestBoard {
	private Set<TestBoardCell> cells;
	public TestBoard() {
	//	 this.cells = new Set<TestBoardCell>(22, 26);
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		
	}
	
	public Set<TestBoardCell> getTargets() {
		
		return null;
		
	}
	
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell cell = new TestBoardCell(row,col);
		return cell;
	}
}
