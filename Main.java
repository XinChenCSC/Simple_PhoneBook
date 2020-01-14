package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application  {
	
	public static void main(String[] args) {
		launch(args);
	}
	 Stage window;
	public void start(Stage primaryStage) {
		try {
				window = primaryStage;
				
			  Parent root = FXMLLoader.load(getClass().getResource("Phonebook.fxml"));
	            Scene scene = new Scene(root,400,400);
	            primaryStage.setScene(scene);
	            primaryStage.setTitle("Phonebook Editor by Xin");
	            
	            primaryStage.show();
		} catch(Exception e) {	
			e.printStackTrace();
		}
	}
}
