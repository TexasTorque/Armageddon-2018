package org.texastorque.io;

import java.util.ArrayList;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutoMode;
import org.texastorque.subsystems.Drivebase;
import org.texastorque.torquelib.util.TorqueToggle;

/* The purpose of this class is to read inputs from a controller for the same
 * length as the autonomous period, and then create an XML file from
 * which an autonomous mode can be read. This is the recording/writing class, 
 * AutoManager handles the reading.
 */
public class InputRecorder extends HumanInput{

	protected TorqueToggle recording;
	
	private XMLEncoder writer;
	private XMLDecoder reader;
	private AutoMode currentMode;
	private boolean recordOn = false;
	private static InputRecorder instance;
	private static String fileName;
	
/* The file has to be inside of the roboRIO or else the program will not execute properly because it does not have
 * the file permissions needed to save it.
 */
	
	public InputRecorder(){
		init();
	}
	
	
	public void init(){
		fileName = AutoManager.getInstance().getFileName();
		recording = new TorqueToggle();
		HumanInput.getInstance().init();			//SEE IF THIS IS NECESSARY
		currentMode = new AutoMode();
		
	}
	
	
	public void update(){
		super.update();
		updateRecordingStatus();
		if(recording.get()){
				recordDrive();
		}
			
	}
	
	public void updateRecordingStatus() {
			recording.calc(driver.getAButton()); 
		//If possible, make this more than 1 button so it is harder to overwrite
		if(driver.getBButton()) {		     //something
			writeFile();
			System.out.println("Called Write File");
		}
	}
	
	public void recordDrive(){
		currentMode.addDBLeftSpeed((float)DB_leftSpeed);
		currentMode.addDBRightSpeed((float)DB_rightSpeed);
	}
	
	
	
	public void writeFile(){

	/*
		try {
			writer = new XMLEncoder(new FileOutputStream(fileName));
			writer.writeObject(currentMode);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
		try {
			writer = new XMLEncoder(new FileOutputStream(fileName));
			writer.writeObject(Drivebase.getInstance());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public static InputRecorder getInstance(){
		return instance == null ? instance = new InputRecorder() : instance;
	}
}
