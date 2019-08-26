package csc130.pente.team3;

import java.io.File;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.geometry.Pos;

public class GameScreen extends BorderPane {

	private BoardGUI boardGUI;

	private Player player1;
	private Player player2;

	// Variables to control the size of the grid
	private int x;
	private int y;

	private Stage primaryStage;

	// Side panels displaying player info
	private VBox player1Section;
	private VBox player2Section;

	private Label capturedStonesLabel1;
	private Label capturedStonesLabel2;
	
	// Keeps track of turn time
	private Timeline timeline; 
	private	IntegerProperty timerIntegerP1;
	private IntegerProperty timerIntegerP2;
	
	private Label warningLabelP1;
	private Label triaAndTesseraLabelP1; 
	private Label triaAndTesseraLabelP2; 
	
	private boolean isFirstPlayersTurn = true;
	private boolean firstTurnOfGame = true;

	private int turnNumber;
	
	/**
	 * For starting a game normally
	 * @param primaryStage
	 * @param player1
	 * @param player2
	 * @param x
	 * @param y
	 */
	public GameScreen(Stage primaryStage, Player player1, Player player2, int x, int y) {
		this.primaryStage = primaryStage;
		setPlayer1(player1);
		setPlayer2(player2);
		setX(x);
		setY(y);
		this.player1Section = createPlayerSection(player1);
		this.player2Section = createPlayerSection(player2);
		this.setLeft(player1Section);
		this.setRight(player2Section);
		// this.getChildren().addAll(player1Section, player2Section);
		// primaryStage.setResizable(false);
		createGrid();
	}

	/**
	 * For loading a saved game
	 * @param primaryStage
	 * @param fileName
	 */
	public GameScreen(Stage primaryStage, String fileName) {
		this.primaryStage = primaryStage;
		Board board = new Board();
		board.loadBoard(fileName);
		this.setPlayer1(board.getPlayer1());
		this.setPlayer2(board.getPlayer2());
		this.turnNumber = board.getTurnNumber();
		this.player1Section = createPlayerSection(board.getPlayer1());
		this.player2Section = createPlayerSection(board.getPlayer2());
		this.setLeft(player1Section);
		this.setRight(player2Section);
		BoardGUI boardGUI = new BoardGUI(this.primaryStage, board, this);
		
		this.boardGUI = boardGUI;
		this.setCenter(boardGUI);
		this.setBoard(boardGUI);
		

		if (turnNumber > 1) {
			firstTurnOfGame = false;
		}
		if (turnNumber % 2 == 0 ) {
			firstTurnOfGame = false;
			isFirstPlayersTurn = false;
			timerIntegerP1.set(0);
			timerIntegerP2.set(20);
			player2Section.setStyle("-fx-background-color: #98ff98");
			player1Section.setStyle("-fx-background-color: #D3D3D3");
		}
	
	}

