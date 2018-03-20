package org.texastorque.subsystems;

import org.texastorque.constants.Constants;
import org.texastorque.subsystems.Subsystem.AutoType;
import org.texastorque.torquelib.controlLoop.ScheduledPID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pivot extends Subsystem {
	
	private static final double SETPOINT_DEFAULT = 0;
	
	/**
	 * The absolute magnitude of the largest possible value for speed.
	 * 
	 * The original speed calculation had an asymptote in [0.7, 0.8]. 
	 * For this reason, the value is capped at 0.7 for initial PID tests.
	 */
	private static final double OUTPUT_MAX_ABS = 0.7;

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
	
	private final ScheduledPID pivotPID;

	private Pivot() {
		delay = 0;
		
		this.pivotPID = new ScheduledPID.Builder(SETPOINT_DEFAULT, OUTPUT_MAX_ABS)
				.setPGains(0.05)
				.build();
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
<<<<<<< HEAD
		if(type.equals(AutoType.RECORDING))
			recordingAutoContin();
		else commandAutoContin();
		output();
	}

	private void recordingAutoContin() {
		setpoint = auto.getPTSetpoint();
		currentAngle = f.getPTAngle();
		currentArmSetpoint = auto.getArmSetpoint();
		currentArmDistance = f.getArmDistance();
		
		if (setpoint != previousSetpoint) {
			if(currentArmSetpoint < 400 && currentArmDistance > 400) {
				setpoint = 190;
			}
			previousSetpoint = setpoint;
		}
		speed = (1.5/Math.PI) * Math.atan(0.03 * (setpoint - currentAngle));
		
	}
	
	private void commandAutoContin() {
		if(autoStartTime + delay < Timer.getFPGATimestamp()) 
=======
		if (hasTimeToRun(true)) {
>>>>>>> lubecki-lonestar
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
<<<<<<< HEAD
		/*reach = Math.abs(Math.cos((Math.toRadians( -(   (.67)*currentAngle)) + ADJUSTMENT )  ));
		
		if((currentAngle >=80 && currentAngle < 120) && (reach * currentArmPosition >= LIMIT)){
			setpoint = currentAngle;			
		}
		*/
=======

>>>>>>> lubecki-lonestar
		if (setpoint != previousSetpoint) {
			if (currentArmSetpoint < 400 && currentArmDistance > 400) {
				setpoint = currentAngle;
			}

			previousSetpoint = setpoint;
			pivotPID.changeSetpoint(setpoint);
		}

		// Original Code - Retain for test comparison.
		// speed = (1.5 / Math.PI) * Math.atan(0.03 * (setpoint - currentAngle));
		
		speed = pivotPID.calculate(currentAngle);
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
