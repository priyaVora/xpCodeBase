package csc130.pente.team3;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NameScreen extends VBox {

	Stage primaryStage;
	private Player player1;
	private Player player2;

	public NameScreen(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.getStyleClass().add("background");
		makeLayout();
	}

	private void makeLayout() {
		Label names = new Label("Enter Names");
		names.getStyleClass().add("header");
		// Each player has their own section in which they input their names.
		Label player1Label = new Label("Player 1:");
		final TextField player1field = new TextField();
		HBox player1Section = new HBox();
		player1Section.setSpacing(5);
		player1Section.getChildren().addAll(player1Label, player1field);

		Label player2Label = new Label("Player 2:");
		final TextField player2field = new TextField();
		HBox player2Section = new HBox();
		player2Section.setSpacing(5);
		player2Section.getChildren().addAll(player2Label, player2field);

		Label xLabel = new Label("Width:");
		final TextField xField = new TextField();
		HBox xSection = new HBox();
		xSection.setSpacing(5);
		xSection.getChildren().addAll(xLabel, xField);

		Label yLabel = new Label("Height:");
		final TextField yField = new TextField();
		HBox ySection = new HBox();
		ySection.setSpacing(5);
		ySection.getChildren().addAll(yLabel, yField);

		Button submit = new Button("Submit");
		submit.getStyleClass().add("buttons");
		// Changes screens to the GameScreen which holds the board.
		submit.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				String player1Name = player1field.getText().trim();
				String player2Name = player2field.getText().trim();
				int xDimension = 2;
				int yDimension = 2;
				try {
					xDimension = Integer.parseInt(xField.getText().trim());
					yDimension = Integer.parseInt(yField.getText().trim());
				} catch (NumberFormatException e) {
				}
				if (xDimension % 2 == 1 && yDimension % 2 == 1 && xDimension <= 39 && xDimension >= 9
						&& yDimension <= 39 && yDimension >= 9) {
					if(player1field.getText().trim().length() == 0 || player1field.getText() == null) {
						player1field.setText("Player 1");
					}
					if(player2field.getText().trim().length() == 0 || player2field.getText() == null) {
						player2field.setText("Player 2");
					}
					player1 = new Player(player1field.getText(), true);
					player2 = new Player(player2field.getText(), false);
					GameScreen gameScreen = new GameScreen(primaryStage, player1, player2,
							Integer.parseInt(xField.getText().trim()), Integer.parseInt(yField.getText().trim()));
					Scene s = new Scene(gameScreen);
					File stylesheet = new File("images/menus.css");
					s.getStylesheets().add(stylesheet.toURI().toString());
					primaryStage.setScene(s);
					primaryStage.centerOnScreen();
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Dimensions Error");
					alert.setHeaderText("Invalid Dimensions");
					alert.setContentText("Width and Height must be odd and between 9 and 39.");

					alert.showAndWait();
				}

			}
		});
		
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
		this.getChildren().addAll(names, player1Section, player2Section, xSection, ySection, submit, back);
	}

}
