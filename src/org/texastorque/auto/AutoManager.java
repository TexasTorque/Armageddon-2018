package org.texastorque.auto;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.texastorque.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoManager {
	
	private XMLDecoder reader;
	private String fileName;
	
	private static AutoManager instance;
	private static AutoMode modeInProgress;
	
	
	public AutoManager(){
		init();
	}
	
	public void init() {
		fileName = getFileName();
		try {
			reader = new XMLDecoder(new FileInputStream(fileName));
			modeInProgress = (AutoMode) reader.readObject();
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		
	}

	public String getFileName() {
		double autoSelection = SmartDashboard.getNumber("AutoMode", 0);
		String fieldConfiguration = DriverStation.getInstance().getGameSpecificMessage();
		String modeString;
		switch(fieldConfiguration) {
		case "RRR":
			modeString = "2";
			break;
		case "RLR":
			modeString = "4";
			break;
		case "LRL":
			modeString = "6";
			break;
		case "LLL":
			modeString = "8";
			break;
		default:
			modeString = "";
			break;
		}
		return "/home/lvuser/" + SmartDashboard.getNumber("AutoMode", 0) + modeString + ".xml";
		//either need to hard code the LLL/LRL/RLR/RRR field configuration when recording modes or 
		//create a switch that chooses the file based on the field configuration

	}

	
	public AutoMode getRunningMode() {
		return modeInProgress;
	}

	public void resetAuto() {
		fileName = getFileName();
	try {
		reader = new XMLDecoder(new FileInputStream(fileName));
		modeInProgress = (AutoMode) reader.readObject();
		reader.close();
	} catch (FileNotFoundException e) {
		System.out.println("File not found");
	}
	
		modeInProgress.resetIndex();
	}
	
	
	public static AutoManager getInstance() {
		return instance == null ? instance = new AutoManager() : instance;
	}
	
}