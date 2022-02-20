package application;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class addinshorunce2orderController {

	@FXML
	private Button addCust;

	@FXML
	private Button add_insh;

	@FXML
	private Button cancel;

	@FXML
	private ImageView imgInsh;

	@FXML
	private TextField insh_id;
	


	@FXML
	void addNewCustomer(ActionEvent event) {

		try { // open new stage
			Stage stage;
			Parent root;
			stage = (Stage) addCust.getScene().getWindow();
			stage.close();
			root = FXMLLoader.load(getClass().getResource("inshurance.fxml"));
			Scene scene = new Scene(root, 859, 702);
			stage.setScene(scene);
			stage.setTitle("inshurance");
			stage.show();

		} catch (IOException e1) {
		}

	}

	@FXML
	void add_insh(ActionEvent event) {

		if (!insh_id.getText().equals("")) {
			PreparedStatement st2, st3;
			try {
				st2 = Connector.a.connectDB().prepareStatement(
						"select * from inshurance where coustumerID=" + Integer.parseInt(insh_id.getText()) + ";");
//				System.out.println("ok1");
				ResultSet r2 = st2.executeQuery();
//				System.out.println("ok2");
				if (r2.next()) {
					orderesController.cusName = r2.getString(2);
					orderesController.cusId = r2.getInt(1);
					orderesController.cusCompany = r2.getString(3);
					st3 = Connector.a.connectDB()
							.prepareStatement("select * from inshurance_company where inshurance_companyName= '"
									+ r2.getString(3) + "';");
//					System.out.println("ok3");
					ResultSet r3 = st3.executeQuery();
//					System.out.println("ok4");
					if (r3.next()) {
						orderesController.disc = (double) r3.getInt(2) / 100;
//						System.out.println(orderesController.disc + ">>"+ r3.getInt(2) + ">>"+ r3.getInt(2)/100);
					}

				} else {
					Message.displayMassage("This customer does not exist\nplease add it as new customer", "error");
				}
			} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}
		} else {
			Message.displayMassage("Please Put inshurance ID", "warning");
		}
		Stage stage = (Stage) add_insh.getScene().getWindow();
		stage.close();

	}

	@FXML
	void cancel(ActionEvent event) {

		Stage stage = (Stage) cancel.getScene().getWindow();
		stage.close();
	}

}
