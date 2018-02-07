package org.texastorque.io;

import org.texastorque.torquelib.component.TorqueMotor;
<<<<<<< HEAD
import org.texastorque.constants.Ports;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
=======
import org.texastorque.torquelib.util.TorqueMathUtil;
>>>>>>> k

import edu.wpi.first.wpilibj.VictorSP;

import org.texastorque.constants.Constants;
import org.texastorque.constants.Ports;

public class RobotOutput {
	
	private TorqueMotor DB_leftFore;
	private TorqueMotor DB_leftRear;
	private TorqueMotor DB_rightFore;
	private TorqueMotor DB_rightRear;

	private TorqueMotor AM_right;
	private TorqueMotor AM_left;
	private DoubleSolenoid CL_sole;

	private static boolean clockwise = true;

	private TorqueMotor IN_upper;
	private TorqueMotor IN_lower;

	
	public static RobotOutput instance;

	public RobotOutput(){

		DB_leftFore = new TorqueMotor(new VictorSP(Ports.DB_LEFT_FORE_PORT), !clockwise);
		DB_leftRear = new TorqueMotor(new VictorSP(Ports.DB_LEFT_REAR_PORT), !clockwise);
		DB_rightFore = new TorqueMotor(new VictorSP(Ports.DB_RIGHT_FORE_PORT), clockwise);
		DB_rightRear = new TorqueMotor(new VictorSP(Ports.DB_RIGHT_REAR_PORT), clockwise);
		AM_right = 	   new TorqueMotor(new VictorSP(Ports.AM_RIGHT_PORT), clockwise);
		AM_left = 	   new TorqueMotor(new VictorSP(Ports.AM_LEFT_PORT), !clockwise);
		CL_sole =      new DoubleSolenoid(Ports.CL_PORT_A, Ports.CL_PORT_B);
		
		IN_upper = new TorqueMotor(new VictorSP(Ports.IN_LOWER), !clockwise);
		IN_lower = new TorqueMotor(new VictorSP(Ports.IN_UPPER), clockwise);
		

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
	

	public void setArmSpeed(double speed) {
		AM_left.set(speed);
		AM_right.set(-1*speed);
	}
	
	public void setClaw(boolean closed) {
		CL_sole.set(closed ? Value.kForward : Value.kReverse);
	}

	public void setIntakeSpeed(double upperSpeed, double lowerSpeed) {
		upperSpeed = TorqueMathUtil.constrain(upperSpeed, Constants.IN_LIMIT.getDouble());
		IN_upper.set(upperSpeed);
		IN_lower.set(lowerSpeed);
                                 	}

}

