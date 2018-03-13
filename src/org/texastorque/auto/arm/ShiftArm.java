package org.texastorque.auto.arm;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutoCommand;

import org.texastorque.feedback.Feedback;

public class ShiftArm extends AutoCommand {
	
	private int setpointIndex;
	private double time;
	
	public ShiftArm(int setpointIndex, int time) {
		this.setpointIndex = setpointIndex;
		this.time = time;
	}
	
	@Override
	public void run() {
		Feedback.getInstance().resetArmEncoders();
		
		input.setArmSetpoint(setpointIndex);
		AutoManager.pause(time);
	}

	@Override
	public void reset() {
		setpointIndex = 0;
	}
	
}
