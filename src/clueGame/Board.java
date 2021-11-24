// Authors: Matthew McCormick and Zhen Liu

package clueGame;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener{
	// Random object
	Random r = new Random();
	
	// Variables to define board size
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
	
	// Game Solution
	private Solution soln;

	// Creating 2d array list of strings that will represent layout
	public ArrayList<ArrayList<String>> layoutStrings = new ArrayList<ArrayList<String>>(0);
	// Creating 2d array list of strings that will hold info about tile types
	public ArrayList<ArrayList<String>> setupStrings = new ArrayList<ArrayList<String>>(0);
	// Map that holds rooms
	public static HashMap<Character, Room> rooms = new HashMap<Character, Room>();
	// an array of special characters: <,>,^,*,#,v that show up in layout file
	public static ArrayList<String> specialC = new ArrayList<String>(Arrays.asList("<",">","^","v","#","*"));
	// Character that represents spaces players can't go
	public static Character closetChar;
	
	// list of weapons
	public static ArrayList<String> weapons = new ArrayList<String>();
	// deck of all cards
	public ArrayList<Card> deck = new ArrayList<Card>();

	// Arraylist that holds players
	public static ArrayList<Player> players = new ArrayList<Player>();
	// Keeps track of where current player is in list
	public int currentPlayerIndex = 0;

	// Flags
	// Keeps track of if human player has moved yet
	boolean notMoved = true;
	// Keeps track of if player suggestion has been made
	public boolean suggestMade = true;
	// Keeps track of if board has already been initialized (for running all tests at once)
	public boolean alreadyInitialized = false;
	// Keeps track of if player accusation had been made
	boolean accMade = true;
	
	// Suggestion dialog object
	public suggestionDialog suggDialog;

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

		// Making sure deck is empty for junit tests
		deck.clear();

		// Adding mouse listener to board class to detect clicks
		addMouseListener(this);

		// Initializing objects based on their type
		for (ArrayList<String> row : setupStrings) {
			String type = row.get(0);
			if(type.equals("Room") || type.equals("Space")) {
				if (!type.equals("Space")) {
					deck.add(new Card(row.get(1), CardType.ROOM));
				}
				initialRoom(row);
			} else if (type.equals("Weapon")) {
				initialWeapon(row);
				deck.add(new Card(row.get(1), CardType.WEAPON));
			} else if (type.equals("Player")) {
				initialHumanPlayer(row);
				deck.add(new Card(row.get(1), CardType.PERSON));
			} else if (type.equals("Computer")) {
				initialComputer(row);
				deck.add(new Card(row.get(1), CardType.PERSON));
			}
		}

		// Creating board cells
		initialCells();
		// Running adjacenty on doors so rooms have proper adjacency
		doorAdjList();

		// Only dealing cards if there are weapons in the setup file
		if (weapons.size() > 0) {
			dealCards();
		}

		// Setting flag to say board has already been initialized
		alreadyInitialized = true;
		
		repaint();

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

	public void dealCards() {
		// Shuffling deck
		Collections.shuffle(deck);

		// Variables that will hold solution cards
		Card roomCard = null;
		Card weaponCard = null;
		Card playerCard = null;

		// Getting first card of each type in deck
		for (Card card : deck) {
			if (card.getCardType() == CardType.ROOM) {
				roomCard = card;
			}
			if (card.getCardType() == CardType.WEAPON) {
				weaponCard = card;
			}
			if (card.getCardType() == CardType.PERSON) {
				playerCard = card;
			}
		}

		// Removing solution cards from deck so rest of deck can be dealt
		deck.remove(roomCard);
		deck.remove(weaponCard);
		deck.remove(playerCard);

		soln = new Solution(roomCard, weaponCard, playerCard);

		// Shuffling deck
		Collections.shuffle(deck);

		// Dealing cards one at a time to player
		int playerIndex = 0;
		for (Card card : deck) {
			players.get(playerIndex).updatehand(card);
			playerIndex++;
			if (playerIndex >= players.size()) {
				playerIndex = 0;
			}
		}

		// Readding solution cards to deck so deck setup test passes
		deck.add(roomCard);
		deck.add(weaponCard);
		deck.add(playerCard);

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
		if (weapons.size() < 6) {
			weapons.add(row.get(1));
		}
	}

	public void initialHumanPlayer(ArrayList<String> row) {
		if (players.size() < 6) {
			String[] coords = row.get(3).split("-");
			Integer y = Integer.valueOf(coords[0]);
			Integer x = Integer.valueOf(coords[1]);
			HumanPlayer player = new HumanPlayer(row.get(1), row.get(2),  y, x);
			players.add(player);
		}
	}

	public void initialComputer(ArrayList<String> row) {
		if (players.size() < 6) {
			String[] coords = row.get(3).split("-");
			Integer y = Integer.valueOf(coords[0]);
			Integer x = Integer.valueOf(coords[1]);
			ComputerPlayer player = new ComputerPlayer(row.get(1), row.get(2),  y, x);
			players.add(player);
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
				if (Board.getRoom(layoutString.charAt(0)).getDoorRoom()) {
					grid[r][c].setIsRoom(true);
				}
				// Checking if there is a special character after room character to set attributes of cell
				if (layoutString.length() > 1) {
					// Getting room object using first character in layoutString
					Room room = Board.getRoom(layoutString.charAt(0));
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
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Defining variables needed to get cell dimension
		int panelWidth = this.getWidth();
		int panelHeight = this.getHeight();
		int widthLimit = (int) panelWidth / 28;
		int heightLimit = (int) panelHeight / 22;
		
		// Defining size of board cells based on smallest side length of window
		int cellDim;
		if (widthLimit > heightLimit) {
			cellDim = heightLimit;
		} else {
			cellDim = widthLimit;
		}

		// Calling draw function for each cell in board
		if (grid != null) {
			for (int x = 0; x < grid.length; x++) {
				for (int y = 0; y < grid[0].length; y++) {
					grid[x][y].draw(y * cellDim, x * cellDim, cellDim, g);
				}
			}
		}

		// Calling cell draw function for objects that go on top of the board squares like doors and text
		if (grid != null) {
			for (int x = 0; x < grid.length; x++) {
				for (int y = 0; y < grid[0].length; y++) {
					grid[x][y].drawSecond(y * cellDim, x * cellDim, cellDim, g);
				}
			}
		}

		// Drawing possible movement spaces if it is player's turn
		if(ClueGame.controlPanel.getTurn().equals(players.get(0).getName())) {
			for(BoardCell target: targets) {
				target.drawTarget(cellDim, g);
			}
		}

		// Calling draw function for each player
		for (int i=0;i<players.size();i++) {
			players.get(i).draw(cellDim, g);
		}
		
	}
	
	public void runTurn() {
		// Updating current player
		Player currentPlayer = players.get(currentPlayerIndex);
		// Getting random dice roll
		int roll = r.nextInt(6) + 1;
		// Getting current position
		int col = currentPlayer.getColumn();
		int row = currentPlayer.getRow();

		// Resetting info panels to be blank
		ClueGame.controlPanel.setGuess("");
		ClueGame.controlPanel.setGuessResult("");

		// This runs for the human player
		if (currentPlayerIndex == 0) {
			BoardCell start = grid[players.get(0).getRow()][players.get(0).getColumn()];
			calcTargets(start,roll);	
		} else {
			// This runs for the computer players
			runCompTurn(currentPlayer, roll, col, row);
		}

		// Updating information on bottom panel for who is playing
		ClueGame.controlPanel.setTurn(currentPlayer, roll);

		// Logic to go to next player
		if (currentPlayerIndex == players.size() - 1) {
			currentPlayerIndex = 0;
		} else {
			currentPlayerIndex++;
		}

		// Repainting board to update computer movement
		repaint();

	}
	
	public void runCompTurn(Player currentPlayer, int roll, int col, int row) {
		ComputerPlayer comp = (ComputerPlayer) currentPlayer;

		// Checking if computer has accusation ready, if they do then their solution is displayed and game ends
		if (comp.getAccusation() != null) {
			Card[] accCards = comp.getAccusation().getSolution();
			String winMessage = "Player " + comp.getName() + " just won with solution " + accCards[0].getCardName() + ", " + accCards[1].getCardName() + ", " + accCards[2].getCardName() + ". You Lose!";
			JOptionPane.showMessageDialog(ClueGame.frame, winMessage);
			System.exit(0);
		}

		// Selecting space for computer to move and moving there
		BoardCell target = comp.selectTarget(grid[row][col], roll);
		comp.setColumn(target.getCol());
		comp.setRow(target.getRow());
		
		// Computer suggestion logic
		if (target.inRoom()) {
			// Creating suggestion
			Solution compSugg = comp.createSuggestion(target);
			Card[] solArr = compSugg.getSolution();
			// Displaing suggestion on control panel
			ClueGame.controlPanel.setGuess(solArr[0].getCardName() + ", " + solArr[1].getCardName() + ", " + solArr[2].getCardName());
			
			// Getting card shown that disproves suggestion
			Card shownCard = handleSuggestion(players, compSugg, comp);
			
			// Pulling suggested player into room
			pullPlayer(target.getRow(), target.getCol(), solArr[2].getCardName());
			
			if (shownCard != null) {
				comp.addSeenCard(shownCard);
				ClueGame.controlPanel.setGuessResult("Suggestion disproven!");
			} else {
				ClueGame.controlPanel.setGuessResult("Suggestion can't be disproven!");
				if (!comp.getSeenRooms().contains(solArr[0])) {
					// Readying accusation if room hasn't been seen
					comp.setAccusation(compSugg);
				}
			}
		}
	}
	
	// Method to pull player to position of other player, used for pulling players into rooms
	public void pullPlayer(int row, int col, String name) {
		for (Player player : players) {
			if (player.getName().equals(name)) {
				player.setColumn(col);
				player.setRow(row);
				repaint();
				break;
			}
		}
	}
	
	// Checking if accusation is correct
	public boolean checkAccusation(Solution s) {
		boolean result = false;
		if(soln.getSolution()[0].getCardName().equals(s.getSolution()[0].getCardName()) && getSolution().getSolution()[1].getCardName().equals(s.getSolution()[1].getCardName()) && getSolution().getSolution()[2].getCardName().equals(s.getSolution()[2].getCardName()))
			result = true;

		return result;
	}

	public Card handleSuggestion(ArrayList<Player> suggPlayers, Solution suggestion, Player suggestPlayer) {
		int startIndex = suggPlayers.indexOf(suggestPlayer);
		int currentIndex;
		
		// Gets next index of next player in list, if at end of list go back to start of list
		if (startIndex != suggPlayers.size() - 1) {
			currentIndex = startIndex + 1;
		} else {
			currentIndex = 0;
		}
		
		// While loop that runs over subsequent players until a card to disprove suggestion is found, or all players are checked without a card found
		while (currentIndex != startIndex) {
			Card suggestResult = suggPlayers.get(currentIndex).checkSuggestion(suggestion);
			if (suggestResult != null) {
				return suggestResult;
			}
			// Gets next index of next player in list, if at end of list go back to start of list
			if (currentIndex != suggPlayers.size() - 1) {
				currentIndex++;
			} else {
				currentIndex = 0;
			}
		}
		
		// Returning null if no card can be found to disprove suggestion
		return null;
	}
	
	public void handleHumanAccusation(Solution accusation) {
		if (checkAccusation(accusation)) {
			JOptionPane.showMessageDialog(null, "Congratulations, you got the correct solution! You win!");
		} else {
			JOptionPane.showMessageDialog(null, "Sorry but that's the wrong solution. You lose!");
		}
		System.exit(0);
	}
	
	// Checking if human just entered room, and creating suggestion dialogue if so
	public void createHumanSuggestion(){
		String playerName = ClueGame.controlPanel.getTurn();

		for(Player player:players) {
			int row = player.getRow();
			int column= player.getColumn();

			if(grid[row][column].inRoom() && player.getName().equals(playerName)) {
				suggDialog = new suggestionDialog();

			}
		}
	}
	
	public void handleHumanSuggestion(Solution newSuggestion) {
		Player humanPlayer = players.get(0);
		Card shownCard = handleSuggestion(players, newSuggestion, humanPlayer);
		
		// Pulling suggested player into room
		pullPlayer(humanPlayer.getRow(), humanPlayer.getColumn(), newSuggestion.getSolution()[2].getCardName());
		
		if (shownCard != null) {
			humanPlayer.addSeenCard(shownCard);
			ClueGame.controlPanel.setGuessResult("Suggestion disproven!");
		} else {
			ClueGame.controlPanel.setGuessResult("Suggestion can't be disproven!");
		}
	}

	// Pathfinding methods

	public void findAllTargets(BoardCell cell, int length) {
		for (BoardCell adjCell: getAdjList(cell)) {
			if(visitedList.contains(adjCell) || (adjCell.getOccupied() && (!adjCell.inRoom()))) {
				continue;
			}
			visitedList.add(adjCell);
			if(length == 1 || adjCell.inRoom()) {
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
					throw new BadConfigFormatException("Incorrect setup file format.");
				}
			}
		}
		layoutReader.close();
	}

	// Getters and setters
	public Set<BoardCell> getTargets() {
		return targets;
	}

	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

	public static Room getRoom(Character x) {
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
	public Solution getSolution() {
		return soln;
	}
	
	public void setSolution(Card room,Card weapon,Card player) {
		soln = new Solution(room,weapon,player);

	}

	public BoardCell[][] getGrid(){
		return grid;
	}

	public void nextPressed() {
		notMoved = true;
		runTurn();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		boolean valid = false;
		int x = e.getPoint().x;
		int y = e.getPoint().y;
		int panelWidth = this.getWidth();
		int panelHeight = this.getHeight();
		int widthLimit = (int) panelWidth / 28;
		int heightLimit = (int) panelHeight / 22;
		int cellDim;
		if (widthLimit > heightLimit) {
			cellDim = heightLimit;
		} else {
			cellDim = widthLimit;
		}
		// Checking if mouse click matches where any highlighted targets are
		for(BoardCell target: targets) {
			if(y/cellDim == target.getRow() && x/cellDim == target.getCol() && notMoved) {
				players.get(0).setRow(target.getRow());
				players.get(0).setColumn(target.getCol());
				valid = true;
				// Setting flag that player has moved
				notMoved = false;
				// Setting suggestMade to be false to indicate that suggestion must be addressed
				if (target.inRoom) {
					suggestMade = false;
				}
				createHumanSuggestion();
			}
		}
		// Printing error message if invalid cell is clicked on
		if (valid == false) {
			JOptionPane.showMessageDialog(null, "Please click highlighted cell.");		
		}
		// Repainting to update player position
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
}
