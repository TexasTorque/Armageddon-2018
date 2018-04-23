package org.texastorque.auto.arm;

import org.texastorque.auto.AutoCommand;
import org.texastorque.auto.AutoManager;

public class SetClaw extends AutoCommand {

	private boolean open;

	public SetClaw(boolean open, boolean pause) {
		super(pause);
		this.open = open;		
		if(pause)
			AutoManager.pause(.5);

	}

	@Override
	public void run() {
		input.setClaw(open);
	}

	@Override
	public void reset() {
	}
}
