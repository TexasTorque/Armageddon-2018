package org.texastorque.subsystems;

import org.texastorque.auto.AutoManager;
import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;
import org.texastorque.io.HumanInput;

import org.texastorque.torquelib.controlLoop.TorquePV;
import org.texastorque.torquelib.controlLoop.TorqueRIMP;
import org.texastorque.torquelib.controlLoop.TorqueTMP;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivebase extends Subsystem{

	private static Drivebase instance;
	
	private double leftSpeed;
	private double rightSpeed;
	
	
	private double setpoint = 0;
	private double previousSetpoint = 0;
	private double previousTime;
	private double precision;
	
	private TorqueTMP tmp;
	private TorquePV leftPV;
	private TorquePV rightPV;
	private TorqueRIMP leftRIMP;
	private TorqueRIMP rightRIMP;
	private double previousError;

	private double targetPosition;
	private double targetVelocity;
	private double targetAcceleration;

	private double turnPreviousSetpoint = 0;
	private double turnSetpoint;

	private TorqueTMP turnProfile;
	private TorquePV turnPV;

	private double targetAngle;
	private double targetAngularVelocity;
	
	public enum DriveType {
		TELEOP, AUTODRIVE, AUTOTURN, AUTOOVERRIDE, AUTOBACKUP, WAIT;
	}
	
	private DriveType type;
	
	public Drivebase() {
		init();
	}
	
	@Override
	public void autoInit() {
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
		tmp = new TorqueTMP(Constants.DB_MVELOCITY.getDouble(), Constants.DB_MACCELERATION.getDouble());
		turnProfile = new TorqueTMP(Constants.DB_MAVELOCITY.getDouble(), Constants.DB_MAACCELERATION.getDouble());
		leftPV = new TorquePV();
		rightPV = new TorquePV();
		turnPV = new TorquePV();
		
		leftPV.setGains(Constants.DB_LEFT_PV_P.getDouble(), Constants.DB_LEFT_PV_V.getDouble(),
				Constants.DB_LEFT_PV_ffV.getDouble(), Constants.DB_LEFT_PV_ffA.getDouble());
		leftPV.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());
		
		rightPV.setGains(Constants.DB_RIGHT_PV_P.getDouble(), Constants.DB_RIGHT_PV_V.getDouble(),
				Constants.DB_RIGHT_PV_ffV.getDouble(), Constants.DB_RIGHT_PV_ffA.getDouble());
		rightPV.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());

		turnPV.setGains(Constants.DB_TURN_PV_P.getDouble(), Constants.DB_TURN_PV_V.getDouble(),
				Constants.DB_TURN_PV_ffV.getDouble(), Constants.DB_TURN_PV_ffA.getDouble());
		turnPV.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void autoContinuous() {
		switch (type) {
			case AUTODRIVE:
				setpoint = i.getDB_setpoint();
				if (setpoint != previousSetpoint) {
					previousSetpoint = setpoint;
					precision = i.getDB_precision();
					tmp.generateTrapezoid(setpoint, 0d, 0d);
					previousTime = Timer.getFPGATimestamp();
				}
				if (TorqueMathUtil.near(setpoint, f.getDB_leftDistance(), precision)
						&& TorqueMathUtil.near(setpoint, f.getDB_rightDistance(), precision))
					AutoManager.interruptThread();
				double dt = Timer.getFPGATimestamp() - previousTime;
				previousTime = Timer.getFPGATimestamp();
				tmp.calculateNextSituation(dt);
		
				targetPosition = tmp.getCurrentPosition();
				targetVelocity = tmp.getCurrentVelocity();
				targetAcceleration = tmp.getCurrentAcceleration();
		
				leftSpeed = leftPV.calculate(tmp, f.getDB_leftDistance(), f.getDB_leftRate());
				rightSpeed = rightPV.calculate(tmp, f.getDB_rightDistance(), f.getDB_rightRate());
				break;
			
			case AUTOTURN:
				break;
			case AUTOBACKUP:
				System.out.println(Timer.getFPGATimestamp());
				while(Timer.getFPGATimestamp()>1.2) {
					leftSpeed = 1.0;
					rightSpeed = -1.0;
				}
				
			default:
				leftSpeed = 0;
				rightSpeed = 0;
				break;
		}
		output();
	}

	@Override
	public void teleopContinuous() {
		switch (type) {
		case TELEOP:
			leftSpeed = i.getDB_leftSpeed();
			rightSpeed = i.getDB_rightSpeed();
			break;
		}
		
		output();
	}
	
	public void output() {
		o.setDrivebaseSpeed(leftSpeed, rightSpeed);
	}
	
	public void setType (DriveType type) {
		this.type = type;
	}

	@Override
	public void smartDashboard() {
		SmartDashboard.putNumber("DB_LEFTSPEED", leftSpeed);
		SmartDashboard.putNumber("DB_RIGHTSPEED", rightSpeed);

		SmartDashboard.putString("DBA_TYPE", type.toString());
		SmartDashboard.putNumber("DBA_TARGETPOSITION", targetPosition);
		SmartDashboard.putNumber("DBA_TARGETVELOCITY", targetVelocity);
		SmartDashboard.putNumber("DBA_TARGETACCELERATION", targetAcceleration);
		SmartDashboard.putNumber("DBA_TARGETANGLE", targetAngle);
		SmartDashboard.putNumber("DBA_TARGETANGULARVELOCITY", targetAngularVelocity);
	}
	
	public static Drivebase getInstance(){
		return instance == null ? instance = new Drivebase() : instance;
	}
	
}
