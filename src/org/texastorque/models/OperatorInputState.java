package org.texastorque.models;

import org.texastorque.torquelib.util.GenericController;

public class OperatorInputState {
	
	Vector2D leftStick;
	Vector2D rightStick;
	
	boolean buttonA, buttonB, buttonX, buttonY;
	
	boolean triggerLeft, triggerRight;
	boolean bumperLeft, bumperRight;
	
	boolean clickLeftStick, clickRightStick;
	
    public OperatorInputState() { }  // Required for deserialization.
	
	public OperatorInputState(GenericController controller) {
		double leftX = controller.getLeftXAxis();
		double leftY = controller.getLeftYAxis();
		double rightX = controller.getRightXAxis();
		double rightY = controller.getRightYAxis();
		
		this.leftStick = new Vector2D(leftX, leftY);
		this.rightStick = new Vector2D(rightX, rightY);
		
		this.buttonA = controller.getAButton();
		this.buttonB = controller.getBButton();
		this.buttonX = controller.getXButton();
		this.buttonY = controller.getYButton();
		
		this.triggerLeft = controller.getLeftTrigger();
		this.triggerRight = controller.getRightTrigger();
		
		this.bumperLeft = controller.getLeftBumper();
		this.bumperRight = controller.getRightBumper();
		
		this.clickLeftStick = controller.getLeftStickClick();
		this.clickRightStick = controller.getRightStickClick();
	}
	
}
