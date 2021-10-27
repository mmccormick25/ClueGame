// Authors: Matthew McCormick and Zhen Liu

package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private static int numRows;
	private static int numCols;

	// Pathfinding arrays
	private Set<BoardCell> targets = new HashSet<>();
	private HashSet<BoardCell> visitedList = new HashSet<BoardCell>();
	// Board single instance creation
	public static Board theInstance = new Board();
	// Blank array that will hold cells
	private BoardCell[][] grid;
	// Config files
	private static File setupFile;
	private static File layoutFile;

	// Creating 2d array list of strings that will represent layout
	public ArrayList<ArrayList<String>> layoutStrings = new ArrayList<ArrayList<String>>(0);
	// Creating 2d array list of strings that will hold info about tile types
	public ArrayList<ArrayList<String>> setupStrings = new ArrayList<ArrayList<String>>(0);
	// Map that holds rooms
	public static HashMap<Character, Room> rooms = new HashMap<Character, Room>();
	// an array of special characters: <,>,^,*,#,v
	public static ArrayList<String> specialC = new ArrayList<String>(Arrays.asList("<",">","^","v","#","*"));


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
	public void initialize() {
		// Using try catch to handle exceptions from loadSetupConfig and loadLayoutConfig
		try {
			loadSetupConfig();
			loadLayoutConfig();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}

		// Getting number of rows which is num of arrayLists in layoutStrings
		numRows = layoutStrings.size();
		// Getting length of first row which is length of any arrayList in layoutStrings
		numCols = layoutStrings.get(0).size();

		// Initializing 2d list of cells to be same size as board
		grid = new BoardCell[numRows][numCols];
		initialRooms();
		initialCells();
		doorAdjList();
		
		
	}
		public void initialRooms() {
			// create 11 rooms with constructors.
			// store all the rooms in the map rooms.
			// r is a single line in the setup file, split into words
			for (ArrayList<String> r : setupStrings) {
				// Looking for character that represents room in layout file
				char c = r.get(2).charAt(0);
				// Creating new room with name in constructor
				Room room = new Room(r.get(1));
				// Checking if room is of room with door or regular space
				if (r.get(0).equals("Room")) {
					room.setDoorRoom();
				}
				// Adding room to map with key of character that represents it in layout file
				rooms.put(c, room);
			}
		}
		
