package org.texastorque.subsystems;

import org.texastorque.auto.playback.PlaybackAutoMode;
import org.texastorque.subsystems.Subsystem.AutoType;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.Timer;

public class Arm extends Subsystem {

	private static Arm instance;

	private double speed;
	private double setpoint;
	private double currentDistance;
	private double currentAngle;
	private double autoStartTime;
	private double delay;

	private double delayStartTime;

	private Arm() {
		setpoint = 0;
		speed = 0d;
	}

	@Override
	public void autoInit() {
		autoStartTime = Timer.getFPGATimestamp();
		delay = 0;
	}

	@Override
	public void teleopInit() {
		setpoint = f.getArmDistance();
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
		output();
	}
	
	private void recordingAutoContin() {
		setpoint = auto.getArmSetpoint();
		currentDistance = f.getArmDistance();
		currentAngle = f.getPTAngle();
		
		//if((currentAngle < 55 && auto.getPTSetpoint() > 60) || (currentAngle > 100))/* && i.getPTSetpoint() < 275)*/ {
		//	setpoint = currentDistance;
		//}

		if((currentAngle < 35 && auto.getPTSetpoint() > 40) || (currentAngle > 100))/* && i.getPTSetpoint() < 275)*/ {
			setpoint = currentDistance;
		}
		
		if(TorqueMathUtil.near(setpoint, currentDistance, 12)){
			auto.setArmSpeed(0);
		} else {
			auto.setArmSpeed((1.5/Math.PI) * Math.atan(0.01 * (setpoint - currentDistance)));
		}
		if(!f.getBlockade()) {
			if(auto.getArmSpeed() > 0) {
				System.out.println("gfndsjk");
				auto.setArmSpeed(0);
			}
			
		}
		speed = auto.getArmSpeed();
	
	}
	
	private void commandAutoContin() {
		if(autoStartTime + delay < Timer.getFPGATimestamp()) {
			setpoint = i.getArmSetpoint();
			currentDistance = f.getArmDistance();
			currentAngle = f.getPTAngle();
			if((currentAngle < 175 && i.getPTSetpoint() > 177) || (i.getPTSetpoint() > 250 && currentAngle < 248)) {
				setpoint = currentDistance;
			}
				i.setArmSpeed((2.0 / Math.PI) * Math.atan(0.06 * (setpoint - currentDistance)));
			}

			speed = i.getArmSpeed();
			output();
		
	}
	

	@Override
	public void teleopContinuous() {
		if (i.getEncodersDead()) {
			handleDeadEncoders();
		} else if (delayStartTime + delay < Timer.getFPGATimestamp()) {
			handleWorkingEncoders();
		}

		output();
	}

	private void handleDeadEncoders() {
		if (i.getArmForward()) {
			speed = .3;
		} else if (i.getArmBack()) {
			speed = -.3;
		} else {
			speed = 0;
		}
	}

	private void handleWorkingEncoders() {
		setpoint = i.getArmSetpoint();
		currentDistance = f.getArmDistance();
		currentAngle = f.getPTAngle();

		
		if((currentAngle < 160 && i.getPTSetpoint() > 162) || (i.getPTSetpoint() > 250 && currentAngle < 248)) {
			setpoint = currentDistance;
		}
				
		if (TorqueMathUtil.near(setpoint, currentDistance, 12)) {
			i.setArmSpeed(0);
		} else {
			i.setArmSpeed((2.0/Math.PI) * Math.atan(0.04 * (setpoint - currentDistance)));
		}

		if (!f.getBlockade() && i.getArmSpeed() > 0) {
			System.out.println("gfndsjk");
			i.setArmSpeed(0);
		}

		if (i.getClimbing()) {
			speed = -1;
		} else {
			speed = i.getArmSpeed();
		}
	}

	public void setDelay(double time) {
		autoStartTime = Timer.getFPGATimestamp();
		delay = time;
		System.out.println(autoStartTime + "AST" + delay + "DLY" + Timer.getFPGATimestamp() + "TMR");
	}

	public void teleopSetDelay(double time) {
		delayStartTime = Timer.getFPGATimestamp();
		delay = time;
	}

	@Override
	public void smartDashboard() {
	}

	public void output() {
		o.setArmSpeed(speed);
	}

	
	public static Arm getInstance() {
		return instance == null ? instance = new Arm() : instance;
	}
}