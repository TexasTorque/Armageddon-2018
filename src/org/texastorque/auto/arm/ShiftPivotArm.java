package org.texastorque.auto.arm;

import org.texastorque.auto.AutoCommand;
import org.texastorque.auto.AutoManager;
import org.texastorque.subsystems.Arm;
import org.texastorque.subsystems.Pivot;

import edu.wpi.first.wpilibj.Timer;

public class ShiftPivotArm extends AutoCommand {

	private int setpointIndex;
	private final double time;
	private double delay;
	
	public ShiftPivotArm(int setpointIndex, double time, boolean pause, double delay) {
		super(pause);
		this.setpointIndex = setpointIndex;
		this.time = time;
		this.delay = delay;
	}

	@Override
	public void run() {
		input.setPTSetpoint(setpointIndex);
		input.setArmSetpoint(setpointIndex);		
		Arm.getInstance().setDelay(delay);
		Pivot.getInstance().setDelay(delay);

		if (pause) {
			AutoManager.pause(time);
		}
	}

	@Override
	public void reset() {
		setpointIndex = 0;
	}
}
