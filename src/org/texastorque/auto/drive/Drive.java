package org.texastorque.auto.drive;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutonomousCommand;
import org.texastorque.feedback.Feedback;
import org.texastorque.subsystems.Drivebase;

public class Drive extends AutonomousCommand {
	
	private final double D_CONSTANT = 0.056;
	private final double D_PRECISTION = 0.125;
	
	private double distance;
	private double precision;
	private double dTime = -999;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		distance = 0;
	}

}
