package org.texastorque.io;

import org.texastorque.torquelib.util.GenericController;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

public class OperatorConsole extends Joystick {

	/**
	 * Same concept as 2015 for custom controls in 2018 - Jonathon
	 * Oh ok - Daniel
	 */

    public OperatorConsole(int port) {
        super(port);
    }
    
    public synchronized boolean getButton(int x) {
    	return getRawButton(x);
    }
    
    public synchronized double getSlider() {
    	return getRawAxis(5);
    }
    
    //Not used
    public synchronized double getDial() {
    	return getRawAxis(4);
    }
}
