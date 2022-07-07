package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		
			Pane root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Palestine Shortest Path");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}