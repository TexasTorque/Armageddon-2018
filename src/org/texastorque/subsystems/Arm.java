package org.texastorque.subsystems;

public class Arm extends Subsystem {
	
	private double speed;
	private static Arm instance;
	
	public Arm() {
		speed = 0d;
	}
	
	
	@Override
	public void autoInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void teleopContinuous() {
		speed = i.getArmSpeed();
		output();
	}

	@Override
	public void smartDashboard() {
		// TODO Auto-generated method stub
		
	}
	
	public void output() {
		o.setArmSpeed(speed);
	}
	
	public static Arm getInstance() {
		return instance == null ? instance = new Arm() : instance;
	}

	
}
