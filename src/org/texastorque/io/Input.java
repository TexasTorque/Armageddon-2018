package org.texastorque.io;

import org.texastorque.torquelib.util.TorqueToggle;

public class Input {

	private static Input instance;
	
	protected double DB_leftSpeed;
	protected double DB_rightSpeed;
	protected double IN_lowerSpeed;
	protected double IN_upperSpeed;

	
	protected double AM_speed;
	
	protected int PT_index;
	protected double[] PT_setpoints = new double[] {1,2,3,4,5,6,7,8,9,10}; //TBD
	
	protected boolean PT_setpointChanged;
	
	protected TorqueToggle CL_closed;
	
	public Input(){
		DB_leftSpeed = 0d;
		DB_rightSpeed = 0d;
		AM_speed = 0d;
		CL_closed = new TorqueToggle();
		PT_setpointChanged = false;
	}
	
	public double getDBLeftSpeed() {
		return DB_leftSpeed;
	}
	
	public double getDBRightSpeed() {
		return DB_rightSpeed;
	}
	

	public double getArmSpeed() {
		return AM_speed;
	}
	
	public boolean getClawClosed() {
		return CL_closed.get();
	}
	
	public double getPTSetpoint() {
		return PT_setpoints[PT_index];
	}
	

	public double getINLowerSpeed() {
		return IN_lowerSpeed;
	}
	
	public double getINUpperSpeed() {
		return IN_upperSpeed;
	}

	public boolean getPTSetpointChanged() {
		return PT_setpointChanged;
	}


	public static Input getInstance() {
		return instance == null ? instance = new Input() : instance;
	
	}
	
	public void setIN_lowerSpeed(double speed) {
		IN_lowerSpeed = speed;
	}
	
	public void setIN_upperSpeed(double speed) {
		IN_upperSpeed = speed;
	}



}
