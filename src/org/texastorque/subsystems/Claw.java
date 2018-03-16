package org.texastorque.subsystems;

public class Claw extends Subsystem {

	private boolean closed;
	private static Claw instance;

	@Override
	public void autoInit() {
	}

	@Override
	public void teleopInit() {
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledContinuous() {
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
	}

	public static Claw getInstance() {
		return instance == null ? instance = new Claw() : instance;
	}

}
