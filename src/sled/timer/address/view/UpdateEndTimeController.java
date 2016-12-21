package sled.timer.address.view;



import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sled.timer.address.MainApp;

public class UpdateEndTimeController {
	@FXML
	private TextField raceNumber; 
	
	private MainApp mainApp;

	
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
	
	public int getNumber(){
		this.number = Integer.parseInt(raceNumber.getText());
		return number; 
		
	}
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the tablepersonTable.setItems(mainApp.getPersonData());
	}
	
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			//person.setFirstName(firstNameField.getText());
			

			okClicked = true;
			dialogStage.close();
		}
	}
	
	private boolean isInputValid() {
		String errorMessage = "";

//		if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
//			errorMessage += "No valid name!\n"; 
//		}
		
		int i = 0; 
		//System.out.println(mainApp.getPersonData().get(i).getNumber());
		//System.out.println(mainApp.getPersonData());
		if(mainApp.getPersonData().size() > 0){
			while(i < mainApp.getPersonData().size() && mainApp.getPersonData().get(i).getNumber() != Integer.parseInt(raceNumber.getText()) ){
				i++; 
			}
		}
//		
		
		if (mainApp.getPersonData().size() == 0 || raceNumber.getText() == null || raceNumber.getText().length() == 0 || !raceNumber.getText().matches("[0-9]*") || i == mainApp.getPersonData().size() ) {
			errorMessage += "No valid race number!\n"; 
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}
}
