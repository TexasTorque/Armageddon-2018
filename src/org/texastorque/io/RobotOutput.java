package org.texastorque.io;

import org.texastorque.torquelib.component.TorqueMotor;

import edu.wpi.first.wpilibj.VictorSP;
import org.texastorque.constants.Ports;
public class RobotOutput {
	
	private TorqueMotor DB_leftFore;
	private TorqueMotor DB_leftRear;
	private TorqueMotor DB_rightFore;
	private TorqueMotor DB_rightRear;
	
	private boolean clockwise = true;
	
	public static RobotOutput instance;

	public RobotOutput(){
		DB_leftFore = new TorqueMotor(new VictorSP(Ports.DB_LEFT_FORE_PORT), clockwise);
		DB_leftRear = new TorqueMotor(new VictorSP(Ports.DB_LEFT_REAR_PORT), clockwise);
		DB_rightFore = new TorqueMotor(new VictorSP(Ports.DB_RIGHT_FORE_PORT), !clockwise);
		DB_rightRear = new TorqueMotor(new VictorSP(Ports.DB_RIGHT_REAR_PORT), !clockwise);
		// etc etc
	}
	
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}

	
	public void setDrivebaseSpeed(double leftSpeed, double rightSpeed){
		DB_leftFore.set(leftSpeed);
		DB_leftRear.set(leftSpeed);
		DB_rightFore.set(rightSpeed);
		DB_rightRear.set(rightSpeed);
	}
}
