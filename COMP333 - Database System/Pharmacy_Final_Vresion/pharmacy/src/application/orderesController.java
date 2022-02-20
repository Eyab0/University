package application;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.IntegerStringConverter;

public class orderesController {

	public static boolean isCash = true;
	public static boolean isInsurance = false;
	public static int cusId = 0;
	public static String cusName = "";
	public static String cusCompany = "";
	public static double disc = 0;
	public static int orderId;
	private double priceToShow = 0;
	private double originalPrice = 0;
	private double priceWithDisc = 0;
	String toFile = "";
	private ArrayList<invoiceData> data;
	private ObservableList<invoiceData> dataList;
	@FXML
	private TableView<invoiceData> TableData;
	boolean isCreatOrder = false;
	@FXML
	private TableColumn<invoiceData, Number> counter;

	@FXML
	private TableColumn<invoiceData, Integer> itemCategory;

	@FXML
	private TableColumn<invoiceData, String> itemName;

	@FXML
	private TableColumn<invoiceData, Integer> itemParcode;

	@FXML
	private TableColumn<invoiceData, Double> itemPrice;

	@FXML
	private TableColumn<invoiceData, Integer> itemQuantity;

	@FXML
	private TableColumn<invoiceData, Integer> itembyEmployee;

	@FXML
	private TableColumn<invoiceData, String> expDate;

	@FXML
	private Button addItem;

	@FXML
	private Button addinsurance;

	@FXML
	private Button btnback;

	@FXML
	private Button cancelOrdre;

	@FXML
	private ImageView imgDis;

	@FXML
	private ImageView imgInsh;

	@FXML
	private ImageView imgPr;

	@FXML
	private ToggleButton cashOrder;

	@FXML
	private Button deleteItem;

	@FXML
	private Label discount;

	@FXML
	private ToggleButton insuranceOrder;

	@FXML
	private Label orderID;

	@FXML
	private Label price;

	@FXML
	private Label priceBefore;

	@FXML
	private Button print;

	@FXML
	private TextField search;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	LocalDateTime now = LocalDateTime.now();

