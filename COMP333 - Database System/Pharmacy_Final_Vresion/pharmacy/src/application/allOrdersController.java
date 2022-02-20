package application;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatter.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.converter.StringConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class allOrdersController {

	public static boolean isOpen = false;
	public static int ordId;

	private ArrayList<billData> data;
	private ObservableList<billData> dataList;
	@FXML
	private TableView<billData> TableData;
	@FXML
	private Button btnback;
	@FXML
	private TableColumn<billData, Integer> orderId;
	@FXML
	private TableColumn<billData, Integer> byEmp;
	@FXML
	private TableColumn<billData, Double> price;
	@FXML
	private TableColumn<billData, String> date;
	@FXML
	private TableColumn<billData, String> bill_type;

	@FXML
	private TextField by_emp_search;
	@FXML
	private DatePicker dateFrom;

	@FXML
	private Button search;

	@FXML
	private ChoiceBox<String> setType;
	@FXML
	private DatePicker dateTo;

	@FXML
	private Button deleteItem;

	@FXML
	private Button openOrder;

	@FXML
	private TextField ordeID;

	@FXML
	private Button refresh;

	@FXML
	private Button showStattics;

	@FXML
	private TableView<billData> tableData;

	String SQL = "select * from bill order by order_id;";
	boolean isSearch = false;
	String typ = "";

	@FXML
	void search(ActionEvent event) {
		isSearch = true;
		boolean isDate = false;
		boolean isType = false;
		if (dateFrom.getValue() == (null) && !(dateTo.getValue() == (null))
				|| !(dateFrom.getValue() == (null)) && dateTo.getValue() == (null)) {
			Message.displayMassage("please put 'date from' and 'date to' together", "warning");
			return;
		}
		SQL = "select * from bill ";

		if (dateFrom.getValue() != null || setType.getValue() != "All" || by_emp_search.getText() != "") {
			SQL += " where ";
		}

		if (dateFrom.getValue() != null) {
			SQL += " (order_date BETWEEN '" + dateFrom.getValue() + "'and '" + dateTo.getValue() + "') ";
			isDate = true;
		}
		if (setType.getValue() != "All") {
			if (isDate) {
				SQL += " and ";
			}
			SQL += " bill_type = '" + setType.getValue() + "'";
			typ = setType.getValue();
			isType = true;
		}
		if (by_emp_search.getText() != "") {
			if (isDate || isType) {
				SQL += " and ";
			}

			SQL += " emp_id =  " + Integer.parseInt(by_emp_search.getText());
		}

		SQL += " order by order_id; ";
		initialize();
	}

	@FXML
	public void initialize() {

		data = new ArrayList<>();
		dataList = FXCollections.observableArrayList(data);
		setType.getItems().removeAll(setType.getItems());
		setType.getItems().addAll("All", "Cash", "Insurance");
		setType.getSelectionModel().select("All");
		if (!isSearch) {
			TableData.setEditable(true);
			dateFrom.setValue(null);
			dateTo.setValue(null);
			by_emp_search.clear();
			ordeID.clear();
			typ = "";
			

		}
		if(typ != "") {
			setType.getSelectionModel().select(typ);
		}
		

		isSearch = false;
		by_emp_search.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					by_emp_search.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		orderId.setCellValueFactory(new PropertyValueFactory<billData, Integer>("order_id"));
		date.setCellValueFactory(new PropertyValueFactory<billData, String>("order_date"));
		price.setCellValueFactory(new PropertyValueFactory<billData, Double>("full_price"));
		byEmp.setCellValueFactory(new PropertyValueFactory<billData, Integer>("emp_id"));
		bill_type.setCellValueFactory(new PropertyValueFactory<billData, String>("bill_type"));
		getData();
		TableData.setItems(dataList);

	}

	public void getData() {

		try {
			Connector.a.connectDB();
			java.sql.Statement state = Connector.a.connectDB().createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {

				billData it = new billData(rs.getInt(1), rs.getDate(2).toString(), rs.getDouble(3), rs.getDouble(4),
						rs.getInt(6), rs.getString(5));

				dataList.add(it);

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

	@FXML
	void deleteItem(ActionEvent event) {
		ObservableList<billData> selectedRows = TableData.getSelectionModel().getSelectedItems();
		ArrayList<billData> rows = new ArrayList<>(selectedRows);
		if (rows.size() == 0) {
			return;
		}
		deleteRow(rows.get(0));

	}

	private void deleteRow(billData row) {

		try {
			Connector.a.connectDB();
			Connector.a.ExecuteStatement("delete from bill where  order_id=" + row.getOrder_id() + ";");
			Connector.a.connectDB().close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		initialize();
	}

	@FXML
	void openOrder(ActionEvent event) {

		if (!ordeID.getText().equals("")) {
			isOpen = true;
			ordId = Integer.parseInt(ordeID.getText());

			try { // open new stage
				Stage stage;
				Parent root;
				stage = (Stage) openOrder.getScene().getWindow();
				stage.close();
				root = FXMLLoader.load(getClass().getResource("orderes.fxml"));
				Scene scene = new Scene(root, 951, 781);
				stage.setScene(scene);
				stage.setTitle("Orderes");
				stage.show();

			} catch (IOException e1) {
			}
		} else {
			Message.displayMassage("Please Put Order Number", "warning");
		}
	}

	@FXML
	void showStattics(ActionEvent event) {

		if (Manegar.mng.getName().equals("nathera alwan")) {
			Scene scene;
			Stage stage = null;
			try { // open new stage
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("staticsOrder.fxml"));
				scene = new Scene(fxmlLoader.load(), 918, 463);
				stage = new Stage();
				stage.setTitle("statics Orders");
				stage.setScene(scene);
				stage.show();

			} catch (IOException e1) {
			}
		} else {
			Message.displayMassage("You do not have permission to access", "warning");
		}
	}

	@FXML
	void refresh(ActionEvent event) {
		SQL = "select * from bill order by order_id;";
		isSearch = false;
		initialize();
	}

}
