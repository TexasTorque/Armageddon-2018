package org.texastorque.auto;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AutoManager {

	private AutoMode currentMode;
	private XMLDecoder reader;
	private String fileName;
	//retrieve input from SmartDashboard, use that to call proper xml file
	//readFile occurs here now
	
	public AutoManager(){
		fileName = "AutoMode" + "1-3" + ".xml";
		try {
			reader = new XMLDecoder(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File not found (this one I actually know");
		}
		readFile();
	}
	
	public void readFile(){
		currentMode = (AutoMode) reader.readObject();
		
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
}
