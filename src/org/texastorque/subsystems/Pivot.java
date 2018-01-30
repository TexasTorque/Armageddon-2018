package org.texastorque.subsystems;

import org.texastorque.torquelib.controlLoop.TorquePV;
import org.texastorque.torquelib.controlLoop.TorqueTMP;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pivot extends Subsystem {

	
	private static Pivot instance;
	
	private double speed;
	
	private TorqueTMP tmp;
	
	private double setpoint;
	private double previousSetpoint;

	private boolean setTime;
	private double cachedTime;
	private double prevTime;
	
	private TorquePV solePV;
	private double targetPosition;
	private double targetVelocity;
	private double targetAcceleration;
	
	
	public Pivot() {
		//tmp = new TorqueTMP();
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
		if(i.getGC_outake()) {
			setpoint = i.getGC_outakeSetpoint();
			if(!setTime) {
				cachedTime = Timer.getFPGATimestamp();
				setTime = true;
			}
		} else {
			setpoint = i.getGC_setpoint();
			setTime = false;
		}
		if (previousSetpoint != setpoint) {
			previousSetpoint = setpoint;
			tmp.generateTrapezoid(setpoint, 0d, 0d);
			prevTime = Timer.getFPGATimestamp();
		} else {
			double dt = Timer.getFPGATimestamp() - prevTime;
			prevTime = Timer.getFPGATimestamp();
			tmp.calculateNextSituation(dt);

			targetPosition = tmp.getCurrentPosition();
			targetVelocity = tmp.getCurrentVelocity();
			targetAcceleration = tmp.getCurrentAcceleration();

			speed = solePV.calculate(tmp, Feedback.getInstance().getGC_distance(),
					Feedback.getInstance().getGC_rate());
		
	}

	@Override
	public void smartDashboard() {
		
		
	}
	
	public Pivot getInstance() {
		return instance == null ? instance = new Pivot() : instance;
	}

}
