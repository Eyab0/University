package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class addItem2OrderController {

	@FXML
	private TextField Item_name;

	@FXML
	private Button addToCart;

	@FXML
	private ToggleButton byItemName;

	@FXML
	private ToggleButton byParcode;

	@FXML
	private Button cancelItem;

	@FXML
	private ToggleGroup cartTgl;

	@FXML
	private HBox itemName;

	@FXML
	private TextField item_parcode;

	@FXML
	private Spinner<Integer> item_quant;

	@FXML
	private HBox parcode;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	LocalDateTime now = LocalDateTime.now();

	@FXML
	public void initialize() {

		parcode.setVisible(true);
		itemName.setVisible(false);
		parcode.setLayoutX(67);
		parcode.setLayoutY(270);
		SpinnerValueFactory<Integer> quant = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200, 1);
		item_quant.setValueFactory(quant);
	}

	@FXML
	void addToCart(ActionEvent event) {

		PreparedStatement st2 = null;
		ResultSet r2 = null;
		int qunt;
		try {
			if (!item_parcode.getText().equals("")) {
				st2 = Connector.a.connectDB().prepareStatement(
						"select * from item where par_code=" + Integer.parseInt(item_parcode.getText()) + ";");

			} else {
				st2 = Connector.a.connectDB()
						.prepareStatement("select * from item where item_name='" + Item_name.getText() + "';");

			}
			r2 = st2.executeQuery();
			if (r2.next()) {
//				System.out.println("name >>" + r2.getString(1));
//				System.out.println("id >>" + r2.getInt(2));
//				System.out.println("quant >>" + r2.getInt(3));
//				System.out.println("seal price >>" + r2.getDouble(5));
//				System.out.println("orginal price >>" + r2.getDouble(6));

			} else {
				Message.displayMassage("this item not found !", "error");
				return;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if (r2.getInt(3) - item_quant.getValue() >= 0) {
				try {
					if (r2.getDate(9).toString().compareTo(dtf.format(now).toString()) <=0) {
						Message.displayMassage("Warning\nThis product is expired ", "warning");

					}
//					System.out.println(r2.getDate(9).toString());
//					System.out.println(dtf.format(now).toString());
					Connector.a.connectDB();
					String sql = "insert into invoice(quantity ,full_sale_price ,full_original_price ,par_code,order_id) values (?,?,?,?,?);";
					PreparedStatement ps = (PreparedStatement) Connector.a.connectDB().prepareStatement(sql);
					Connector.a.ExecuteStatement("update item set quantity = " + (r2.getInt(3) - item_quant.getValue())
							+ " where par_code = " + r2.getInt(2) + " and provide_company_name = '" + r2.getString(7)
							+ "' and cat_id = " + r2.getInt(8) + ";");

					ps.setInt(1, item_quant.getValue());
					ps.setDouble(2, r2.getDouble(5));
					ps.setDouble(3, r2.getDouble(6));
					ps.setInt(4, r2.getInt(2));
					ps.setInt(5, orderesController.orderId);
					ps.execute();

				} catch (SQLException e) {
					if (e.getErrorCode() == 1062) {
						Message.displayMassage("this item is already added !", "warning");
						return;
					}
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

				Stage stage = (Stage) cancelItem.getScene().getWindow();
				stage.close();
			} else {
				if (r2.getInt(3) > 0) {
					Message.displayMassage(
							"There is not enough quantity of this product!\r\n" + "There is only: " + r2.getInt(3),
							"warning");
				} else {
					Message.displayMassage("out of stock !", "warning");

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void byItemName(ActionEvent event) {

		item_parcode.clear();
		Item_name.clear();
		parcode.setVisible(false);
		itemName.setVisible(true);
		itemName.setLayoutX(67);
		itemName.setLayoutY(270);

	}

	@FXML
	void byParcode(ActionEvent event) {
		item_parcode.clear();
		Item_name.clear();
		parcode.setVisible(true);
		itemName.setVisible(false);
		parcode.setLayoutX(67);
		parcode.setLayoutY(270);

	}

	@FXML
	void cancelItem(ActionEvent event) {

		Stage stage = (Stage) cancelItem.getScene().getWindow();
		stage.close();
	}

}
