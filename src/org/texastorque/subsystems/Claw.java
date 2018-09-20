package org.texastorque.subsystems;

public class Claw extends Subsystem{

	private boolean closed;
	private static volatile Claw instance;

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
		if(autoType.equals(AutoType.RECORDING))
			recordingAutoContin();
		else commandAutoContin();
		o.setClaw(closed);
	}
	
	private void recordingAutoContin() {
	//	closed = auto.getClawClosed();
		
	}
	
	private void commandAutoContin() {
		closed = i.getClawClosed();
	}

	@Override
	public void teleopContinuous() {
		closed = i.getClawClosed();
		o.setClaw(closed);
	}

	@Override
	public void smartDashboard() {
	}

	public static synchronized Claw getInstance() {
		return instance == null ? instance = new Claw() : instance;
	}

}
