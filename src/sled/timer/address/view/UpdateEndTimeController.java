package sled.timer.address.view;



import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sled.timer.address.MainApp;

public class UpdateEndTimeController {
	@FXML
	private TextField raceNumber1; 
	@FXML
	private TextField raceNumber2; 
	@FXML
	private TextField raceNumber3; 
	@FXML
	private TextField raceNumber4; 

	private MainApp mainApp;

	ArrayList<Integer> racers = new ArrayList<Integer>(); 
	private Stage dialogStage; 
	private boolean okClicked = false; 
	int number = 0; 

	@FXML
	private void initialize(){
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	public ArrayList<Integer> getNumber(){
		if(isInputValid()){
			if(raceNumber1.getText().equals("")){
				System.out.println("null empty");
				racers.add(null); 
			}
			else {
				racers.add(Integer.parseInt(raceNumber1.getText()));
			}

			if(raceNumber2.getText().equals("")){
				racers.add(null); 
			}
			else racers.add(Integer.parseInt(raceNumber2.getText()));

			if(raceNumber3.getText().equals("")){
				racers.add(null); 
			}
			else racers.add(Integer.parseInt(raceNumber3.getText()));

			if(raceNumber4.getText().equals("")){
				racers.add(null); 
			}
			else racers.add(Integer.parseInt(raceNumber4.getText()));

		}
		return racers; 

	}
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the tablepersonTable.setItems(mainApp.getPersonData());
	}

	@FXML
	private void handleOk() {
		if (isInputValid()) {
			okClicked = true;
			dialogStage.close();
		}
	}

	private boolean isInputValid() {
		String errorMessage = "";
		int i = 0; 

		if(mainApp.getPersonData().size() > 0){
			while(i < mainApp.getPersonData().size() && mainApp.getPersonData().get(i).getNumber() != Integer.parseInt(raceNumber1.getText()) ){
				i++; 
			}
		}	
		if(!(raceNumber1.getText().equals("") || !(raceNumber2.getText().equals("")) || !(raceNumber3.getText().equals("")) || !(raceNumber4.getText().equals("")))){

			if(!raceNumber1.getText().matches("[0-9]*") || i == mainApp.getPersonData().size() 
									|| !raceNumber2.getText().matches("[0-9]*") || !raceNumber3.getText().matches("[0-9]*") || !raceNumber4.getText().matches("[0-9]*")){
				
				errorMessage += "No valid race number!\n"; 
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(dialogStage);
				alert.setTitle("Invalid Fields");
				alert.setHeaderText("Please correct invalid fields");
				alert.setContentText(errorMessage);

				alert.showAndWait();

				return false;
			}
		}
		if (errorMessage.length() == 0) {
			return true;
		} 
		else if (raceNumber1.getText().equals("") || raceNumber2.getText().equals("") || raceNumber3.getText().equals("") || raceNumber4.getText().equals("")){
			return true; 
		}
		else {
			return true; 

		}
	}
}
