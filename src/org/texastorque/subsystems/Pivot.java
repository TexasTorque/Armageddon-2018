package org.texastorque.subsystems;

import org.texastorque.auto.AutoManager;
import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;
import org.texastorque.subsystems.Drivebase.DriveType;
import org.texastorque.torquelib.controlLoop.TorquePV;
import org.texastorque.torquelib.controlLoop.TorqueTMP;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pivot extends Subsystem {

	private static Pivot instance;
	
	private double speed;
	
	private TorqueTMP pivotTMP;
	private TorqueTMP pivotDownTMP;
	private TorquePV pivotPV;
	
	private double setpoint = 0;
	private double previousSetpoint = 0;
	private double previousTime;
	private double precision;
	
	private double targetAngle;
	private double targetVelocity;
	private double targetAcceleration;
	
	private double autoStartTime;
	private double delay;
	
	private boolean goingDown;
	
	public Pivot() {
		init();
		delay = 0;
	}
	
	@Override
	public void autoInit() {
		autoStartTime = Timer.getFPGATimestamp();
		init();
	}

	@Override
	public void teleopInit() {
		init();
	}

	@Override
	public void disabledInit() {
		speed = 0;
	}
	
	private void init() {
		pivotTMP = new TorqueTMP(Constants.PT_MVELOCITY.getDouble(), Constants.PT_MACCELERATION.getDouble());
		pivotPV = new TorquePV();
		
		pivotPV.setGains(Constants.PT_PV_P.getDouble(), Constants.PT_PV_V.getDouble(),
				Constants.PT_PV_ffV.getDouble(), Constants.PT_PV_ffA.getDouble());
		pivotPV.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());
		
		goingDown = false;
		previousTime = Timer.getFPGATimestamp();
	}

	@Override
	public void disabledContinuous() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void autoContinuous() {
		if(autoStartTime + delay < Timer.getFPGATimestamp()) 
			runPivot();
	}

	@Override
	public void teleopContinuous() {
		runPivot();
	}
	
	public void runPivot() {
		setpoint = i.getPTSetpoint();
		double currentArmPosition = f.getArmDistance();
		if(i.getPickingUp()) {
			setpoint = 7;
		}
			if (setpoint != previousSetpoint) {
				if(currentArmPosition > 400) {
					setpoint = 85;
				} 
				previousSetpoint = setpoint;
				pivotTMP.generateTrapezoid(setpoint, f.getPTAngle(), 0d);
				previousTime = Timer.getFPGATimestamp();
			}
			
			double dt = Timer.getFPGATimestamp() - previousTime;
			previousTime = Timer.getFPGATimestamp();
			pivotTMP.calculateNextSituation(dt);
			
			targetAngle = pivotTMP.getCurrentPosition();
			targetVelocity = pivotTMP.getCurrentVelocity();
			targetAcceleration = pivotTMP.getCurrentAcceleration();
				
		if(i.getMovingLeft()) {
			speed = -.2;
			setpoint = f.getPTAngle();
		} else if(i.getMovingRight()) {
			speed = .2;
			setpoint = f.getPTAngle();
		}
		speed = pivotPV.calculate(pivotTMP, f.getPTAngle(), f.getPTAngleRate());
		
		output();
	}
	
	public void output() {
		o.setPivotSpeed(speed);
	}

	public double getSpeed() {
		return speed;
	}
	
	public void setDelay(double time) {
		delay = time;
	}
	
	@Override
	public void smartDashboard() {
		SmartDashboard.putNumber("PT_SPEED", speed);
	}
	
	public static Pivot getInstance() {
		return instance == null ? instance = new Pivot() : instance;
	}

}
