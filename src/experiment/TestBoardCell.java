// Authors: Matthew McCormick and Zhen Liu
package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int row;
	private int col;
	// Creating test set to avoid errors
	private Set<TestBoardCell> testAdjacent = new HashSet<>();
	private boolean inRoom = false;
	private boolean isOccupied = false;
	public TestBoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	// check the boundaries of the testboard and return the adjacent list. 
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
	// set the boolean value to room.
	public void setIsRoom(boolean room) {
		inRoom = room;
	}
	// get the value of inRoom
	public boolean isRoom( ) {
		return inRoom;
	}
	// set the boolean value to occupied
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}
	// get the value of occupied
	public boolean getOccupied( ) {
		return isOccupied;
	}
	
	public void addAdjacency(TestBoardCell cell) {
		testAdjacent.add(cell);
	}
/* for debugging use.
	@Override
	public String toString() {
		return "TestCell [" + row + "][" + col + "]";
	}

	*/
}
