package csc130.pente.team3;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoadScreen extends VBox{
	
	private Stage primaryStage;
	
	public LoadScreen(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.getStyleClass().add("background");
		createLoadScreen();
	}

	private void createLoadScreen() {
		File savesFile = new File("saves/");
		savesFile.mkdirs();
		File saves = new File("saves/");
		for (File f : saves.listFiles()) {
			Button save = new Button(f.getName().substring(0, f.getName().length() - 4));
			save.getStyleClass().add("buttons");
			save.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					GameScreen gameScreen = new GameScreen(primaryStage, f.getName());
					Scene s = new Scene(gameScreen);
					File stylesheet = new File("images/menus.css");
					s.getStylesheets().add(stylesheet.toURI().toString());
					primaryStage.setScene(s);
				}
			});
			this.setSpacing(10);
			this.getChildren().add(save);
		}
		
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
		
		this.getChildren().add(back);
		
	}

}
