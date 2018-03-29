package org.texastorque.auto.arm;

import org.texastorque.auto.AutoCommand;
import org.texastorque.auto.AutoManager;

public class SetClaw extends AutoCommand {

	private boolean open;

	public SetClaw(boolean open, boolean pause) {
		super(pause);
		this.open = open;
	}

	@Override
	public void run() {
		input.setClaw(open);
	}

	public void run(double time) {
		input.setClaw(open);
		AutoManager.getInstance();
		AutoManager.pause(time);
	}

	@Override
	public void reset() {
	}
}
