package application;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class itemController {

	private ArrayList<Item> data;
	private ObservableList<Item> dataList;

	@FXML
	private TextField AddCatId;

	@FXML
	private TextField AddCompanyName;

	@FXML
	private TextField AddOrigenPrice;

	@FXML
	private TextField AddDiscription;

	@FXML
	private TextField AddSalePrice;

	@FXML
	private Button BackButton;

	@FXML
	private Button Delete;

	@FXML
	private TextField Parcode;

	@FXML
	private TextField NewCatId;

	@FXML
	private TextField NewCompanyName;

	@FXML
	private TextField NewOrigenPrice;

	@FXML
	private TextField NewQuantity;

	@FXML
	private TextField NewSalePrice;

	@FXML
	private TextField SearchFiled;

	@FXML
	private TableView<Item> TableData;

	@FXML
	private Button Update;

	@FXML
	private Button add;

	@FXML
	private TextField addName;

	@FXML
	private TextField addParcode;

	@FXML
	private TextField addQuantity;

	@FXML
	private TableColumn<Item, Integer> cat_idColumn;

	@FXML
	private TableColumn<Item, String> discriptionColumn;

	@FXML
	private TableColumn<Item, String> item_nameColumn;

	@FXML
	private TextField newDiscription;

	@FXML
	private TextField newName;

	@FXML
	private TextField newParcode;

	@FXML
	private TextField oldParcode;

	@FXML
	private TableColumn<Item, Double> origen_priceColumn;

	@FXML
	private TableColumn<Item, Integer> par_codeColumn;

	@FXML
	private TableColumn<Item, String> provide_company_nameColumn;

	@FXML
	private TableColumn<Item, Integer> quantityColumn;

	@FXML
	private TableColumn<Item, Double> sale_priceColumn;

	@FXML
	private TextField OldCatId;

	@FXML
	private TextField OldCompanyName;

	@FXML
	private TextField DeleteCatId;

	@FXML
	private TextField DeleteCompanyName;

	@FXML
	private TextField DeleteParcode;

	@FXML
	private Button Refresh;

	@FXML
	private Button Search;
	
	@FXML
    private TableColumn<Item, String> ExpDateColumn;
	
	@FXML
    private TextField AddExpDate;
	
	@FXML
    private TextField NewExpDate;
	
	@FXML
    private Button ShowExpiredItems;
	
	@FXML
    private TextField CompanyText;

	@FXML
    private TextField DateOfExp;

    @FXML
    private Button DateOfExpbtn;
	    
    @FXML
    private Button FilterCompany;

    @FXML
    private Button ItemsLessThan;

    @FXML
    private TextField NumberOfItems;


	@FXML
	void refresh(ActionEvent event) {

		initialize();
	}

	@FXML
	void addOnAction(ActionEvent event) {

		try {
			Item rc;
			rc = new Item(addName.getText(), Integer.parseInt(addParcode.getText()),
					Integer.parseInt(addQuantity.getText()), AddDiscription.getText(),
					Double.parseDouble(AddSalePrice.getText()), Double.parseDouble(AddOrigenPrice.getText()),
					AddCompanyName.getText(), Integer.parseInt(AddCatId.getText()), AddExpDate.getText());
			

			dataList.add(rc);
			insertData(rc);
			refresh();

			addParcode.clear();
			addName.clear();
			addQuantity.clear();
			AddDiscription.clear();
			AddSalePrice.clear();
			AddOrigenPrice.clear();
			AddCompanyName.clear();
			AddCatId.clear();
			AddExpDate.clear();

		} catch (Exception e) {

		}

	}

	@FXML
	void deleteOnAction(ActionEvent event) {

		try {
			if (DeleteParcode.getText() != null && DeleteCompanyName.getText() != null
					&& DeleteCatId.getText() != null) {
				String parcode = (DeleteParcode.getText());
				String compName = DeleteCompanyName.getText();
				String catID = (DeleteCatId.getText());
				deleteRow(parcode, compName, catID);
			}
			DeleteParcode.clear();
			DeleteCompanyName.clear();
			DeleteCatId.clear();
		} catch (Exception e) {

		}
		refresh();

	}

	private void deleteRow(String parcode, String compName, String catID) {
		try {
			Connector.a.connectDB();
			Connector.a.ExecuteStatement("delete from  item where par_code =" + parcode + " and provide_company_name= '"
					+ compName + "' and cat_id=" + catID + ";");
			Connector.a.ExecuteStatement("update categores set number_of_item = number_of_item -1 " + " where cat_id = "
					+ catID + ";");
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
			if (oldParcode.getText() != null && OldCompanyName.getText() != null && OldCatId.getText() != null) {
				int parcode = Integer.parseInt(oldParcode.getText());
				String compName = OldCompanyName.getText();
				int catID = Integer.parseInt(OldCatId.getText());

				if (newParcode.getText().length() > 0) {
					updateParcode(parcode, compName, catID, newParcode.getText());
				}

				if (newName.getText().length() > 0) {
					updateName(parcode, compName, catID, newName.getText());
				}

				if (NewQuantity.getText().length() > 0) {
					updateQuantity(parcode, compName, catID, NewQuantity.getText());
				}

				if (newDiscription.getText().length() > 0) {
					updateDiscription(parcode, compName, catID, newDiscription.getText());
				}

				if (NewSalePrice.getText().length() > 0) {
					updateSalePrice(parcode, compName, catID, NewSalePrice.getText());
				}

				if (NewOrigenPrice.getText().length() > 0) {
					updateOrigenPrice(parcode, compName, catID, NewOrigenPrice.getText());
				}
				
				if (NewExpDate.getText().length() > 0) {
					updateExpDate(parcode, compName, catID, NewExpDate.getText());
				}

				refresh();

				oldParcode.clear();
				OldCompanyName.clear();
				OldCatId.clear();
				newParcode.clear();
				newName.clear();
				NewQuantity.clear();
				newDiscription.clear();
				NewSalePrice.clear();
				NewOrigenPrice.clear();
				NewCompanyName.clear();
				NewCatId.clear();
				NewExpDate.clear();

			}
		} catch (Exception e) {

		}
		refresh();

	}

	private void insertData(Item rc) {

		try {
			
			
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			java.util.Date myDate = null;
			java.sql.Date sqlDate;
			
			try {
				myDate = formatter.parse(rc.getExp_date());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sqlDate = new java.sql.Date(myDate.getTime());

			
			
			

			Connector.a.connectDB();
			String sql = "Insert into item (par_code, item_name, quantity, discription, sale_price, origen_price, provide_company_name, cat_id, exp_date) values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) Connector.a.connectDB().prepareStatement(sql);

			ps.setInt(1, rc.getPar_code());
			ps.setString(2, rc.getItem_name());
			ps.setInt(3, rc.getQuantity());
			ps.setString(4, rc.getDiscription());
			ps.setDouble(5, rc.getSale_price());
			ps.setDouble(6, rc.getOrigen_price());
			ps.setString(7, rc.getProvide_company_name());
			ps.setInt(8, rc.getCat_id());
			ps.setTimestamp(9, new java.sql.Timestamp(myDate.getTime()));
			ps.execute();
			Connector.a.ExecuteStatement("update categores set number_of_item = number_of_item +1 " + " where cat_id = "
					+ rc.getCat_id() + ";");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void refresh() {
		TableData.getItems().clear();
		getData();
		TableData.setItems(dataList);

	}

	public void initialize() {
		data = new ArrayList<>();
		dataList = FXCollections.observableArrayList(data);
		TableData.setEditable(true);

		par_codeColumn.setCellFactory(TextFieldTableCell.<Item, Integer>forTableColumn(new IntegerStringConverter()));
		par_codeColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("par_code"));

		item_nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("item_name"));
		item_nameColumn.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		item_nameColumn.setOnEditCommit((CellEditEvent<Item, String> t) -> {
			((Item) t.getTableView().getItems().get(t.getTablePosition().getRow())).setItem_name(t.getNewValue()); // display
			// only

			updateName(t.getRowValue().getPar_code(), t.getRowValue().getProvide_company_name(),
					t.getRowValue().getCat_id(), t.getNewValue());
		});

		quantityColumn.setCellFactory(TextFieldTableCell.<Item, Integer>forTableColumn(new IntegerStringConverter()));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("quantity"));
		quantityColumn.setOnEditCommit((CellEditEvent<Item, Integer> t) -> {
			((Item) t.getTableView().getItems().get(t.getTablePosition().getRow())).setQuantity(t.getNewValue()); // display
																													// only

			updateQuantity(t.getRowValue().getPar_code(), t.getRowValue().getProvide_company_name(),
					t.getRowValue().getCat_id(), Integer.toString(t.getNewValue()));
		});

		discriptionColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("discription"));
		discriptionColumn.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		discriptionColumn.setOnEditCommit((CellEditEvent<Item, String> t) -> {
			((Item) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDiscription(t.getNewValue()); // display
			// only

			updateDiscription(t.getRowValue().getPar_code(), t.getRowValue().getProvide_company_name(),
					t.getRowValue().getCat_id(), t.getNewValue());
		});

		sale_priceColumn.setCellFactory(TextFieldTableCell.<Item, Double>forTableColumn(new DoubleStringConverter()));
		sale_priceColumn.setCellValueFactory(new PropertyValueFactory<Item, Double>("sale_price"));
		sale_priceColumn.setOnEditCommit((CellEditEvent<Item, Double> t) -> {
			((Item) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSale_price(t.getNewValue()); // display
																													// only

			updateSalePrice(t.getRowValue().getPar_code(), t.getRowValue().getProvide_company_name(),
					t.getRowValue().getCat_id(), Double.toString(t.getNewValue()));
		});

		origen_priceColumn.setCellFactory(TextFieldTableCell.<Item, Double>forTableColumn(new DoubleStringConverter()));
		origen_priceColumn.setCellValueFactory(new PropertyValueFactory<Item, Double>("origen_price"));
		origen_priceColumn.setOnEditCommit((CellEditEvent<Item, Double> t) -> {
			((Item) t.getTableView().getItems().get(t.getTablePosition().getRow())).setOrigen_price(t.getNewValue()); // display
																														// only

			updateOrigenPrice(t.getRowValue().getPar_code(), t.getRowValue().getProvide_company_name(),
					t.getRowValue().getCat_id(), Double.toString(t.getNewValue()));
		});

		
		
		ExpDateColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("exp_date"));
		ExpDateColumn.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		ExpDateColumn.setOnEditCommit((CellEditEvent<Item, String> t) -> {
			((Item) t.getTableView().getItems().get(t.getTablePosition().getRow())).setExp_date(t.getNewValue()); // display
			// only

			updateExpDate(t.getRowValue().getPar_code(), t.getRowValue().getProvide_company_name(),
					t.getRowValue().getCat_id(), t.getNewValue());
		});
		
		
		
		
		provide_company_nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("provide_company_name"));

		cat_idColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("cat_id"));

		getData();
		TableData.setItems(dataList);

		searchItems();
	}

	public void getData() {
		String SQL = "select * from item";
		try {
			Connector.a.connectDB();
			java.sql.Statement state = Connector.a.connectDB().createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {
				Item it = new Item(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDouble(5),
						rs.getDouble(6), rs.getString(7), rs.getInt(8), rs.getString(9));
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

	public void updateParcode(int parcode, String CompName, int catID, String newParcode) {

		try {
			Connector.a.connectDB();
			Connector.a.ExecuteStatement("update  item set par_code = " + newParcode + " where par_code = " + parcode
					+ " and provide_company_name = '" + CompName + "' and cat_id = " + catID + ";");
			Connector.a.connectDB().close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void updateName(int parcode, String CompName, int catID, String newitem_name) {

		try {
			Connector.a.connectDB();
			Connector.a.ExecuteStatement("update  item set item_name = '" + newitem_name + "' where par_code = "
					+ parcode + " and provide_company_name = '" + CompName + "' and cat_id = " + catID + ";");
			Connector.a.connectDB().close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void updateQuantity(int parcode, String CompName, int catID, String NewQuantity) {

		try {
			Connector.a.connectDB();
			Connector.a.ExecuteStatement("update  item set quantity = " + NewQuantity + " where par_code = " + parcode
					+ " and provide_company_name = '" + CompName + "' and cat_id = " + catID + ";");
			Connector.a.connectDB().close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void updateDiscription(int parcode, String CompName, int catID, String newDiscription) {

		try {
			Connector.a.connectDB();
			Connector.a.ExecuteStatement("update  item set discription = '" + newDiscription + "' where par_code = "
					+ parcode + " and provide_company_name = '" + CompName + "' and cat_id = " + catID + ";");
			Connector.a.connectDB().close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void updateSalePrice(int parcode, String CompName, int catID, String NewSalePrice) {

		try {
			Connector.a.connectDB();
			Connector.a.ExecuteStatement("update  item set sale_price = " + NewSalePrice + " where par_code = "
					+ parcode + " and provide_company_name = '" + CompName + "' and cat_id = " + catID + ";");
			Connector.a.connectDB().close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void updateOrigenPrice(int parcode, String CompName, int catID, String NewOrigenPrice) {

		try {
			Connector.a.connectDB();
			Connector.a.ExecuteStatement("update  item set origen_price = " + NewOrigenPrice + " where par_code = "
					+ parcode + " and provide_company_name = '" + CompName + "' and cat_id = " + catID + ";");
			Connector.a.connectDB().close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public void updateExpDate(int parcode, String CompName, int catID, String NewDate) {
		
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			java.util.Date myDate = null;
			java.sql.Date sqlDate;
			try {
				myDate = formatter.parse(NewDate);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sqlDate = new java.sql.Date(myDate.getTime());

			Connector.a.connectDB();
			Connector.a.ExecuteStatement("update  item set exp_date = '" + sqlDate + "' where par_code = "	+ parcode + " and provide_company_name = '" + CompName + "' and cat_id = " + catID + ";");
			Connector.a.connectDB().close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	@FXML
	void back(ActionEvent event) {

		try { // open new stage
			Stage stage;
			Parent root;
			stage = (Stage) BackButton.getScene().getWindow();
			stage.close();
			root = FXMLLoader.load(getClass().getResource("Start.fxml"));
			Scene scene = new Scene(root, 901, 649);
			stage.setScene(scene);
			stage.setTitle("Chose One");
			stage.show();

		} catch (IOException e1) {

		}
	}

	public void searchItems() {
		FilteredList<Item> filteredData = new FilteredList<>(dataList, b -> true);
		SearchFiled.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(item -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (String.valueOf(item.getPar_code()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				} else if (item.getItem_name().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				} else if (item.getProvide_company_name().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				} else if (String.valueOf(item.getCat_id()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				} else
					return false; // Does not match.
			});
		});
		SortedList<Item> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(TableData.comparatorProperty());
		TableData.setItems(sortedData);
	}
	
	@FXML
    void filter(ActionEvent event) {

		
		ObservableList<Item> expList;
		
		String SQL = " SELECT * FROM item WHERE exp_date <= NOW();";
		expList = FXCollections.observableArrayList(data);
		try {
			Connector.a.connectDB();
			java.sql.Statement state = Connector.a.connectDB().createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {
				Item it = new Item(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDouble(5),
						rs.getDouble(6), rs.getString(7), rs.getInt(8), rs.getString(9));
				expList.add(it);
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
		
		TableData.setItems(expList);
		
		
		
    }

	
	@FXML
	 void DateOfExp(ActionEvent event) {
		
		
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			java.util.Date myDate = null;
			java.sql.Date sqlDate;
			try {
				myDate = formatter.parse(DateOfExp.getText());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sqlDate = new java.sql.Date(myDate.getTime());
			
		
		
			ObservableList<Item> expList;
		
		String SQL = " SELECT * FROM item WHERE exp_date <= '"+ sqlDate +"';";
		expList = FXCollections.observableArrayList(data);
		try {
			Connector.a.connectDB();
			java.sql.Statement state = Connector.a.connectDB().createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {
				Item it = new Item(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDouble(5),
						rs.getDouble(6), rs.getString(7), rs.getInt(8), rs.getString(9));
				expList.add(it);
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
		
		TableData.setItems(expList);
		
		
	    }

	@FXML
	void FilterCompany(ActionEvent event) {
		
ObservableList<Item> filter;
		
		String SQL = " SELECT * FROM item WHERE provide_company_name = '"+ CompanyText.getText() +"' order by item_name ;";
		filter = FXCollections.observableArrayList(data);
		try {
			Connector.a.connectDB();
			java.sql.Statement state = Connector.a.connectDB().createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {
				Item it = new Item(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDouble(5),
						rs.getDouble(6), rs.getString(7), rs.getInt(8), rs.getString(9));
				filter.add(it);
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
		
		TableData.setItems(filter);
	    }

    @FXML
	void ItemsLessThan(ActionEvent event) {
    	
		
    	ObservableList<Item> filter;
		
		String SQL = " SELECT * FROM item WHERE quantity <="+ NumberOfItems.getText()+ " order by quantity;";
		filter = FXCollections.observableArrayList(data);
		try {
			Connector.a.connectDB();
			java.sql.Statement state = Connector.a.connectDB().createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {
				Item it = new Item(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDouble(5),
						rs.getDouble(6), rs.getString(7), rs.getInt(8), rs.getString(9));
				filter.add(it);
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
		
		TableData.setItems(filter);
	    }
	    


	 

}