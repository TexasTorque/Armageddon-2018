package org.texastorque.subsystems;

import org.texastorque.auto.AutoManager;
import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;
import org.texastorque.io.HumanInput;
import org.texastorque.subsystems.Drivebase.DriveType;
import org.texastorque.torquelib.controlLoop.TorquePV;
import org.texastorque.torquelib.controlLoop.TorqueRIMP;
import org.texastorque.torquelib.controlLoop.TorqueTMP;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivebase extends Subsystem {

	private static Drivebase instance;
	
	private double leftSpeed;
	private double rightSpeed;
	
	//Drive
	private double setpoint = 0;
	private double previousSetpoint = 0;
	private double previousTime;
	private double precision;
	
	private TorqueTMP driveTMP;
	private TorquePV leftPV;
	private TorquePV rightPV;
	private TorqueRIMP leftRIMP;
	private TorqueRIMP rightRIMP;
	private double previousError;

	private double targetPosition;
	private double targetVelocity;
	private double targetAcceleration;
	
	//Turn
	private double turnPreviousSetpoint = 0;
	private double turnSetpoint;

	private TorqueTMP turnTMP;
	private TorquePV turnPV;

	private double targetAngle;
	private double targetAngularVelocity;
	
	public enum DriveType {
		TELEOP, AUTODRIVE, AUTOTURN, AUTOOVERRIDE, WAIT;
	}
	private DriveType type;
	
	public Drivebase() {
		init();
	}
	
	@Override
	public void autoInit() {
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
		turnTMP = new TorqueTMP(Constants.DB_TURN_MVELOCITY.getDouble(), Constants.DB_TURN_MACCELERATION.getDouble());
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
		output();
	}

	@Override
	public void autoContinuous() {

	}

	@Override
	public void teleopContinuous() {
		leftSpeed = i.getDBLeftSpeed();
		rightSpeed = i.getDBRightSpeed();
		output();
	}
		
	
	private void output() {
		o.setDrivebaseSpeed(leftSpeed, rightSpeed);
	}
	
	public void setType(DriveType type) {
		this.type = type;
	}

	@Override
	public void smartDashboard() {
		SmartDashboard.putNumber("DB_LEFTSPEED", leftSpeed);
		SmartDashboard.putNumber("DB_RIGHTSPEED", rightSpeed);
		
		SmartDashboard.putNumber("DBA_TARGETPOSITION", targetPosition);
		SmartDashboard.putNumber("DBA_TARGETVELOCITY", targetVelocity);
		SmartDashboard.putNumber("DBA_TARGETACCELERATION", targetAcceleration);
		SmartDashboard.putNumber("DBA_TARGETANGLE", targetAngle);
		SmartDashboard.putNumber("DBA_TARGETANGULARVELOCITY", targetAngularVelocity);
	}
	
	public static Drivebase getInstance() {
		return instance == null ? instance = new Drivebase() : instance;
	}
	
}
