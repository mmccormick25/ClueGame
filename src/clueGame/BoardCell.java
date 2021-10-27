// Authors: Matthew McCormick and Zhen Liu

package clueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BoardCell {
	int row;
	int col;
	// Creating test set to avoid errors
	Set<BoardCell> adjacent = new HashSet<>();
	boolean inRoom = false;
	private boolean isOccupied = false;
	boolean isDoorway = false;
	// String that represents cell in layoutStrings 2d array
	String layoutString;
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
		adjacent.add(cell);
	}
	
	public void setDoorway() {
		this.isDoorway = true;
	}
	
	public boolean isDoorway() {
		return isDoorway;
	}
	
	// Returning door direction based on what character is at second position of layoutString
	public DoorDirection getDoorDirection() {
		if(layoutString.contains("<")) {	
			return DoorDirection.LEFT;
		} else if (layoutString.contains(">")) {
			return DoorDirection.RIGHT;
		} else if (layoutString.contains("^")) {
			return DoorDirection.UP;
		} else if (layoutString.contains("v")) {
			return DoorDirection.DOWN;
		}
		return null;
	}
	
	public boolean isLabel() {	
		if (layoutStringLength > 1) {
			if (layoutString.contains("#")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isRoomCenter() {
		if (layoutStringLength > 1) {
			if (layoutString.contains("*")) {
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

}

