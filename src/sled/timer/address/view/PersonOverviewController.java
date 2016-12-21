package sled.timer.address.view;

import sled.timer.address.MainApp;

import sled.timer.address.model.Person;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	StopWatch stopWatch; 

	int number = 0; 
	
	int startCount = 0; 
	
	Person update; 

	boolean paused = false; 
	// Reference to the main application.
	private MainApp mainApp;

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
		System.out.println("new");
		Person tempPerson = new Person();
		boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
		if (okClicked) {
			mainApp.getPersonData().add(tempPerson);
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	@FXML
	private void handleEditPerson() {
		Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
		System.out.println("hi");
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
	private void handleUpdateTime() throws ParseException{
		String endTime = timer.getText(); 
		number = mainApp.showUpdateTimeDialog(); 
		//System.out.println("asdf" + number);
		for(int i = 0; i < mainApp.getPersonData().size(); i++){
			if(mainApp.getPersonData().get(i).getNumber() == number){
				update = mainApp.getPersonData().get(i); 
				break; 
			}
		}
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
