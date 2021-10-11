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
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
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

	@Override
	public String toString() {
		return "TestCell [" + row + "][" + col + "]";
	}
	
	public boolean isDoorway() {
		return false;
	}
	public DoorDirection getDoorDirection() {
		return null;
	}
	public boolean isLabel() {	
		boolean result = false;
		try {
			if(this.toString().equals(Board.rooms.get('C').getLabelCell().toString()) &&  Board.getInstance().getRoom(this) ==Board.rooms.get('C') ) {
				result = true;
			}else if(this.toString().equals(Board.rooms.get('K').getLabelCell().toString()) &&  Board.getInstance().getRoom(this) ==Board.rooms.get('K') ) {
				result = true;
			}else if(this.toString().equals(Board.rooms.get('B').getLabelCell().toString()) &&  Board.getInstance().getRoom(this) ==Board.rooms.get('B') ) {
				result = true;
			}else if(this.toString().equals(Board.rooms.get('R').getLabelCell().toString()) &&  Board.getInstance().getRoom(this) ==Board.rooms.get('R') ) {
				result = true;
			}else if(this.toString().equals(Board.rooms.get('L').getLabelCell().toString()) &&  Board.getInstance().getRoom(this) ==Board.rooms.get('L') ) {
				result = true;
			}else if(this.toString().equals(Board.rooms.get('S').getLabelCell().toString()) &&  Board.getInstance().getRoom(this) ==Board.rooms.get('S') ) {
				result = true;
			}else if(this.toString().equals(Board.rooms.get('D').getLabelCell().toString()) &&  Board.getInstance().getRoom(this) ==Board.rooms.get('D') ) {
				result = true;
			}else if((this.toString().equals(Board.rooms.get('O').getLabelCell().toString())) &&  (Board.getInstance().getRoom(this) ==Board.rooms.get('O')) ) {
				result = true;
			}else if(this.toString().equals(Board.rooms.get('H').getLabelCell().toString()) &&  Board.getInstance().getRoom(this) ==Board.rooms.get('H') ) {
				result = true;
			}
		} catch (Exception e) {
			System.out.println("Error message.");
		}
				
			return result;			
	}
	public boolean isRoomCenter() {
		boolean result = false;
		try {
			if(this.toString().equals(Board.rooms.get('C').getCenterCell().toString()) &&  Board.getInstance().getRoom(this) ==Board.rooms.get('C') ) {
				result = true;
			}else if(this.toString().equals(Board.rooms.get('K').getCenterCell().toString()) &&  Board.getInstance().getRoom(this) ==Board.rooms.get('K') ) {
				result = true;
			}else if(this.toString().equals(Board.rooms.get('B').getCenterCell().toString()) &&  Board.getInstance().getRoom(this) ==Board.rooms.get('B') ) {
				result = true;
			}else if(this.toString().equals(Board.rooms.get('R').getCenterCell().toString()) &&  Board.getInstance().getRoom(this) ==Board.rooms.get('R') ) {
				result = true;
			}else if(this.toString().equals(Board.rooms.get('L').getCenterCell().toString()) &&  Board.getInstance().getRoom(this) ==Board.rooms.get('L') ) {
				result = true;
			}else if(this.toString().equals(Board.rooms.get('S').getCenterCell().toString()) &&  Board.getInstance().getRoom(this) ==Board.rooms.get('S') ) {
				result = true;
			}else if(this.toString().equals(Board.rooms.get('D').getCenterCell().toString()) &&  Board.getInstance().getRoom(this) ==Board.rooms.get('D') ) {
				result = true;
			}else if((this.toString().equals(Board.rooms.get('O').getCenterCell().toString())) &&  (Board.getInstance().getRoom(this) ==Board.rooms.get('O')) ) {
				result = true;
			}else if(this.toString().equals(Board.rooms.get('H').getCenterCell().toString()) &&  Board.getInstance().getRoom(this) ==Board.rooms.get('H') ) {
				result = true;
			}
		} catch (Exception e) {
			System.out.println("Error message.");
		}
			
		return result;		
	}

	public char getSecretPassage() {
		return 'a';
	}
	
}

