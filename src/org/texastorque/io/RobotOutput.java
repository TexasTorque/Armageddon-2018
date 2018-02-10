package org.texastorque.io;

import org.texastorque.torquelib.component.TorqueMotor;
import org.texastorque.constants.Ports;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import edu.wpi.first.wpilibj.VictorSP;
import org.texastorque.constants.Ports;

public class RobotOutput {
	
	private TorqueMotor DB_leftFore;
	private TorqueMotor DB_leftRear;
	private TorqueMotor DB_rightFore;
	private TorqueMotor DB_rightRear;
	
	private TorqueMotor PT_motor;
	
	private TorqueMotor AM_right;
	private TorqueMotor AM_left;
	private DoubleSolenoid CL_sole;

	private static boolean clockwise = true;
	
	public static RobotOutput instance;

	public RobotOutput() {
		DB_leftFore = new TorqueMotor(new VictorSP(Ports.DB_LEFT_FORE_PORT), !clockwise);
		DB_leftRear = new TorqueMotor(new VictorSP(Ports.DB_LEFT_REAR_PORT), !clockwise);
		DB_rightFore = new TorqueMotor(new VictorSP(Ports.DB_RIGHT_FORE_PORT), clockwise);
		DB_rightRear = new TorqueMotor(new VictorSP(Ports.DB_RIGHT_REAR_PORT), clockwise);
		
		PT_motor = new TorqueMotor(new VictorSP(Ports.PT_PORT), !clockwise);
		
		AM_left = new TorqueMotor(new VictorSP(Ports.AM_LEFT_PORT), !clockwise);
		AM_right = new TorqueMotor(new VictorSP(Ports.AM_RIGHT_PORT), clockwise);
		CL_sole = new DoubleSolenoid(Ports.CL_PORT_A, Ports.CL_PORT_B);
	}
	
	public void setDrivebaseSpeed(double leftSpeed, double rightSpeed) {
		DB_leftFore.set(leftSpeed);
		DB_leftRear.set(leftSpeed);
		DB_rightFore.set(rightSpeed);
		DB_rightRear.set(rightSpeed);
	}
	
	public void setPivotSpeed(double speed) {
		PT_motor.set(speed);
	}
	
	public void setArmSpeed(double speed) {
		AM_left.set(speed);
		AM_right.set(-speed);
	}
	
	public void setClaw(boolean closed) {
		CL_sole.set(closed ? Value.kForward : Value.kReverse);
	}
	
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}
}
