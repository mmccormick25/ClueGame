// Authors: Matthew McCormick and Zhen Liu

package clueGame;

public class Room {
	private static String name;
	private static BoardCell centerCell;
	private static BoardCell labelCell;
	
	
	public Room(String name, BoardCell centerCell, BoardCell labelCell) {
		this.name = name;
		this.centerCell = centerCell;
		this.labelCell = labelCell;
	}
	
	public String getName() {
		return name;
	}
	
	public BoardCell getLabelCell() {
		return centerCell;
	}
	
	public BoardCell getCenterCell() {
		return centerCell;
	}

}
