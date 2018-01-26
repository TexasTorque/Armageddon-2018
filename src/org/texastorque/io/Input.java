package org.texastorque.io;

public class Input {

	private static Input instance;
	
	protected double DB_leftSpeed;
	protected double DB_rightSpeed;
	protected double AM_speed;
	
	public Input(){
		DB_leftSpeed = 0d;
		DB_rightSpeed = 0d;
		AM_speed = 0d;
	}
	
	public double getLeftSpeed(){
		return DB_leftSpeed;
	}
	
	public double getRightSpeed(){
		return DB_rightSpeed;
	}

	public double getArmSpeed() {
		return AM_speed;
	}
	
	public static Input getInstance() {
		return instance == null ? instance = new Input() : instance;
	}

}
