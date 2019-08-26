package csc130.pente.team3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Board implements BoardInterface, Serializable {

	private static final long serialVersionUID = 1L;
	private Stone[][] locations;
	
	// Variables to control the size of the grid
	private int x;
	private int y;

	private int turnNumber = 1;

	private Player player1;
	private Player player2;

	// Allows the board and the boardGUI to send certain information back and forth
	private BoardGUIInterface bgi;

	private Controller controller;

	/**
	 * For normally starting a game
	 * @param dimensionX
	 * @param dimensionY
	 * @param player1
	 * @param player2
	 * @param bgi
	 */
	public Board(int dimensionX, int dimensionY, Player player1, Player player2, BoardGUIInterface bgi) {
		locations = new Stone[dimensionX][dimensionY];
		fillBoard(dimensionX, dimensionY);
		setPlayer1(player1);
		setPlayer2(player2);
		this.bgi = bgi;
		controller = new Controller(this);
	}

	/**
	 * For loading a saved game
	 * @param dimensionX
	 * @param dimensionY
	 * @param player1
	 * @param player2
	 */
	public Board(int dimensionX, int dimensionY, Player player1, Player player2) {
		locations = new Stone[dimensionX][dimensionY];
		fillBoard(dimensionX, dimensionY);
		setPlayer1(player1);
		setPlayer2(player2);
		controller = new Controller(this);
	}
	
	public Board() {}

	/**
	 * Fills the board with "Stones"
	 * @param dimensionX
	 * @param dimensionY
	 */
	private void fillBoard(int dimensionX, int dimensionY) {
		// Create a 19x19 grid. Each space in the 2D array is a button.
		// Each button is an intersection in which a piece can be placed.
		for (x = 0; x < dimensionX; x++) {
			for (y = 0; y < dimensionY; y++) {
				locations[x][y] = new Stone();
				locations[x][y].setLocationX(x);
				locations[x][y].setLocationY(y);
			}
		}
	}

	public Stone[][] getLocations() {
		return locations;
	}

	public void setLocations(Stone[][] locations) {
		this.locations = locations;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public void buttonClicked(int positionX, int positionY) {
		Stone clickedStone = locations[positionX][positionY];
		if (controller.turn(player1, player2, turnNumber, clickedStone.getLocationX(), clickedStone.getLocationY(),
				locations)) {
			// When the button is clicked, the image changes to a white stone on that
			// intersection

			if (turnNumber % 2 == 0) {
				bgi.updateTimers(player2);
				locations[positionX][positionY].setOwner(player2);
				if (bgi != null) {
					bgi.sendInfo(player2, positionX, positionY);
				}
			} else {
				bgi.updateTimers(player1);
				locations[positionX][positionY].setOwner(player1);
				if (bgi != null) {
					bgi.sendInfo(player1, positionX, positionY);
				}
			}
			turnNumber++;
		}
	}

	/**
	 * Called when stones need to be removed due to a capture
	 * @param positionX1
	 * @param positionY1
	 * @param positionX2
	 * @param positionY2
	 * @param player
	 */
	public void stonesRemoved(int positionX1, int positionY1, int positionX2, int positionY2, Player player) {
		if (bgi != null) {
			bgi.clearStones(positionX1, positionY1, positionX2, positionY2, player);
		}
	}

	/**
	 * Called when a player wins
	 * @param player
	 */
	public void playerWon(Player player) {
		if (bgi != null) {
			bgi.playerWon(player);
		}
	}

	@Override
	public void saveBoard(String fileName) {
		FileOutputStream fout;
		ObjectOutputStream oos = null;
		SavableBoard savedBoard = new SavableBoard(locations, player1, player2, turnNumber);
		File savesFile = new File("saves/");
		savesFile.mkdirs();
		File file = new File("saves/" + fileName + ".ser");
		try {
			fout = new FileOutputStream(file);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(savedBoard);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadBoard(String fileName) {
		FileInputStream fin;
		ObjectInputStream ois = null;
		SavableBoard savedBoard = null;
		File savesFile = new File("saves/");
		savesFile.mkdirs();
		File file = new File("saves/" + fileName);
		try {
			fin = new FileInputStream(file);
			ois = new ObjectInputStream(fin);
			savedBoard = (SavableBoard)ois.readObject();
			this.controller = new Controller(this);
			this.locations = savedBoard.getLocations();
			this.player1 = savedBoard.getPlayer1();
			this.player2 = savedBoard.getPlayer2();
			this.turnNumber = savedBoard.getTurnNumber();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setBgi(BoardGUIInterface bgi) {
		this.bgi = bgi;
	}
	
	/**
	 * Displays "tessera" or "tria" when acquired
	 * @param text
	 * @param player
	 */
	public void updateLabelForInARow(String text, Player player) {
		bgi.updateInARowLabel(text, player);
	}

	@Override
	public void updateTurn(boolean firstTurnOfGame) {
		locations[locations.length / 2][locations[0].length / 2].setOwner(player1);
		if (bgi != null) {
			bgi.sendInfo(player1, locations.length / 2, locations[0].length / 2);
		}
		turnNumber++;
	}

	public void notifyIllegalMove(String text) {
		bgi.updateNotificationLabel(text);
		
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public BoardGUIInterface getBgi() {
		return bgi;
	}
	
}
