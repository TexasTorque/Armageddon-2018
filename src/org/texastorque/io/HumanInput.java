package org.texastorque.io;

import org.texastorque.torquelib.util.GenericController;

public class HumanInput extends Input{

	public static HumanInput instance;

	private GenericController driver;
	private GenericController operator;
	
	public HumanInput(){
		init();
	}
	
	public void update(){
		updateDrive();

		updateWheelIntake();
	}
	
	public void updateDrive(){
		DB_leftSpeed = -driver.getLeftYAxis() + driver.getRightXAxis();
		DB_rightSpeed = -driver.getLeftYAxis() - driver.getRightXAxis();
	
	}

	
	public void init(){
		driver = new GenericController(0 ,.1);
		operator = new GenericController(1, .1);
	}
	 
	
	
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
	
	public void updateWheelIntake() {
		boolean intaking;
		if (operator.getLeftBumper() && operator.getRightStickClick()) {
			IN_upperSpeed = 1d; // .5
			IN_lowerSpeed = .3d;					
		intaking = true;
		} else if (operator.getRightBumper()) {
			IN_upperSpeed = 1d;
			IN_lowerSpeed = -1;
			intaking = true;
		} else if (operator.getRightBumper()) {
			IN_upperSpeed = -1d;
			IN_lowerSpeed = 1d;
			intaking = true;
		} else {
			intaking = false;
			IN_upperSpeed = 0d;
			IN_lowerSpeed = 0d;
		}
	}

}
