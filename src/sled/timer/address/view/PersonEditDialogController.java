package sled.timer.address.view;

import sled.timer.address.model.Person;
//import ch.makery.address.util.DateUtil;
//import ch.makery.address.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class PersonEditDialogController {
	
	//private TextField firstNameField;
	@FXML
	private TextField raceNumber;
	@FXML
	private TextField startTimer;


	private Stage dialogStage;
	private Person person;
	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setPerson(Person person) {
		this.person = person;

		//firstNameField.setText(person.getFirstName());
		raceNumber.setText(Integer.toString(person.getNumber()));
		startTimer.setText(person.getTime());
	}
	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		if (isInputValid()) {
			//person.setFirstName(firstNameField.getText());
			person.setRaceNumber(Integer.parseInt(raceNumber.getText()));
			person.setTime(startTimer.getText());

			okClicked = true;
			dialogStage.close();
		}
	}
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}


	private boolean isInputValid() {
		String errorMessage = "";

//		if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
//			errorMessage += "No valid name!\n"; 
//		}
		if (raceNumber.getText() == null || raceNumber.getText().length() == 0 || !raceNumber.getText().matches("[0-9]*")) {
			errorMessage += "No valid race number!\n"; 
		}

		if (startTimer.getText() == null || startTimer.getText().length() == 0) {
			errorMessage += "No valid start time!\n"; 
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
