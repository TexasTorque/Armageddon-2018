package org.texastorque.subsystems;

public class Arm extends Subsystem {
	
	private static Arm instance;
	
	private boolean armUp;
	
	public static Arm getInstance() {
		return instance == null ? instance = new Arm() : instance;
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
		armUp = i.getArmUp();
		output();
	}
	
	public void output() {
		o.setArmUp(armUp);
	}

	@Override
	public void smartDashboard() {
		// TODO Auto-generated method stub

	}

}
