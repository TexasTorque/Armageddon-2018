package org.texastorque.auto.arm;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutoCommand;

import org.texastorque.feedback.Feedback;
import org.texastorque.subsystems.Pivot;

public class SetClaw extends AutoCommand {
	
	
	public SetClaw(boolean pause) {
		super(pause);
	}
	
	@Override
	public void run() {
		System.out.println("set claw");
		input.toggleClaw();
	}

	@Override
	public void reset() {}
}
