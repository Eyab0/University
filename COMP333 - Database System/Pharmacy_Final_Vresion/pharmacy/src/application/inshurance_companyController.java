package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class inshurance_companyController {

	private ArrayList<Inshurance_companyData> data;
	private ObservableList<Inshurance_companyData> dataList;

	@FXML
	private Button btnback;

	@FXML
	private TextField searchName;

	@FXML
	private Button add;

	@FXML
	private TextField addName;

	@FXML
	private TextField addDiscount;

	@FXML
	private Button delete;

	@FXML
	private TextField deleteName;

	@FXML
	private Button update;

	@FXML
	private TextField updateOldName;

	@FXML
	private TextField updateNewName;

	@FXML
	private TextField updateDiscount;

	@FXML
	private TextField updateCustomerNumber;

	@FXML
	private TextField addCustomerNumber;

	@FXML
	private TableColumn<Inshurance_companyData, Integer> discountCol;

	@FXML
	private TableColumn<Inshurance_companyData, String> nameCol;

	@FXML
	private TableColumn<Inshurance_companyData, Integer> customerNumCol;

	@FXML
	private TableView<Inshurance_companyData> TableData;

	@FXML
	public void initialize() {
		data = new ArrayList<>();
		dataList = FXCollections.observableArrayList(data);
		TableData.setEditable(true);

		nameCol.setCellFactory(TextFieldTableCell.<Inshurance_companyData>forTableColumn());
		nameCol.setCellValueFactory(new PropertyValueFactory<Inshurance_companyData, String>("companyName"));
		nameCol.setOnEditCommit((CellEditEvent<Inshurance_companyData, String> t) -> {
			String value = t.getRowValue().getCompanyName();
			((Inshurance_companyData) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCompanyName(t.getNewValue()); // display

			updateName(value, t.getNewValue());

		});

		discountCol.setCellValueFactory(new PropertyValueFactory<Inshurance_companyData, Integer>("companyDiscount"));
		discountCol.setCellFactory(
				TextFieldTableCell.<Inshurance_companyData, Integer>forTableColumn(new IntegerStringConverter()));
		discountCol.setOnEditCommit((CellEditEvent<Inshurance_companyData, Integer> t) -> {
			((Inshurance_companyData) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCompanyDiscount(t.getNewValue());

			updateDiscount(t.getRowValue().getCompanyName(), t.getNewValue());

		});

		customerNumCol
				.setCellValueFactory(new PropertyValueFactory<Inshurance_companyData, Integer>("numberOfCustomer"));
		customerNumCol.setCellFactory(
				TextFieldTableCell.<Inshurance_companyData, Integer>forTableColumn(new IntegerStringConverter()));
		customerNumCol.setOnEditCommit((CellEditEvent<Inshurance_companyData, Integer> t) -> {
			((Inshurance_companyData) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCompanyDiscount(t.getNewValue());

			updateCustomerNumber(t.getRowValue().getCompanyName(), t.getNewValue());

		});

		getData();
		TableData.setItems(dataList);
		search();
	}

	public void getData() {
		String SQL = "select * from inshurance_company";
		try {
			Connector.a.connectDB();
			java.sql.Statement state = Connector.a.connectDB().createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {
				Inshurance_companyData em = new Inshurance_companyData(rs.getString(1).toString(),
						Integer.parseInt(rs.getString(2).toString()), Integer.parseInt(rs.getString(3).toString()));
				dataList.add(em);
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

	public void updateDiscount(String name, int discount) {

		try {
			Connector.a.connectDB();
			Connector.a.ExecuteStatement("update inshurance_company set inshurance_companyDiscount = " + discount
					+ " where inshurance_companyName = '" + name + "';");
			Connector.a.connectDB().close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void updateCustomerNumber(String name, int customerNumber) {

		try {
			Connector.a.connectDB();
			Connector.a.ExecuteStatement("update inshurance_company set numberOfCustomer = " + customerNumber
					+ " where inshurance_companyName = '" + name + "';");
			Connector.a.connectDB().close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void updateName(String name, String newName) {

		try {
			Connector.a.connectDB();
			Connector.a.ExecuteStatement("update inshurance_company set inshurance_companyName = '" + newName
					+ "' where inshurance_companyName = '" + name + "';");
			Connector.a.connectDB().close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void updateOnAction(ActionEvent event) {

		try {
			if (updateOldName.getText() != null) {
				String Name = updateOldName.getText();
				if (updateDiscount.getText().length() > 0) {
//					System.out.println(updateDiscount.getText());
					updateDiscount(Name, Integer.parseInt(updateDiscount.getText()));
				}
				if (updateCustomerNumber.getText().length() > 0) {
//					System.out.println(Integer.parseInt(updateCustomerNumber.getText()));
					updateCustomerNumber(Name, Integer.parseInt(updateCustomerNumber.getText()));
				}
				if (updateNewName.getText().length() > 0) {
//					System.out.println(newName.getText());
					updateName(Name, updateNewName.getText());
				}

				updateOldName.clear();
				updateNewName.clear();
				updateDiscount.clear();
				updateCustomerNumber.clear();

			}
		} catch (Exception e) {

		}
		initialize();

	}

	private void insertData(Inshurance_companyData rc) {

		try {

			Connector.a.connectDB();
			String sql = "Insert into inshurance_company (inshurance_companyName, inshurance_companyDiscount)"
					+ " values(?,?)";
			PreparedStatement ps = (PreparedStatement) Connector.a.connectDB().prepareStatement(sql);
			ps.setString(1, rc.getCompanyName());
			ps.setString(2, String.valueOf(rc.getCompanyDiscount()));
//			ps.setString(3, String.valueOf(rc.getNumberOfCustomer()));
			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void insertOnAction(ActionEvent event) {

		try {
			Inshurance_companyData rc;
			rc = new Inshurance_companyData(addName.getText(), Integer.parseInt(addDiscount.getText()),
					0);
			dataList.add(rc);
			insertData(rc);
			initialize();
			addName.clear();
			addDiscount.clear();
			addCustomerNumber.clear();
		} catch (Exception e) {

		}

	}

	private void deleteRow(String Name) {
		try {
			Connector.a.connectDB();
			Connector.a
					.ExecuteStatement("delete from  inshurance_company where inshurance_companyName ='" + Name + "';");
			Connector.a.connectDB().close();
//			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void deleteOnAction(ActionEvent event) {

		try {
			if (deleteName.getText() != null) {
				String Name = deleteName.getText();
				deleteRow(Name);
			}
			deleteName.clear();
		} catch (Exception e) {

		}
		initialize();

	}

	private void search() {
		// TODO Auto-generated method stub
		FilteredList<Inshurance_companyData> filteredData = new FilteredList<>(dataList, b -> true);
		searchName.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(inshurance_companyData -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (inshurance_companyData.getCompanyName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches car Id
				} else
					return false; // Does not match.
			});
		});
		SortedList<Inshurance_companyData> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(TableData.comparatorProperty());
		TableData.setItems(sortedData);
	}

}
