package org.texastorque.subsystems;

import edu.wpi.first.wpilibj.Timer;

public class WheelIntake extends Subsystem {

	private static volatile WheelIntake instance;

	private double speed = 0d;
	private boolean out = false;
	private boolean down = false;
	private double delay;
	private double delayStartTime;
	
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
		if (delayStartTime + delay < Timer.getFPGATimestamp()) {
			speed = i.getINSpeed();
		
			out = i.getINOut();
			down = i.getINDown();
			output();
		}
	}

	public void teleopSetDelay(double time) {
		delayStartTime = Timer.getFPGATimestamp();
		delay = time;
	}

	private void output() {
		o.setIntakeSpeed(speed);
		o.setIntakePneumatics(out, down);
	}

	@Override
	public void smartDashboard() {
	}

	public static synchronized WheelIntake getInstance() {
		return instance == null ? instance = new WheelIntake() : instance;
	}
}