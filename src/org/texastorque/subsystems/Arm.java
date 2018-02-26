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
		if(currentAngle < 30) {
			setpoint = 350;
		}
		if((currentAngle >=20 && currentAngle < 60) || currentAngle > 130) {
			setpoint = 10;
		}
			
		if(TorqueMathUtil.near(setpoint, currentDistance, 20)){
			i.setArmSpeed(0);
		} else {
			i.setArmSpeed((1/Math.PI) * Math.atan(0.01 * (setpoint - currentDistance)));
	}	
			
			speed = i.getArmSpeed();
			output();
		
		/*setpoint = i.getArmSetpoint();
		
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
		
		if (setpoint != previousSetpoint) {
			if (TorqueMathUtil.near(setpoint, f.getArmDistance(), 30)) {
				i.setArmSpeed(0);
				previousSetpoint = setpoint;
			}
			else if (Feedback.getInstance().getPTAngle() < 45) {
				i.setArmSpeed(0);
			}
			else {
				i.setArmSpeed((2/Math.PI) * Math.atan(0.01 * (setpoint - f.getArmDistance())));
			}
		}
		speed = i.getArmSpeed();
		output();
		*/
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