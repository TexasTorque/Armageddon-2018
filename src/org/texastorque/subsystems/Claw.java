package org.texastorque.subsystems;

public class Claw extends Subsystem{

	private boolean closed;
	private static Claw instance;

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
		closed = i.getClawClosed();
		o.setClaw(closed);
	}

	@Override
	public void teleopContinuous() {
		closed = i.getClawClosed();
		o.setClaw(closed);
	}

	@Override
	public void smartDashboard() {
		// TODO Auto-generated method stub
		
	}
	
	public static Claw getInstance() {
		return instance == null ? instance = new Claw() : instance;
	}
	
}
