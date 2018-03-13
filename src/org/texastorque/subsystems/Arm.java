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
	private double reach;
	private final double LIMIT = 200;
	private final double ADJUSTMENT = 20;
	
	private double delayStartTime;
	
	public Arm() {
		setpoint = 0;
		previousSetpoint = 0;
		speed = 0d;
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
		
	}

	@Override
	public void autoContinuous() {
		if(autoStartTime + delay < Timer.getFPGATimestamp()) {
			setpoint = i.getArmSetpoint();
			currentDistance = f.getArmDistance();
			currentAngle = f.getPTAngle();
		//	reach = Math.abs(Math.cos((Math.toRadians( -(   (d/z)*currentAngle)) + ADJUSTMENT )  );
			if((currentAngle < 190 && i.getPTSetpoint() > 205) || currentAngle > 280 ) {
				setpoint = currentDistance;
			}
		/*	if((currentAngle >=35 && currentAngle < 80) && (reach * currentDistance >= LIMIT)){
				setpoint = currentDistance;
			}
			*/		
			if(TorqueMathUtil.near(setpoint, currentDistance, 12)){
				i.setArmSpeed(0);
			} else {
				i.setArmSpeed((1/Math.PI) * Math.atan(0.01 * (setpoint - currentDistance)));
			}	
				
			speed = i.getArmSpeed();
			output();
		}		
	}

	@Override
	public void teleopContinuous() {
		if (i.getEncodersDead()) {
			if (i.getArmForward()) {
				speed = .3;
			} 
			else if (i.getArmBack()) {
				speed = -.3;
			} 
			else speed = 0;
		} 
		else {
			setpoint = i.getArmSetpoint();
			currentDistance = f.getArmDistance();
			currentAngle = f.getPTAngle();
			/*	reach = Math.abs(Math.cos((Math.toRadians( -(   (.67)*currentAngle)) + ADJUSTMENT )  ));
			if(currentAngle > 12 && )
			if((currentAngle >= 12) && (reach * currentDistance >= LIMIT)){
					setpoint = currentDistance;			
			}
			 */
			/*if((currentAngle < 205 && i.getPTSetpoint() != 0) || currentAngle > 280 ) {
				setpoint = currentDistance;
			}
			if(i.getPickingUp()) {
				setpoint = 305;
			}*/
			
			/*
			if (i.getPickingUp()) {
				setpoint = 430;
			}
			else if (i.getPullingBack()) {
				setpoint = 0;
			}
			*/
			if((currentAngle < 200 && i.getPTSetpoint() > 115) || (currentAngle > 280))/* && i.getPTSetpoint() < 275)*/ {
				setpoint = currentDistance;
			}
			if(TorqueMathUtil.near(setpoint, currentDistance, 12)){
				i.setArmSpeed(0);
			} else {
				i.setArmSpeed((1.75/Math.PI) * Math.atan(0.01 * (setpoint - currentDistance)));
			}
			/*if(!f.getBlockade()) {
				if(i.getArmSpeed() > 0) {
					//System.out.println("gfndsjk");
					i.setArmSpeed(0);
				}
			}*/
			if(i.getClimbing()){
				speed = -1; 
			} else 
				speed = i.getArmSpeed();
		}

		output();
	}
	
	public void setDelay(double time) {
		delay = time;
	}
	
	public void teleopSetDelay(double time) {
		delayStartTime = Timer.getFPGATimestamp();
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