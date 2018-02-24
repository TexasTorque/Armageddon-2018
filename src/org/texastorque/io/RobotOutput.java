package org.texastorque.io;

import org.texastorque.torquelib.component.TorqueMotor;

import org.texastorque.constants.Ports;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.VictorSP;

import org.texastorque.constants.Constants;
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

	private TorqueMotor IN_left;
	private TorqueMotor IN_right;
	private DoubleSolenoid IN_leftDown;
	private DoubleSolenoid IN_rightDown;
	private DoubleSolenoid IN_out;

	private static boolean clockwise = true;	
	public static RobotOutput instance;


	public RobotOutput() {
		DB_leftFore = new TorqueMotor(new VictorSP(Ports.DB_LEFT_FORE_MOTOR), !clockwise);
		DB_leftRear = new TorqueMotor(new VictorSP(Ports.DB_LEFT_REAR_MOTOR), !clockwise);
		DB_rightFore = new TorqueMotor(new VictorSP(Ports.DB_RIGHT_FORE_MOTOR), clockwise);
		DB_rightRear = new TorqueMotor(new VictorSP(Ports.DB_RIGHT_REAR_MOTOR), clockwise);
		
		PT_motor = new TorqueMotor(new VictorSP(Ports.PT_PORT), !clockwise);
		
		IN_left = new TorqueMotor(new VictorSP(Ports.IN_LEFT_MOTOR), !clockwise);
		IN_right = new TorqueMotor(new VictorSP(Ports.IN_RIGHT_MOTOR), clockwise);
		IN_leftDown = new DoubleSolenoid(2, Ports.IN_LEFT_DOWN_SOLE_A, Ports.IN_LEFT_DOWN_SOLE_B);
		IN_rightDown = new DoubleSolenoid(2, Ports.IN_RIGHT_DOWN_SOLE_A, Ports.IN_RIGHT_DOWN_SOLE_B);
		IN_out = new DoubleSolenoid(2, Ports.IN_OUT_SOLE_A, Ports.IN_OUT_SOLE_B);
		
		AM_right = new TorqueMotor(new VictorSP(Ports.AM_RIGHT_MOTOR), clockwise);
		AM_left = new TorqueMotor(new VictorSP(Ports.AM_LEFT_MOTOR), clockwise);
		
		CL_sole = new DoubleSolenoid(2, Ports.CL_SOLE_A, Ports.CL_SOLE_B);
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

	public void setIntakeSpeed(double speed) {
		IN_left.set(speed);
		IN_right.set(-speed);
    }
	
	public void setIntakePneumatics(boolean out, boolean down) {
		IN_out.set(out ? Value.kForward : Value.kReverse);
		IN_leftDown.set(down ? Value.kForward : Value.kReverse);
		IN_rightDown.set(down ? Value.kForward : Value.kReverse);
	}
	
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}
}

