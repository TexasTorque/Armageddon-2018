package org.texastorque.auto.drive;

import org.texastorque.auto.AutoCommand;
import org.texastorque.auto.AutoManager;
import org.texastorque.feedback.Feedback;
import org.texastorque.subsystems.Drivebase.DriveType;

public class FirstTurnDrift extends AutoCommand{


	public FirstTurnDrift(boolean left, boolean forward) {
		super(true);
		drivebase.setDriftDirection(left, forward);
	}

	@Override
	public void run() {
		Feedback.getInstance().resetDriveEncoders();
		drivebase.setType(DriveType.AUTODRIFT);
		AutoManager.pause(5);
		
	}

	@Override
	public void reset() {
	
	}
}
