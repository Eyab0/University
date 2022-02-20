package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class SampleController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	private StringBuilder mem = new StringBuilder("");
	int cyclesForMemoryAccess = 1;
	int numberOfElementsInMemory = 0;
	int pointer = 0;
	FileChooser fc = new FileChooser();
	File ChoisenFile;
	private ArrayList<Process> processes = new ArrayList<>();

	public ArrayList<Process> getProcesses() {
		return processes;
	}

	public void setProcesses(ArrayList<Process> processes) {
		this.processes = processes;
	}

	Frame memory[];
	public ArrayList<Process> readyQueue = new ArrayList<>();
	private int cycles, i = 0;
	int tQuant = 2;
	int sizeOfMemory = 10;
	int cyclesForDisk = 300;
	int prevProcess = 0;
	private int totalNumberOfFaults = 0;
	String inputFilesPath;
	private ObservableList<Process> dataList;

	@FXML
	private TableView<Process> tableData;

	@FXML
	private TableColumn<Process, String> pid;

	@FXML
	private TableColumn<Process, Double> Arrival;

	@FXML
	private TableColumn<Process, Double> BurstTime;

	@FXML
	private TableColumn<Process, Integer> NOpages;

	@FXML
	private TableColumn<Process, Double> start;

	@FXML
	private TableColumn<Process, Double> finish;

	@FXML
	private TableColumn<Process, Double> ta;

	@FXML
	private TableColumn<Process, Double> wt;

	@FXML
	private TableColumn<Process, Integer> numofFal;

	@FXML
	private TextField timeQuant;

	@FXML
	private TextArea memo;

	@FXML
	private Button reset1;

	@FXML
	private TextField totalC;

	@FXML
	private TextField totalP;

	@FXML
	private Button GFile;

	@FXML
	private Button openFile;

	@FXML
	private Button exit;

	@FXML
	private ComboBox<String> compoBox;

	boolean readFile = false;

	public SampleController() {

	}

	@FXML
	public void initialize() {
		compoBox.getItems().removeAll(compoBox.getItems());
		compoBox.getItems().addAll("FIFO", "LRU");
		compoBox.getSelectionModel().select("FIFO");
		tableData.setEditable(true);

	}

	@FXML
	void GFilebtn(ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("GFileUI.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stage = new Stage();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	@FXML
	void exitbtn(ActionEvent event) {
		// get a handle to the stage
		Stage stage = (Stage) exit.getScene().getWindow();
		// do what you have to do
		stage.close();

	}

	@FXML
	void openFilebtn(ActionEvent event) throws FileNotFoundException {

		fc.setInitialDirectory(new File("C:\\"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("Text Document", "*.txt"));
		ChoisenFile = fc.showOpenDialog(null);
		if (ChoisenFile == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Choisen File ");
			alert.setContentText("Please Enter True Text File ");
			alert.showAndWait();
			return;
		} else if (ChoisenFile != null) {
			readFile = true;
			readFile(ChoisenFile);
		}

		dataList = FXCollections.observableArrayList(processes);
		pid.setCellValueFactory(new PropertyValueFactory<Process, String>("processName"));
		Arrival.setCellValueFactory(new PropertyValueFactory<Process, Double>("arrivalTime"));
		BurstTime.setCellValueFactory(new PropertyValueFactory<Process, Double>("burstTime"));
		NOpages.setCellValueFactory(new PropertyValueFactory<Process, Integer>("size"));
		start.setCellValueFactory(new PropertyValueFactory<Process, Double>("startTime"));
		finish.setCellValueFactory(new PropertyValueFactory<Process, Double>("finishTime"));
		ta.setCellValueFactory(new PropertyValueFactory<Process, Double>("turnaround"));
		wt.setCellValueFactory(new PropertyValueFactory<Process, Double>("waitTime"));
		numofFal.setCellValueFactory(new PropertyValueFactory<Process, Integer>("numberOfFaults"));
		tableData.setItems(dataList);
		totalC.setText("" + cycles);
		totalP.setText("" + totalNumberOfFaults);

	}

	public void readFile(File file) {
		String take[] = new String[200];
		String proc_num;
		String memory_size;
		String min_frames;
		Scanner input = null;
		try {
			input = new Scanner(file);
			proc_num = input.nextLine();
			memory_size = input.nextLine();
			sizeOfMemory = Integer.valueOf(memory_size);
			min_frames = input.nextLine();
			int n = Integer.parseInt(proc_num);
			int m = Integer.parseInt(memory_size);
			int s = Integer.parseInt(min_frames);

			while (input.hasNextLine()) {
				take[0] = input.next(); // take Pn
				take[1] = input.next(); // take start
				take[2] = input.next(); // take duration
				take[3] = input.next(); // take size
				String memory_traces = input.nextLine(); // take all traces to the string
				memory_traces = memory_traces.replaceAll("[ \t\n]", ""); // remove spaces and tabs

				processes.add(new Process(take[0], Double.parseDouble(take[1]), Double.parseDouble(take[2]),
						Integer.parseInt(take[3])));
				String address[] = memory_traces.split("[,]");
				int i = 0;
				int x = Integer.parseInt(take[3]);
				while (i < x) {
					int pageNumber = (int) (Integer.parseInt(address[i].trim(), 16) / Math.pow(2, 12));
					processes.get(processes.size() - 1).pages.add(new Page(processes.size() - 1, pageNumber));
					i++;
				}
			}
			for (int k = 0; k < processes.size(); k++) {
				totalNumberOfFaults += processes.get(k).numberOfFaults;

			}
			roundRobin();
			sendProccess();
			for (int k = 0; k < processes.size(); k++) {
				totalNumberOfFaults += processes.get(k).numberOfFaults;
			}

		} catch (FileNotFoundException ex) {
		}

	}

	public List<Process> sendProccess() {
		return processes;

	}

	public void roundRobin() {

		memory = new Frame[sizeOfMemory];
		for (int i = 0; i < sizeOfMemory; i++) // initialize memory
		{
			memory[i] = new Frame();
		}

		int i = 0;
		if (!timeQuant.getText().equals("")) {
			tQuant = Integer.valueOf(timeQuant.getText());
		}

		int time = 0;// current time
		while (!checkProcessFinshid()) {
			prevProcess = i;
			processesArriv(time); // check if any process has arriced at current moment
			if (readyQueue.size() == 0) {
				time++;
				continue;
			}
			i = i % readyQueue.size();

			Process p = readyQueue.get(i++);
			if (prevProcess != i) {
				cycles += 5;
			}

			if (p.startTime == -1) {
				p.startTime = time;
			}

			for (int j = p.pageLocation; j < tQuant + p.pageLocation; j++) {
				if (compoBox.getValue() == "FIFO") {
					FIFO(p.pages.get(j));
				} else if (compoBox.getValue() == "LRU") {
					LRU(p.pages.get(j));
				}
				printMemory();
				processesArriv(++time);
				p.remainingTime--;
				if (p.remainingTime == 0) {
					break;
				}

			}
			p.pageLocation += tQuant;

			if (p.remainingTime == 0) {
				p.finishTime = time;
				readyQueue.remove(p);

				p.turnaround = p.finishTime - p.arrivalTime;
				p.waitTime = p.turnaround - p.burstTime;
			}

		}

	}

	public void printMemory() {
		String result = "";
		for (int i = 0; i < sizeOfMemory; i++) {
			if (memory[i].page != null) {
				if (i != pointer) {
					result += "( process: " + processes.get(memory[i].page.processPid).processName + " > #Page: "
							+ memory[i].page.pageNumber + " )\n";
				} else {
					result += "(process: " + processes.get(memory[i].page.processPid).processName + " > #Page: "
							+ memory[i].page.pageNumber + " )  <--\n";
				}
			} else {
				result += "\nXXXXXXXXXXXXXXXXXXXX\n";
			}
		}
		result += "\n--------------------\n";
		mem.append(result);
		memo.setText(mem.toString());
	}

	public boolean checkProcessFinshid() {
		for (Process p : processes) {
			if (p.finishTime == -1) {
				return false;
			}
		}
		return true;
	}

	public void processesArriv(int time) {
		for (Process p : processes) {
			if (p.arrivalTime == time && !readyQueue.contains(p)) {
				readyQueue.add(p);
			}
		}
	}

	public void FIFO(Page pg) {

		int indexOfPageInMemory;
		indexOfPageInMemory = indexMemory(pg);

		if (indexOfPageInMemory != -1) { // Process p is in memory
			cycles += cyclesForMemoryAccess;
		} else {
			memory[pointer].page = pg;
			pointer = (pointer + 1) % sizeOfMemory;
			numberOfElementsInMemory++;
			cycles += cyclesForDisk;
			processes.get(pg.processPid).numberOfFaults++;
		}

	}

	@FXML
	void resetbtn(ActionEvent event) {

		sizeOfMemory = 10;
		cyclesForDisk = 300;
		cyclesForMemoryAccess = 1;
		numberOfElementsInMemory = 0;
		pointer = 0;
		fc = new FileChooser();
		ChoisenFile = null;
		processes.clear();

		for (int i = 0; i < memory.length; i++) {
			memory[i] = null;
		}
		readyQueue.clear();
		cycles = 0;
		i = 0;
		tQuant = 2;
		prevProcess = 0;
		totalNumberOfFaults = 0;
		inputFilesPath = "";
		dataList.clear();
		tableData.getItems().clear();
		mem.delete(0, mem.length());
		memo.setText("");
		timeQuant.setText("");
		totalC.setText("");
		totalP.setText("");

	}

	public void LRU(Page pg) {

		int indexOfPageInMemory;
		indexOfPageInMemory = indexMemory(pg);

		if (numberOfElementsInMemory < sizeOfMemory) {
			if (indexOfPageInMemory != -1) { // Process p is in memory
				memory[indexOfPageInMemory].counter = i;
				cycles += cyclesForMemoryAccess;
				i++;
			} else {
				memory[pointer].page = pg;
				memory[pointer].counter = i;
				pointer = (pointer + 1) % sizeOfMemory;
				numberOfElementsInMemory++;
				cycles += cyclesForDisk;
				processes.get(pg.processPid).numberOfFaults++;
				i++;
			}

		} else if (indexOfPageInMemory != -1) { // Process p is in memory
			memory[indexOfPageInMemory].counter = i;
			i++;
			cycles += cyclesForMemoryAccess;
		} else {
			int LRU = findleast(memory);
			for (int x = 0; x < sizeOfMemory; x++) {
				if (memory[x].counter == LRU) {
					memory[x].page = pg;
					memory[x].counter = i;
					i++;
					break;
				}
			}
			pointer = (pointer + 1) % sizeOfMemory;
			cycles += cyclesForDisk;
			processes.get(pg.processPid).numberOfFaults++;
		}
	}

	public boolean equalPages(Page pg1, Page pg2) {
		if (pg1 == null || pg2 == null) {
			return false;
		}
		if ((pg1.pageNumber == pg2.pageNumber) && (pg1.processPid == pg2.processPid)) {
			return true;
		}

		return false;
	}

	public int indexMemory(Page pg) {
		for (int i = 0; i < sizeOfMemory; i++) {
			if (equalPages(pg, memory[i].page)) {
				return i;
			}
		}
		return -1;
	}

	public int findleast(Frame mem[]) {
		int arr[] = new int[sizeOfMemory], temp;
		for (int i = 0; i < sizeOfMemory; i++) {
			arr[i] = memory[i].counter;
		}
		for (int i = 0; i < sizeOfMemory; i++) {
			for (int j = i + 1; j < sizeOfMemory; j++) {
				if (arr[i] > arr[j]) {
					temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}
		}
		return arr[0];
	}

	public int getCycles() {
		return cycles;
	}

	public void setCycles(int cycles) {
		this.cycles = cycles;
	}

	public int getTotalNumberOfFaults() {
		return totalNumberOfFaults;
	}

	public void setTotalNumberOfFaults(int totalNumberOfFaults) {
		this.totalNumberOfFaults = totalNumberOfFaults;
	}

}
