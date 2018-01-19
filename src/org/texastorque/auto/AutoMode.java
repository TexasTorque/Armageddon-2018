package org.texastorque.auto;

import java.util.ArrayList;

import org.texastorque.io.Input;
import org.texastorque.io.InputRecorder;
import org.texastorque.io.RobotOutput;

public class AutoMode extends Input{

	private ArrayList<Float> leftSpeeds;
	private ArrayList<Float> rightSpeeds;
	private ArrayList<Boolean> pneumaticValues;
	private int length = 7500;
	private int index = 0;
	private static RobotOutput o;
	//serialize in XML, need to figure out how to name things, might have to change
	//a string manually every time in order to create a new AutoMode?
	
	public AutoMode(String name){
		o = RobotOutput.getInstance();
		leftSpeeds = new ArrayList<Float>();
		rightSpeeds= new ArrayList<Float>();
		pneumaticValues = new ArrayList<Boolean>();
	}
	
	public void setLeftSpeed(ArrayList<Float> dbLeftValues){
		for(Float f: dbLeftValues){
			leftSpeeds.add(f);
		}
	}
	
	public void setRightSpeed(ArrayList<Float> dbRightValues){
		for(Float f: dbRightValues){
			rightSpeeds.add(f);
		}
	}
	
	public void setPneumatics(ArrayList<Boolean> pnValues){
		for(Boolean b: pnValues)
			pneumaticValues.add(b);
	}
	
	public void run(){
		while(index<length) //might need to be <=, also I really hope this timer works
			runDrive();     //wait wouldn't this just crunch the entire thing into 1.5 seconds
			index++;
	}
	
	public void runDrive(){
		o.setDrivebaseSpeed(leftSpeeds.get(index), rightSpeeds.get(index));
	}
}
