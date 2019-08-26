package csc130.pente.team3;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends VBox {
	Stage primaryStage;
	
	public MainMenu(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.getStyleClass().add("background");
		setupMainMenu();
	}
	
	public void setupMainMenu() {
		
		Button beginGame = new Button("Start Game");
		beginGame.getStyleClass().add("buttons");
		//Changes scene to the name input scene. 
		beginGame.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				NameScreen enterNames = new NameScreen(primaryStage);
				Scene s = new Scene(enterNames);
				File stylesheet = new File("images/menus.css");
				s.getStylesheets().add(stylesheet.toURI().toString());
				primaryStage.setScene(s);
			}
		});
		Button loadGame = new Button("Load Game");
		loadGame.getStyleClass().add("buttons");
		//Changes scene to the name input scene. 
		loadGame.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				LoadScreen loadScreen = new LoadScreen(primaryStage);
				Scene s = new Scene(loadScreen);
				File stylesheet = new File("images/menus.css");
				s.getStylesheets().add(stylesheet.toURI().toString());
				primaryStage.setScene(s);
			}
		});
		Button instructions = new Button("Instructions");
		instructions.getStyleClass().add("buttons");
		instructions.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				InstructionsScreen instructionsScreen = new InstructionsScreen(primaryStage);
				Scene s = new Scene(instructionsScreen);
				File stylesheet = new File("images/menus.css");
				s.getStylesheets().add(stylesheet.toURI().toString());
				primaryStage.setScene(s);
			}
		});
		Button exit = new Button("Exit");
		exit.getStyleClass().add("buttons");
		exit.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				primaryStage.close();
			}
		});
		this.getChildren().addAll(beginGame, loadGame, instructions, exit);
		this.setSpacing(10);
		
	}
	
	
	
}
