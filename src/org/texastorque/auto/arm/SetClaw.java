package org.texastorque.auto.arm;

import org.texastorque.auto.AutoCommand;
import org.texastorque.auto.AutoManager;

public class SetClaw extends AutoCommand {

	public SetClaw(boolean pause) {
		super(pause);
	}

	@Override
	public void run() {
		input.toggleClaw();
	}

	public void run(double time) {
		input.toggleClaw();
		AutoManager.getInstance();
		AutoManager.pause(time);
	}

	@Override
	public void reset() {
	}
}
