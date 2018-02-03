package org.texastorque.auto;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.time.LocalDateTime;

import org.texastorque.feedback.Feedback;
import org.texastorque.io.Input;
import org.texastorque.io.InputRecorder;
import org.texastorque.io.RobotOutput;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoMode extends Input{

	public double[] DB_leftSpeeds;
	public double[] DB_rightSpeeds;
	public double[] DB_leftDistances;
	public double[] DB_rightDistances;
	
	private double leftDistanceCorrection = 0;
	private double rightDistanceCorrection = 0;

	private double currentLeftDistance;
	private double currentRightDistance;
	private double expectedLeftDistance;
	private double expectedRightDistance;
	private double leftError;
	private double rightError;
	private double leftPrevError = 0;
	private double rightPrevError = 0;
	private double leftDeltaError;
	private double rightDeltaError;
	
	private static final double kP = 4.0;
	private static final double kI = 4.0;
	private static final double snake = 4.0;
	
	private static RobotOutput o;
	private int index;
	
	public AutoMode(){
		o = RobotOutput.getInstance();
		DB_leftSpeeds = new double[1500];
		DB_rightSpeeds= new double[1500];
		DB_leftDistances = new double[1500];
		DB_rightDistances = new double[1500];
		index = 0;
	}
	
	public void setDBLeftSpeed(int index, double value) {
		DB_leftSpeeds[index] = value;
	}
	
	public void setDBRightSpeed(int index, double value) {
		DB_rightSpeeds[index] = value;
	}
	
	public void setDBLeftDistance(int index, double value) {
		DB_leftDistances[index] = value;
	}
	
	public void setDBRightDistance(int index, double value) {
		DB_rightDistances[index] = value;
	}
	
	public void run(){
		if(index < 1500) {
			runDrive(index);
			index++;
		}
	}
	
	public void resetIndex() {
		index = 0;
	}
	
	public void runDrive(int index){
		tuneMode();
//		DB_leftSpeeds[index] += leftDistanceCorrection;
//		DB_rightSpeeds[index] *= .98;
		DB_rightSpeeds[index] += rightDistanceCorrection;
		o.setDrivebaseSpeed(DB_leftSpeeds[index], DB_rightSpeeds[index]);
	}

	/*
	 * runDrive puts the values in the ArrayList to the motors then takes those values out of the ArrayList
	 * This is necessary because it prevents the loop in the other file from calling the first value endlessly
	 */
		
	public double getLeftDeltaError() {
		return leftDeltaError;
		
	}
	
	public double getRightDeltaError() {
		return rightDeltaError;
	}

	
	private void tuneMode(){
	/*	currentLeftDistance = Feedback.getInstance().getLeftEncoder().getDistance();
		currentRightDistance = Feedback.getInstance().getRightEncoder().getDistance();
		expectedLeftDistance = DB_leftDistances[index];
		expectedRightDistance = DB_rightDistances[index];
		leftError = currentLeftDistance - expectedLeftDistance;
		rightError = currentRightDistance - expectedRightDistance;
		fix();
		System.out.println(index + "--"+ currentRightDistance + "--" + rightError + "--" + rightPrevError + 
				"--" + rightDeltaError);
		leftDeltaError = leftError - leftPrevError;
		rightDeltaError = rightError - rightPrevError;
		
		leftPrevError = leftError;
		rightPrevError = rightError;
	*/
	}
	
	private void fix() {
		/*if(leftError > 4) {
			leftDistanceCorrection = 
					DB_leftSpeed * leftError / Feedback.getInstance().getLeftEncoder().getRate();
		}
		if(rightError > 4) {
			rightDistanceCorrection = 
					-1 * (DB_rightSpeed * rightError / Feedback.getInstance().getRightEncoder().getRate());
		}
		*/
	}
	
	public void SmartDashboard() {
		SmartDashboard.putNumber("leftDeltaError", leftDeltaError);
		SmartDashboard.putNumber("rightDeltaError", rightDeltaError);
	}

	
	
}
