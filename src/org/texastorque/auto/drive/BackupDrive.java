package org.texastorque.auto.drive;

import org.texastorque.auto.AutoCommand;

import edu.wpi.first.wpilibj.Timer;

public class BackupDrive extends AutoCommand {
	
	private double time;
	
	public BackupDrive(double time) {
		this.time = time;
	}

	@Override
	public void run() {
		double startTime = Timer.getFPGATimestamp();
		if (Timer.getFPGATimestamp() < startTime + time) {
			output.setDrivebaseSpeed(.65, .65);
		} else output.setDrivebaseSpeed(0.0, 0.0);
	}
	
	//when and why is this method used?
	@Override
	public void reset() {
		time = 0;
	}
}
