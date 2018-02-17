package org.texastorque.io;

import org.texastorque.torquelib.util.GenericController;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

public class OperatorConsole {

	/*
	 * Same concept as 2015 for custom controls in 2018 - Jonathon
	 * 
	 */
	
    private GenericController board;

    public OperatorConsole(int port) {
        board = new GenericController(port, .1);
    }
    
    public boolean getButton(int x) {
    	System.out.println("Button " + x);
    	if (x == 7) {
    		return board.getRawButton(11);
    	}
    	else if (x != 8) {
    		return board.getRawButton(x);
    	}
    	return board.getRawButton(12);
    }
    
    public double getSlider() {
    	return board.getRightYAxis();
    }
    
    public double getDial() {
    	return board.getLeftXAxis();
    }
}
