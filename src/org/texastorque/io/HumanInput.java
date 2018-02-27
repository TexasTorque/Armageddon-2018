package org.texastorque.io;

import org.texastorque.auto.AutoManager;
import org.texastorque.feedback.Feedback;
import org.texastorque.torquelib.util.GenericController;
import org.texastorque.torquelib.util.TorqueToggle;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HumanInput extends Input {

	public static HumanInput instance;

	private double lastLeftSpeed = 0;
	private double lastRightSpeed = 0;
	private double leftNegativeTest = 0;
	private double rightNegativeTest = 0;
	
	protected GenericController driver;
	protected GenericController operator;
	protected OperatorConsole board;
	
	private int PT_test;
	
	public HumanInput(){
		driver = new GenericController(0 , .1);
		operator = new GenericController(1, .1);
		board = new OperatorConsole(2);
		PT_test = 0;
	}
	

	
	public void update() {
		updateDrive();
//		updateFile();
		updateArm();
		updateClaw();
		updateWheelIntake();
		updateBoardSubsystems();

	}
	
	public void updateDrive(){
		/*
		final double MAX_START_ACCEL = .05;
		boolean starting = false;
		if(Math.abs(lastLeftSpeed) <.5)
			starting = true;
			
		
		/*
		 * Checks to see if the drivebase should be going more positive or negative
		 */
		/*
		leftNegativeTest = (-driver.getLeftYAxis() + driver.getRightXAxis())
							/(Math.abs(-driver.getLeftYAxis() + driver.getRightXAxis()));
		rightNegativeTest = (-driver.getLeftYAxis() - driver.getRightXAxis())
							  /(Math.abs(-driver.getLeftYAxis() - driver.getRightXAxis()));
		*/
		
		/*
		if(starting && Math.abs(DB_leftSpeed) > 0.05) {
			DB_leftSpeed = lastLeftSpeed + MAX_START_ACCEL * leftNegativeTest;
			DB_rightSpeed = lastRightSpeed + MAX_START_ACCEL * rightNegativeTest;
		} else {
			
		}
		
		if(starting) {
			lastLeftSpeed = DB_leftSpeed;
			lastRightSpeed = DB_rightSpeed;
		} */
		DB_leftSpeed = -driver.getLeftYAxis() + .75 * driver.getRightXAxis();
		DB_rightSpeed = -driver.getLeftYAxis() - .75 * driver.getRightXAxis();
	}

	public void updateFile() {
		if(driver.getYButton())
			AutoManager.getInstance();
	}
	
	
	public void updateArm() {
		
	}
	
	public void updateClaw() {
		CL_closed.calc(operator.getBButton());
	}
	 

	public void updateWheelIntake() {

		IN_down.calc(operator.getXButton());
		IN_out.calc(operator.getAButton());
		if(operator.getLeftBumper()) {
			IN_speed = -.5;
		} else if(operator.getRightBumper()) {
			IN_speed = .35;
		} else IN_speed = 0;
	}
	
	
	public void updateBoardSubsystems() {	
		MAXIMUM_OVERDRIVE.calc(board.getButton(10));
		if(MAXIMUM_OVERDRIVE.get()) {
			AM_setpoint = board.getSlider() * AM_CONVERSION;
			PT_setpoint = (int)(Math.round(board.getDial() / 0.00787401571)) * 10;			
		} else {
		if(driver.getAButton()) {
			climbing = true;
//			AM_setpoint = 0;
		} else {
			climbing = false;
		}
		for(int x = 1; x < 10; x++) {
			if(board.getButton(x)) {
				PT_index = x;
				AM_index = x;
				MAXIMUM_OVERDRIVE.set(false);
				PT_setpoint = PT_setpoints[PT_index];
				AM_setpoint = AM_setpoints[AM_index];
			} //if
		}//for
		if(board.getButton(11)) {
			PT_index = 0;
			AM_index = 0;
			MAXIMUM_OVERDRIVE.set(false);
			PT_setpoint = PT_setpoints[PT_index];
			AM_setpoint = AM_setpoints[AM_index];
		}
		if(operator.getLeftCenterButton()) {
			PT_setpoint -=3;
			Feedback.getInstance().resetPivot();
		}
		
		}
	}

	
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
	
}

// Emily Lauren Roth was here!!!