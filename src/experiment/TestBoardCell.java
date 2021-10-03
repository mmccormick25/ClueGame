package experiment;

import java.util.Set;

public class TestBoardCell {
	private int row;
	private int col;
	private Set<TestBoardCell> adjacent;
	private boolean inRoom = false;
	private boolean isOccupied = false;
	public TestBoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjacent;
	}
	
	public void setRoom(boolean room) {
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
	
}
