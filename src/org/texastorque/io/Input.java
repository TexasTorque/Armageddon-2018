package org.texastorque.io;

import org.texastorque.torquelib.util.TorqueToggle;

public class Input {

	private static Input instance;
	
	protected double DB_leftSpeed;
	protected double DB_rightSpeed;
	
	protected double AM_speed;
	
	protected TorqueToggle CL_closed;
	
	public Input(){
		DB_leftSpeed = 0d;
		DB_rightSpeed = 0d;
		AM_speed = 0d;
		CL_closed = new TorqueToggle();
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
	
	public void getClosed() {
		return CL_closed.get();
	}
	public static Input getInstance() {
		return instance == null ? instance = new Input() : instance;
	}

}
