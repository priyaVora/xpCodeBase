package csc130.pente.team3;

import java.io.File;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BoardGUI extends GridPane implements BoardGUIInterface {
	private StoneGUI[][] locations;
	
	// Variables to control the size of the grid
	private int x;
	private int y;

	private Player player1;
	private Player player2;

	// Allows the board and the boardGUI to send certain information back and forth
	private BoardInterface bi;

	private Stage primaryStage;

	private GameScreen gameScreen;

	private Board board;
	
	private int imageSize = 23;

	/**
	 * For starting a game normally
	 * @param primaryStage
	 * @param dimensionX
	 * @param dimensionY
	 * @param player1
	 * @param player2
	 * @param gameScreen
	 */
	public BoardGUI(Stage primaryStage, int dimensionX, int dimensionY, Player player1, Player player2,
			GameScreen gameScreen) {
		if(dimensionY > 30) {
			imageSize = 17;
		} else if (dimensionY < 12) {
			imageSize = 32;
		} else { 
			imageSize = 23;
		}
		this.gameScreen = gameScreen;
		this.primaryStage = primaryStage;
		locations = new StoneGUI[dimensionX][dimensionY];
		fillBoard(dimensionX, dimensionY);
		setPlayer1(player1);
		setPlayer2(player2);
		bi = new Board(dimensionX, dimensionY, player1, player2, this);
	}

	/**
	 * For loading a saved game
	 * @param primaryStage
	 * @param board
	 * @param gameScreen
	 */
	public BoardGUI(Stage primaryStage, Board board, GameScreen gameScreen) {
		this.primaryStage = primaryStage;
		this.gameScreen = gameScreen;
		locations = new StoneGUI[board.getLocations().length][board.getLocations()[0].length];
		this.board = board;
		bi = this.board;
		this.board.setBgi(this);
		setPlayer1(board.getPlayer1());
		setPlayer2(board.getPlayer2());
		if( board.getLocations()[0].length > 30) {
			imageSize = 17;
		} else if (board.getLocations()[0].length < 12){ 
			imageSize = 32;
		} else {
			imageSize = 23;
		}
		fillBoard(board.getLocations().length, board.getLocations()[0].length);
	}

	/**
	 * Fills the boardGUI with StoneGUI
	 * @param dimensionX
	 * @param dimensionY
	 */
	private void fillBoard(int dimensionX, int dimensionY) {
		
		// Create a 19x19 grid. Each space in the 2D array is a button.
		// Each button is an intersection in which a piece can be placed.
		for (x = 0; x < dimensionX; x++) {
			for (y = 0; y < dimensionY; y++) {
				locations[x][y] = new StoneGUI();
				if (board != null) {
					locations[x][y].setOwner(board.getLocations()[x][y].getOwner());
				}
				locations[x][y].setPadding(Insets.EMPTY);
				locations[x][y].setLocationX(x);
				locations[x][y].setLocationY(y);
				locations[x][y].setOnAction(new EventHandler<ActionEvent>() {

					public void handle(ActionEvent event) {
						StoneGUI clickedStone = (StoneGUI) event.getSource();
						bi.buttonClicked(clickedStone.getLocationX(), clickedStone.getLocationY());
					}
				});
				// Button defaults to an empty intersection.
				Image image;
				if (locations[x][y].getOwner() != null && locations[x][y].getOwner().equals(player1)) {
					File blankSquareImageFile = new File("images/blackStone.PNG");
					image = new Image(blankSquareImageFile.toURI().toString(), imageSize, imageSize, true, true);
				} else if (locations[x][y].getOwner() != null && locations[x][y].getOwner().equals(player2)) {
					File blankSquareImageFile = new File("images/whiteStone.PNG");
					image = new Image(blankSquareImageFile.toURI().toString(), imageSize, imageSize, true, true);
				} else {
					File blankSquareImageFile = new File("images/square.PNG");
					image = new Image(blankSquareImageFile.toURI().toString(), imageSize, imageSize, true, true);
				}

				locations[x][y].setGraphic(new ImageView(image));
				GridPane.setConstraints(locations[x][y], x, y);
				this.getChildren().add(locations[x][y]);
			}
		}
	}

	public StoneGUI[][] getLocations() {
		return locations;
	}

	public void setLocations(StoneGUI[][] locations) {
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

	public void sendInfo(Player player, int positionX, int positionY) {
		locations[positionX][positionY].setOwner(player);
		if (player.isFirstPlayer()) {
			// change grid to black stone
			File blackSquareImageFile = new File("images/blackStone.PNG");
			Image image = new Image(blackSquareImageFile.toURI().toString(), imageSize, imageSize, true, true);

			locations[positionX][positionY].setGraphic(new ImageView(image));
			GridPane.setConstraints(locations[positionX][positionY], positionX, positionY);
		} else {
			// change grid to white stone
			File whiteSquareImageFile = new File("images/whiteStone.PNG");
			Image image = new Image(whiteSquareImageFile.toURI().toString(), imageSize, imageSize, true, true);

			locations[positionX][positionY].setGraphic(new ImageView(image));
			GridPane.setConstraints(locations[positionX][positionY], positionX, positionY);
		}
	}

	public void clearStones(int positionX1, int positionY1, int positionX2, int positionY2, Player player) {
		locations[positionX1][positionY1].setOwner(null);
		File blankSquareImageFile = new File("images/square.PNG");
		Image image = new Image(blankSquareImageFile.toURI().toString(), imageSize, imageSize, true, true);

		locations[positionX1][positionY1].setGraphic(new ImageView(image));
		GridPane.setConstraints(locations[positionX1][positionY1], positionX1, positionY1);

		locations[positionX2][positionY2].setOwner(null);
		blankSquareImageFile = new File("images/square.PNG");
		image = new Image(blankSquareImageFile.toURI().toString(), imageSize, imageSize, true, true);

		locations[positionX2][positionY2].setGraphic(new ImageView(image));
		GridPane.setConstraints(locations[positionX2][positionY2], positionX2, positionY2);

		gameScreen.increaseStoneCount(player);

	}

	public void playerWon(Player player) {
		gameScreen.stopTimer();
		WinScreen winScreen = new WinScreen(primaryStage, player);
		Scene s = new Scene(winScreen);
		File stylesheet = new File("images/menus.css");
		s.getStylesheets().add(stylesheet.toURI().toString());
		primaryStage.setScene(s);
		primaryStage.centerOnScreen();
	}

	public void saveBoard() {
		boolean valid = false;
		while (!valid) {
			TextInputDialog dialog = new TextInputDialog("Save Game");
			dialog.setTitle("Save Game");
			dialog.setHeaderText("Enter the Name of Your Saved Game");
			dialog.setContentText("Please enter a save name:");
			
			valid = true;

			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				if (!result.get().trim().equals("") && !result.get().contains(".")) {
					bi.saveBoard(result.get());
					valid = true;
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Invalid Save Name");
					alert.setHeaderText("Invalid Save Name Entered");
					alert.setContentText("Please make sure your save name is not empty, a space, or contains a period!");

					alert.showAndWait();
					valid = false;
				}
			}
		}
	}
	//turn has ended by time
	public void turnOver(boolean firstTurnOfGame) {
		bi.updateTurn(firstTurnOfGame);
	}

	@Override
	public void updateTimers(Player player) {
		gameScreen.updateTimers(player);
	}

	@Override
	public void updateInARowLabel(String text, Player player) {
		gameScreen.updateInaARowLabel(text, player);
	}

	@Override
	public void updateNotificationLabel(String text) {
		gameScreen.updateNotificationLabel(text);
	}
}
