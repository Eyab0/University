package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class inshController {

    @FXML
    private Button btnback;

    @FXML
    private Button insh_comp;

    @FXML
    private Button insh_cust;

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
    void insh_comp(ActionEvent event) {

    	try { // open new stage
			Stage stage;
			Parent root;
			stage = (Stage) insh_comp.getScene().getWindow();
			stage.close();
			root = FXMLLoader.load(getClass().getResource("inshurance_company.fxml"));
			Scene scene = new Scene(root, 859, 702);
			stage.setScene(scene);
			stage.setTitle("inshurance company");
			stage.show();

		} catch (IOException e1) {
		}
    	
    }

    @FXML
    void insh_cust(ActionEvent event) {

    	try { // open new stage
			Stage stage;
			Parent root;
			stage = (Stage) insh_cust.getScene().getWindow();
			stage.close();
			root = FXMLLoader.load(getClass().getResource("inshurance.fxml"));
			Scene scene = new Scene(root, 859, 702);
			stage.setScene(scene);
			stage.setTitle("inshurance");
			stage.show();

		} catch (IOException e1) {
		}
    	
    }

}