public void initialCells() {
	// Running through layoutStrings 2d array list to create 2d array of cells based on entries
			for (int r = 0; r < numRows; r++) {
				for (int c = 0; c < numCols; c++) {
					// Getting string that will be used to construct cell
					String layoutString = layoutStrings.get(r).get(c);
					// Creating new cell at specified row and column with its own string that represents it in the layout
					grid[r][c] = new BoardCell(r, c, layoutString);
					// Setting cell to be in room based on first char in layout board
					if (this.getRoom(layoutString.charAt(0)).getDoorRoom()) {
						grid[r][c].setIsRoom(true);
					}
					// Checking if there is a special character after room character to set attributes of cell
					if (layoutString.length() > 1) {
						// Getting room object using first character in layoutString
						Room room = this.getRoom(layoutString.charAt(0));
						// Changing things about cell depending on what second char is
						char secondChar = layoutString.charAt(1);
						if (secondChar == '#') {
							room.setLabelCell(grid[r][c]);
							// set the as the Center cell of the room 
						} else if (secondChar == '*') {
							room.setCenterCell(grid[r][c]);
							// set cell of door way to be true. 
						} else if (secondChar == '<' || secondChar == '>' || secondChar == '^' || secondChar == 'v') {
							grid[r][c].setDoorway();
							// set the cell as the secret path for the room.
						} else {
							room.setSecretPath(grid[r][c]);
						}
					}
				}
			}

}
// get the adjacent list for rooms by getting the adjacent list for the doors.
		public void doorAdjList() {
			for (BoardCell[] r : grid) {
				for (BoardCell c : r) {
					if (c.isDoorway()) {
						theInstance.getAdjList(c);
					}

				}
			}
		}
	
		
	

	// Pathfinding methods

	public void findAllTargets(BoardCell cell, int length) {
		for (BoardCell adjCell: getAdjList(cell)) {
			if(visitedList.contains(adjCell) || (adjCell.getOccupied() && (!adjCell.isRoom()))) {
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
		targets.clear();
		visitedList.add(startCell);
		findAllTargets(startCell,pathlength);
	}

	// File init methods

	public void setConfigFiles(String fileName1,String fileName2) {
		// Creating new file objects to hold source files for board
		layoutFile = new File("data/" + fileName1);
		setupFile = new File("data/" + fileName2);
	}

	public void loadLayoutConfig() throws FileNotFoundException,BadConfigFormatException {
		layoutStrings.clear();
		Scanner setupReader = new Scanner(layoutFile);
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
		setupReader.close();

		// handle the exception if the length of each isn't the same. 
		int num = layoutStrings.get(0).size();
		int temp1 = 0;
		int temp2 = 0;
		for (ArrayList<String> i : layoutStrings) {
			for (String n : i) {
				if (num != i.size()) {
					throw new BadConfigFormatException("The gameboard doesn't have the same length in each row.");
				}
				if(true) {

					for(ArrayList<String> j : setupStrings) {
						if (!j.contains(n.substring(0,1)) ) {	// possible error
							temp1++;
						}
						if (n.length() >1) {
							if(!j.contains(n.substring(1)) && !specialC.contains(n.substring(1)) ){
								temp2++;
							}
						}
					}
				}
				if(temp1 == setupStrings.size() || n.length()>2 || temp2 == setupStrings.size()) {
					throw new BadConfigFormatException("Game board doesn't have such a room.");
				}
			}
		}
	}

	public void loadSetupConfig() throws FileNotFoundException,BadConfigFormatException  {
		setupStrings.clear();
		Scanner layoutReader = new Scanner(setupFile);
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
			if (row.size() > 1) {
				if (!row.get(0).equals("Room") && !row.get(0).equals("Space")) {
					throw new BadConfigFormatException("Incorrect setup file format.");
				}
			}
		}
		layoutReader.close();
	}

	// Getters

	public Set<BoardCell> getTargets() {
	/*	for(BoardCell test:targets) {
			System.out.print(test);
		}
		System.out.println();
		*/
		return targets;
	}

	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

	public Room getRoom(Character x) {
		return rooms.get(x);
	}

	public Room getRoom(BoardCell cell) {
		// Getting character that represents what room cell is in
		Character c = cell.getLayoutString().charAt(0);
		// Getting room from that character
		return getRoom(c);
	}

	public Set<BoardCell> getAdjList(BoardCell boardCell) {
		if (!boardCell.inRoom) {
			boardCell.adjacent.clear();
		}
		
		BoardCell up = null;
		BoardCell down = null;
		BoardCell left = null;
		BoardCell right = null;
		
		Character blockChar = 'X';
		
		// Checking if directions are valid based on if they are on edge of the board or are in closet
		if (boardCell.row > 0) {
			up = getCell(boardCell.row - 1, boardCell.col);
			if (up.getLayoutString().charAt(0) == blockChar || up.getOccupied()) {
				up = null;
			}
		}
		if (boardCell.row < Board.getNumRows() - 1) {
			down = getCell(boardCell.row + 1, boardCell.col);
			if (down.getLayoutString().charAt(0) == blockChar || down.getOccupied()) {
				down = null;
			}
		}
		if (boardCell.col > 0) {
			left = getCell(boardCell.row, boardCell.col - 1);
			if (left.getLayoutString().charAt(0) == blockChar || left.getOccupied()) {
				left = null;
			}
		}
		if (boardCell.col < Board.getNumColumns() - 1) {
			right = getCell(boardCell.row, boardCell.col + 1);
			if (right.getLayoutString().charAt(0) == blockChar ||  right.getOccupied()) {
				right = null;
			}
		}
		
		// Logic for doorways, only adds room to adjacency list if door is facing that room
		if (boardCell.isDoorway) { 
			if (up != null) {
				if (up.inRoom && boardCell.getDoorDirection() == DoorDirection.UP) {
					Room room = getRoom(up);
					BoardCell center = room.getCenterCell();
					center.addAdjacency(boardCell);
					boardCell.adjacent.add(center);
				} else if (!up.inRoom) {
					boardCell.adjacent.add(up);
				}
			}
			if (down != null) {
				if (down.inRoom && boardCell.getDoorDirection() == DoorDirection.DOWN) {
					Room room = getRoom(down);
					BoardCell center = room.getCenterCell();
					center.addAdjacency(boardCell);
					boardCell.adjacent.add(center);
				} else if (!down.inRoom) {
					boardCell.adjacent.add(down);
				}
			}
			if (left != null) {
				if (left.inRoom && boardCell.getDoorDirection() == DoorDirection.LEFT) {
					Room room = getRoom(left);
					BoardCell center = room.getCenterCell();
					center.addAdjacency(boardCell);
					boardCell.adjacent.add(center);
				} else if (!left.inRoom) {
					boardCell.adjacent.add(left);
				}
			}
			if (right != null) {
				if (right.inRoom && boardCell.getDoorDirection() == DoorDirection.RIGHT) {
					Room room = getRoom(right);
					BoardCell center = room.getCenterCell();
					center.addAdjacency(boardCell);
					boardCell.adjacent.add(center);
				} else if (!right.inRoom) {
					boardCell.adjacent.add(right);
				}
			}
		// Logic for regular pathways
		} else if (!boardCell.inRoom){
			if (up != null && !up.inRoom) {
				boardCell.adjacent.add(up);
			}
			if (down != null && !down.inRoom) {
				boardCell.adjacent.add(down);
			}
			if (left != null && !left.inRoom) {
				boardCell.adjacent.add(left);
			}
			if (right != null && !right.inRoom) {
				boardCell.adjacent.add(right);
			}
			// check the cell in the room.
		} else if (boardCell.inRoom) {
			Room room = getRoom(boardCell.layoutString.charAt(0));
			if(room.getSecretPath()) {
				char temp = room.getSecretCell().layoutString.charAt(1);
				Room transferRoom = getRoom(temp);
				BoardCell center = transferRoom.getCenterCell();
				boardCell.adjacent.add(center);
			}
		}
		
		return boardCell.adjacent;
	}
	
	public static int getNumRows() {
		return Board.numRows;
	}

	public static int getNumColumns() {
		return Board.numCols;

	}



}
