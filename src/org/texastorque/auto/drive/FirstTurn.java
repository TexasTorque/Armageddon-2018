package org.texastorque.auto.drive;

import org.texastorque.auto.AutoCommand;
import org.texastorque.auto.AutoManager;
import org.texastorque.feedback.Feedback;
import org.texastorque.subsystems.Drivebase.DriveType;

public class FirstTurn extends AutoCommand{

	private boolean forward;
	
	public FirstTurn(boolean clockwise, boolean forward) {
		super(true);
		this.forward = forward;
		drivebase.setDriftDirection(clockwise);
	}

	@Override
	public void run() {
		Feedback.getInstance().resetDriveEncoders();
		if(forward)
			drivebase.setType(DriveType.AUTODRIFTFORWARD);
		else drivebase.setType(DriveType.AUTODRIFTBACKWARD);
		AutoManager.pause(6.5);
		
	}

	@Override
	public void reset() {
	
	}
}
