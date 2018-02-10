package org.texastorque.io;

import org.texastorque.torquelib.util.GenericController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HumanInput extends Input {

	public static HumanInput instance;

	private GenericController driver;
	private GenericController operator;
	
	public HumanInput(){
		init();
	}

	public void init() {
		driver = new GenericController(0 , .1);
		operator = new GenericController(1, .1);
	}
	
	public void update() {
		updateDrive();
		updateArm();
		updateClaw();
	}
	
	public void updateDrive() {
		DB_leftSpeed = -driver.getLeftYAxis() + driver.getRightXAxis();
		DB_rightSpeed = -driver.getLeftYAxis() - driver.getRightXAxis();
	}
	
	public void updatePivot() {
		PT_setpoint = 0; //test value
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
	
	public void smartDashboard() {
		
	}
	
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
}
