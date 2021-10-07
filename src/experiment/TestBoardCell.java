package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	public int row;
	public int col;
	// Creating test set to avoid errors
	public Set<TestBoardCell> testAdjacent = new HashSet<>();
	private boolean inRoom = false;
	private boolean isOccupied = false;
	public TestBoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Set<TestBoardCell> getAdjList(TestBoard board) {
		testAdjacent.clear();
		if (row > 0) {
			testAdjacent.add(board.getCell(row - 1, col));
		}
		if (row < TestBoard.ROWS - 1) {
			testAdjacent.add(board.getCell(row + 1, col));
		}
		if (col > 0) {
			testAdjacent.add(board.getCell(row, col - 1));
		}
		if (col < TestBoard.COLS - 1) {
			testAdjacent.add(board.getCell(row, col + 1));
		}
		return testAdjacent;
	}
	
	public void setIsRoom(boolean room) {
		inRoom = room;
	}
	
	public boolean isRoom( ) {
		return inRoom;
	}
	
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}
	
	public boolean getOccupied( ) {
		return isOccupied;
	}
	
	public void addAdjacency(TestBoardCell cell) {
		testAdjacent.add(cell);
	}

	@Override
	public String toString() {
		return "TestCell [" + row + "][" + col + "]";
	}

	
}
