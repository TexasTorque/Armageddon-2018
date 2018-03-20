package org.texastorque.auto.arm;

import org.texastorque.auto.AutoCommand;

import org.texastorque.auto.AutoManager;
import org.texastorque.subsystems.Arm;
import org.texastorque.subsystems.Pivot;

import edu.wpi.first.wpilibj.Timer;

public class ShiftPivotArm extends AutoCommand {

	private int setpointIndex;
	private final double time;
	private final double startingTime;

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
		input.setIntakeDown(false);
		input.setIntakeOut(true);
		input.setPTSetpoint(setpointIndex);
		input.setArmSetpoint(setpointIndex);
		if (pause) {
			AutoManager.pause(time);
		}
	}

	@Override
	public void reset() {
		setpointIndex = 0;
	}
}
