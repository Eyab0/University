package application;

import java.lang.invoke.WrongMethodTypeException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.mysql.jdbc.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddEmployeeController {

	@FXML
	private DatePicker addDateOfBirth;

	@FXML
	private TextField addName;

	@FXML
	private DatePicker adddateofemployment;

	@FXML
	private TextField addPassWord;

	@FXML
	private TextField addHPrice;

	@FXML
	private TextField addWHours;

	@FXML
	private Button btnCancel;

	@FXML
	private Button add;
    int statmentID;
	@FXML
	void CancelOnAction(ActionEvent event) {
		Stage stage;
		stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
	}

	@FXML
	void addOnAction(ActionEvent event) {
		Employeedata rc;
		try {
		rc = new Employeedata(addName.getText(), addDateOfBirth.getValue().toString(), adddateofemployment.getValue().toString(),
				addPassWord.getText(), Double.parseDouble(addWHours.getText()),
				Double.parseDouble(addHPrice.getText()));
         Employeedata.emd=rc;
//		dataList.add(rc);
		insertData(rc);
		addName.clear();
		addPassWord.clear();
		addWHours.clear();
		addHPrice.clear();
		}
		catch (Exception e) {
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
	private void insertData(Employeedata rc) {

		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			java.util.Date myDate = null;
			java.sql.Date sqlDate;
			try {
				myDate = formatter.parse(rc.getBirthday());

			} catch (Exception e) {
				   showDialog(null, "Wrong Format For the date!!", "Please use this format for data : \n year-month-day", AlertType.ERROR);   
			}

			sqlDate = new java.sql.Date(myDate.getTime());

			java.util.Date myDate2 = null;
			java.sql.Date sqlDate2;
			try {
				myDate2 = formatter.parse(rc.getDate_of_employment());

			} catch (Exception e) {
				   showDialog(null, "Wrong Format For the date!!", "Please use this format for data : \n year-month-day", AlertType.ERROR);   
			}
			sqlDate2 = new java.sql.Date(myDate.getTime());

//			System.out.println("Insert into employee (id,employee_name,birthday,date_of_employment) values("
//					+ rc.getId() + ",'" + rc.getEmployee_name() + "','" + sqlDate + "','" + sqlDate2 + "')");

			Connector.a.connectDB();
			String sql = "Insert into employee (employee_name,birthday,date_of_employment,emp_password) values(?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) Connector.a.connectDB().prepareStatement(sql);
			ps.setString(4, rc.getEmp_password());
			ps.setString(1, rc.getEmployee_name());
			ps.setTimestamp(2, new java.sql.Timestamp(myDate.getTime()));
			ps.setTimestamp(3, new java.sql.Timestamp(myDate2.getTime()));
			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
			String SQL = "select id from employee where employee_name='" + rc.getEmployee_name()
					+ "' and emp_password='" + rc.getEmp_password() + "';";
			try {
				Connector.a.connectDB();
				java.sql.Statement state = Connector.a.connectDB().createStatement();
				ResultSet rs = state.executeQuery(SQL);
				while (rs.next()) {
					statmentID = rs.getInt(1);
				}
				rs.close();
				state.close();
				Connector.a.connectDB().close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Connector.a.connectDB();
				String sql = "Insert into hourly_employee (id,work_hours,hour_price) values(?,?,?)";
				PreparedStatement ps = (PreparedStatement) Connector.a.connectDB().prepareStatement(sql);
				ps.setInt(1, statmentID);
				ps.setDouble(2, rc.getWork_hours());
				ps.setDouble(3, rc.getHour_price());
				ps.execute();

			} catch (SQLException e) {

			} catch (ClassNotFoundException e) {

			}
			Stage stage;
			stage = (Stage) add.getScene().getWindow();
			stage.close();
//		else if (Contract) {
//			String SQL = "select id from employee where employee_name='" + rc.getEmployee_name()
//					+ "' and emp_password='" + rc.getEmp_password() + "';";
//			try {
//				Connector.a.connectDB();
//				java.sql.Statement state = Connector.a.connectDB().createStatement();
//				ResultSet rs = state.executeQuery(SQL);
//				while (rs.next()) {
//					statmentID = rs.getInt(1);
//				}
//				rs.close();
//				state.close();
//				Connector.a.connectDB().close();
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			try {
//				Connector.a.connectDB();
//				String sql = "Insert into contrect_employee (id,amount_paid) values(?,?)";
//				PreparedStatement ps = (PreparedStatement) Connector.a.connectDB().prepareStatement(sql);
//				ps.setInt(1, statmentID);
//				ps.setDouble(2, rc.getAmount_paid());
//				ps.execute();
//
//			} catch (SQLException e) {
//
//			} catch (ClassNotFoundException e) {
//
//			}
//
//		}
	}

}
