package org.texastorque.auto.arm;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutoCommand;

import org.texastorque.feedback.Feedback;
import org.texastorque.subsystems.Pivot;

public class SetClaw extends AutoCommand {
	
	private boolean closed;
	
	public SetClaw(boolean closed) {
		this.closed = closed;
	}
	
	@Override
	public void run() {
		System.out.println("set claw");
		input.setClawClosed(closed);
	}

	@Override
	public void reset() {}
}
