package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class chooseOrderController {

    @FXML
    private Button allOrders;

    @FXML
    private Button btnback;

    @FXML
    private Button newOrder;

    
    @FXML
	public void initialize() {


	}
    
    
    @FXML
    void allOrders(ActionEvent event) {

    	try { // open new stage
			Stage stage;
			Parent root;
			stage = (Stage) allOrders.getScene().getWindow();
			stage.close();
			root = FXMLLoader.load(getClass().getResource("allOrders.fxml"));
			Scene scene = new Scene(root, 703, 611);
			stage.setScene(scene);
			stage.setTitle("All Orders");
			stage.show();

		} catch (IOException e1) {
		}
    	
    	
    }

    @FXML
    void back(ActionEvent event) {

    	try { // open new stage
			Stage stage;
			Parent root;
			stage = (Stage) btnback.getScene().getWindow();
			stage.close();
			root = FXMLLoader.load(getClass().getResource("Start.fxml"));
			Scene scene = new Scene(root, 901, 649);
			stage.setScene(scene);
			stage.setTitle("Pharmacy");
			stage.show();

		} catch (IOException e1) {

		}
    }

    @FXML
    void newOrder(ActionEvent event) {

    	try { // open new stage
			Stage stage;
			Parent root;
			stage = (Stage) newOrder.getScene().getWindow();
			stage.close();
//			System.out.println("ok12");
			root = FXMLLoader.load(getClass().getResource("orderes.fxml"));
//			System.out.println("ok1");
			Scene scene = new Scene(root, 951, 781);
			stage.setScene(scene);
//			System.out.println("ok2");
			stage.setTitle("Orderes");
			stage.show();
//			System.out.println("ok");

		} catch (IOException e1) {
		}
    }

}
