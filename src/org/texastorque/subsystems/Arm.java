package org.texastorque.subsystems;

import org.texastorque.feedback.Feedback;
import org.texastorque.torquelib.controlLoop.TorqueTMP;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.Timer;

public class Arm extends Subsystem {
	
	private static Arm instance;
	
	private double speed;
	private double setpoint;
	private double previousSetpoint;
	private double currentDistance;
	private double currentAngle;
	private double autoStartTime;
	private double delay;
	
	private TorqueTMP armTMP;
	
	public Arm() {
		setpoint = 0;
		previousSetpoint = 0;
		speed = 0d;
		armTMP = new TorqueTMP(0.3, 0.3);
	}
	
	
	@Override
	public void autoInit() {
		autoStartTime = Timer.getFPGATimestamp();
		delay = 0;
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
		if(autoStartTime + delay < Timer.getFPGATimestamp()) {
			setpoint = i.getArmSetpoint();
			currentDistance = f.getArmDistance();
			currentAngle = f.getPTAngle();
			if(currentAngle < 30) {
				setpoint = currentDistance;
			}
			if((currentAngle >=20 && currentAngle < 45) || currentAngle > 130) {
				setpoint = 10;
			}
				
			if(TorqueMathUtil.near(setpoint, currentDistance, 20)){
				i.setArmSpeed(0);
			} else {
				i.setArmSpeed((2/Math.PI) * Math.atan(0.01 * (setpoint - currentDistance)));
			}	
				
			speed = i.getArmSpeed();
			output();
		}		
	}

	@Override
	public void teleopContinuous() {
		setpoint = i.getArmSetpoint();
		currentDistance = f.getArmDistance();
		currentAngle = f.getPTAngle();
		
		if((currentAngle >=35 && currentAngle < 80) || currentAngle > 110 || 
				(currentAngle < 80 && i.getPTSetpoint() > 1)) {
			setpoint = 10;
		}
		if(i.getPickingUp()) {
			setpoint = 350;
		}
		if(TorqueMathUtil.near(setpoint, currentDistance, 12)){
			i.setArmSpeed(0);
		} else {
			i.setArmSpeed((1/Math.PI) * Math.atan(0.01 * (setpoint - currentDistance)));
		}
		
		if(i.getClimbing()){
			speed = .15;
		} else 
			speed = i.getArmSpeed();
			
		output();
	}
	
	public void setDelay(double time) {
		delay = time;
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