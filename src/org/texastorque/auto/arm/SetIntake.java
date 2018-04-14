package org.texastorque.auto.arm;

import org.texastorque.auto.AutoCommand;
import org.texastorque.auto.AutoManager;
import org.texastorque.subsystems.WheelIntake;

import edu.wpi.first.wpilibj.Timer;

public class SetIntake extends AutoCommand {

	private boolean on;
	private boolean down;

	public SetIntake(boolean on) {
		this.on = on;
		this.down = on;
	}
	public SetIntake(boolean on, boolean down) {
		super(false);
		this.on = on;
		this.down = down;
	}

	@Override
	public void run() {
		input.setIntakeDown(down);
		input.setIntakeOut(on);
		if(on) {
			input.startIntaking();
		} else input.stopSpinning();

	}

	@Override
	public void reset() {
	}
}
