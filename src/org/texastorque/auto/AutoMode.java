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

	public double[] DB_leftSpeeds;
	public double[] DB_rightSpeeds;
	private static RobotOutput o;
	private int index;
	
	public AutoMode(){
		o = RobotOutput.getInstance();
		DB_leftSpeeds = new double[1500];
		DB_rightSpeeds= new double[1500];
		index = 0;
	}
	
	public void setDBLeftSpeed(int index, double value) {
		DB_leftSpeeds[index] = value;
	}
	
	public void setDBRightSpeed(int index, double value) {
		DB_rightSpeeds[index] = value;
	}
	
	public void run(){
		if(index < 1500) {
			runDrive(index);
			index++;
		}
	}
	
	public void resetIndex() {
		index = 0;
	}
	
	public void runDrive(int index){
		o.setDrivebaseSpeed(DB_leftSpeeds[index], DB_rightSpeeds[index]);
	}
	

	public void tuneMode(){
		
		
		
		
		//interpolate like Lubecki said, use encoders to make things line up better like Mr. Rip suggested
	}
	

	/*
	 * runDrive puts the values in the ArrayList to the motors then takes those values out of the ArrayList
	 * This is necessary because it prevents the loop in the other file from calling the first value endlessly
	 */
}
