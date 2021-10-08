// Authors: Matthew McCormick and Zhen Liu

package clueGame;

public class Room {
	public String name;
	public BoardCell centerCell;
	public BoardCell labelCell;
	
	public Room(String name, BoardCell centerCell, BoardCell labelCell) {
		
	}
	public Room(BoardCell cell) {
		
	}
	public String getName() {
		return "hi";
	}
	public BoardCell getLabelCell() {
		return centerCell;
	}
	public BoardCell getCenterCell() {
		return centerCell;
	}

}
