package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

public class CategoryStatisticsController implements Initializable{

    @FXML
    private StackedBarChart<String,Integer> barChart;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		String SQL = "select * from categores order by cat_id";
		try {
			Connector.a.connectDB();
			java.sql.Statement state = Connector.a.connectDB().createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {
				XYChart.Series<String,Integer> s = new XYChart.Series<>();
				s.setName("Categry ID: " + rs.getInt(1));
				String name = rs.getString(2);
				int numOfItem = rs.getInt(3);
				s.getData().add(new XYChart.Data(name,numOfItem));
				barChart.getData().addAll(s);
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
