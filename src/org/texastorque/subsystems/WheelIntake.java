package org.texastorque.subsystems;

public class WheelIntake extends Subsystem {
	
private static WheelIntake instance;
	
	private double speed = 0d;
	private boolean out = false;
	private boolean down = false;
	
	@Override
	public void autoInit() {
		init();
	}

	@Override
	public void teleopInit() {
		init();
	}
	
	@Override
	public void disabledInit() {
		
	}

	private void init() {
		speed = 0d;
	}
	
	@Override
	public void autoContinuous() {
		speed = auto.getINSpeed();
		out = auto.getINOut();
		down = auto.getINDown();
		output();
	}

	@Override
	public void teleopContinuous() {
		speed = i.getINSpeed();
		out = i.getINOut();
		down = i.getINDown();
		output();
	}
	
	@Override
	public void disabledContinuous() {
		output();
	}

	private void output() {
		o.setIntakeSpeed(speed);
		o.setIntakePneumatics(out, down);
	}
	
	@Override
	public void smartDashboard() {
	}
	
	public static WheelIntake getInstance() {
		return instance == null ? instance = new WheelIntake() : instance;
	}

}