	private VBox createPlayerSection(Player player) {
		VBox playerSectionP1 = new VBox();
		VBox playerSectionP2 = new VBox();
		if (player.isFirstPlayer()) {
			Label playerNameLabel = new Label(player.getName());
			playerNameLabel.setAlignment(Pos.CENTER);
			playerNameLabel.getStyleClass().add("sectionLabels");
			warningLabelP1 = new Label();
			warningLabelP1.setAlignment(Pos.CENTER);
			warningLabelP1.getStyleClass().add("sectionLabels");
			triaAndTesseraLabelP1 = new Label();
			triaAndTesseraLabelP1.setAlignment(Pos.CENTER);
			triaAndTesseraLabelP1.getStyleClass().add("notifLabels");
			
			Label timerDescription = new Label("Timer");
			timerDescription.setAlignment(Pos.CENTER);
			timerDescription.getStyleClass().add("sectionLabels");
			Label timeLabel = new Label();
			timeLabel.getStyleClass().add("sectionLabels");
			timeLabel.setAlignment(Pos.CENTER);
			timerIntegerP1 = new SimpleIntegerProperty();
			timerIntegerP1.set(20);
			timeLabel.textProperty().bind(timerIntegerP1.asString());
			
			playerSectionP1.setStyle("-fx-background-color: #98ff98");
			Label capturedStoneDescription = new Label("Captured Pairs");
			capturedStoneDescription.setAlignment(Pos.CENTER);
			capturedStoneDescription.getStyleClass().add("sectionLabels");
			
			capturedStonesLabel1 = new Label((player.getCapturedStonesValue() / 2) + "");
			capturedStonesLabel1.setAlignment(Pos.CENTER);
			capturedStonesLabel1.getStyleClass().add("sectionLabels");
			
			Button mainMenu = new Button("Main Menu");
			mainMenu.getStyleClass().add("saveButton");
			mainMenu.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					timeline.stop();
					MainMenu mainMenu = new MainMenu(primaryStage);
					Scene s = new Scene(mainMenu);
					File stylesheet = new File("images/menus.css");
					s.getStylesheets().add(stylesheet.toURI().toString());
					primaryStage.setScene(s);
				}
			});

			playerSectionP1.getChildren().addAll(playerNameLabel,timerDescription, timeLabel,capturedStoneDescription, capturedStonesLabel1, warningLabelP1, triaAndTesseraLabelP1, mainMenu);
			return playerSectionP1;
		} else {
			playerSectionP2.setStyle("-fx-background-color: #D3D3D3");
			Label playerNameLabel = new Label(player.getName());
			playerNameLabel.setAlignment(Pos.CENTER);
			playerNameLabel.getStyleClass().add("sectionLabels");
			triaAndTesseraLabelP2 = new Label();
			triaAndTesseraLabelP2.setAlignment(Pos.CENTER);
			triaAndTesseraLabelP2.getStyleClass().add("notifLabels");
			Label timerDescription = new Label("Timer");
			timerDescription.setAlignment(Pos.CENTER);
			timerDescription.getStyleClass().add("sectionLabels");
			Label timeLabel = new Label();
			timeLabel.setAlignment(Pos.CENTER);
			timeLabel.getStyleClass().add("sectionLabels");

			timerIntegerP2 = new SimpleIntegerProperty();
			timerIntegerP2.set(0);
			timeLabel.textProperty().bind(timerIntegerP2.asString());
			// managing turn timers and their updates
			
			timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
				if( isFirstPlayersTurn && timerIntegerP1.get() != 0) {
					timerIntegerP1.set(timerIntegerP1.get() - 1);
				} else if (isFirstPlayersTurn && timerIntegerP1.get() == 0) {
					boardGUI.turnOver(firstTurnOfGame);
					firstTurnOfGame = false;
					isFirstPlayersTurn = false;
					timeline.stop();
					timerIntegerP1.set(0);
					timerIntegerP2.set(20);
					player2Section.setStyle("-fx-background-color: #98ff98");
					player1Section.setStyle("-fx-background-color: #D3D3D3");
					
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Turn Over");
					alert.setHeaderText(player1.getName() + "'s Turn has ended");
					alert.setContentText("Next Player's turn");
					alert.setOnHidden(evt -> timeline.play());
				    alert.show(); 
				}else if(!isFirstPlayersTurn && timerIntegerP2.get() != 0) {
					timerIntegerP2.set(timerIntegerP2.get() - 1);
				} else if (!isFirstPlayersTurn && timerIntegerP2.get() == 0) {
					boardGUI.turnOver(firstTurnOfGame);
					isFirstPlayersTurn = true;
					timeline.stop();
					timerIntegerP1.set(20);
					timerIntegerP2.set(0);
					player1Section.setStyle("-fx-background-color: #98ff98");
					player2Section.setStyle("-fx-background-color: #D3D3D3");
					
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Turn Over");
					alert.setHeaderText(player2.getName() + "'s Turn has ended");
					alert.setContentText("Next Player's turn");
					alert.setOnHidden(evt -> timeline.play());
				    alert.show(); 
				}
			}));
			timeline.setCycleCount(Animation.INDEFINITE);
			timeline.play();
			Label capturedStoneDescription = new Label("Captured Pairs");
			capturedStoneDescription.setAlignment(Pos.CENTER);
			capturedStoneDescription.getStyleClass().add("sectionLabels");
			capturedStonesLabel2 = new Label((player.getCapturedStonesValue() / 2)+ "");
			capturedStonesLabel2.setAlignment(Pos.CENTER);
			capturedStonesLabel2.getStyleClass().add("sectionLabels");
			
			Label spacingLabel = new Label("");
			spacingLabel.setAlignment(Pos.CENTER);
			spacingLabel.getStyleClass().add("sectionLabels");

			Button save = new Button("Save Game");
			save.getStyleClass().add("saveButton");
			save.setAlignment(Pos.CENTER);
			save.setOnAction(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent event) {
					boardGUI.saveBoard();
				}
			});

			playerSectionP2.getChildren().addAll(playerNameLabel, timerDescription, timeLabel, capturedStoneDescription, capturedStonesLabel2, triaAndTesseraLabelP2, spacingLabel, save);
			return playerSectionP2;
		}
	}

	private void createGrid() {
		BoardGUI board = new BoardGUI(primaryStage, this.x, this.y, this.player1, this.player2, this);
		this.boardGUI = board;
		this.setCenter(board);
		this.setBoard(board);
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public BoardGUI getBoard() {
		return boardGUI;
	}

	public void setBoard(BoardGUI boardGUI) {
		this.boardGUI = boardGUI;
	}

	public void increaseStoneCount(Player player) {
		if (player.isFirstPlayer()) {
			capturedStonesLabel1.setText((player.getCapturedStonesValue() / 2) + "");
		} else {
			capturedStonesLabel2.setText((player.getCapturedStonesValue() / 2) + "");
		}
	}

	public void updateTimers(Player player) {
		if(player.isFirstPlayer()) {
			firstTurnOfGame = false;
			isFirstPlayersTurn = false;
			timerIntegerP1.set(0);
			timerIntegerP2.set(20);
			player2Section.setStyle("-fx-background-color: #98ff98");
			player1Section.setStyle("-fx-background-color: #D3D3D3");
		} else if (!player.isFirstPlayer()) {
			firstTurnOfGame = false;
			isFirstPlayersTurn = true;
			timerIntegerP1.set(20);
			timerIntegerP2.set(0);
			player1Section.setStyle("-fx-background-color: #98ff98");
			player2Section.setStyle("-fx-background-color: #D3D3D3");
		}
	}

	public void updateInaARowLabel(String text, Player player) {
		if(text.equals("")) {
			if(player.isFirstPlayer()) {
				triaAndTesseraLabelP2.setText(text);
			}else {
				triaAndTesseraLabelP1.setText(text);
			}
		}else {
			if(player.isFirstPlayer()) {
				triaAndTesseraLabelP1.setText(text);
			}else {
				triaAndTesseraLabelP2.setText(text);
			}
			
		}
	}

	public void updateNotificationLabel(String text) {
		warningLabelP1.setText(text);
		warningLabelP1.setStyle("-fx-text-fill: #f00;");
	}

	public void stopTimer() {
		timeline.stop();
		
	}


}
