// Authors: Matthew McCormick and Zhen Liu

package clueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BoardCell {
	private int row;
	private int col;
	// Creating test set to avoid errors
	private Set<BoardCell> testAdjacent = new HashSet<>();
	private boolean inRoom = false;
	private boolean isOccupied = false;
	private boolean isDoorway = false;

	private String layoutString;
	public BoardCell(int row, int col, String layoutString) {
		this.row = row;
		this.col = col;
		this.layoutString = layoutString;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public Set<BoardCell> getAdjList(Board board) {
		testAdjacent.clear();
		if (row > 0) {
			testAdjacent.add(board.getCell(row - 1, col));
		}
		if (row < Board.getNumRows() - 1) {
			testAdjacent.add(board.getCell(row + 1, col));
		}
		if (col > 0) {
			testAdjacent.add(board.getCell(row, col - 1));
		}
		if (col < Board.getNumColumns() - 1) {
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
	
	public void setDoorway() {
		this.isDoorway = true;
	}
	
	public boolean isDoorway() {
		return isDoorway;
	}
	
	public DoorDirection getDoorDirection() {
		if (layoutString.charAt(1) == '<') {
			return DoorDirection.LEFT;
		} else if (layoutString.charAt(1) == '>') {
			return DoorDirection.RIGHT;
		} else if (layoutString.charAt(1) == '^') {
			return DoorDirection.UP;
		} else if (layoutString.charAt(1) == 'v') {
			return DoorDirection.DOWN;
		}
		return null;
	}
	
	public boolean isLabel() {	
		if (layoutString.length() > 1) {
			if (layoutString.charAt(1) == '#') {
				return true;
			}
		}
		return false;
	}
	
	public boolean isRoomCenter() {
		if (layoutString.length() > 1) {
			if (layoutString.charAt(1) == '*') {
				return true;
			}
		}
		return false;		
	}

	public char getSecretPassage() {
		if(layoutString.length()> 1) {
			return layoutString.charAt(1);
		}
		return 'a';
	}
	
	@Override
	public String toString() {
		return "TestCell [" + row + "][" + col + "]";
	}
	
}

