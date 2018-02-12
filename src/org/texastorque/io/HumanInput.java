package org.texastorque.io;

import org.texastorque.torquelib.util.GenericController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HumanInput extends Input {

	public static HumanInput instance;

	private GenericController driver;
	private GenericController operator;
	private OperatorConsole board;
	
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
		updateArm();
		updateClaw();
		updateWheelIntake();
		updatePivot();

	}
	
	public void updateDrive() {
		DB_leftSpeed = -driver.getLeftYAxis() + driver.getRightXAxis();
		DB_rightSpeed = -driver.getLeftYAxis() - driver.getRightXAxis();
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
