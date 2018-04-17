package org.texastorque.subsystems;

import org.texastorque.auto.AutoManager;
import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;
import org.texastorque.torquelib.controlLoop.TorquePV;
import org.texastorque.torquelib.controlLoop.TorqueRIMP;
import org.texastorque.torquelib.controlLoop.TorqueTMP;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.Timer;

public class Drivebase extends Subsystem {

	private static volatile Drivebase instance;

	private double leftSpeed;
	private double rightSpeed;

	// Drive
	private double setpoint = 0;
	private double previousSetpoint = 0;
	private double previousTime;
	private double precision;

	private TorqueTMP driveTMP;
	private TorquePV leftPV;
	private TorquePV rightPV;
	private TorqueRIMP leftRIMP;
	private TorqueRIMP rightRIMP;

	private double targetPosition;
	private double targetVelocity;
	private double targetAcceleration;
	// Turn
	private double turnSetpoint;
	private double currentAngle;

	private boolean driftClockwise;
	private boolean driftForward;
	private int driftIndex;
	private double currentDistance;
	
	public enum DriveType {
		TELEOP, AUTODRIVE, AUTOTURN, AUTOBACKUP, AUTOOVERRIDE, WAIT, AUTODRIFTFORWARD, AUTODRIFTBACKWARD;
	}

	private DriveType type;

	private Drivebase() {
		init();
	}

	@Override
	public void autoInit() {
		driftIndex = 0;
		type = DriveType.AUTODRIVE;
		init();
	}

	@Override
	public void teleopInit() {
		type = DriveType.TELEOP;
		init();
	}

	@Override
	public void disabledInit() {
		leftSpeed = 0;
		rightSpeed = 0;
	}

	private void init() {
		driveTMP = new TorqueTMP(Constants.DB_MVELOCITY.getDouble(), Constants.DB_MACCELERATION.getDouble());
		leftPV = new TorquePV();
		rightPV = new TorquePV();

		leftPV.setGains(Constants.DB_LEFT_PV_P.getDouble(), Constants.DB_LEFT_PV_V.getDouble(),
				Constants.DB_LEFT_PV_ffV.getDouble(), Constants.DB_LEFT_PV_ffA.getDouble());
		leftPV.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());

