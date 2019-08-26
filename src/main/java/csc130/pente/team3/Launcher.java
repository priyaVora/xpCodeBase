package csc130.pente.team3;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Launcher extends Application{
	
	public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		MainMenu firstWindow = new MainMenu(primaryStage);
		Scene s = new Scene(firstWindow);
		File stylesheet = new File("images/menus.css");
		s.getStylesheets().add(stylesheet.toURI().toString());
		File iconImage = new File("images/whiteStone.PNG");
		primaryStage.getIcons().add(new Image(iconImage.toURI().toString()));
		primaryStage.setTitle("Pente");
		primaryStage.setScene(s);
		primaryStage.show();
	}

	

}
