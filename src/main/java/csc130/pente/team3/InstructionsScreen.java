package csc130.pente.team3;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InstructionsScreen extends VBox{
	
	private Stage primaryStage;
	
	public InstructionsScreen(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.getStyleClass().add("background");
		createScreen(primaryStage);
	}

	private void createScreen(Stage primaryStage) {
		
		Label instructions = new Label("Description:\r\n" + 
				"Players take turns placing stones on the intersections of a grid. \r\n" + 
				"The first player places black stones and the second player places white stones.\r\n" + 
				"\r\n" + 
				"Rules:\r\n" + 
				"The first player must place their first stone in the center intersection. \r\n" + 
				"The first player must also place their second stone three or more spaces from the center to remove the advantage of the first player.\r\n" + 
				"For the first player’s third stone and on they may place anywhere on the board as long as it is their turn and the intersection has not already been occupied.\r\n" + 
				"The second player may place anywhere on the board as long as it is their turn and the intersection has not already been occupied.\r\n" + 
				"When a player has three consecutive stones on the board they must call “tria”.\r\n" + 
				"When a player has four consecutive stones on the board they must call “tessera”.\r\n" + 
				"To capture stones place stones around two (and only two) consecutive stones that belong to the other player. This can happen in any direction including diagonal.\r\n" + 
				"When stones are captured they are removed from the board and placed in the capturing player’s inventory.\r\n" + 
				"If a player places themselves into a capture it will not initiate a capture for the other player.\r\n" + 
				"See below for example of a capture.\r\n" + 
				"\r\n" + 
				"X: Your stones\r\n" + 
				"O: Other player stones\r\n" + 
				"\r\n" + 
				"XOOX\r\n" + 
				"\r\n" + 
				"X\r\n" + 
				"O\r\n" + 
				"O\r\n" + 
				"X\r\n" + 
				"\r\n" + 
				"Win:\r\n" + 
				"In order to win a player must place 5 or more stones in a row, vertically, horizontally, or diagonally, with no empty intersections between them. \r\n" + 
				"A player may also win by capturing 5 pairs or more of the opponent’s stone’s.\r\n");
		instructions.getStyleClass().add("instructionsLabel");
		
		Button back = new Button("Back");
		back.getStyleClass().add("buttons");
		back.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				MainMenu mainMenu = new MainMenu(primaryStage);
				Scene s = new Scene(mainMenu);
				File stylesheet = new File("images/menus.css");
				s.getStylesheets().add(stylesheet.toURI().toString());
				primaryStage.setScene(s);
			}
		});
		
		this.setSpacing(10);
		this.getChildren().addAll(instructions, back);
	}

}
