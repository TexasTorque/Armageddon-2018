package org.texastorque.auto;

import org.texastorque.auto.playback.PlaybackAutoMode;
import org.texastorque.util.FileUtils;

public class PlaybackAutoManager {

	private PlaybackAutoMode mode;
	
	public PlaybackAutoManager(String fileLocation){
		this.mode = FileUtils.readFromJSON(fileLocation, PlaybackAutoMode.class);
	}
}

