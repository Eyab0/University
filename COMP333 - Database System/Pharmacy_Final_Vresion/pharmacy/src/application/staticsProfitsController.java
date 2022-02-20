package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class staticsProfitsController {

	@FXML
	private Label netIncome;

	@FXML
	private Label numOrders;

	@FXML
	private Label Profits;
	@FXML
	private PieChart paymentMethods;

	@FXML
	public void initialize() {

		try {
			int num = 0;
			Connector.a.connectDB();
			PreparedStatement st2;
			st2 = Connector.a.connectDB().prepareStatement("select count(*) from bill;");
			ResultSet r2 = st2.executeQuery();
			if (r2.next()) {
				num = r2.getInt(1);
			}
			numOrders.setText(num + "");

			/////////////
			double num2 = 0;
			PreparedStatement st3;
			st3 = Connector.a.connectDB().prepareStatement("select sum(profits) from bill");
			ResultSet r3 = st3.executeQuery();
			if (r3.next()) {
				num2 = r3.getDouble(1);
			}
			Profits.setText(num2 + "$");
			/////////////////
			PreparedStatement st4;
			double num3 = 0;
			st4 = Connector.a.connectDB().prepareStatement("select sum(full_price) from bill");
			ResultSet r4 = st4.executeQuery();
			if (r4.next()) {
				num3 = r4.getDouble(1);
			}
			netIncome.setText(num3 + "$");
			int cash=0;
			int insh=0;
			
			PreparedStatement st5;
			st5 = Connector.a.connectDB().prepareStatement("select count(*) from bill where bill_type = 'cash';");
			ResultSet r5 = st5.executeQuery();
			if (r5.next()) {
				cash = r5.getInt(1);
			}
			PreparedStatement st6;
			st6 = Connector.a.connectDB().prepareStatement("select count(*) from bill where bill_type = 'insurance';");
			ResultSet r6 = st6.executeQuery();
			if (r6.next()) {
				insh = r6.getInt(1);
			}
			
			ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
					new PieChart.Data("insurance", insh), new PieChart.Data("Cash", cash));
			paymentMethods.setData(pieChartData);

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
