package org.texastorque.subsystems;

import org.texastorque.io.HumanInput;
import org.texastorque.io.RobotOutput;

public class WheelIntake extends Subsystem {
	
private static WheelIntake instance;
	
	private double leftSpeed = 0d;
	private double rightSpeed = 0d;
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
		leftSpeed = 0;
		rightSpeed = 0;
	}

	private void init() {
	}
	
	@Override
	public void autoContinuous() {
//		run();
	}

	@Override
	public void teleopContinuous() {
		run();
	}
	
	@Override
	public void disabledContinuous() {
		output();
	}

	private void run() {
		leftSpeed = i.getINLowerSpeed();
		rightSpeed = i.getINUpperSpeed();
		out = 
		output();
	}
	
	private void output() {
		if(i instanceof HumanInput) {
			//if(i.getVI_rpmsGood()) {
				RobotOutput.getInstance().setIntakeSpeed(1, .3);
			} else {
				RobotOutput.getInstance().setIntakeSpeed(rightSpeed, leftSpeed);
			}
		}
	//}
	
	@Override
	public void smartDashboard() {
	}
	
	public static WheelIntake getInstance() {
		return instance == null ? instance = new WheelIntake() : instance;
	}

}