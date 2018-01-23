package org.texastorque.auto;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.texastorque.subsystems.Drivebase;

import edu.wpi.first.wpilibj.DriverStation;

public class AutoManager {

	private static AutoManager instance;
	private Drivebase modeInProgress;
	private XMLDecoder reader;
	private String fileName;
	//retrieve input from SmartDashboard, use that to call proper xml file
	//readFile occurs here now
	
	public AutoManager(){
		fileName = "/home/lvuser/fooblah.xml";
		try {
			reader = new XMLDecoder(new FileInputStream(fileName));
			modeInProgress = (Drivebase) reader.readObject();
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found (this one I actually know");
		}
		readFile();
	}
	
	public void readFile(){
		
		/* In order to read file, create an ArrayList of the ArrayLists and read
		 * from the .dat until it reaches end, move to next
		 * 
		 * Or use xml...pretty sure we're using xml
		 * 
		 */
	}
	
	public void modifyCurrentMode(){
		//add to the speeds in the drivebase arraylists to match the fields 
		//measurements
		//or maybe we'll have vision and it won't matter
	}
	
	public Drivebase getRunningMode() {
		return modeInProgress;
	}
	public void setAutoModeName(String name) {
		fileName = name;
	}
	
	public static AutoManager getInstance() {
		return instance == null ? instance = new AutoManager() : instance;
	}
	
}
