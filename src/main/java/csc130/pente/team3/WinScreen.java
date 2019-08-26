package csc130.pente.team3;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WinScreen extends VBox{
	
	private Stage primaryStage;
	
	public WinScreen(Stage primaryStage, Player player) {
		this.primaryStage = primaryStage;
		this.getStyleClass().add("background");
		createWinScreen(player);
	}

	private void createWinScreen(Player player) {
		Label winner = new Label("The winner is: " + player.getName());
		winner.getStyleClass().add("winLabel");
		Button mainMenu = new Button("Main Menu");
		mainMenu.getStyleClass().add("winButtons");
		mainMenu.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				MainMenu menu = new MainMenu(primaryStage);
				Scene s = new Scene(menu);
				File stylesheet = new File("images/menus.css");
				s.getStylesheets().add(stylesheet.toURI().toString());
				primaryStage.setScene(s);
			}
		});
		
		Button exit = new Button("Exit");
		exit.getStyleClass().add("winButtons");
		exit.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				primaryStage.close();
			}
		});
		
		this.getChildren().addAll(winner, mainMenu, exit);
		this.setSpacing(10);
	}

}
