package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class categoresController {

    @FXML
    private TableView<Category> TableData;

    @FXML
    private TableColumn<Category, Integer> id;

    @FXML
    private TableColumn<Category, String> name;

    @FXML
    private TableColumn<Category, Integer> numberOfItem;

    @FXML
    private Button Update;

    @FXML
    private TextField oldID;

    @FXML
    private TextField newID;

    @FXML
    private TextField newName;

    @FXML
    private TextField newNumber;

    @FXML
    private TextField SearchFiled;

    @FXML
    private TextField CategoryID;

    @FXML
    private Button Delete;

    @FXML
    private TextField addCat_id;

    @FXML
    private TextField addCat_Name;

    @FXML
    private TextField addNum_Item;

    @FXML
    private Button add;

    @FXML
    private Button btnback;

    @FXML
    private Button statistic;
    
    private ArrayList<Category> data;
	private ObservableList<Category> dataList;

	public void initialize() {
		data = new ArrayList<>();
		dataList = FXCollections.observableArrayList(data);
		TableData.setEditable(true);
		
		id.setCellValueFactory(new PropertyValueFactory<Category, Integer>("cat_id"));
		id.setCellFactory(TextFieldTableCell.<Category, Integer>forTableColumn(new IntegerStringConverter()));
		id.setOnEditCommit((CellEditEvent<Category, Integer> t) -> {
			int d = t.getRowValue().getCat_id();
			((Category) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCat_id(t.getNewValue()); // display
			// only

			updateCat_ID(d, t.getNewValue());
		});


		name.setCellValueFactory(new PropertyValueFactory<Category, String>("categores_name"));
		name.setCellFactory(TextFieldTableCell.<Category>forTableColumn());
		name.setOnEditCommit((CellEditEvent<Category, String> t) -> {
			((Category) t.getTableView().getItems().get(t.getTablePosition().getRow()))
			.setCategores_name(t.getNewValue()); // display
			// only

			updateName(t.getRowValue().getCat_id(), t.getNewValue());
		});

		
		numberOfItem.setCellValueFactory(new PropertyValueFactory<Category, Integer>("number_of_item"));
		numberOfItem.setCellFactory(TextFieldTableCell.<Category, Integer>forTableColumn(new IntegerStringConverter()));
		numberOfItem.setOnEditCommit((CellEditEvent<Category, Integer> t) -> {
			((Category) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setNumber_of_item(t.getNewValue()); // display
			// only

			updateNumber_Of_Item(t.getRowValue().getCat_id(), t.getNewValue());
		});

		getData();
		TableData.setItems(dataList);
		searchRentalCategory();
	}
	
	
	private void updateNumber_Of_Item(int cat_id, Integer newValue) {
		try {
//			System.out.println("update  employee set employee_name = '" + name + "' where id = " + ID_num);
			Connector.a.connectDB();
			Connector.a
					.ExecuteStatement("update  categores set number_of_item = " + newValue + " where cat_id = " + cat_id + ";");
			Connector.a.connectDB().close();
//			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}


	private void updateName(int cat_id, String newValue) {
		// TODO Auto-generated method stub
		try {
//			System.out.println("update  employee set employee_name = '" + name + "' where id = " + ID_num);
			Connector.a.connectDB();
			Connector.a
					.ExecuteStatement("update  categores set categores_name = '" + newValue + "' where cat_id = " + cat_id + ";");
			Connector.a.connectDB().close();
//			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}


	private void updateCat_ID(int d, Integer newValue) {
		try {
//			System.out.println("update  employee set employee_name = '" + name + "' where id = " + ID_num);
			Connector.a.connectDB();
			Connector.a
					.ExecuteStatement("update  categores set cat_id = " + newValue + " where cat_id = " + d + ";");
			Connector.a.connectDB().close();
//			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}


	private void getData() {
		String SQL = "select * from categores order by cat_id";
		try {
			Connector.a.connectDB();
			java.sql.Statement state = Connector.a.connectDB().createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {
				Category cat = new Category(rs.getInt(1),
						rs.getString(2).toString(),rs.getInt(3));
				dataList.add(cat);
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
	
	private void searchRentalCategory() {
		FilteredList<Category> filteredData = new FilteredList<>(dataList, b -> true);
		SearchFiled.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(category -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (String.valueOf(category.getCat_id()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches car Id
				} else if (category.getCategores_name().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches password
				}
				else
					return false; // Does not match.
			});
		});
		SortedList<Category> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(TableData.comparatorProperty());
		TableData.setItems(sortedData);
	}
	@FXML
	void deleteOnAction2(ActionEvent event) {

		ObservableList<Category> selectedRows = TableData.getSelectionModel().getSelectedItems();
		ArrayList<Category> rows = new ArrayList<>(selectedRows);
		if(rows.size()==0) {
			return;
		}
	
		deleteRow(rows.get(0).getCat_id());
		initialize();

	}
    @FXML
    void addOnAction(ActionEvent event) {
    	try {

			Stage stage = new Stage();
			Parent root;
			root = FXMLLoader.load(getClass().getResource("AddCategory.fxml"));
			Scene scene = new Scene(root, 448, 263);
			stage.setScene(scene);
			stage.setTitle("Add Category");
			stage.showAndWait();

		} catch (Exception e) {
			// TODO: handle exception
		}
		if (Category.cat != null) {
			dataList.add(Category.cat);
		}
		Category.cat = null;
		initialize();
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
    void deleteOnAction(ActionEvent event) {
    	try {
			if (CategoryID.getText() != null) {
				int id = Integer.parseInt(CategoryID.getText());
				deleteRow(id);
			}
		
			CategoryID.clear();
		} catch (Exception e) {

		}
		initialize();

    }
    private void deleteRow(int id) {
		try {
//			System.out.println("delete from  employee where id =" + id + ";");
			Connector.a.connectDB();
			Connector.a.ExecuteStatement("delete from  categores where cat_id =" + id + ";");
			Connector.a.connectDB().close();
//			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
    @FXML
    void updateOnAction(ActionEvent event) {
    	try {
			if (oldID.getText() != null) {
				int d = Integer.parseInt(oldID.getText()); 
				if (newName.getText().length() > 0) {
					updateName(d, newName.getText());
				}
				
				if (newNumber.getText().length() > 0) {
					updateNumber_Of_Item(d, Integer.parseInt(newNumber.getText()));
				}
				if (newID.getText().length() > 0) {
					updateCat_ID(d, Integer.parseInt(newID.getText())); 
				}
					
				
			//	refresh();
			    oldID.clear();
				newName.clear();
				newID.clear();
				newNumber.clear();
				initialize();

			}
		} catch (Exception e) {
			  oldID.clear();
				newName.clear();
				newID.clear();
				newNumber.clear();
			showDialog("Watch out!!", "", "you entered a rong format", AlertType.ERROR);
		}
    }
    public void showDialog(String title, String header, String body, AlertType type) {
    	Alert alert = new Alert(type); // infotrmation or error or..
    	alert.setTitle(title);
    	alert.setHeaderText(header);
    	alert.setContentText(body);

    	alert.show();

    }
    @FXML
    void btnRefresh(ActionEvent event) {
           initialize();
    }
    @FXML
    void btnStatistics(ActionEvent event) {
    	try {

			Stage stage = new Stage();
			Parent root;
			root = FXMLLoader.load(getClass().getResource("CategoryStatistics.fxml"));
			Scene scene = new Scene(root, 620, 507);
			stage.setScene(scene);
			stage.setTitle("Statistics");
			stage.show();

		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    }
}
