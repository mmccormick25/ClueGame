// Authors: Matthew McCormick and Zhen Liu

package clueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BoardCell {
	private int row;
	private int col;
	// Creating test set to avoid errors
	private Set<BoardCell> adjacent = new HashSet<>();
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
		if (!this.inRoom) {
			adjacent.clear();
		}
		
		BoardCell up = null;
		BoardCell down = null;
		BoardCell left = null;
		BoardCell right = null;
		
		Character blockChar = 'X';
		
		// Checking if directions are valid based on if they are on edge of the board or are in closet
		if (row > 0) {
			up = board.getCell(row - 1, col);
			if (up.getLayoutString().charAt(0) == blockChar || up.getOccupied()) {
				up = null;
			}
		}
		if (row < Board.getNumRows() - 1) {
			down = board.getCell(row + 1, col);
			if (down.getLayoutString().charAt(0) == blockChar || down.getOccupied()) {
				down = null;
			}
		}
		if (col > 0) {
			left = board.getCell(row, col - 1);
			if (left.getLayoutString().charAt(0) == blockChar || left.getOccupied()) {
				left = null;
			}
		}
		if (col < Board.getNumColumns() - 1) {
			right = board.getCell(row, col + 1);
			if (right.getLayoutString().charAt(0) == blockChar ||  right.getOccupied()) {
				right = null;
			}
		}
		
		// Logic for doorways, only adds room to adjacency list if door is facing that room
		if (isDoorway) { 
			if (up != null) {
				if (up.inRoom && this.getDoorDirection() == DoorDirection.UP) {
					Room room = board.getRoom(up);
					BoardCell center = room.getCenterCell();
					center.addAdjacency(this);
					adjacent.add(center);
				} else if (!up.inRoom) {
					adjacent.add(up);
				}
			}
			if (down != null) {
				if (down.inRoom && this.getDoorDirection() == DoorDirection.DOWN) {
					Room room = board.getRoom(down);
					BoardCell center = room.getCenterCell();
					center.addAdjacency(this);
					adjacent.add(center);
				} else if (!down.inRoom) {
					adjacent.add(down);
				}
			}
			if (left != null) {
				if (left.inRoom && this.getDoorDirection() == DoorDirection.LEFT) {
					Room room = board.getRoom(left);
					BoardCell center = room.getCenterCell();
					center.addAdjacency(this);
					adjacent.add(center);
				} else if (!left.inRoom) {
					adjacent.add(left);
				}
			}
			if (right != null) {
				if (right.inRoom && this.getDoorDirection() == DoorDirection.RIGHT) {
					Room room = board.getRoom(right);
					BoardCell center = room.getCenterCell();
					center.addAdjacency(this);
					adjacent.add(center);
				} else if (!right.inRoom) {
					adjacent.add(right);
				}
			}
		// Logic for regular pathways
		} else if (!inRoom){
			if (up != null && !up.inRoom) {
				adjacent.add(up);
			}
			if (down != null && !down.inRoom) {
				adjacent.add(down);
			}
			if (left != null && !left.inRoom) {
				adjacent.add(left);
			}
			if (right != null && !right.inRoom) {
				adjacent.add(right);
			}
			// check the cell in the room.
		} else if (inRoom) {
			Room room = board.getRoom(this.layoutString.charAt(0));
			if(room.getSecretPath()) {
				char temp = room.getSecretCell().layoutString.charAt(1);
				Room transferRoom = board.getRoom(temp);
				BoardCell center = transferRoom.getCenterCell();
				adjacent.add(center);
			}
		}
		
		return adjacent;
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

