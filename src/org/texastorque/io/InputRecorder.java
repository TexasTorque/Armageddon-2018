package org.texastorque.io;

import java.util.ArrayList;
import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.texastorque.auto.AutoMode;

/* The purpose of this class is to read inputs from a controller for the same
 * length as the autonomous period, and then create an XML file from
 * which an autonomous mode can be read. This is the recording/writing class, 
 * AutoManager handles the reading.
 */
public class InputRecorder extends HumanInput{


	private ArrayList<Float> driveRecordingLeft;
	private ArrayList<Float> driveRecordingRight;
	
	private int cycleReduction;
	private XMLEncoder writer;
	
	private AutoMode currentMode;
	private static String fileName = "AutoMode1-3.xml";
	
	private static InputRecorder instance;
	
	public InputRecorder(){
		init();
	}
	
	
	public void init(){
		try {
			writer = new XMLEncoder(new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Created? Found? Not really sure");
		}
		
		driveRecordingLeft = new ArrayList<Float>();
		driveRecordingRight = new ArrayList<Float>();
	//still need to figure out where to init timer, should ideally start at 0? 
		//or do all timing by hand and it will work but kinda annoying
	}
	
	
	public void update(){
		super.update();
		System.out.println("I AM HERE I AM HERE I AM HERE");
		if(recording.get()){
			recordDrive();
			System.out.println("RECORDING RECORDING RECORDING AHHHHHHH");
	}
		if(recorded)
			writeFile();
		
	}
	
	public void recordDrive(){
		driveRecordingLeft.add((float) DB_leftSpeed);
		driveRecordingRight.add((float) DB_rightSpeed);
		System.out.println("recording the drive" + DB_leftSpeed);
		System.out.println("recording the drive" + DB_rightSpeed);

	}
	
	
	
	public void writeFile(){
		currentMode = new AutoMode(fileName);
		fillMode();
		writer.writeObject(currentMode);
		writer.close();
	}
	
	public void fillMode(){
		currentMode.setLeftSpeed(driveRecordingLeft);
		currentMode.setRightSpeed(driveRecordingRight);
	}
	
	public static InputRecorder getInstance(){
		return instance == null ? instance = new InputRecorder() : instance;
	}
}
