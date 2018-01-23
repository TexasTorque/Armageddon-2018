package org.texastorque.subsystems;


public class Drivebase extends Subsystem{

	private static Drivebase instance;
	
	private double leftSpeed;
	private double rightSpeed;
	
	@Override
	public void autoInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void teleopInit() {
		
		
	}

	@Override
	public void disabledInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabledContinuous() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void autoContinuous() {
		
	}

	@Override
	public void teleopContinuous() {
		leftSpeed = i.getLeftSpeed();
		rightSpeed = i.getRightSpeed();
		output();
	}
	
	public void output(){
		o.setDrivebaseSpeed(leftSpeed, rightSpeed);
	}
	
	public static Drivebase getInstance(){
		return instance == null ? instance = new Drivebase() : instance;
	}

	@Override
	public void smartDashboard() {
		// TODO Auto-generated method stub
		
	}

	
}
