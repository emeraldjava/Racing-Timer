package sled.timer.address.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
public class Person {

	//private final StringProperty name;
	private final StringProperty time; 
	private final IntegerProperty raceNumber; 
	private final StringProperty totalTime; 
	//private final ObjectProperty<LocalDate> birthday;

	/**
	 * Default constructor.
	 */
	public Person() {
		this(null);
	}

	/**
	 * Constructor with some initial data.
	 * 
	 * @param Name
	 */
	public Person(String name) {
		this.totalTime = new SimpleStringProperty("00:00.00");
		//this.name = new SimpleStringProperty("name"); 

		// Some initial dummy data, just for convenient testing.
		this.time = new SimpleStringProperty("00:00.00"); 
		this.raceNumber = new SimpleIntegerProperty(0); 
	}

//	public String getFirstName() {
//		return name.get();
//	}
//
//	public void setFirstName(String firstName) {
//		this.name.set(firstName);
//	}
//
//	public StringProperty firstNameProperty() {
//		return name;
//	}

	public int getNumber(){
		return raceNumber.get(); 
	}

	public void setRaceNumber(int number){
		this.raceNumber.set(number);
	}

	public IntegerProperty raceNumber(){
		return raceNumber; 
	}

	public String getTime() {
		return time.get();
	}

	public void setTime(String time) {
		this.time.set(time);
	}

	public StringProperty time() {
		return time;
	}

	public String getTotalTime() {
		return totalTime.get();
	}

	public void setTotalTime(String time) {
		this.totalTime.set(time);
	}

	public StringProperty totalTime() {
		return totalTime;
	}

}