package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class StartController {

	@FXML
	private Button btnItem;

	@FXML
	private Button btnOrder;

	@FXML
	private Button btnProv;

	@FXML
	private Button btnCat;

	@FXML
	private Button btnEmp;

	@FXML
	private Button btnInsh;
	@FXML
    private Button btnLogOut;

	@FXML
	void Cat(ActionEvent event) {
		try { // open new stage
			Stage stage;
			Parent root;
			stage = (Stage) btnCat.getScene().getWindow();
			stage.close();
			root = FXMLLoader.load(getClass().getResource("categores.fxml"));
			Scene scene = new Scene(root, 933, 725);
			stage.setScene(scene);
			stage.setTitle("categores");
			stage.show();

		} catch (IOException e1) {
		}
	}

	@FXML
	void Emp(ActionEvent event) {
		if (Manegar.mng.getName().equals("nathera alwan") && Manegar.mng.getPassword().equals("admin")) {
			try { // open new stage
				Stage stage;
				Parent root;
				stage = (Stage) btnEmp.getScene().getWindow();
				stage.close();
				root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
				Scene scene = new Scene(root, 1051, 773);
				stage.setScene(scene);
				stage.setTitle("Employee");
				stage.show();

			} catch (IOException e1) {
			}
		} else {
			showDialog("error", "you are not permission to visit this", null, AlertType.ERROR);
		}
	}

	@FXML
	void Insh(ActionEvent event) {
		try { // open new stage
			Stage stage;
			Parent root;
			stage = (Stage) btnInsh.getScene().getWindow();
			stage.close();
			root = FXMLLoader.load(getClass().getResource("insh.fxml"));
			Scene scene = new Scene(root, 348, 474);
			stage.setScene(scene);
			stage.setTitle("inshurance");
			stage.show();

		} catch (IOException e1) {
		}
	}

	@FXML
	void Item(ActionEvent event) {
		try { // open new stage
			
			Stage stage;
			Parent root;
			stage = (Stage) btnItem.getScene().getWindow();
			stage.close();
			root = FXMLLoader.load(getClass().getResource("item.fxml"));
			Scene scene = new Scene(root, 1364, 759);
			stage.setScene(scene);
			stage.setTitle("item");
			stage.show();
			

		} catch (IOException e1) {
		}
	}

	@FXML
	void Order(ActionEvent event) {
		try { // open new stage
			
			Stage stage;
			Parent root;
			stage = (Stage) btnOrder.getScene().getWindow();
			stage.close();
			root = FXMLLoader.load(getClass().getResource("chooseOrders.fxml"));
			Scene scene = new Scene(root, 369, 474);
			stage.setScene(scene);
			stage.setTitle("orderes");
			stage.show();

		} catch (IOException e1) {
		}
		
	}

	@FXML
	void Prov(ActionEvent event) {
		try { // open new stage
			Stage stage;
			Parent root;
			stage = (Stage) btnProv.getScene().getWindow();
			stage.close();
			root = FXMLLoader.load(getClass().getResource("provide_company.fxml"));
			Scene scene = new Scene(root, 933, 725);
			stage.setScene(scene);
			stage.setTitle("provide company");
			stage.show();

		} catch (IOException e1) {
		}
	}
	  @FXML
	    void LogOut(ActionEvent event) {
			try { // open new stage
				Stage stage;
				Parent root;
				stage = (Stage) btnProv.getScene().getWindow();
				stage.close();
				root = FXMLLoader.load(getClass().getResource("Scene.fxml"));
				Scene scene = new Scene(root, 795,618);
				stage.setScene(scene);
				stage.show();

			} catch (IOException e1) {
			}
	    }
	public void showDialog(String title, String header, String body, AlertType type) {
		Alert alert = new Alert(type); // infotrmation or error or..
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(body);

		alert.show();

	}
	
}
