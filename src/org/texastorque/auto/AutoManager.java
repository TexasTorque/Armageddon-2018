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
		fileName = getFileName();
		try {
			reader = new XMLDecoder(new FileInputStream(fileName));
			modeInProgress = (AutoMode) reader.readObject();
			
		//	System.out.println(modeInProgress.getDBLeftSpeeds.size());
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}

	public String getFileName() {
		return "/home/lvuser/"+SmartDashboard.getNumber("AutoMode", 0) 
						+ DriverStation.getInstance().getGameSpecificMessage() + ".xml";

	}

	
	public void modifyCurrentMode(){
		//add to the speeds in the drivebase arraylists to match the fields //measurements
	}
	
	public AutoMode getRunningMode() {
		return modeInProgress;
	}

	
	public static AutoManager getInstance() {
		return instance == null ? instance = new AutoManager() : instance;
	}
	
}