package org.texastorque.auto.drive;


import org.texastorque.auto.AutoSequence;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Timer;

public class ForwardMode extends AutoSequence {
	
	private double time;

	public ForwardMode(double time) {
		this.time = time;
		init();
	}
	
	@Override
	public void init() {
		commandList.add(new BackupDrive(time));
	}
}
