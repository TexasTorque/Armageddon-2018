package org.texastorque.auto.arm;

import org.texastorque.auto.AutoCommand;
import org.texastorque.auto.AutoManager;
import org.texastorque.subsystems.WheelIntake;

import edu.wpi.first.wpilibj.Timer;

public class SetIntake extends AutoCommand {

	private boolean on;

	public SetIntake(boolean on) {
		super(false);
		this.on = on;
	}

	@Override
	public void run() {
		input.setIntakeDown(on);
		input.setIntakeOut(on);
		if(on) {
			input.startIntaking();
		} else input.stopSpinning();

	}

	@Override
	public void reset() {
	}
}
