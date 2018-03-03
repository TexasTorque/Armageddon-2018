package org.texastorque.auto.drive;

import org.texastorque.auto.AutoCommand;
import org.texastorque.auto.AutoManager;
import org.texastorque.subsystems.Drivebase.DriveType;

import edu.wpi.first.wpilibj.Timer;

public class BackupDrive extends AutoCommand {
	
	private double time;
	
	public BackupDrive(double time, boolean pause) {
		super(pause);
		this.time = time;
	}

	@Override
	public void run() {
		drivebase.setType(DriveType.AUTOBACKUP);
		if(pause)
			AutoManager.pause(time);
	}
	
	//when and why is this method used?
	@Override
	public void reset() {
		time = 0;
	}
}
