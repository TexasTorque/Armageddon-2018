package org.texastorque.auto.sequences;


import org.texastorque.auto.AutoSequence;
import org.texastorque.auto.drive.BackupDrive;

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
