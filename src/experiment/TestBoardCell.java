package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int row;
	private int col;
	// Creating test set to avoid errors
	public Set<TestBoardCell> testAdjacent = new HashSet<>();
	private boolean inRoom = false;
	private boolean isOccupied = false;
	public TestBoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Set<TestBoardCell> getAdjList() {
		// Adding incorrect TestBoardCell to set
		testAdjacent.add(new TestBoardCell(-1, -1));
		return testAdjacent;
	}
	
	public void setIsRoom(boolean room) {
		
	}
	
	public boolean isRoom( ) {
		return inRoom;
	}
	
	public void setOccupied(boolean occupied) {

	}
	
	public boolean getOccupied( ) {
		return isOccupied;
	}

	
}
