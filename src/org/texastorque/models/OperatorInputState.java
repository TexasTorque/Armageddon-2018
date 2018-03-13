package org.texastorque.models;

import org.texastorque.io.OperatorConsole;
import org.texastorque.torquelib.util.GenericController;

public class OperatorInputState {
	
	public Vector2D leftStick;
	public Vector2D rightStick;
	
	public boolean buttonA, buttonB, buttonX, buttonY;
	public boolean buttonYReleased;
	
	public int currentState;
	
    public OperatorInputState() { }  // Required for deserialization.
	
	public OperatorInputState(GenericController controller, OperatorConsole board) {
		this.buttonA = controller.getAButton();
		this.buttonB = controller.getBButton();
		this.buttonX = controller.getXButton();
		this.buttonY = controller.getYButton();
		
		this.buttonYReleased = controller.getRawButtonReleased(controller.controllerMap[15]);
		
		this.currentState = board.currentButton;
	}
	
}
