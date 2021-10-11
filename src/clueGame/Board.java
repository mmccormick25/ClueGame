// Authors: Matthew McCormick and Zhen Liu

package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private static int numRows;
	private static int numCols;

	private BoardCell[][] cells;
	private Set<BoardCell> targets = new HashSet<>();
	HashSet<BoardCell> visitedList = new HashSet<BoardCell>();

	public static Board theInstance = new Board();
	private static File setupFile;
	private static File layoutFile;

	public static BoardCell blankCell = new BoardCell(0, 0, "X");

	// Creating 2d array list of strings that will represent layout
	public ArrayList<ArrayList<String>> layoutStrings = new ArrayList<ArrayList<String>>(0);
	// Creating 2d array list of strings that will hold info about tile types
	public ArrayList<ArrayList<String>> setupStrings = new ArrayList<ArrayList<String>>(0);
	// Map that holds rooms
	public static HashMap<Character, Room> rooms = new HashMap<Character, Room>();
	// an array of special characters: <,>,^,*,#,v
	public static ArrayList<String> specialC = new ArrayList(Arrays.asList("<",">","^","v","#","*"));


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
	public void initialize() throws FileNotFoundException, BadConfigFormatException
	{
		loadSetupConfig();
		loadLayoutConfig();

		// Getting number of rows which is num of arrayLists in layoutStrings
		numRows = layoutStrings.size();
		// Getting length of first row which is length of any arrayList in layoutStrings
		numCols = layoutStrings.get(0).size();

		// Initializing 2d list of cells to be same size as board
		cells = new BoardCell[numRows][numCols];
		// create 11 rooms with constructors.
		// store all the rooms in the arrayList: rooms.
	
		
		for (ArrayList<String> r : setupStrings) {
			char c = r.get(2).charAt(0);
			Room room= new Room(r.get(1));
			rooms.put(c, room);
		}




		for (Map.Entry<Character, Room> entry : rooms.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue().getName());
		}

		
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				String layoutString = layoutStrings.get(r).get(c);
				cells[r][c] = new BoardCell(r, c, layoutString);
				if (layoutString.length() > 1) {
					Room room = this.getRoom(layoutString.charAt(0));
					if (layoutString.charAt(1) == '#') {
						room.setLabelCell(cells[r][c]);
					} else if (layoutString.charAt(1) == '*') {
						room.setCenterCell(cells[r][c]);
					} else if (layoutString.charAt(1) == '<' || layoutString.charAt(1) == '>' || layoutString.charAt(1) == '^' || layoutString.charAt(1) == 'v') {
						cells[r][c].setDoorway();					}
				}
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

	public Room getRoom(Character x) {
		return rooms.get(x);
	}

	public static int getNumRows() {
		return Board.numRows;

	}

	public static int getNumColumns() {
		return Board.numCols;

	}

	public Room getRoom(BoardCell cell) {
		Character c = layoutStrings.get(cell.getRow()).get(cell.getCol()).charAt(0);
		return getRoom(c);
		/*
		if((cell.getRow() > 0 && cell.getRow()<= 3 && cell.getCol()>=0 && cell.getCol() <7) || 
				(cell.getRow()==0 && cell.getCol() >=0 && cell.getCol() <7)) {	

			return rooms.get('S');
		}else if (cell.getRow()>0 && cell.getRow() <7 && cell.getCol()>8 && cell.getCol()<15) {
			return rooms.get('H');
		}else if((cell.getRow()>18) && (cell.getRow() <24) && (cell.getCol()>16) && (cell.getCol()<24) || (cell.getRow()==18 && cell.getCol()>16 && cell.getCol()>23) ) {
			return rooms.get('K');
		}else if ((cell.getRow()>0 && cell.getRow() < 6 && cell.getCol()>16 && cell.getCol()<24) || (cell.getRow()==0 && cell.getCol()>17 && cell.getCol()<24)) {
			return rooms.get('O');
		}else if ((cell.getRow()>5 && cell.getRow()<11 && cell.getCol()>0 && cell.getCol()< 6 ) || ((cell.getCol()==0 || cell.getCol()==6) && cell.getRow()>6 && cell.getRow()<10)){
			return rooms.get('L');
		}else if ((cell.getRow() >8 && cell.getRow() < 15 && cell.getCol()>15 && cell.getCol() <24) || (cell.getRow()==15 && cell.getCol()>18 && cell.getCol()<24) ) {
			return rooms.get('D');
		}else if (cell.getRow()>11 && cell.getRow()<17 && cell.getCol()>=0 && cell.getCol()<6) {
			return rooms.get('R');
		}else if ((cell.getRow()>19 && cell.getRow() <24 && cell.getCol()>=0 && cell.getCol() <6)||(cell.getRow()==19 && cell.getCol()>0 && cell.getCol()<6)) {
			return rooms.get('C');
		}else if ((cell.getRow() >16 && cell.getRow()<23 && cell.getCol() >7 && cell.getCol()<16)||(cell.getRow()==23 && cell.getCol() >9 && cell.getCol()<14)) {
			return rooms.get('B');
		}else if ((cell.getRow()>7 && cell.getRow()<15 && cell.getCol() >8 && cell.getCol()<14)||(cell.getRow()==0 && (cell.getCol()==6 || cell.getCol()==17 || (cell.getCol()>7 && cell.getCol()<16))||
				(cell.getRow()==24 && cell.getCol()!=9 && cell.getCol()!= 14))|| (cell.getCol() ==0 && (cell.getRow()==4 || cell.getRow() ==6 || cell.getRow() ==10 || cell.getRow()==11 || cell.getRow()==17 || cell.getRow()==19)) 
				|| (cell.getCol()==23 || cell.getRow()==6 || cell.getRow() ==8 || cell.getRow()==16 || cell.getRow()==18)) {
			return rooms.get('X');
		}else {			
			return rooms.get('W');
		}
*/
		

	}

	public void setConfigFiles(String fileName1,String fileName2) {
		// Creating new file objects to hold source files for board
		layoutFile = new File("data/" + fileName1);
		setupFile = new File("data/" + fileName2);
	}

	public void loadLayoutConfig() throws FileNotFoundException,BadConfigFormatException {
		try {
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
		}
		catch (FileNotFoundException e) {
			System.out.println("File not exists.");
		}	

		// handle the exception if the length of each isn't the same. 
		int num = layoutStrings.get(0).size();
		int temp1 = 0;
		int temp2 = 0;
		for (ArrayList<String> i : layoutStrings) {
			for (String n : i) {
				try {
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


				}catch (BadConfigFormatException e) {
					System.out.println(e);
					System.out.println(e.getMessage());
				} 


				System.out.print(n + " ");
			}
			System.out.println("");
		}

	}

	public void loadSetupConfig() throws FileNotFoundException,BadConfigFormatException  {
		try {
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
			}
			layoutReader.close();

		}
		catch (FileNotFoundException e) {
			System.out.println("file can't be found.");
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
