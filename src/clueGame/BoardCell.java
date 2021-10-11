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
	// String that represents cell in layoutStrings 2d array
	private String layoutString;
	private int layoutStringLength;
	
	public BoardCell(int row, int col, String layoutString) {
		this.row = row;
		this.col = col;
		this.layoutString = layoutString;
		this.layoutStringLength = layoutString.length();
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
	
	// Returning door direction based on what character is at second position of layoutString
	public DoorDirection getDoorDirection() {
		char secondChar = layoutString.charAt(1);
		if (secondChar == '<') {
			return DoorDirection.LEFT;
		} else if (secondChar == '>') {
			return DoorDirection.RIGHT;
		} else if (secondChar == '^') {
			return DoorDirection.UP;
		} else if (secondChar == 'v') {
			return DoorDirection.DOWN;
		}
		return null;
	}
	
	public boolean isLabel() {	
		if (layoutStringLength > 1) {
			if (layoutString.charAt(1) == '#') {
				return true;
			}
		}
		return false;
	}
	
	public boolean isRoomCenter() {
		if (layoutStringLength > 1) {
			if (layoutString.charAt(1) == '*') {
				return true;
			}
		}
		return false;		
	}

	public char getSecretPassage() {
		if(layoutStringLength> 1) {
			return layoutString.charAt(1);
		}
		return (Character) null;
	}
	
	public String getLayoutString() {
		return layoutString;
	}
	
	@Override
	public String toString() {
		return "TestCell [" + row + "][" + col + "]";
	}
	
}

