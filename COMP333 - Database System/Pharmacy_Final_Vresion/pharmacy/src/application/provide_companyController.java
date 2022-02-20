package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class provide_companyController {

	@FXML
	private TableView<Provide_Company> TableData;

	@FXML
	private TableColumn<Provide_Company, String> name;

	@FXML
	private TableColumn<Provide_Company, Integer> phone;

	@FXML
	private TableColumn<Provide_Company, String> address;

	@FXML
	private Button Update;

	@FXML
	private TextField oldName;

	@FXML
	private TextField newName;

	@FXML
	private TextField newPhone;

	@FXML
	private TextField newAddress;

	@FXML
	private TextField SearchFiled;

	@FXML
	private TextField nameCompany;

	@FXML
	private Button Delete;

	@FXML
	private TextField addName;

	@FXML
	private TextField addPhone;

	@FXML
	private TextField addAddress;
	@FXML
	private Button btnback;

	@FXML
	private Button order;
	
	@FXML
	private Button add;

	private ArrayList<Provide_Company> data;
	private ObservableList<Provide_Company> dataList;
	private String companyName;

	public void initialize() {
		data = new ArrayList<>();
		dataList = FXCollections.observableArrayList(data);
		TableData.setEditable(true);

		name.setCellValueFactory(new PropertyValueFactory<Provide_Company, String>("companyName"));
		name.setCellFactory(TextFieldTableCell.<Provide_Company>forTableColumn());
		name.setOnEditCommit((CellEditEvent<Provide_Company, String> t) -> {
			String string = t.getRowValue().getCompanyName();
			((Provide_Company) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCompanyName(t.getNewValue()); // display
			// only

			updateName(string, t.getNewValue());
		});

		phone.setCellValueFactory(new PropertyValueFactory<Provide_Company, Integer>("phone"));
		phone.setCellFactory(TextFieldTableCell.<Provide_Company, Integer>forTableColumn(new IntegerStringConverter()));
		phone.setOnEditCommit((CellEditEvent<Provide_Company, Integer> t) -> {
			((Provide_Company) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setPhone(t.getNewValue()); // display
			// only

			updatePhone(t.getRowValue().getCompanyName(), t.getNewValue());
		});

		address.setCellValueFactory(new PropertyValueFactory<Provide_Company, String>("companyAddress"));
		address.setCellFactory(TextFieldTableCell.<Provide_Company>forTableColumn());
		address.setOnEditCommit((CellEditEvent<Provide_Company, String> t) -> {
			((Provide_Company) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCompanyAddress(t.getNewValue());// display
			// only

			updateAddress(t.getRowValue().getCompanyName(), t.getNewValue());
		});

		getData();
		TableData.setItems(dataList);
		searchRentalEmployee();
		getCompanyName();
	}

	private void updateAddress(String companyName, String newValue) {
		// TODO Auto-generated method stub
		try {
//			System.out.println("update  employee set employee_name = '" + name + "' where id = " + ID_num);
			Connector.a.connectDB();
			Connector.a.ExecuteStatement("update  provide_company set address = '" + newValue
					+ "' where company_name = '" + companyName + "';");
			Connector.a.connectDB().close();
//			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void updatePhone(String companyName, int newValue) {
		// TODO Auto-generated method stub
		try {
//			System.out.println("update  employee set employee_name = '" + name + "' where id = " + ID_num);
			Connector.a.connectDB();
			Connector.a.ExecuteStatement("update  provide_company set phone = '" + newValue + "' where company_name = '"
					+ companyName + "';");
			Connector.a.connectDB().close();
//			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void getData() {
		String SQL = "select * from provide_company order by company_name";
		try {
			Connector.a.connectDB();
			java.sql.Statement state = Connector.a.connectDB().createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {
				Provide_Company company = new Provide_Company(rs.getString(1).toString(), rs.getInt(2),
						rs.getString(3).toString());
				dataList.add(company);
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

	}

	private void updateName(String companyName, String newValue) {
		// TODO Auto-generated method stub
		try {

			Connector.a.connectDB();
			Connector.a.ExecuteStatement("update  provide_company set company_name = '" + newValue
					+ "' where company_name = '" + companyName + "';");
			Connector.a.connectDB().close();
//			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void searchRentalEmployee() {
		FilteredList<Provide_Company> filteredData = new FilteredList<>(dataList, b -> true);
		SearchFiled.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(provide_company -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (provide_company.getCompanyName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches car Id
				} else if (String.valueOf(provide_company.getPhone()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches password
				} else if (provide_company.getCompanyAddress().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches password
				}

				else
					return false; // Does not match.
			});
		});
		SortedList<Provide_Company> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(TableData.comparatorProperty());
		TableData.setItems(sortedData);
	}

	@FXML
	void addOnAction(ActionEvent event) {
		try {

			Stage stage = new Stage();
			Parent root;
			root = FXMLLoader.load(getClass().getResource("AddProv_Company.fxml"));
			Scene scene = new Scene(root, 584, 263);
			stage.setScene(scene);
			stage.setTitle("Add Provider Company");
			stage.showAndWait();

		} catch (Exception e) {
			// TODO: handle exception
		}
		if (Provide_Company.prov != null) {
			dataList.add(Provide_Company.prov);
		}
		Provide_Company.prov = null;
		initialize();
	}

	@FXML
	void deleteOnAction(ActionEvent event) {
		try {
			if (nameCompany.getText() != null) {
				String nameString = nameCompany.getText();
				deleteRow(nameString);
			}

			nameCompany.clear();
		} catch (Exception e) {

		}
		initialize();

	}

	@FXML
	void deleteOnAction2(ActionEvent event) {

		ObservableList<Provide_Company> selectedRows = TableData.getSelectionModel().getSelectedItems();
		ArrayList<Provide_Company> rows = new ArrayList<>(selectedRows);
		if (rows.size() == 0) {
			return;
		}

		deleteRow(rows.get(0).getCompanyName());
		initialize();

	}

	private void deleteRow(String nameString) {
		try {
//			System.out.println("delete from  employee where id =" + id + ";");
			Connector.a.connectDB();
			Connector.a.ExecuteStatement("delete from  provide_company where company_name ='" + nameString + "';");
			Connector.a.connectDB().close();
//			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void updateOnAction(ActionEvent event) throws SQLException {

		try {
			if (oldName.getText() != null) {
				String st = oldName.getText();

				if (newPhone.getText().length() > 0) {
					updatePhone(st, Integer.parseInt(newPhone.getText()));
				}

				if (newAddress.getText().length() > 0) {
					updateAddress(st, newAddress.getText());
				}
				if (newName.getText().length() > 0) {
					updateName(st, newName.getText());
				}
				// refresh();
				oldName.clear();
				newName.clear();
				newPhone.clear();
				newAddress.clear();
				initialize();

			}
		} catch (Exception e) {
			oldName.clear();
			newName.clear();
			newPhone.clear();
			newAddress.clear();
			showDialog("new Phone", "", "you entered a rong format for phone", AlertType.ERROR);
		}
//		refresh();

	}

	public void showDialog(String title, String header, String body, AlertType type) {
		Alert alert = new Alert(type); // infotrmation or error or..
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(body);

		alert.show();

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
			stage.setTitle("Chose One");
			stage.show();

		} catch (IOException e1) {

		}
	}

	@FXML
	void btnRefresh(ActionEvent event) {
		initialize();
	}

	@FXML
	void btnOrder(ActionEvent event) {
		if (companyName != null) {
			Prov_CompanyOrder.provOrder.setCompanyName(companyName);
			try {

				Stage stage;
				Parent root;
				stage = (Stage) order.getScene().getWindow();
				stage.close();
				root = FXMLLoader.load(getClass().getResource("Prov_CompanyOrder.fxml"));
				Scene scene = new Scene(root, 875, 716);
				stage.setScene(scene);
				stage.setTitle("Make Order");
				stage.show();

			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			showDialog("watch out!!", "Please select row from table that you want to make order for it", null,
					AlertType.WARNING);
		}
	}

	private void getCompanyName() {
		TableData.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				companyName = TableData.getSelectionModel().getSelectedItem().getCompanyName();

			} else {
				companyName = null;
			}
		});
	}
}
