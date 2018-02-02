package org.texastorque.io;

import org.texastorque.auto.AutoManager;
import org.texastorque.torquelib.util.GenericController;
import org.texastorque.torquelib.util.TorqueToggle;

public class HumanInput extends Input{

	public static HumanInput instance;

	protected static GenericController driver;
	protected static GenericController operator;
	
	private double lastLeftSpeed = 0;
	private double lastRightSpeed = 0;
	
	
	public HumanInput(){
		init();
		
	}
	
	public void init(){
		driver = new GenericController(0 ,.1);
		operator = new GenericController(1, .1);
	}

	public void update(){
		updateDrive();
		updateFile();
		updateArm();
		updateClaw();
	}
	
	public void updateDrive(){
		final double MAX_START_ACCEL = .05;
		boolean starting = false;
		if(Math.abs(lastLeftSpeed) <.5)
			starting = true;
			
		
		/*
		 * Checks to see if the drivebase should be going more positive or negative
		 */
		double leftNegativeTest = (-driver.getLeftYAxis() + driver.getRightXAxis())
									/(Math.abs(-driver.getLeftYAxis() + driver.getRightXAxis()));
		double rightNegativeTest = (-driver.getLeftYAxis() - driver.getRightXAxis())
				/(Math.abs(-driver.getLeftYAxis() - driver.getRightXAxis()));
		
		
		
		if(starting && Math.abs(DB_leftSpeed) > 0.05) {
			DB_leftSpeed = lastLeftSpeed + MAX_START_ACCEL * leftNegativeTest;
			DB_rightSpeed = lastRightSpeed + MAX_START_ACCEL * rightNegativeTest;
		} else {
			DB_leftSpeed = -driver.getLeftYAxis() + driver.getRightXAxis();
			DB_rightSpeed = -driver.getLeftYAxis() - driver.getRightXAxis();
		}
		
		if(starting) {
			lastLeftSpeed = DB_leftSpeed;
			lastRightSpeed = DB_rightSpeed;
		}
	
	}

	public void updateFile() {
		if(driver.getYButton())
			AutoManager.getInstance();
	}
	
	
	public void updateArm() {
		if(driver.getRightTrigger())
			AM_speed = 1d;
		else if(driver.getLeftTrigger())
			AM_speed = -1d;
		
	}
	
	public void updateClaw() {
		CL_closed.calc(driver.getXButton());
			

	}
	
	
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
}
