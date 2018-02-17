package org.texastorque.io;

import org.texastorque.torquelib.component.TorqueMotor;

import org.texastorque.constants.Ports;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;

import org.texastorque.torquelib.util.TorqueMathUtil;


import edu.wpi.first.wpilibj.VictorSP;

import org.texastorque.constants.Constants;


public class RobotOutput {
	
	private TorqueMotor DB_leftFore;
	private TorqueMotor DB_leftRear;
	private TorqueMotor DB_rightFore;
	private TorqueMotor DB_rightRear;
	
	private TorqueMotor PT_motor;
	
	private TorqueMotor AM_right;
	private TorqueMotor AM_left;
	private DoubleSolenoid CL_sole;
	
	private Solenoid test3;

	private TorqueMotor IN_left;
	private TorqueMotor IN_right;

	private DoubleSolenoid IN_down;
	private DoubleSolenoid IN_out;
	
	private TorqueMotor PT_sole;

	private static boolean clockwise = true;	
	public static RobotOutput instance;


	public RobotOutput() {
		DB_leftFore = new TorqueMotor(new VictorSP(Ports.DB_LEFT_FORE_PORT), !clockwise);
		DB_leftRear = new TorqueMotor(new VictorSP(Ports.DB_LEFT_REAR_PORT), !clockwise);
		DB_rightFore = new TorqueMotor(new VictorSP(Ports.DB_RIGHT_FORE_PORT), clockwise);
		DB_rightRear = new TorqueMotor(new VictorSP(Ports.DB_RIGHT_REAR_PORT), clockwise);
		
		AM_right = 	   new TorqueMotor(new VictorSP(Ports.AM_RIGHT_PORT), clockwise);
		AM_left = 	   new TorqueMotor(new VictorSP(Ports.AM_LEFT_PORT), clockwise);

		CL_sole =      new DoubleSolenoid(Ports.PCM_PORT, Ports.CL_PORT_A, Ports.CL_PORT_B);
		IN_out = new DoubleSolenoid(Ports.PCM_PORT, Ports.IN_OUT_A, Ports.IN_OUT_B);
		IN_down = new DoubleSolenoid(Ports.PCM_PORT, Ports.IN_DOWN_A, Ports.IN_DOWN_B);
		
		IN_left = new TorqueMotor(new VictorSP(Ports.IN_LEFT), !clockwise);
		IN_right = new TorqueMotor(new VictorSP(Ports.IN_RIGHT), clockwise);
		
		PT_sole = new TorqueMotor(new VictorSP(Ports.PT_SOLE), clockwise);
		
	}
	
	public void setDrivebaseSpeed(double leftSpeed, double rightSpeed) {
		DB_leftFore.set(leftSpeed);
		DB_leftRear.set(leftSpeed);
		DB_rightFore.set(rightSpeed);
		DB_rightRear.set(rightSpeed);
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
		IN_right.set(speed);
    }
	
	public void setIntakePneumatics(boolean out, boolean down) {
		IN_out.set(out ? Value.kForward : Value.kReverse);
		IN_down.set(down ? Value.kForward : Value.kReverse);
	}
	
	public void setPivotSpeed(double speed) {
		PT_sole.set(speed);
	}

	
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}
}

