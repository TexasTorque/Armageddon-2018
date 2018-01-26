package org.texastorque.auto;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.time.LocalDateTime;

import org.texastorque.io.Input;
import org.texastorque.io.InputRecorder;
import org.texastorque.io.RobotOutput;

public class AutoMode extends Input{

	private ArrayList<Double> DB_leftSpeeds;
	private ArrayList<Double> DB_rightSpeeds;
	private ArrayList<Boolean> pneumaticValues;
	public int trash = 5;
	private static RobotOutput o;
	//serialize in XML, need to figure out how to name things, might have to change
	//a string manually every time in order to create a new AutoMode?
	
	public AutoMode(){
		o = RobotOutput.getInstance();
		DB_leftSpeeds = new ArrayList<Double>();
		DB_rightSpeeds= new ArrayList<Double>();
		pneumaticValues = new ArrayList<Boolean>();
	}
	
	public void addDBLeftSpeed(double value) {
		DB_leftSpeeds.add(value);
	}
	
	public void addDBRightSpeed(double value) {
		DB_rightSpeeds.add(value);
	}
	
	public ArrayList<Double> getDBLeftSpeeds(){
		return DB_leftSpeeds;
	}
	public void run(){
		System.out.println(DB_leftSpeeds.size());
	}
	
	public void runDrive(int index){
		o.setDrivebaseSpeed(DB_leftSpeeds.get(index), DB_rightSpeeds.get(index));
	}

	/*
	 * runDrive puts the values in the ArrayList to the motors then takes those values out of the ArrayList
	 * This is necessary because it prevents the loop in the other file from calling the first value endlessly
	 */
}
