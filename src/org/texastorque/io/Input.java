package org.texastorque.io;

public class Input {

	private static Input instance;
	
	protected double DB_leftSpeed;
	protected double DB_rightSpeed;
	protected double IN_lowerSpeed;
	protected double IN_upperSpeed;

	
	public Input(){
		DB_leftSpeed = 0.0;
		DB_rightSpeed = 0.0;
	}
	
	public double getLeftSpeed(){
		return DB_leftSpeed;
	}
	
	public double getRightSpeed(){
		return DB_rightSpeed;
	}
	
	public double getIN_lowerSpeed() {
		return IN_lowerSpeed;
	}
	
	public double getIN_upperSpeed() {
		return IN_upperSpeed;
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
