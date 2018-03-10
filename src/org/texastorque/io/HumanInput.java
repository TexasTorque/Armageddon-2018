package org.texastorque.io;

import org.texastorque.auto.AutoManager;
import org.texastorque.feedback.Feedback;
import org.texastorque.torquelib.util.GenericController;
import org.texastorque.torquelib.util.TorqueMathUtil;
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
	
	protected Feedback feedback;
	
	private int PT_test;
	
	public HumanInput() {
		driver = new GenericController(0 , .1);
		operator = new GenericController(1, .1);
		board = new OperatorConsole(2);
		
		feedback = Feedback.getInstance();
		
		PT_test = 0;
	}
	
	public void update() {
		updateDrive();
		updateClaw();
//		updateFile();
		updateWheelIntake();
		updateBoardSubsystems();
		updateKill();
	}
	
	public void updateDrive() {
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
	
	public void updateClaw() {
		CL_closed.calc(operator.getBButton());
	}
	
	public void updateWheelIntake() {
		IN_down.calc(operator.getXButton());
		IN_out.calc(driver.getAButton());
		if(driver.getLeftBumper()) {
			IN_speed = -.5;
		} else if(driver.getRightBumper()) {
			IN_speed = .35;
		} else IN_speed = 0;
	}
	
	public void updateBoardSubsystems() {	
		if(getEncodersDead()) {
			updatePivotArmBackup();
		} 
		else {
			MAXIMUM_OVERDRIVE.calc(board.getButton(10));

			if(MAXIMUM_OVERDRIVE.get()) {
				AM_setpoint = board.getSlider() * AM_CONVERSION;
				PT_setpoint = (int)(Math.round(board.getDial() / 0.00787401571)) * 18;			
			}
			else {
				updateNotManualOverride();
			} //if not manual override
		} //if encoders not dead
	} //method close

	private void updateNotManualOverride() {
		if(driver.getXButton()) {
			climbing = true;
			AM_setpoint = 0;
		} else {
			climbing = false;
		}
		/*
		if(operator.getLeftCenterButton() || operator.getRightCenterButton()) {
			Feedback.getInstance().resetArmEncoders();
			PT_index = 0;
			AM_index = 0;
			PT_setpoint = PT_setpoints[PT_index];
			AM_setpoint = AM_setpoints[AM_index];
		}*/
		
		if (operator.getYButton()) {
			setClaw(false);
			PT_index = 10;
			AM_index = 10;
			MAXIMUM_OVERDRIVE.set(false);
			PT_setpoint = PT_setpoints[PT_index];
			AM_setpoint = AM_setpoints[AM_index];
		} 
		else if (operator.getRawButtonReleased(operator.controllerMap[15])) {
			setClaw(true);
			PT_index = 0;
			AM_index = 0;
			MAXIMUM_OVERDRIVE.set(false);
			PT_setpoint = PT_setpoints[PT_index];
			AM_setpoint = AM_setpoints[AM_index];
		}
		
		for (int x = 1; x < 10; x++) {
			if(board.getButton(x)) {
				PT_index = x;
				AM_index = x;
				MAXIMUM_OVERDRIVE.set(false);
				PT_setpoint = PT_setpoints[PT_index];
				AM_setpoint = AM_setpoints[AM_index];
			} 
		}
		if(board.getButton(11)) {
			PT_index = 0;
			AM_index = 0;
			MAXIMUM_OVERDRIVE.set(false);
			PT_setpoint = PT_setpoints[PT_index];
			AM_setpoint = AM_setpoints[AM_index];
		}
	}
	
	private void updatePivotArmBackup() {
		if(operator.getDPADLeft())
			pivotCCW = true;
		else if(operator.getDPADRight()) {
			pivotCW = true;
			pivotCCW = false;
		} else {
			pivotCCW = false;
			pivotCW = false;
		}
		if(operator.getDPADUp()) {
			armFWD = true;
		} else if(operator.getDPADDown()) {
			armBACK = true;
			armFWD = false;
		} else {
			armFWD = false;
			armBACK = false;
		}
	}
	
	public void updateKill() {
		encodersDead.calc(operator.getRightTrigger() && operator.getLeftTrigger());
	}
	
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
	
}

// Emily Lauren Roth was here!!!