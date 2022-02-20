package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class GFileController {

	@FXML
	private TextField filename;

	@FXML
	private TextField SOmemory;

	@FXML
	private TextField min;

	@FXML
	private TextField NOPages;

	@FXML
	private Button start;

	@FXML
	private Button exit;

	@FXML
	void exitbtn(ActionEvent event) {
		// get a handle to the stage
		Stage stage = (Stage) exit.getScene().getWindow();
		// do what you have to do
		stage.close();

	}

	@FXML
	void startbtn(ActionEvent event) {
		if (filename.getText() != "" && SOmemory.getText() != "" && min.getText() != "" && NOPages.getText() != "") {
			try {
				File myObj = new File(filename.getText() + ".txt");
				if (myObj.createNewFile()) {
					System.out.println("File created: " + myObj.getName());
					FileWriter out = new FileWriter(myObj);
					out.write(genrateStringFile(Integer.parseInt(NOPages.getText()),
							Integer.parseInt(SOmemory.getText()), Integer.parseInt(min.getText())));
					out.close();
					exitbtn(event);
				} else {
					System.out.println("File already exists.");
				}
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}

	}

	public static String genrateStringFile(int n, int m, int s) {
		String full = "";
		Random rand = new Random();
		if (s > m) {
//			System.out.println("error , s must be less than  m !!");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Choisen File ");
			alert.setContentText("minimum frames per process Must be Less than size of physical memory in frames ");
			alert.showAndWait();
//			System.exit(1);
		}
		String hex = "ABCDEF";
		String[] mem = new String[100];
		String[] p = new String[n];
		int r;
		int temp;
		for (int i = 0; i < n; i++) {
			p[i] = "";
			p[i] += "P" + String.valueOf(i) + " "; // pid
			p[i] += String.valueOf(rand.nextInt(10)) + " "; // start
			temp = rand.nextInt(10) + 1;
			p[i] += String.valueOf(temp) + " " + String.valueOf(temp) + " "; // dur , size
			mem[i] = "";
			for (int k = 0; k < temp; k++) {
				mem[i] += "0000";
				for (int j = 0; j < 4; j++) {
					r = rand.nextInt(2);
					if (r == 1) {
						mem[i] += hex.charAt(rand.nextInt(hex.length()));
					} else {
						mem[i] += String.valueOf(rand.nextInt(10));
					}
				}
				mem[i] += ",";
			}
		}

		full += n + "\n";
		full += m + "\n";
		full += s + "\n";
//		System.out.println(n);
//		System.out.println(m);
//		System.out.println(s);
		for (int i = 0; i < n; i++) {
//			System.out.print(p[i] + " ");
			full += p[i] + " ";
//			System.out.print(mem[i].substring(0, mem[i].length() - 1));
			full += mem[i].substring(0, mem[i].length() - 1);
//			System.out.println();
			full += "\n";
		}

//        System.out.println("\n>>>\n"+full);
		return full;
	}
}
