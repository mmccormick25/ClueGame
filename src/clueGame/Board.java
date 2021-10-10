// Authors: Matthew McCormick and Zhen Liu

package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private static int numRows;
	private static int numCols;
	
	private BoardCell[][] cells;
	private Set<BoardCell> targets = new HashSet<>();
	HashSet<BoardCell> visitedList = new HashSet<BoardCell>();
	
	private static Board theInstance = new Board();
	private static File setupFile;
	private static File layoutFile;
	
	// Creating 2d array list of strings that will represent layout
	ArrayList<ArrayList<String>> layoutStrings = new ArrayList<ArrayList<String>>(0);
	// Creating 2d array list of strings that will hold info about tile types
	ArrayList<ArrayList<String>> setupStrings = new ArrayList<ArrayList<String>>(0);

	// Singleton object
	// constructor is private to ensure only one can be created
	private Board() {
		super() ;
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize() throws FileNotFoundException
	{
		loadSetupConfig();
		loadLayoutConfig();
		
		// Getting number of rows which is num of arrayLists in layoutStrings
		numRows = layoutStrings.size();
		// Getting length of first row which is length of any arrayList in layoutStrings
		numCols = layoutStrings.get(0).size();
		
		// Initializing 2d list of cells to be same size as board
		cells = new BoardCell[numRows][numCols];
		
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				cells[r][c] = new BoardCell(r, c);
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
			} else {
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
		return cells[row][col];
	}
	
	public Room getRoom(char x) {

		return new Room(null);

	}
	
	public int getNumRows() {
		return numRows;

	}
	
	public int getNumColumns() {
		return numCols;

	}
	
	public Room getRoom(BoardCell cell) {
		Room room = new Room(cell);
		return room;
	}
	
	public void setConfigFiles(String fileName1,String fileName2) {
		// Creating new file objects to hold source files for board
		setupFile = new File("data/" + fileName1);
		layoutFile = new File("data/" + fileName2);
	}
	
	public void loadSetupConfig() throws FileNotFoundException {
		Scanner setupReader = new Scanner(setupFile);
		while (setupReader.hasNextLine()) {
			// Creating array list of strings that represents a row in the board
			ArrayList<String> row = new ArrayList<String>(0);
			// Getting list of strings by splitting line at commas
	        String data = setupReader.nextLine();
	        String[] splitData = data.split(",");
	        // Adding strings to row
	        for (String s : splitData) {
	        	row.add(s);
	        }
	        // Adding row to 2d array
	        layoutStrings.add(row);
	    }
	}
	
	public void loadLayoutConfig() throws FileNotFoundException {
		Scanner layoutReader = new Scanner(layoutFile);
		while (layoutReader.hasNextLine()) {
			// Creating array list of strings that will hold each tile info entry
			ArrayList<String> row = new ArrayList<String>(0);
			// Getting list of strings by splitting line at commas with a space after
	        String data = layoutReader.nextLine();
	        String[] splitData = data.split(", ");
	        // Adding strings to row
	        for (String s : splitData) {
	        	row.add(s);
	        }
	        // Checking if row starts with '//', if so it is not added to the 2d array
	        if (!row.get(0).substring(0, 2).equals("//")) {
	        	setupStrings.add(row);
        	}
	    }
		// Testing file was read properly
		for (ArrayList<String> i : setupStrings) {
			for (String n : i) {
				System.out.print(n + " ");
			}
			System.out.println("");
		}
			
	}



}
