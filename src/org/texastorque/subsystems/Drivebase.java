package org.texastorque.subsystems;

import org.texastorque.auto.AutoManager;
import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;
import org.texastorque.io.HumanInput;

import org.texastorque.torquelib.controlLoop.TorquePV;
import org.texastorque.torquelib.controlLoop.TorqueRIMP;
import org.texastorque.torquelib.controlLoop.TorqueTMP;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivebase extends Subsystem{

	private static Drivebase instance;
	
	private double leftSpeed;
	private double rightSpeed;
	
	private boolean highGear;
	
	private double setpoint = 0;
	private double previousSetpoint = 0;
	private double previousTime;
	private double precision;
	
	private TorqueTMP tmp;
	private TorquePV leftPV;
	private TorquePV rightPV;
	private TorqueRIMP leftRIMP;
	private TorqueRIMP rightRIMP;
	private double previousError;

	private double targetPosition;
	private double targetVelocity;
	private double targetAcceleration;

	private double turnPreviousSetpoint = 0;
	private double turnSetpoint;

	private TorqueTMP turnProfile;
	private TorquePV turnPV;

	private double targetAngle;
	private double targetAngularVelocity;
	
	public enum DriveType {
		TELEOP, AUTODRIVE, AUTOTURN, AUTOOVERRIDE, WAIT;
	}
	
	private DriveType type;
	
	public Drivebase() {
		init();
	}
	
	@Override
	public void autoInit() {
		init();
	}

	@Override
	public void teleopInit() {
		type = DriveType.TELEOP;
		init();
	}

	@Override
	public void disabledInit() {
		leftSpeed = 0;
		rightSpeed = 0;
	}
	
	private void init() {
		leftPV = new TorquePV();
		rightPV = new TorquePV();
		turnPV = new TorquePV();
		
		previousTime = Timer.getFPGATimestamp();
	}

	@Override
	public void disabledContinuous() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void autoContinuous() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void teleopContinuous() {
		switch (type) {
		case TELEOP:
			leftSpeed = i.getDB_leftSpeed();
			rightSpeed = i.getDB_rightSpeed();
			highGear = i.getDB_gearSole();
			break;
		}
		
		output();
	}
	
	public void output(){
		if (i instanceof HumanInput) {
			o.setHighGear(highGear);
		} 
		else {
			o.setHighGear(false);
		}
		o.setDrivebaseSpeed(leftSpeed, rightSpeed);
	}
	
	public void setType (DriveType type) {
		this.type = type;
	}

	@Override
	public void smartDashboard() {
		// TODO Auto-generated method stub
		
	}
	
	public static Drivebase getInstance(){
		return instance == null ? instance = new Drivebase() : instance;
	}
	
}
