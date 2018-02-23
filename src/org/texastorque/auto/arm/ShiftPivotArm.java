package org.texastorque.auto.arm;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutoCommand;

import org.texastorque.feedback.Feedback;
import org.texastorque.subsystems.Pivot;
import org.texastorque.subsystems.Arm;

public class ShiftPivotArm extends AutoCommand {
	
	private int setpointIndex;
	private double time;
	
	public ShiftPivotArm(int setpointIndex, double time) {
		this.setpointIndex = setpointIndex;
		this.time = time;
	}
	
	@Override
	public void run() {
		Feedback.getInstance().resetEncoders();
		
		System.out.println("shift pivot arm");
		input.setPTSetpoint(setpointIndex);
		input.setArmSetpoint(setpointIndex);
		AutoManager.pause(time);
	}

	@Override
	public void reset() {
		setpointIndex = 0;
	}
}
