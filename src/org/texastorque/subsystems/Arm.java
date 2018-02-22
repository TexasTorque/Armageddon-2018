package org.texastorque.subsystems;

import org.texastorque.feedback.Feedback;
import org.texastorque.torquelib.controlLoop.TorqueTMP;
import org.texastorque.torquelib.util.TorqueMathUtil;

public class Arm extends Subsystem {
	
	private double speed;
	private static Arm instance;
	private double setpoint;
	private double previousSetpoint;
	
	private TorqueTMP armTMP;
	
	public Arm() {
		setpoint = 0;
		previousSetpoint = 0;
		speed = 0d;
		armTMP = new TorqueTMP(0.3, 0.3);
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
		setpoint = i.getArmSetpoint();
		if(TorqueMathUtil.near(setpoint, f.getArmDistance(), 30)) {
			i.setArmSpeed(0);
		}
		else if(setpoint != previousSetpoint) {
			
			if(setpoint - previousSetpoint < 0) {
				i.setArmSpeed(-.75);
			} else i.setArmSpeed(.75);
		//	if(f.getPTAngle() < 60) {
		//		i.setArmSpeed(-.2);
		//	}
			if(Feedback.getInstance().getPTAngle() < 45)
				i.setArmSpeed(0);
			else previousSetpoint = setpoint;
		}
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