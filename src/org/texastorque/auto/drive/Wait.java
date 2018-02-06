package org.texastorque.auto.drive;

import org.texastorque.auto.AutoCommand;
import org.texastorque.auto.AutoManager;
import org.texastorque.feedback.Feedback;
import org.texastorque.subsystems.Drivebase;
import org.texastorque.subsystems.Drivebase.DriveType;

public class Wait extends AutoCommand {
	private final double D_CONSTANT = 0.00926; //0.0744;
	private final double D_PRECISION = 0.125;
	
	private double distance;
	private double precision;
	private double time = -999;
	
	public Wait(double time) {
		this.distance = distance;
		this.precision = precision;
		this.time = time;
	}
	
	@Override
	public void run() {
		Drivebase.getInstance().setType(DriveType.WAIT);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}



}