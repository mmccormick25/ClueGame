// Authors: Matthew McCormick and Zhen Liu

package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
	
	public void draw(int x, int y, int d, Graphics g) {
		if (inRoom) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(x, y, d, d);
		} else {
			g.setColor(Color.GRAY);
			g.fillRect(x, y, d, d);
			g.setColor(Color.black);
			g.drawRect(x, y, d, d);
		}
		
		if (layoutString.charAt(0) == (Board.closetChar)) {
			g.setColor(Color.red);
			g.fillRect(x, y, d, d);
		}
	}
	
	public void drawTarget(int d, Graphics g) {
		g.setColor(Color.white);
		g.fillRect(col * d, row * d, d, d);
		
	}
	
	public void drawSecond(int x, int y, int d, Graphics g) {
		g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		if (isDoorway) {
			g.setColor(Color.blue);
			if (layoutString.charAt(1) == '<') {
				g.fillRect(x - 5, y, 5, d);
			} else if (layoutString.charAt(1) == '>') {
				g.fillRect(x + d, y, 5, d);
			} else if (layoutString.charAt(1) == 'v') {
				g.fillRect(x, y + d, d, 5);
			} else if (layoutString.charAt(1) == '^') {
				g.fillRect(x, y - 5, d, 5);
			}
		} else if (layoutString.length() > 1) {
			if (layoutString.charAt(1) == '#') {
				g.setColor(Color.cyan);
				Room room = Board.getInstance().getRoom(layoutString.charAt(0));
				g.drawString(room.getName(), x, y);
			}
		}
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

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", col=" + col + "]";
	}
	
	

	public String getLayoutString() {
		return layoutString;
	}

}

