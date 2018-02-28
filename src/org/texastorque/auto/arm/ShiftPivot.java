package org.texastorque.auto.arm;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutoCommand;

import org.texastorque.feedback.Feedback;
import org.texastorque.subsystems.Arm;

public class ShiftPivot extends AutoCommand {

	private int setpointIndex;
	private double time;
	
	public ShiftPivot(int setpointIndex, int time) {
		this.setpointIndex = setpointIndex;
		this.time = time;
	}
	
	@Override
	public void run() {
		Feedback.getInstance().resetEncoders();
		
		input.setPTSetpoint(setpointIndex);
		AutoManager.pause(time);
	}

	@Override
	public void reset() {
		setpointIndex = 0;
	}
}
