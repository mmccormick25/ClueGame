// Authors: Matthew McCormick and Zhen Liu

package clueGame;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private boolean doorRoom;
	private BoardCell secretCell;
	private boolean hasSecretPath;
	
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
	
	public boolean getDoorRoom() {
		return this.doorRoom;
	}
	
	public void setDoorRoom() {
		this.doorRoom = true;
	}
	
	public void setLabelCell(BoardCell cell) {
		this.labelCell = cell;
	}
	
	public void setCenterCell(BoardCell cell) {
		this.centerCell = cell;
	}
	public void setSecretPath(BoardCell cell) {
		this.hasSecretPath = true;
		this.secretCell = cell;
	}
	
	public boolean getSecretPath() {
		return this.hasSecretPath;
	}
	public BoardCell getSecretCell() {
		return this.secretCell;
	}
	
	@Override
	public String toString() {
		return "Room [" + name + "]";
	}
	



}
