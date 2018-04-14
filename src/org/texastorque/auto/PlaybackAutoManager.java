package org.texastorque.auto;

import org.texastorque.auto.playback.PlaybackAutoMode;
import org.texastorque.util.FileUtils;

import edu.wpi.first.wpilibj.DriverStation;

public class PlaybackAutoManager {

	private static PlaybackAutoMode mode;
	private static PlaybackAutoManager instance;
	private static String fileLocation;
	
	public PlaybackAutoManager(){
		fileLocation = "recording" /*DriverStation.getInstance().getGameSpecificMessage()*/ + "null.json";
//		this.mode = FileUtils.readFromJSON(fileLocation, PlaybackAutoMode.class);
	}
	
	public PlaybackAutoMode getMode() {
		return mode;
	}
	
	public static PlaybackAutoManager getInstance() {
		return instance == null ? instance = new PlaybackAutoManager() : instance;
	}
}

