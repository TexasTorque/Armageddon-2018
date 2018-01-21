package org.texastorque.io;

import java.util.ArrayList;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.texastorque.auto.AutoMode;
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
	private static String fileName = "InputRecorderTest.xml";
	
	private static InputRecorder instance;
	
	public InputRecorder(){
		init();
	}
	
	
	public void init(){

		recording = new TorqueToggle();
		HumanInput.getInstance().init();			//SEE IF THIS IS NECESSARY
		currentMode = new AutoMode(fileName);
		
	}
	
	
	public void update(){
	
		updateRecordingStatus();
		if(recording.get()){
			if(Math.abs(DB_leftSpeed) > .1) {
				recordDrive();
			}
			
		}
			
		
	}
	
	public void updateRecordingStatus() {
		recording.calc(driver.getAButton());
		if(driver.getBButton()) {
			writeFile();
			System.out.println("Called Write File");
		}
	}
	
	public void recordDrive(){
		
		
	}
	
	
	
	public void writeFile(){
	
		try {
			writer = new XMLEncoder(new FileOutputStream(fileName));
			writer.writeObject("i just want something to work.");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static InputRecorder getInstance(){
		return instance == null ? instance = new InputRecorder() : instance;
	}
}
