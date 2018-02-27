package org.texastorque.auto.arm;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutoCommand;

import org.texastorque.feedback.Feedback;
import org.texastorque.subsystems.Pivot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

import org.texastorque.subsystems.Arm;

public class ShiftPivotArm extends AutoCommand {
	
	private int setpointIndex;
	private double time;
	private double startingTime;
	private double delay;
	
	public ShiftPivotArm(int setpointIndex, double time, boolean pause, double delay) {
		super(pause);
		startingTime = Timer.getFPGATimestamp();
		this.setpointIndex = setpointIndex;
		this.time = time;
		Arm.getInstance().setDelay(delay);
		Pivot.getInstance().setDelay(delay);
	}
	
	@Override
	public void run() {
		System.out.println("shift pivot arm");
		input.setIntakeDown(false);
		
		input.setPTSetpoint(setpointIndex);
		input.setArmSetpoint(setpointIndex);
		if (pause)
			AutoManager.pause(time);
	}

	@Override
	public void reset() {
		setpointIndex = 0;
	}
}
