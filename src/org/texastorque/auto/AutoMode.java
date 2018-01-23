package org.texastorque.auto;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.texastorque.io.Input;
import org.texastorque.io.InputRecorder;
import org.texastorque.io.RobotOutput;

public class AutoMode extends Input{

	XMLDecoder reader;
	private ArrayList<Float> DB_leftSpeeds;
	private ArrayList<Float> DB_rightSpeeds;
	private ArrayList<Boolean> pneumaticValues;
	private static RobotOutput o;
	//serialize in XML, need to figure out how to name things, might have to change
	//a string manually every time in order to create a new AutoMode?
	
	public AutoMode(String name){
		try {
			reader = new XMLDecoder(new FileInputStream(name));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		o = RobotOutput.getInstance();
		DB_leftSpeeds = new ArrayList<Float>();
		DB_rightSpeeds= new ArrayList<Float>();
		pneumaticValues = new ArrayList<Boolean>();
	}
	
	public void addDBLeftSpeed(float value) {
		DB_leftSpeeds.add(value);
	}
	
	public void addDBRightSpeed(float value) {
		DB_rightSpeeds.add(value);
	}
	
	
	public void run(){
		for(int x = 0; x < DB_rightSpeeds.size(); x++) { //might need to make the index static or else it will
			runDrive(x);								 //always call the first number
		}
	}
	
	/*
	 * runDrive puts the values in the ArrayList to the motors then takes those values out of the ArrayList
	 * This is necessary because it prevents the loop in the other file from calling the first value endlessly
	 */
	public void runDrive(int index){
		o.setDrivebaseSpeed(DB_leftSpeeds.get(index), DB_rightSpeeds.get(index));
		DB_leftSpeeds.remove(0);
		DB_rightSpeeds.remove(0);
	}
}
