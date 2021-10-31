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
	// list of weapons
	public static ArrayList<String> weapons = new ArrayList<String>();
	// deck of all cards
	public static ArrayList<Card> deck = new ArrayList<Card>();
	
	public static Character closetChar;
	// create 6 players;
	public static ArrayList<Player> players = new ArrayList<Player>();
	// create the answer for the game;
	
	


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
		
		for (ArrayList<String> row : setupStrings) {
			String type = row.get(0);
			System.out.println(type);
			if (!type.equals("Space")) {
				deck.add(new Card(row.get(1)));
			}
			if(type.equals("Room") || type.equals("Space")) {
				initialRoom(row);
			} else if (type.equals("Weapon")) {
				initialWeapon(row);
			} else if (type.equals("Player")) {
				initialPlayer(row);
			} else if (type.equals("Computer")) {
				initialComputer(row);
			}
		}
		
		initialCells();
		doorAdjList();
		dealCards();

	}
	
	public void dealCards() {
		// TODO Auto-generated method stub
		
	}
	public void initialRoom(ArrayList<String> row) {
		// create 11 rooms with constructors.
		// store all the rooms in the map rooms.
		// r is a single line in the setup file, split into words
		// Looking for character that represents room in layout file
		char c = row.get(2).charAt(0);
		// Creating new room with name in constructor
		Room room = new Room(row.get(1));

		// Getting character that represents closet
		if (room.getName().equals("Unused")) {
			closetChar = row.get(2).charAt(0);
		}

		// Checking if room is of room with door or regular space
		if (row.get(0).equals("Room")) {
			room.setDoorRoom();
		}
		// Adding room to map with key of character that represents it in layout file
		rooms.put(c, room);
	}
	
	public void initialWeapon(ArrayList<String> row) {
		weapons.add(row.get(1));
	}
	
	public void initialPlayer(ArrayList<String> row) {
		String[] coords = row.get(3).split("-");
		Integer y = Integer.valueOf(coords[0]);
		Integer x = Integer.valueOf(coords[1]);
		HumanPlayer player = new HumanPlayer(row.get(1), row.get(2),  y, x);
		players.add(player);
	}
	
	public void initialComputer(ArrayList<String> row) {
		String[] coords = row.get(3).split("-");
		Integer y = Integer.valueOf(coords[0]);
		Integer x = Integer.valueOf(coords[1]);
		ComputerPlayer player = new ComputerPlayer(row.get(1), row.get(2),  y, x);
		players.add(player);
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
				System.out.println(this.getRoom(layoutString.charAt(0)));
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
			ArrayList<String> validTypes = new ArrayList<String>(Arrays.asList("Room", "Space", "Weapon", "Player", "Computer"));			
			String type = row.get(0);
			
			if (!type.substring(0, 2).equals("//")) {
				setupStrings.add(row);
			}
			
			if (row.size() > 1) {
				if (!validTypes.contains(type)) {
					System.out.println(type);
					throw new BadConfigFormatException("Incorrect setup file format.");
				}
			}
		}
		layoutReader.close();
	}

	// Getters

	public Set<BoardCell> getTargets() {
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

		BoardCell[] orthoCells = getValidCells(boardCell);

		for (int n = 0; n < orthoCells.length; n++) {
			// Logic for doorways, only adds room to adjacency list if door is facing that room

			if (boardCell.isDoorway) { 

				DoorDirection rightDir = DoorDirection.UP;
				if (n == 1) {
					rightDir = DoorDirection.DOWN;
				} else if (n == 2) {
					rightDir = DoorDirection.LEFT;
				} else if (n == 3) {
					rightDir = DoorDirection.RIGHT;
				}

				if (orthoCells[n] != null) {
					if (orthoCells[n].inRoom && boardCell.getDoorDirection() == rightDir) {
						Room room = getRoom(orthoCells[n]);
						BoardCell center = room.getCenterCell();
						center.addAdjacency(boardCell);
						boardCell.adjacent.add(center);
					} else if (!orthoCells[n].inRoom) {
						boardCell.adjacent.add(orthoCells[n]);
					}
				}

				// Logic for regular pathways
			} else if (!boardCell.inRoom){
				if (orthoCells[n] != null && !orthoCells[n].inRoom) {
					boardCell.adjacent.add(orthoCells[n]);
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
		}

		return boardCell.adjacent;
	}

	public BoardCell[] getValidCells(BoardCell boardCell) {

		BoardCell[] orthoCells = {null, null, null, null};

		// Checking if directions are valid based on if they are on edge of the board or are in closet
		if (boardCell.row > 0) {
			orthoCells[0] = getCell(boardCell.row - 1, boardCell.col);
			orthoCells[0] = checkTileValidity(orthoCells[0]);
		}
		if (boardCell.row < Board.getNumRows() - 1) {
			orthoCells[1] = getCell(boardCell.row + 1, boardCell.col);
			orthoCells[1] = checkTileValidity(orthoCells[1]);
		}
		if (boardCell.col > 0) {
			orthoCells[2] = getCell(boardCell.row, boardCell.col - 1);
			orthoCells[2] = checkTileValidity(orthoCells[2]);
		}
		if (boardCell.col < Board.getNumColumns() - 1) {
			orthoCells[3] = getCell(boardCell.row, boardCell.col + 1);
			orthoCells[3] = checkTileValidity(orthoCells[3]);
		}

		return orthoCells;
	}

	private BoardCell checkTileValidity(BoardCell boardCell) {
		if (boardCell.getLayoutString().charAt(0) == closetChar || boardCell.getOccupied()) {
			boardCell = null;
		}
		return boardCell;
	}

	public static int getNumRows() {
		return Board.numRows;
	}

	public static int getNumColumns() {
		return Board.numCols;

	}



}
