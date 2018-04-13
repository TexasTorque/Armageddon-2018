package org.texastorque.subsystems;

import org.texastorque.auto.playback.PlaybackAutoMode;

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
		if(autoType.equals(AutoType.RECORDING))
			recordingAutoContin();
		else commandAutoContin();
		output();
	}
	
	private void recordingAutoContin() {
//		speed = auto.getINSpeed();
//		out = auto.getINOut();
//		down = auto.getINDown();
		
	}
	
	private void commandAutoContin() {
		speed = i.getINSpeed();
		out = i.getINOut();
		down = i.getINDown();
	}
	
	@Override
	public void disabledContinuous() {

	}
	
	@Override
	public void teleopContinuous() {
		speed = i.getINSpeed();
		out = i.getINOut();
		down = i.getINDown();
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