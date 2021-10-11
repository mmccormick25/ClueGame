// Authors: Matthew McCormick and Zhen Liu

package clueGame;

import java.util.ArrayList;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private Character label;
	private ArrayList<BoardCell> doorCell;
	
	public Room(String name) {
		super();
		this.name = name;
	}
	

	
	public String getName() {
		return this.name;
	}
	
	public BoardCell getLabelCell() {
		return this.labelCell;
	}
	
	public BoardCell getCenterCell() {
		return this.centerCell;
	}
	
	public void setLabelCell(BoardCell cell) {
		this.labelCell = cell;
	}
	
	public void setCenterCell(BoardCell cell) {
		this.centerCell = cell;
	}
	
	@Override
	public String toString() {
		return "Room [" + name + "]";
	}
	



}
