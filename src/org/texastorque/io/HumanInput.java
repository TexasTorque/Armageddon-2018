package org.texastorque.io;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.playback.HumanInputRecorder;
import org.texastorque.feedback.Feedback;
import org.texastorque.subsystems.Arm;
import org.texastorque.subsystems.Pivot;
import org.texastorque.torquelib.util.GenericController;

public class HumanInput extends Input {

	private static final double PIVOT_MAX_ENCODER = 320; // click-value
	private static final double DIAL_RANGE = 0.118;
	private static final double ENCODER_DIAL_CONVERSION = PIVOT_MAX_ENCODER / DIAL_RANGE;

	public static HumanInput instance;

	public GenericController driver;
	public  GenericController operator;
	public OperatorConsole board;

	protected Feedback feedback;

	public HumanInput() {
		driver = new GenericController(0, .1);
		operator = new GenericController(1, .1);
		board = new OperatorConsole(2);

		feedback = Feedback.getInstance();
	}

	public void update() {
		updateDrive();
		updateClaw();
		updateWheelIntake();
		updateBoardSubsystems();
		updateKill();
	}

	public void updateDrive() {
		DB_leftSpeed = -driver.getLeftYAxis() + .75 * driver.getRightXAxis();
		DB_rightSpeed = -driver.getLeftYAxis() - .75 * driver.getRightXAxis();
		
	}

	public void updateFile() {
		if (driver.getYButton()) {
			AutoManager.getInstance();
		}
	}

	public void updateClaw() {
		CL_closed.calc(operator.getBButton());
	}

	public void updateWheelIntake() {
		IN_down.calc(operator.getXButton());
		IN_out.calc(driver.getAButton());
		if (driver.getLeftBumper()) {
			startIntaking();
		} else if (driver.getRightBumper()) {
			startOutaking();
		} else {
			stopSpinning();
		}
	}

	public void updateBoardSubsystems() {
		if (getEncodersDead()) {
			updatePivotArmBackup();
		} else {
			MAXIMUM_OVERDRIVE.calc(board.getButton(10));

			if (MAXIMUM_OVERDRIVE.get()) {
				handleManualOverride();
			} else {
				handleInputs();
			}
		}
	}

	private void handleManualOverride() {
		AM_setpoint = board.getSlider() * AM_CONVERSION;
		PT_setpoint = (int) (Math.round(board.getDial() * ENCODER_DIAL_CONVERSION));
	}

	private void handleDriverInputs() {
		if (driver.getBButton()) {
			AM_setpoint = 950;
			if(feedback.getArmDistance() > 850)
				PT_setpoint = 85.0;
		} 
		if (driver.getXButton()) {
			AM_setpoint = -2000;
		}

		if (driver.getRawButtonReleased(driver.controllerMap[14])) {
			AM_setpoint = Feedback.getInstance().getArmDistance();
			AM_speed = 0;
		}

		if (driver.getYButton()) {
			climbing = false;
		}
	}

	private void handleOperatorInputs() {
		if (operator.getYButtonPressed()) {
			setClaw(true);
			Pivot.getInstance().teleopSetDelay(0.25);
			Arm.getInstance().teleopSetDelay(0.25);
			PT_index = 10;
			AM_index = 10;
			MAXIMUM_OVERDRIVE.set(false);
			PT_setpoint = PT_setpoints[PT_index];
			AM_setpoint = AM_setpoints[AM_index];
		} 
		else if (operator.getYButtonReleased()) {
			setClaw(false);
			setIntakeOut(false);
			Pivot.getInstance().teleopSetDelay(0.4);
			Arm.getInstance().teleopSetDelay(0.4);
			PT_index = 0;
			AM_index = 0;
			MAXIMUM_OVERDRIVE.set(false);
			PT_setpoint = PT_setpoints[PT_index];
			AM_setpoint = AM_setpoints[AM_index];
		}
	}

	private void handleBoardInputs() {
		for (int x = 1; x < 10; x++) {
			if(board.getButton(x)) {
				board.setCurrentButton(x);
				PT_index = x;
				AM_index = x;
				MAXIMUM_OVERDRIVE.set(false);
				PT_setpoint = PT_setpoints[PT_index];
				AM_setpoint = AM_setpoints[AM_index];
			}
		}

		if (board.getButton(11)) {
			setClaw(true);
			board.setCurrentButton(11);
			PT_index = 0;
			AM_index = 0;
			MAXIMUM_OVERDRIVE.set(false);
			PT_setpoint = PT_setpoints[PT_index];
			AM_setpoint = AM_setpoints[AM_index];
		}

	}

	private void handleInputs() {
		handleDriverInputs();
		handleOperatorInputs();
		handleBoardInputs();
	}

	private static boolean didReleaseYButton(GenericController controller) {
		int rawYButton = controller.controllerMap[15];
		return controller.getRawButtonReleased(rawYButton);
	}

	private void updatePivotArmBackup() {
		if (operator.getDPADLeft()) {
			pivotCCW = true;
		} else if (operator.getDPADRight()) {
			pivotCW = true;
			pivotCCW = false;
		} else {
			pivotCCW = false;
			pivotCW = false;
		}

		if (operator.getDPADUp()) {
			armFWD = true;
		} else if (operator.getDPADDown()) {
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