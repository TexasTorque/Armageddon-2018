package org.texastorque.auto;

import org.texastorque.io.Input;
import org.texastorque.io.RobotOutput;
import org.texastorque.feedback.Feedback;

import org.texastorque.subsystems.Drivebase;

public abstract class AutonomousCommand {
	protected RobotOutput output;
	protected Input input;
	protected Feedback feedback;
	
	protected Drivebase drivebase;
	
	public AutonomousCommand() {
		init();
	}
	
	public void init() {
		output = RobotOutput.getInstance();
		input = Input.getInstance();
		feedback = Feedback.getInstance();
		
		drivebase = Drivebase.getInstance();
	}
	
	public abstract void run();
	
	public abstract void reset();
}
