package org.texastorque.subsystems;

import org.texastorque.auto.playback.PlaybackAutoMode;
import org.texastorque.feedback.Feedback;
import org.texastorque.torquelib.TorqueSubsystem;
import org.texastorque.io.Input;
import org.texastorque.io.RobotOutput;

public abstract class Subsystem implements TorqueSubsystem {

	public enum AutoType{
		RECORDING, COMMAND_SEQUENCE;
	}
	
	protected Input i = Input.getInstance();
	protected RobotOutput o = RobotOutput.getInstance();
	protected Feedback f = Feedback.getInstance();
	protected PlaybackAutoMode auto = PlaybackAutoMode.getInstance();
	protected AutoType type = AutoType.COMMAND_SEQUENCE;
	
	
	public void setInput(Input i) {
		this.i = i;
	}
	
	public void changeAutoType() {
		type = AutoType.RECORDING;
	}
	
}