	@FXML
	void CancelOrder(ActionEvent event) {

		try {
			if (!allOrdersController.isOpen) {
				Connector.a.connectDB();
				Connector.a.ExecuteStatement("delete from  invoice where order_id =" + orderId + ";");
				Connector.a.ExecuteStatement("delete from  orderes where order_id =" + orderId + ";");
				Connector.a.connectDB().close();
			} else {
				allOrdersController.isOpen = false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try { // open new stage
			Stage stage;
			Parent root;
			stage = (Stage) cancelOrdre.getScene().getWindow();
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
	void addNew(ActionEvent event) {

		Scene scene;
		Stage stage = null;
		try { // open new stage
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("addItem2Order.fxml"));
			scene = new Scene(fxmlLoader.load(), 394, 598);
			stage = new Stage();
			stage.setTitle("add new item");
			stage.setScene(scene);
			stage.show();

		} catch (IOException e1) {
		}

		stage.setOnHidden(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent paramT) {

				initialize();

			}
		});

	}

	@FXML
	void addinsurance(ActionEvent event) {

		Scene scene;
		Stage stage = null;
		try { // open new stage
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("addinshorunce2order.fxml"));
			scene = new Scene(fxmlLoader.load(), 394, 598);
			stage = new Stage();
			stage.setTitle("add insurance");
			stage.setScene(scene);
			stage.show();

		} catch (IOException e1) {
		}

		stage.setOnHidden(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent paramT) {

				initialize();

			}
		});
	}

	@FXML
	void back(ActionEvent event) {

		try {
			if (!allOrdersController.isOpen) {
				Connector.a.connectDB();
				Connector.a.ExecuteStatement("delete from  invoice where order_id =" + orderId + ";");
				Connector.a.ExecuteStatement("delete from  orderes where order_id =" + orderId + ";");
				Connector.a.connectDB().close();
			} else {
				allOrdersController.isOpen = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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
	void cashOrder(ActionEvent event) {

		isCash = true;
		isInsurance = false;
		priceBefore.setVisible(false);
		discount.setVisible(false);
		addinsurance.setVisible(false);
		imgInsh.setVisible(false);
		imgPr.setVisible(false);
		imgDis.setVisible(false);

	}

	@FXML
	void deleteItem(ActionEvent event) {
		ObservableList<invoiceData> selectedRows = TableData.getSelectionModel().getSelectedItems();
		ArrayList<invoiceData> rows = new ArrayList<>(selectedRows);
		if (rows.size() == 0) {
			return;
		}
		deleteRow(rows.get(0));

	}

	private void deleteRow(invoiceData row) {
		priceToShow -= (row.getFull_sale_price() * row.getQuantity());
		priceWithDisc -= (row.getFull_sale_price() * row.getQuantity())
				- (row.getFull_sale_price() * row.getQuantity() * disc);

		originalPrice -= row.getFull_original_price() * row.getQuantity();
		try {
			Connector.a.connectDB();
//			System.out.println("delete from invoice where  order_id=" + row.getOrder_id() + " and par_code="
//					+ row.getPar_code() + ";");
			Connector.a.ExecuteStatement("delete from invoice where  order_id=" + row.getOrder_id() + " and par_code="
					+ row.getPar_code() + ";");
			PreparedStatement st2 = Connector.a.connectDB()
					.prepareStatement("select * from item where par_code=" + row.getPar_code() + ";");
			ResultSet r2 = st2.executeQuery();
			if (r2.next()) {
//				System.out.println("name >>" + r2.getString(1));
//				System.out.println("id >>" + r2.getInt(2));
//				System.out.println("quant >>" + r2.getInt(3));
//				System.out.println("seal price >>" + r2.getDouble(5));
//				System.out.println("orginal price >>" + r2.getDouble(6));

			}
			Connector.a.ExecuteStatement("update item set quantity = " + (r2.getInt(3) + row.getQuantity())
					+ " where par_code = " + r2.getInt(2) + " and provide_company_name = '" + r2.getString(7)
					+ "' and cat_id = " + r2.getInt(8) + ";");

			Connector.a.connectDB().close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		initialize();
	}

	@FXML
	void insuranceOrder(ActionEvent event) {

		isCash = false;
		isInsurance = true;
		priceBefore.setVisible(true);
		discount.setVisible(true);
		addinsurance.setVisible(true);
		imgInsh.setVisible(true);
		imgPr.setVisible(true);
		imgDis.setVisible(true);
	}

	private void updateQuantity(int id1, int id2, int newValue, int oldValue) {
		try {
			Connector.a.connectDB();

			PreparedStatement st2 = Connector.a.connectDB()
					.prepareStatement("select * from item where par_code=" + id1 + ";");
			ResultSet r2 = st2.executeQuery();
			if (r2.next()) {
//				System.out.println("name >>" + r2.getString(1));
//				System.out.println("id >>" + r2.getInt(2));
//				System.out.println("quant >>" + r2.getInt(3));
//				System.out.println("seal price >>" + r2.getDouble(5));
//				System.out.println("orginal price >>" + r2.getDouble(6));

			}

			if (r2.getInt(3) - (newValue - oldValue) >= 0) {
				Connector.a.ExecuteStatement("update invoice set quantity = " + newValue + " where order_id = " + id2
						+ " and par_code = " + id1 + ";");
				Connector.a.ExecuteStatement("update item set quantity = " + (r2.getInt(3) - (newValue - oldValue))
						+ " where par_code = " + r2.getInt(2) + " and provide_company_name = '" + r2.getString(7)
						+ "' and cat_id = " + r2.getInt(8) + ";");
			} else {
				if (r2.getInt(3) > 0) {
					Message.displayMassage(
							"There is not enough quantity of this product!\r\n" + "There is only: " + r2.getInt(3),
							"warning");
				} else {
					Message.displayMassage("out of stock !", "warning");

				}
			}

			Connector.a.connectDB().close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		initialize();

	}

	@FXML
	void print(ActionEvent event) {

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		java.util.Date myDate = null;
		@SuppressWarnings("unused")
		java.sql.Date sqlDate;
		try {
			myDate = formatter.parse(dtf.format(now));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sqlDate = new java.sql.Date(myDate.getTime());
		String type = "";
		if (isInsurance) {
			type = "insurance";
			try {
				Connector.a.connectDB();
				String sql = "insert into inshuranceOrder(coustumer_inshurance_id,order_date,order_id) value(?,?,?);";
				PreparedStatement ps = (PreparedStatement) Connector.a.connectDB().prepareStatement(sql);
				ps.setInt(1, cusId);
				ps.setInt(3, orderId);
				ps.setTimestamp(2, new java.sql.Timestamp(myDate.getTime()));
				ps.execute();
				Connector.a.connectDB().close();

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			type = "cash";
			try {

				Connector.a.connectDB();
				String sql = "insert into cashOrder(order_id,order_date) value(?,?);";
				PreparedStatement ps = (PreparedStatement) Connector.a.connectDB().prepareStatement(sql);
				ps.setInt(1, orderId);
				ps.setTimestamp(2, new java.sql.Timestamp(myDate.getTime()));
				ps.execute();
				Connector.a.connectDB().close();

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (!allOrdersController.isOpen) {
			try {
				Connector.a.connectDB();
				String sql = "insert into bill(order_id,order_date,full_price,profits,bill_type,emp_id) value(?,?,?,?,?,?);";
				PreparedStatement ps = (PreparedStatement) Connector.a.connectDB().prepareStatement(sql);
				ps.setInt(1, orderId);
				ps.setTimestamp(2, new java.sql.Timestamp(myDate.getTime()));
				ps.setDouble(3, priceToShow);
				ps.setDouble(4, priceToShow - originalPrice);
				ps.setString(5, type);
				ps.setInt(6, SceneController.empId);
				ps.execute();
				Connector.a.connectDB().close();

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
//				System.out.println("okkk");
				Connector.a.connectDB();
				Connector.a.ExecuteStatement(
						"update bill set full_price = " + priceToShow + ", profits = " + (priceToShow - originalPrice)
								+ ", emp_id = " + SceneController.empId + " where order_id = " + orderId + " ;");
				Connector.a.connectDB().close();

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (isInsurance && !cusName.equals("")) {
			toFile += "============================================" + "\n";
			toFile += "Price before : XX " + priceToShow + " $" + " XX\n";
			toFile += "Discount : " + disc * 100 + " %" + "\n";
			toFile += "Total Amount : " + priceWithDisc + " $" + "\n";
			toFile += "____________________________________________" + "\n";
			toFile += "Payment method : Insurance" + "\n";
			toFile += "customer info. >>" + "\n";
			toFile += "Name : " + cusName + "\n";
			toFile += "ID : " + cusId + "\n";
			toFile += "Insurance Company : " + cusCompany + "\n";

		} else {
			toFile += "============================================" + "\n";
			toFile += "Total Amount : " + priceToShow + "\n";
			toFile += "____________________________________________" + "\n";
			toFile += "Payment method : Cash" + "\n";
		}

		String fileName = "Order_" + orderId + ".txt";
		try {

			FileWriter myWriter = new FileWriter(fileName);
			myWriter.write(toFile);
			myWriter.close();
		} catch (IOException e) {
			Message.displayMassage("An error occurred.", "error");
			e.printStackTrace();
		}
		try { // open new stage
			Stage stage;
			Parent root;
			stage = (Stage) cancelOrdre.getScene().getWindow();
			stage.close();
			root = FXMLLoader.load(getClass().getResource("Start.fxml"));
			Scene scene = new Scene(root, 901, 649);
			stage.setScene(scene);
			stage.setTitle("Chose One");
			stage.show();

		} catch (IOException e1) {
			Message.displayMassage("Error In file ", "error");
		}

		try {
			ProcessBuilder pb = new ProcessBuilder("Notepad.exe", fileName);
			pb.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		allOrdersController.isOpen = false;
		Message.displayMassage("The bill has been printed successfully", "ok");

	}

	private void searchRentalEmployee() {
		FilteredList<invoiceData> filteredData = new FilteredList<>(dataList, b -> true);
		search.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(invoice -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (String.valueOf(invoice.getPar_code()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches car Id
				} else if (invoice.getItemName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches password
				} else if (String.valueOf(invoice.getItemCat()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches password
				} else
					return false; // Does not match.
			});
		});
		SortedList<invoiceData> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(TableData.comparatorProperty());
		TableData.setItems(sortedData);
	}

	@FXML
	void refresh(ActionEvent event) {

		initialize();
	}

	@FXML
	public void initialize() {

		if (!allOrdersController.isOpen) {
			if (!isCreatOrder) {
				PreparedStatement st2;
				try {
					Connector.a.connectDB();
					String sql = "insert into orderes(id) value(?);";
					PreparedStatement ps = (PreparedStatement) Connector.a.connectDB().prepareStatement(sql);
					ps.setInt(1, SceneController.empId);
					ps.execute();
					st2 = Connector.a.connectDB().prepareStatement("select MAX(order_id) from orderes;");
					ResultSet r2 = st2.executeQuery();
					if (r2.next()) {
						orderId = r2.getInt(1);
					}
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				isCreatOrder = true;
			}
		} else {
			orderId = allOrdersController.ordId;
		}
		orderID.setText(String.valueOf(orderId));
		if (isCash) {
			priceBefore.setVisible(false);
			discount.setVisible(false);
			addinsurance.setVisible(false);
			imgInsh.setVisible(false);
			imgPr.setVisible(false);
			imgDis.setVisible(false);
		}
		data = new ArrayList<>();
		dataList = FXCollections.observableArrayList(data);
		TableData.setEditable(true);
		counter.setSortable(false);
		counter.setCellValueFactory(
				column -> new ReadOnlyObjectWrapper<Number>(TableData.getItems().indexOf(column.getValue()) + 1));
		itemParcode.setCellValueFactory(new PropertyValueFactory<invoiceData, Integer>("par_code"));
		itemName.setCellValueFactory(new PropertyValueFactory<invoiceData, String>("itemName"));
		itemQuantity.setCellValueFactory(new PropertyValueFactory<invoiceData, Integer>("quantity"));
		itemQuantity
				.setCellFactory(TextFieldTableCell.<invoiceData, Integer>forTableColumn(new IntegerStringConverter()));
		itemQuantity.setOnEditCommit((CellEditEvent<invoiceData, Integer> t) -> {
			int old = t.getOldValue();
			((invoiceData) t.getTableView().getItems().get(t.getTablePosition().getRow())).setQuantity(t.getNewValue()); // display
			// only
//			System.out.println(t.getRowValue().getPar_code());

			updateQuantity(t.getRowValue().getPar_code(), t.getRowValue().getOrder_id(), t.getNewValue(), old);
		});
		itemCategory.setCellValueFactory(new PropertyValueFactory<invoiceData, Integer>("itemCat"));
		itemPrice.setCellValueFactory(new PropertyValueFactory<invoiceData, Double>("full_sale_price"));
		expDate.setCellValueFactory(new PropertyValueFactory<invoiceData, String>("expDate"));

		itembyEmployee.setCellValueFactory(new PropertyValueFactory<invoiceData, Integer>("emp_id"));
		getData();
		priceBefore.setText(priceToShow + " $");
		price.setText(priceWithDisc + " $");
		discount.setText(disc * 100 + " %");
//		System.out.println(">>|" + disc);
		TableData.setItems(dataList);

		searchRentalEmployee();

	}

	public void getData() {
		priceToShow = 0;
		originalPrice = 0;
		priceWithDisc = 0;
		String SQL, od;
		if (allOrdersController.isOpen) {
			SQL = "select * from invoice where order_id=" + allOrdersController.ordId + ";";
			od = "select * from orderes where order_id=" + allOrdersController.ordId + ";";
		} else {
			SQL = "select * from invoice where order_id=" + orderId + ";";
			od = "select * from orderes where order_id=" + orderId + ";";
		}
		try {
			Connector.a.connectDB();
			java.sql.Statement state = Connector.a.connectDB().createStatement();
			ResultSet rs = state.executeQuery(SQL);
//			ResultSet ord = state.executeQuery(od);
			int counter = 1;
			toFile = "";
			toFile += "\t" + "High Care Pharmacy" + "\n";
			toFile += "===========================================" + "\n";
			toFile += "Order id : " + orderId + "\n";
			toFile += "Date : " + dtf.format(now) + "\n";
			toFile += "by Employee : " + SceneController.empId + "\n";
			toFile += "================= Products ================" + "\n";
			toFile += "# | Item Name   | Quantity | Price " + "\n";
			while (rs.next()) {
				String SQL2 = "select * from item where par_code=" + rs.getInt(4) + ";";
				java.sql.Statement state2 = Connector.a.connectDB().createStatement();
				ResultSet rs2 = state2.executeQuery(SQL2);

				if (rs2.next()) {
					java.sql.Statement state3 = Connector.a.connectDB().createStatement();
					ResultSet rs3 = state3.executeQuery(od);
					if (rs3.next()) {

						invoiceData it = new invoiceData(orderId, rs.getInt(1), rs.getDouble(2), rs.getDouble(3),
								rs.getInt(4), rs2.getInt(8), rs2.getString(1), rs3.getInt(2),
								rs2.getDate(9).toString());
						dataList.add(it);
						toFile += counter + "| " + rs2.getString(1) + " | " + rs.getInt(1) + " | "
								+ (rs.getInt(1) * rs.getDouble(2)) + "\n";
						toFile += "-------------------------------------------" + "\n";
						priceToShow += (rs.getDouble(2) * rs.getInt(1));
						priceWithDisc += ((rs.getDouble(2) * rs.getInt(1)) - ((rs.getDouble(2) * rs.getInt(1)) * disc));
//						System.out.println(">>" + (priceToShow * disc));
//						System.out.println("$ :" + priceWithDisc);
						originalPrice += rs.getDouble(3) * rs.getInt(1);
					}
				}
				counter++;
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
}
