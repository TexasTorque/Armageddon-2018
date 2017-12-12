package org.texastorque.io;

public class Input {

	private static Input instance;
	
	protected double DB_leftSpeed;
	protected double DB_rightSpeed;
	
	protected boolean AR_armUp;
	
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
	
	public boolean getArmUp() {
		return AR_armUp;
	}

	public static Input getInstance() {
		return instance == null ? instance = new Input() : instance;
	}

}
