package sled.timer.address.view;

import sled.timer.address.MainApp;

import sled.timer.address.model.Person;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.InvalidationListener; 
import sled.timer.address.MainApp;
import sled.timer.address.model.Person;
import sled.timer.address.model.StopWatch; 
//import .DateUtil;


public class PersonOverviewController {

	@FXML
	private TableView<Person> personTable;
	//	@FXML
	//	private TableColumn<Person, String> firstNameColumn;
	@FXML
	private TableColumn<Person, String> time;
	@FXML
	private TableColumn<Person, Integer> raceNumber; 
	@FXML
	private TableColumn<Person, String> totalTime;
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label timer; 
	@FXML
	private Label racer1; 
	@FXML 
	private Label racer2; 
	@FXML
	private Label racer3; 
	@FXML
	private Label racer4; 

	StopWatch stopWatch; 
	int number = 0; 
	ArrayList<Integer> numbers = new ArrayList<Integer>(); 
	int startCount = 0; 

	Person update; 

	boolean paused = false; 
	// Reference to the main application.
	private MainApp mainApp;

	int updateCount = 1; 

	public PersonOverviewController() {
		stopWatch = new StopWatch(this); 

	}

	@FXML
	private void startTimer(){

		if(startCount == 0 || paused){
			System.out.println("started");
			stopWatch.startTimer();
			paused = false; 
		}
		startCount++; 
	}

	@FXML
	private void pauseTimer(){
		paused = true; 
		System.out.println("paused");
		System.out.println(timer.getText());
		stopWatch.pauseTimer(); 
	}

	@FXML
	private void stopTimer(){
		if(paused){
			System.out.println("reset");
			stopWatch.stopTimer();
			timer.setText("00" + ":" + "00" + "." + "00" );
			startCount = 0; 
			updateCount = 1; 
			racer1.setText("Racer 1 time: ");
			racer2.setText("Racer 2 time: ");
			racer3.setText("Racer 3 time: ");
			racer4.setText("Racer 4 time: ");
		}
	}

	private final Object updaterLock = new Object();
	private boolean updating = false;
	private long value; 

	//@FXML
	public void updateTimer(String millis, String sec, String min ) {
		synchronized (updaterLock) {
			//value = millis;
			if (!updating) {
				updating = true;
				Platform.runLater(() -> {
					synchronized (updaterLock) {
						updating = false;
						//System.out.println(Long.toString(value/1000));
						//timer.setText(Long.toString(value/1000) );

						timer.setText(min + ":" + sec + "." + millis );
					}
				});
			}
		}
	}
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		//		firstNameColumn.setCellValueFactory(
		//				cellData -> cellData.getValue().firstNameProperty());
		totalTime.setCellValueFactory(
				cellData -> cellData.getValue().totalTime());
		time.setCellValueFactory(
				cellData -> cellData.getValue().time());
		raceNumber.setCellValueFactory(
				cellData -> cellData.getValue().raceNumber().asObject());

	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		personTable.setItems(mainApp.getPersonData());
	}
	@FXML
	private void handleDeletePerson() {
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			personTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}    
	}
	@FXML
	private void handleNewPerson() {
		//System.out.println("new");
		Person tempPerson = new Person();
		boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
		if (okClicked) {
			mainApp.getPersonData().add(tempPerson);
		}
	}

	@FXML
	private void handleEditPerson() {
		Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
		//System.out.println("hi");
		if (selectedPerson != null) {
			boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
			if (okClicked) {
				//showPersonDetails(selectedPerson);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}
	@FXML
	private void updateTimeTesting(){
		String endTime = timer.getText(); 
		if(updateCount == 1){
			racer1.setText("Racer 1 time: " + endTime);
		}
		if(updateCount == 2){
			racer2.setText("Racer 2 time: " + endTime);
		}
		if(updateCount == 3){
			racer3.setText("Racer 3 time: " + endTime);
		}
		if(updateCount == 4){
			racer4.setText("Racer 4 time: " + endTime); 
		}
		updateCount++; 
	}
	@FXML
	private void handleCalculateTime() throws ParseException{
		numbers = mainApp.showUpdateTimeDialog(); 
		String endTime1 = racer1.getText().substring(14); 
		System.out.println(endTime1);
		String endTime2 = racer2.getText().substring(14); 
		String endTime3 = racer3.getText().substring(14); 
		String endTime4 = racer4.getText().substring(14); 
		//System.out.println(getPerson(0) == null);
		calculateTime(getPerson(0), endTime1); 
		calculateTime(getPerson(1), endTime2); 
		calculateTime(getPerson(2), endTime3); 
		calculateTime(getPerson(3), endTime4); 


		//System.out.println("asdf" + number);
		// loop through all the numbers and check if it contains it in the numbesr 
		// arrayList 
		updateCount = 1; 
		racer1.setText("Racer 1 time: ");
		racer2.setText("Racer 2 time: ");
		racer3.setText("Racer 3 time: ");
		racer4.setText("Racer 4 time: ");



	}
	public Person getPerson(int i){

		if(numbers.get(i) != null){
			System.out.println("not null");
			number = numbers.get(i); 
			for(int j = 0; j < mainApp.getPersonData().size(); j++){
				if(mainApp.getPersonData().get(j).getNumber() == number){
					System.out.println("returned!");
					update = mainApp.getPersonData().get(j); 
					return update; 
				}
			}
		}

		return null; 
	}
	public void calculateTime(Person update, String endTime) throws ParseException{
		String startTime  = update.getTime(); 
		SimpleDateFormat format = new SimpleDateFormat("mm:ss.SS");
		Date date1 = format.parse(endTime);
		Date date2 = format.parse(startTime);
		long diff = date1.getTime() - date2.getTime(); 

		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		System.out.println(diff);
		String millis = Long.toString(diff);
		millis = millis.substring(millis.length()-2);
		System.out.println(millis.substring(millis.length()-2));

		System.out.println(diffSeconds + "," + diffMinutes);
		System.out.println(startTime + ", " + endTime);


		String sec = ""; 
		String min = ""; 

		millis = millis.length() < 2 ? "0" + millis : millis; 

		sec = Long.toString(diffSeconds).length() < 2 ? "0" + diffSeconds: Long.toString(diffSeconds); 

		min = Long.toString(diffMinutes).length() < 2 ? "0" +diffMinutes : Long.toString(diffMinutes); 

		update.setTotalTime(min + ":" + sec + "." + millis);
		
	}
}
