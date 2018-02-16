package org.texastorque.io;

import org.texastorque.torquelib.util.GenericController;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

public class OperatorConsole {

	/**
	 * Same concept as 2015 for custom controls in 2018 - Jonathon
	 * Oh ok - Daniel
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
    
    public boolean getPivotButton1() {
    	return board.getRawButton(1);
    }
    
    public boolean getPivotButton2() {
    	return board.getRawButton(2);
    }
    
    public boolean getPivotButton3() {
    	return board.getRawButton(3);
    }
    
    public boolean getPivotButton4() {
    	return board.getRawButton(4);
    }
    
    public boolean getPivotButton5() {
    	return board.getRawButton(5);
    }
    
    public boolean getPivotButton6() {
    	return board.getRawButton(6);
    }
    
    public boolean getPivotButton7() {
    	return board.getRawButton(7);
    }
    
    public boolean getPivotButton8() {
    	return board.getRawButton(8);
    }
    
    public boolean getPivotButton9() {
    	return board.getRawButton(9);
    }
    
    public boolean getButton10() {
    	return board.getRawButton(10);
    }
    
    public boolean getButton11() {
    	return board.getRawButton(11);
    }
    
    public boolean getButton12() {
    	return board.getRawButton(12);
    }
    
    public boolean getButton13() {
    	return board.getRawButton(13);
    }
    
    public boolean getButton14() {
    	return board.getRawButton(14);
    }
    
    public boolean getButton15() {
    	return board.getRawButton(15);
    }
    
    public boolean getButton16() {
    	return board.getRawButton(16);
    }
}
