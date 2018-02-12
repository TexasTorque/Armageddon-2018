package org.texastorque.io;

import org.texastorque.auto.AutoManager;
import org.texastorque.torquelib.util.GenericController;
import org.texastorque.torquelib.util.TorqueToggle;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HumanInput extends Input {

	public static HumanInput instance;

	private double lastLeftSpeed = 0;
	private double lastRightSpeed = 0;
	
	protected GenericController driver;
	protected GenericController operator;
	protected OperatorConsole board;
	
	public HumanInput(){
		init();
		
	}
	

	public void init() {
		driver = new GenericController(0 , .1);
		operator = new GenericController(1, .1);
		board = new OperatorConsole(2);
	}
	
	public void update() {
		updateDrive();
		updateFile();
		updateArm();
		updateClaw();
		updateWheelIntake();
		updatePivot();

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
			DB_leftSpeed = driver.getLeftYAxis() - driver.getRightXAxis();
			DB_rightSpeed = driver.getLeftYAxis() + driver.getRightXAxis();
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
	
		//if(slider and current position don't line up)
		//   arm goes all the way up
		//basically its a dumber version of bangbang
		if(driver.getAButton())
			AM_speed = -.1;
		
	}
	
	public void updateClaw() {
		CL_closed.calc(operator.getBButton());
		CL_closed.calc(driver.getYButton());
	}
	 

	public void updateWheelIntake() {
		
		if(operator.getLeftBumper()) {
			IN_speed = -1;
		} else if(operator.getRightBumper()) {
			IN_speed = 1;
		} else IN_speed = 0;
	
		IN_down.calc(operator.getXButton());
		IN_out.calc(operator.getAButton());
		
	}
	
	public void updatePivot() {	
		for(int x = 0; x<10; x++) {
			if (board.getButton(x))
				PT_index = x;
		}
	}

		
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
	
}
