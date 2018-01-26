package org.texastorque.auto.drive;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutonomousCommand;
import org.texastorque.feedback.Feedback;
import org.texastorque.subsystems.Drivebase.DriveType;

public class Drive extends AutonomousCommand {
	
	private final double D_CONSTANT = 0.056;
	private final double D_PRECISION = 0.125;
	
	private double distance;
	private double precision;
	private double dTime = -999;
	
	public Drive(double distance) {
		this.distance = distance;
		precision = D_PRECISION;
	}
	
	public Drive(double distance, double precision) {
		this.distance = distance;
		this.precision = precision;
	}
	
	public Drive(double distance, double precision, double time) {
		this.distance = distance;
		this.precision = precision;
		dTime = time;
	}
	
	@Override
	public void run() {
		output.setHighGear(false);
		Feedback.getInstance().resetDB_gyro();
		Feedback.getInstance().resetDB_encoders();
		
		input.setDB_driveSetpoint(distance, precision);
		drivebase.setType(DriveType.AUTODRIVE);
		if(dTime != -999)
			AutoManager.pause(dTime);
		else
			AutoManager.pause(distance * D_CONSTANT);
	}

	@Override
	public void reset() {
		distance = 0;
	}

}
