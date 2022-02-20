package application;

import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddProv_CompanyController {

    @FXML
    private TextField addPhone;

    @FXML
    private Button btnCancel;

    @FXML
    private Button add;

    @FXML
    private TextField addName;

    @FXML
    private TextField addAddress;

    @FXML
    void CancelOnAction(ActionEvent event) {
    	Stage stage;
		stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
    }

    @FXML
    void addOnAction(ActionEvent event) {
    	try {
			Provide_Company rc;
			rc = new Provide_Company(addName.getText(), Integer.parseInt(addPhone.getText()), addAddress.getText());
		    Provide_Company.prov=rc;
			insertData(rc);
			addName.clear();
			addPhone.clear();
			addAddress.clear();
		
		} catch (Exception e) {
		     showDialog(null, "Wrong input!!", "Please check the input again", AlertType.ERROR);   
				}
			}
			public void showDialog(String title, String header, String body, AlertType type) {
				Alert alert = new Alert(type); // infotrmation or error or..
				alert.setTitle(title);
				alert.setHeaderText(header);
				alert.setContentText(body);

				alert.show();

			}
	private void insertData(Provide_Company rc) {

		try {
		

			Connector.a.connectDB();
			String sql = "Insert into provide_company (company_name,phone,address) values(?,?,?)";
			PreparedStatement ps = (PreparedStatement) Connector.a.connectDB().prepareStatement(sql);
			ps.setString(1, rc.getCompanyName());
			ps.setInt(2,rc.getPhone());
			ps.setString(3, rc.getCompanyAddress());
			ps.execute();
			Stage stage;
			stage = (Stage) add.getScene().getWindow();
			stage.close();
		} catch (SQLException e) {
			
		} catch (ClassNotFoundException e) {
			
		}
	}
}
