package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class Prov_CompanyOrderController implements Initializable {

    @FXML
    private Label companyName;

    @FXML
    private TableView<Prov_CompanyOrder> TableData;

	@FXML
	private TextField SearchFiled;

    @FXML
    private TableColumn<Prov_CompanyOrder, String> itemName;

    @FXML
    private TableColumn<Prov_CompanyOrder, Integer> itemParCode;

    @FXML
    private TableColumn<Prov_CompanyOrder, Integer> categoryID;

    @FXML
    private TableColumn<Prov_CompanyOrder, String> categoryName;

    @FXML
    private Button btnback;
    @FXML
    private TextField quantity;
    @FXML
    private Label price;

    @FXML
    private Label totalPrice;

    private ArrayList<Prov_CompanyOrder> data;
  	private ObservableList<Prov_CompanyOrder> dataList;
  	String companyNameString;
  	String itemNameString;
  	int itemParCodeInteget;
  	int categoryIDinteger;
  	String categoryNameString;
    Double orgPriceDouble; 
  	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		companyName.setText("You are placing an order from " + Prov_CompanyOrder.provOrder.getCompanyName() + " company");
		companyNameString = Prov_CompanyOrder.provOrder.getCompanyName();
		data = new ArrayList<>();
  		dataList = FXCollections.observableArrayList(data);
  		TableData.setEditable(true);
  		
  		itemName.setCellValueFactory(new PropertyValueFactory<Prov_CompanyOrder, String>("itemName"));
  		
  		itemParCode.setCellValueFactory(new PropertyValueFactory<Prov_CompanyOrder, Integer>("itemParCode"));
  		
  		categoryID.setCellValueFactory(new PropertyValueFactory<Prov_CompanyOrder, Integer>("categoryID"));
  		
  		
  		categoryName.setCellValueFactory(new PropertyValueFactory<Prov_CompanyOrder, String>("categoryName"));
  	
  	

  		getData();
  		TableData.setItems(dataList);
  		searchRentalCategory();
  		getOrderData();
		
	}

	private void searchRentalCategory() {
		// TODO Auto-generated method stub
		FilteredList<Prov_CompanyOrder> filteredData = new FilteredList<>(dataList, b -> true);
		SearchFiled.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(prov_CompanyOrder -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (prov_CompanyOrder.getItemName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches car Id
				} else if (String.valueOf(prov_CompanyOrder.getCategoryID()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches password
				} else if (prov_CompanyOrder.getCategoryName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches password
				}
				else if (String.valueOf(prov_CompanyOrder.getItemParCode()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches password
				}
				else
					return false; // Does not match.
			});
		});
		SortedList<Prov_CompanyOrder> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(TableData.comparatorProperty());
		TableData.setItems(sortedData);
	}

	private void getData() {
		String SQL = "select * from item i,categores c where i.cat_id=c.cat_id and i.provide_company_name = '"
	+ Prov_CompanyOrder.provOrder.getCompanyName() + "' order by i.cat_id";
		try {
			Connector.a.connectDB();
			java.sql.Statement state = Connector.a.connectDB().createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {
				Prov_CompanyOrder order = new Prov_CompanyOrder(rs.getString(1).toString(),rs.getInt(2),
						rs.getInt(10),rs.getString(11).toString(),rs.getDouble(6));
				dataList.add(order);
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
	    		root = FXMLLoader.load(getClass().getResource("provide_company.fxml"));
				Scene scene = new Scene(root, 933, 725);
				stage.setScene(scene);
				stage.setTitle("provide company");
				stage.show();
	    		
	    	} catch (IOException e1) {
	    		
	    	}
	    }

		@FXML
		void send(ActionEvent event) {
			if (quantity.getText().toString().equals("")) {
				showDialog("Warning", "Please enter the quantity !!", null, AlertType.WARNING);
			} else {

				if (itemNameString != null && itemParCodeInteget != -1 && categoryIDinteger != -1
						&& categoryNameString != null) {
					
					try {
					totalPrice.setVisible(true);
					price.setVisible(true);
					Double double1 = orgPriceDouble * Double.parseDouble( quantity.getText());
					price.setText(double1+"");
					} catch (Exception e) {
						showDialog("watch out!!", "wrong input for quantity", null,
								AlertType.ERROR);	
						return;
						}
					String s = "From: High Care Pharmacy" + "\n \n" + "To: " + companyNameString + " company"+ "\n \n" 
					+ "========================================================"+ "\n"
							+ "\t We want to make an order from you \n" + "\t Details => \n \n" + "\t Item Name: "
							+ itemNameString + "\n \n" + "\t Item ParCode: " + itemParCodeInteget + "\n \n" + "\t Category ID: " + categoryIDinteger
							+ "\n \n" + "\t Category Name: " + categoryNameString + "\n \n \t Quantity: "
							+ quantity.getText().toString();
					
					writeFile("Order To " + companyNameString , s);
										
					try {
						ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "Order To " + companyNameString);
						pb.start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				} else {
					showDialog("watch out!!", "Please select row from table that you want to make order for it", null,
							AlertType.WARNING);
				}

			}
		}
	    private void getOrderData() {
			TableData.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				if (newSelection != null) {
					itemNameString = TableData.getSelectionModel().getSelectedItem().getItemName();
					itemParCodeInteget = TableData.getSelectionModel().getSelectedItem().getItemParCode();
					categoryIDinteger = TableData.getSelectionModel().getSelectedItem().getCategoryID();
					categoryNameString = TableData.getSelectionModel().getSelectedItem().getCategoryName();
					orgPriceDouble = TableData.getSelectionModel().getSelectedItem().getOrgPrice();

				} else {
					itemNameString = null;
					itemParCodeInteget = -1;
					categoryIDinteger = -1;
					categoryNameString = null;
				}
			});
		}
		public void showDialog(String title, String header, String body, AlertType type) {
			Alert alert = new Alert(type); // infotrmation or error or..
			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(body);

			alert.show();

		}
		public void writeFile(String fileName, String contentFile) {
			File file = new File(fileName); // defined file
			if (!file.exists()) // if file not exists
			{
				try {// create new file 
					file.createNewFile();
				} catch (IOException e3) {
					e3.printStackTrace();
				}
			}
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file); // Creates a file output stream to write to the file represented by the
													// specified File object
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			PrintWriter pw = new PrintWriter(fos);// Creates a new PrintWriter from an existing OutputStream. The
													// constructor creates OutputStreamWriter, which will convert characters
													// into bytes
			pw.write(contentFile);
			pw.close();

			try {
				fos.close();

			} catch (IOException e2) {
				e2.printStackTrace();

			}
			

		}
}
