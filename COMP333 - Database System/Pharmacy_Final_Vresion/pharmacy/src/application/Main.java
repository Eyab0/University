package application;
	
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Scene.fxml"));
			primaryStage.getIcons().add(new Image("file:\\Users\\Eyab\\Desktop\\Project_Pharmacy\\pharmacy\\ic.png"));
			primaryStage.setTitle("High Care Pharmacy");
			Scene scene = new Scene(root,795,618);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
