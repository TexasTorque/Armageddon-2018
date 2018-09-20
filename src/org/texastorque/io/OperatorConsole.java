package org.texastorque.io;

import edu.wpi.first.wpilibj.Joystick;

public class OperatorConsole extends Joystick {

	/**
	 * Same concept as 2015 for custom controls in 2018 - Jonathon Oh ok - Daniel
	 */

	public int currentButton;
	
    public OperatorConsole(int port) {
        super(port);
        currentButton = 0;
    }
    
    public synchronized boolean getButton(int x) {
    	return getRawButton(x);
    }
    
    public void setCurrentButton(int x) {
    	currentButton = x;
    }
    
    public synchronized double getSlider() {
    	return getRawAxis(5);
    }
    
    //Not used
    public synchronized double getDial() {
    	return getRawAxis(4);
    }
}