		rightPV.setGains(Constants.DB_RIGHT_PV_P.getDouble(), Constants.DB_RIGHT_PV_V.getDouble(),
				Constants.DB_RIGHT_PV_ffV.getDouble(), Constants.DB_RIGHT_PV_ffA.getDouble());
		rightPV.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());

		rightRIMP = new TorqueRIMP(Constants.DB_MVELOCITY.getDouble(), Constants.DB_MACCELERATION.getDouble(), 0);
		leftRIMP = new TorqueRIMP(Constants.DB_MVELOCITY.getDouble(), Constants.DB_MACCELERATION.getDouble(), 0);

		rightRIMP.setGains(Constants.DB_RIMP_P.getDouble(), Constants.DB_RIMP_V.getDouble(),
				Constants.DB_RIMP_ffV.getDouble(), Constants.DB_RIMP_ffA.getDouble());
		leftRIMP.setGains(Constants.DB_RIMP_P.getDouble(), Constants.DB_RIMP_V.getDouble(),
				Constants.DB_RIMP_ffV.getDouble(), Constants.DB_RIMP_ffA.getDouble());

		previousTime = Timer.getFPGATimestamp();
	}

	@Override
	public void disabledContinuous() {
		output();
	}

	@Override
	public void teleopContinuous() {
		switch (type) {
		case TELEOP:
			leftSpeed = i.getDBLeftSpeed();
			rightSpeed = i.getDBRightSpeed();
			break;

		default:
			type = DriveType.TELEOP;
			break;
		}
		output();
	}

	@Override
	public void autoContinuous() {
		if (autoType.equals(AutoType.RECORDING))
			recordingAutoContin();
		else
			commandAutoContin();
		output();
	}

	private void recordingAutoContin() {
		// leftSpeed = auto.getDBLeftSpeed();
		// rightSpeed = auto.getDBRightSpeed();
		// System.out.println(leftSpeed);
	}

	private void commandAutoContin() {
		double dt;
		switch (type) {
		case AUTODRIVE:
			setpoint = i.getDBDriveSetpoint();
			if (setpoint != previousSetpoint) {
				previousSetpoint = setpoint;
				precision = i.getDBPrecision();
				driveTMP.generateTrapezoid(setpoint, 0d, 0d);
				previousTime = Timer.getFPGATimestamp();
			}

			if (hasDrivenFarEnough()) {
				AutoManager.setStepDone();
			}

			dt = Timer.getFPGATimestamp() - previousTime;
			previousTime = Timer.getFPGATimestamp();
			driveTMP.calculateNextSituation(dt);

			targetPosition = driveTMP.getCurrentPosition();
			targetVelocity = driveTMP.getCurrentVelocity();
			targetAcceleration = driveTMP.getCurrentAcceleration();

			leftSpeed = leftPV.calculate(driveTMP, f.getDBLeftDistance(), f.getDBLeftRate());
			rightSpeed = rightPV.calculate(driveTMP, f.getDBRightDistance(), f.getDBRightRate());
			break;

		case AUTOTURN:
			turnSetpoint = i.getDBTurnSetpoint();
			currentAngle = f.getDBAngle();
			if (!TorqueMathUtil.near(turnSetpoint, f.getDBAngle(), 3)) {
				if (turnSetpoint - currentAngle > 0) {
					leftSpeed = .5;
				} else if (turnSetpoint - currentAngle < 0) {
					leftSpeed = -.5;
				}
				rightSpeed = -leftSpeed;
			} else {
				leftSpeed = 0;
				rightSpeed = 0;
			}
			/*
			turnSetpoint = i.getDBTurnSetpoint();
			currentAngle = f.getDBAngle();
			if (!TorqueMathUtil.near(turnSetpoint, f.getDBAngle(), 3)) {
				leftSpeed = (1.5 / Math.PI) * Math.atan(0.007 * (setpoint - currentAngle));
				rightSpeed = -leftSpeed;
			} else {
			leftSpeed = 0;
			rightSpeed = 0;
			}
			*/
			
			break;
		case AUTODRIFTFORWARD:
			switch (driftIndex) {
			case 0:
				if (f.getDBLeftDistance() > 80) {
					driftIndex++;
					System.out.println("1");
				}
				leftSpeed = .7;
				rightSpeed = .7;
				break;
			case 1:
				if (driftClockwise) {
					if (f.getDBAngle() > 88) {
						driftIndex++;
						System.out.println("2 CW");
					}
					leftSpeed = .7;
					rightSpeed = .4;
				} else {
					if (f.getDBAngle() < -88) {
						driftIndex++;
						System.out.println("2 CCW");
					}
					leftSpeed = .4;
					rightSpeed = .7;
				} // else
					//
				break;
			/*
			 * case 2: if(Math.abs(90 - Math.abs(f.getDBAngle())) < 2) { driftIndex++;
			 * System.out.println("3"); } else if(driftClockwise) { leftSpeed = .1;
			 * rightSpeed = .3; } else { leftSpeed = .3; rightSpeed = .1; } break;
			 */ case 2:
				i.setDBDriveSetpoint(100, 1);
				setType(DriveType.AUTODRIVE);
				break;

			}
			break;
		case AUTODRIFTBACKWARD:
			currentAngle = f.getDBAngle();
			currentDistance = f.getDBLeftDistance();
			switch (driftIndex) {
			case 0:
				if (currentDistance < -84) {
					driftIndex++;
					System.out.println("1");
				}
				leftSpeed = -.8;
				rightSpeed = -.8;

				break;
			case 1:
				if (driftClockwise) {
					if (currentAngle > 81) {
						Feedback.getInstance().resetDriveEncoders();
						driftIndex++;
						System.out.println("2 L");
					}
					leftSpeed = -.3 + (.3 * currentAngle)/90;
					rightSpeed = -.7 + (.7 * currentAngle)/90;
				} else {
					if (currentAngle < -81) {
						Feedback.getInstance().resetDriveEncoders();
						driftIndex++;
						i.setDBDriveSetpoint(-166, 1);
						System.out.println("2 R");
					}
					rightSpeed = -.3 - (.45 * currentAngle)/90;
					leftSpeed = -.7 - (1.05 * currentAngle)/90;
				} // else
					//
				break;
			
			 case 2:
				setType(DriveType.AUTODRIVE);
				break;

			}
			break;
		case AUTOBACKUP:
			leftSpeed = 0.6;
			rightSpeed = 0.6;
			break;

		default:
			leftSpeed = 0;
			rightSpeed = 0;
			break;
		}

		output();
	}

	private boolean hasDrivenFarEnough() {
		return TorqueMathUtil.near(setpoint, f.getDBLeftDistance(), precision)
				&& TorqueMathUtil.near(setpoint, f.getDBRightDistance(), precision);
	}

	public void setDriftDirection(boolean clockwise) {
		driftClockwise = clockwise;
	}

	private void output() {
		o.setDrivebaseSpeed(leftSpeed, rightSpeed);
	}

	public void setType(DriveType type) {
		this.type = type;
	}

	@Override
	public void smartDashboard() {
	}

	public static synchronized Drivebase getInstance() {
		return instance == null ? instance = new Drivebase() : instance;
	}
}
/*
 * @Override public void autoContinuous() { double dt; switch (type) { case
 * AUTODRIVE: setpoint = i.getDBDriveSetpoint(); if (setpoint !=
 * previousSetpoint) { previousSetpoint = setpoint; precision =
 * i.getDBPrecision(); driveTMP.generateTrapezoid(setpoint, 0d, 0d);
 * previousTime = Timer.getFPGATimestamp(); } if (TorqueMathUtil.near(setpoint,
 * f.getDBLeftDistance(), precision) && TorqueMathUtil.near(setpoint,
 * f.getDBRightDistance(), precision)) AutoManager.interruptThread(); dt =
 * Timer.getFPGATimestamp() - previousTime; previousTime =
 * Timer.getFPGATimestamp(); driveTMP.calculateNextSituation(dt);
 * 
 * leftSpeed = leftPV.calculate(driveTMP, f.getDBLeftDistance(),
 * f.getDBLeftRate()); rightSpeed = rightPV.calculate(driveTMP,
 * f.getDBRightDistance(), f.getDBRightRate()); break;
 * 
 * case AUTOTURN: turnSetpoint = i.getDBTurnSetpoint(); currentAngle =
 * f.getDBAngle(); /* if (turnSetpoint != turnPreviousSetpoint) {
 * turnPreviousSetpoint = turnSetpoint; precision = i.getDBPrecision();
 * turnTMP.generateTrapezoid(turnSetpoint, 0.0, 0.0); previousTime =
 * Timer.getFPGATimestamp(); } if (TorqueMathUtil.near(turnSetpoint,
 * f.getDBAngle(), precision)) { AutoManager.interruptThread(); }
 * //AutoManager.interruptThread(); dt = Timer.getFPGATimestamp() -
 * previousTime; previousTime = Timer.getFPGATimestamp();
 * turnTMP.calculateNextSituation(dt);
 * 
 * targetAngle = turnTMP.getCurrentPosition(); targetAngularVelocity =
 * turnTMP.getCurrentVelocity();
 * 
 * leftSpeed = turnPV.calculate(turnTMP, f.getDBLeftDistance(),
 * f.getDBLeftRate()); rightSpeed = -leftSpeed; break;
 */
/*
 * if(!TorqueMathUtil.near(turnSetpoint, f.getDBAngle(), 3)) { if(turnSetpoint -
 * currentAngle > 0) { leftSpeed = .33; } else if(turnSetpoint - currentAngle <
 * 0) { leftSpeed = -.33; } rightSpeed = -leftSpeed; } else { leftSpeed = 0;
 * rightSpeed = 0; } break;
 * 
 * case AUTOBACKUP: leftSpeed = 0.5; rightSpeed = 0.5; break;
 * 
 * default: leftSpeed = 0; rightSpeed = 0; break; } output(); }
 */