package org.texastorque.io;

import org.texastorque.torquelib.util.GenericController;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

public class OperatorConsole {

	/*
	 * Same concept as 2015 for custom controls in 2018 - Jonathon
	 * 
	 */
	
    private GenericController joystick;

    public OperatorConsole(int port) {
        joystick = new GenericController(port, .1);
    }
    
    public boolean getButton(int x) {
    	if(x != 7 && x != 8) {
    		return joystick.getRawButton(x);
    	} else if(x == 7) {
    		return joystick.getRawButton(11);
    	}
    	return joystick.getRawButton(12);
    }
    
    public double getSlider() {
    	return joystick.getRightYAxis();
    }
    
    public double getDial() {
    	return joystick.getLeftXAxis();
    }
}
