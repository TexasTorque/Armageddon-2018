package org.texastorque.subsystems;

import org.texastorque.feedback.Feedback;
import org.texastorque.torquelib.TorqueSubsystem;
import org.texastorque.io.Input;
import org.texastorque.io.RobotOutput;

public abstract class Subsystem implements TorqueSubsystem {

	protected Input i = Input.getInstance();
	protected RobotOutput o = RobotOutput.getInstance();
	protected Feedback f = Feedback.getInstance();

	public void setInput(Input i) {
		this.i = i;
	}
	
}
