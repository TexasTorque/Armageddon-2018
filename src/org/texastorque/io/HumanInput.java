package org.texastorque.io;

import org.texastorque.torquelib.util.GenericController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HumanInput extends Input {

	public static HumanInput instance;

	private GenericController driver;
	private GenericController operator;
	private OperatorConsole board;
	
	public HumanInput() {
		init();
	}

	public void init() {
		driver = new GenericController(0 , .1);
		operator = new GenericController(1, .1);
		board = new OperatorConsole(2);
	}
	
	public void update() {
		updateDrive();
		updatePivot();
		updateArm();
		updateClaw();
		updateWheelIntake();
	}
	
	public void updateDrive() {
		DB_leftSpeed = driver.getLeftYAxis() + driver.getRightXAxis();
		DB_rightSpeed = driver.getLeftYAxis() - driver.getRightXAxis();
	}
	
	private int counter = 0; //for testing
	
	public void updatePivot() {
		for(int x = 1; x <= 9; x++) {
			if (board.getButton(x)) {
				if (counter % 50 == 0)
					System.out.println("Button " + x);
				PT_setpoint = x;
			}
		}
	}
	
	public void updateArm() {
		if (counter % 50 == 0)
			System.out.println(board.getSlider());
		counter++;
	}
	
	public void updateClaw() {
		CL_closed.calc(operator.getBButton());
		CL_closed.calc(driver.getYButton());
	}
	 

	public void updateWheelIntake() {
		if(operator.getLeftBumper()) {
			IN_speed = -.25;
		} else if(operator.getRightBumper()) {
			IN_speed = .25;
		} else IN_speed = 0;
	
		IN_down.calc(operator.getXButton());
		IN_out.calc(operator.getAButton());
		
	}
		
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
	
}
