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

	private double[] DB_leftSpeeds;
	private double[] DB_rightSpeeds;
	private static RobotOutput o;
	private int index2=0;
	//serialize in XML, need to figure out how to name things, might have to change
	//a string manually every time in order to create a new AutoMode?
	
	public AutoMode(){
		o = RobotOutput.getInstance();
		DB_leftSpeeds = new double[1500];
		DB_rightSpeeds= new double[1500];
	}
	
	public void setDBLeftSpeed(int index, double value) {
		DB_leftSpeeds[index] = value;
	}
	
	public void setDBRightSpeed(int index, double value) {
		DB_rightSpeeds[index] = value;
	}
	
	public double[] getDBLeftSpeeds(){
		return DB_leftSpeeds;
	}
	
	public double[] getDBRightSpeeds() {
		return DB_rightSpeeds;
	}
	
	public void run(){
		index2++;
		System.out.println(DB_leftSpeeds[index2]);
	}
	
	public void runDrive(int index){
		o.setDrivebaseSpeed(DB_leftSpeeds[index], DB_rightSpeeds[index]);
	}

	/*
	 * runDrive puts the values in the ArrayList to the motors then takes those values out of the ArrayList
	 * This is necessary because it prevents the loop in the other file from calling the first value endlessly
	 */
}
