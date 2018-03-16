package org.texastorque.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pivot extends Subsystem {

	private static Pivot instance;

	private double speed;

	private double setpoint = 0;
	private double previousSetpoint = 0;

	private double autoStartTime;
	private double delay;

	private double currentAngle;
	private double currentArmSetpoint;
	private double currentArmDistance;

	private double delayStartTime = 0;

	private Pivot() {
		delay = 0;
	}

	@Override
	public void autoInit() {
		autoStartTime = Timer.getFPGATimestamp();
	}

	@Override
	public void teleopInit() {
		if (f.getPTAngle() > 30) {
			setpoint = 77;
		}
	}

	@Override
	public void disabledInit() {
		speed = 0;
	}

	@Override
	public void disabledContinuous() {
	}

	@Override
	public void autoContinuous() {
		if (hasTimeToRun(true)) {
			runPivot();
		}
	}

	@Override
	public void teleopContinuous() {
		if (i.getEncodersDead()) {
			runPivotBackup();
		} else if (hasTimeToRun(false)) {
			runPivot();
		}
	}

	private void runPivot() {
		setpoint = i.getPTSetpoint();
		currentAngle = f.getPTAngle();
		currentArmSetpoint = i.getArmSetpoint();
		currentArmDistance = f.getArmDistance();

		if (setpoint != previousSetpoint) {
			if (currentArmSetpoint < 400 && currentArmDistance > 400) {
				setpoint = currentAngle;
			}

			previousSetpoint = setpoint;
		}

		speed = (1.5 / Math.PI) * Math.atan(0.03 * (setpoint - currentAngle));
		output();
	}

	public void runPivotBackup() {
		if (i.getPivotCCW()) {
			speed = -.2;
		} else if (i.getPivotCW()) {
			speed = .2;
		}
	}

	private void output() {
		o.setPivotSpeed(speed);
	}

	public double getSpeed() {
		return speed;
	}

	public void setDelay(double time) {
		delay = time;
	}

	public void teleopSetDelay(double time) {
		delayStartTime = Timer.getFPGATimestamp();
		delay = time;
	}

	private boolean hasTimeToRun(boolean isInAuto) {
		double startTime = isInAuto ? autoStartTime : delayStartTime;
		return (startTime + delay) < Timer.getFPGATimestamp();
	}

	@Override
	public void smartDashboard() {
		SmartDashboard.putNumber("PT_SPEED", speed);
	}

	public static Pivot getInstance() {
		return instance == null ? instance = new Pivot() : instance;
	}
}
