package clueGame;

import java.util.HashSet;
import java.util.Set;

public class Board {
	final static int COLS = 4;
	final static int ROWS = 4;
	private BoardCell[][] testCells = new BoardCell[ROWS][COLS];
	private Set<BoardCell> targets = new HashSet<>();
	HashSet<BoardCell> visitedList = new HashSet<BoardCell>();
	
	public Board() {
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				testCells[r][c] = new BoardCell(r, c);
			}
		}
	}
	public void findAllTargets(BoardCell cell, int length) {
		for (BoardCell adjCell: cell.getAdjList(this)) {
			if(visitedList.contains(adjCell) || adjCell.getOccupied()) {
				continue;
			}
			
				visitedList.add(adjCell);
				
			if(length == 1 || adjCell.isRoom()) {
				targets.add(adjCell);
				}else {
					findAllTargets(adjCell,length-1);
				}
			visitedList.remove(adjCell);
		}

		
	}
	
	public void calcTargets(BoardCell startCell, int pathlength) {
		visitedList.clear();
		visitedList.add(startCell);
		findAllTargets(startCell,pathlength);
		
	}
	
	public Set<BoardCell> getTargets() {
		
		for(BoardCell test:targets) {
			System.out.print(test);
		}
		System.out.println();
		
		return targets;
		
	}
	
	public BoardCell getCell(int row, int col) {
		return testCells[row][col];
	}
	

}
