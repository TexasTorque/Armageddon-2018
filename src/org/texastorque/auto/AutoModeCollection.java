package org.texastorque.auto;

import java.util.ArrayList;

public class AutoModeCollection {

	public ArrayList<AutoMode> list;
	private static final String fileLocation = "/home/lvuser/list.xml";
	private static AutoModeCollection instance;
	
	public AutoModeCollection() {
		list = new ArrayList<AutoMode>();
	}
	
	public void addMode(AutoMode mode) {
		list.add(mode);
	}
	
	public static AutoModeCollection getInstance() {
		return instance;
	}
	
	public String getFileLocation() {
		return fileLocation;
	}
}
