package org.texastorque.io;

import org.texastorque.auto.AutoManager;
import org.texastorque.torquelib.util.GenericController;
import org.texastorque.torquelib.util.TorqueToggle;

public class HumanInput extends Input{

	public static HumanInput instance;

	protected static GenericController driver;
	protected static GenericController operator;
	
	
	
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
		DB_leftSpeed = -driver.getLeftYAxis() + driver.getRightXAxis();
		DB_rightSpeed = -driver.getLeftYAxis() - driver.getRightXAxis();
		
	
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
