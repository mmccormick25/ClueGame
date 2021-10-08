package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private int row;
	private int col;
	// Creating test set to avoid errors
	private Set<BoardCell> testAdjacent = new HashSet<>();
	private boolean inRoom = false;
	private boolean isOccupied = false;
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Set<BoardCell> getAdjList(Board board) {
		testAdjacent.clear();
		if (row > 0) {
			testAdjacent.add(board.getCell(row - 1, col));
		}
		if (row < Board.ROWS - 1) {
			testAdjacent.add(board.getCell(row + 1, col));
		}
		if (col > 0) {
			testAdjacent.add(board.getCell(row, col - 1));
		}
		if (col < Board.COLS - 1) {
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
	
	public void addAdjacency(BoardCell cell) {
		testAdjacent.add(cell);
	}

	@Override
	public String toString() {
		return "TestCell [" + row + "][" + col + "]";
	}
	
	public boolean isDoorway() {
		return true;
	}
	public DoorDirection getDoorDirection() {
		return null;
	}
	public boolean isLabel() {		
		return false;		
	}
	public boolean isRoomCenter() {
		return false;
	}
	public char getSecretPassage() {
		return 'a';
	}
	
}

