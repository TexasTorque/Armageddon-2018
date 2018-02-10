package org.texastorque.io;

import org.texastorque.torquelib.util.TorqueToggle;

public class Input {

	private static Input instance;
	
	protected double DB_leftSpeed;
	protected double DB_rightSpeed;
	
	protected double IN_speed;
	protected TorqueToggle IN_down;
	protected TorqueToggle IN_out;
	
	protected double AM_speed;
	
	protected TorqueToggle CL_closed;
	
	protected int PT_index;
	
	public Input(){
		DB_leftSpeed = 0d;
		DB_rightSpeed = 0d;
		AM_speed = 0d;
		CL_closed = new TorqueToggle();
		IN_down = new TorqueToggle();
		IN_out = new TorqueToggle();
		PT_index = 0;
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
	
	public double getINSpeed() {
		return IN_speed;
	}
	
	public boolean getINDown() {
		return IN_down.get();
	}
	
	public boolean getINOut() {
		return IN_out.get();
	}


	public static Input getInstance() {
		return instance == null ? instance = new Input() : instance;
	
	}



}
