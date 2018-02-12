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
import org.texastorque.torquelib.controlLoop.TorquePID;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoMode extends Input{

	public String name;
	
	public double[] DB_leftSpeeds;
	public double[] DB_rightSpeeds;
	public double[] DB_leftEncoderSpeeds;
	public double[] DB_rightEncoderSpeeds;
	public double[] IN_speeds;
	public boolean[] IN_downStates;
	public boolean[] IN_outStates;
	public boolean[] CL_states;
	public double[] AM_speeds;
	public double[] PT_speeds;
	
	private double leftDistanceCorrection = 0;
	private double rightDistanceCorrection = 0;
	private double leftCorrectionNeeded = 0;
	private double rightCorrectionNeeded = 0;
	
	/*
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
	
	private TorquePID leftPID;
	private TorquePID rightPID;
	
	private double currentLeftSpeed;
	private double currentRightSpeed;
	private double expectedLeftSpeed;
	private double expectedRightSpeed;
	private double leftPercentError;
	private double rightPercentError;
	
	*/
	
	
	private double currentSumLeft = 0;
	private double expectedSumLeft = 0;
	private double currentSumRight = 0;
	private double expectedSumRight = 0;
	private double setpoint = 1.5;
	
	private double errorLeft;
	private double prevErrorLeft;
	private double errorRight;
	private double prevErrorRight;
	private static RobotOutput o;
	private int index;
	
	public AutoMode(){
		o = RobotOutput.getInstance();
		DB_leftSpeeds = new double[1500];
		DB_rightSpeeds= new double[1500];
		DB_leftEncoderSpeeds= new double[1500];
		DB_rightEncoderSpeeds= new double[1500];
		IN_speeds = new double[1500];
		IN_downStates = new boolean[1500];
		IN_outStates = new boolean[1500];
		CL_states = new boolean[1500];
		AM_speeds = new double[1500];
		PT_speeds = new double[1500];
		index = 0;
		name = "";
		/*
		leftPID = new TorquePID(.000000001, 0, 0);
		leftPID.setControllingSpeed(true);
		leftPID.setEpsilon(1);
		leftPID.setMaxOutput(.000000001);
		rightPID = new TorquePID(.000000001, 0, 0);
		rightPID.setControllingSpeed(true);
		rightPID.setEpsilon(1);
		rightPID.setMaxOutput(.000000001);
	*/
	}
	
	public void setDBLeftSpeed(int index, double value) {
		DB_leftSpeeds[index] = value;
	}
	
	public void setDBRightSpeed(int index, double value) {
		DB_rightSpeeds[index] = value;
	}
	
	public void setDBLeftEncoderRate(int index, double value) {
		DB_leftEncoderSpeeds[index] = value;
	}
	
	public void setDBRightEncoderRate(int index, double value) {
		DB_rightEncoderSpeeds[index] = value;
	}
	
	public void setAMSpeed(int index, double value) {
		AM_speeds[index] = value;
	}
	
	public void setCLState(int index, boolean on) {
		CL_states[index] = on;
	}
	
	public void setPTSpeed(int index, double speed) {
		PT_speeds[index] = speed;
	}
	
	public void setINPneumatics(int index, boolean down, boolean out) {
		IN_downStates[index] = down;
		IN_outStates[index] = out;
	}

	public void setINSpeed(int index, double speed) {
		IN_speeds[index] = speed;
	}
	
	public void run(){
		if(index < 1500) {
			runDrive(index);
			runArm(index);
			runClaw(index);
			runPivot(index);
			runIntake(index);
			index++;
		}
	}
	
	public void resetIndex() {
		index = 0;
	}
	
	public void runDrive(int index){
		tuneMode();
		o.setDrivebaseSpeed(DB_leftSpeeds[index], DB_rightSpeeds[index]);
	}
	
	public void runArm(int index) {
		o.setArmSpeed(AM_speeds[index]);
	}
	
	public void runClaw(int index) {
		o.setClaw(CL_states[index]);
	}
	
	public void runPivot(int index) {
		o.setPivotSpeed(PT_speeds[index]);
	}
	
	public void runIntake(int index) {
		o.setIntakeSpeed(IN_speeds[index]);
		o.setIntakePneumatics(IN_outStates[index], IN_downStates[index]);
	}
	
	private void tuneMode() {
		updateValues();
		calculateCorrection();
		fix();
//		System.out.println(errorLeft + "/t\t" + errorRight);
		//compare the sum of the rates as time elapses and if it doesn't line up then correct it
	}
	
	private void updateValues() {
		currentSumLeft += Feedback.getInstance().getLeftEncoder().getRate() * .01;
		expectedSumLeft += DB_leftEncoderSpeeds[index] * .01;
		errorLeft = expectedSumLeft - currentSumLeft;
		leftCorrectionNeeded = TorqueMathUtil.decreaseMagnitude(errorLeft, setpoint);

		currentSumRight+= Feedback.getInstance().getRightEncoder().getRate() * .01;
		expectedSumRight += DB_rightEncoderSpeeds[index] * .01;
		errorRight = expectedSumRight - currentSumRight;
		rightCorrectionNeeded = TorqueMathUtil.decreaseMagnitude(errorRight, setpoint);
	}
	
	private void calculateCorrection() {
		calculateCorrection(leftCorrectionNeeded, 'L');
		calculateCorrection(rightCorrectionNeeded, 'R');
	}
	
	private void calculateCorrection(double correctionDeterminer, char side) {	
		if(side == 'L') {
			leftDistanceCorrection = correctionDeterminer / (index * 10);
		} else rightDistanceCorrection = correctionDeterminer / (index * 10);
			
	}
	
	/*
	switch(correctionDeterminer) {
	7	case 1: 
			if(left)
				leftDistanceCorrection = smallNumber that would get larger as the cases get higher if the equation I write doesn't work
			break;
		case 2:
			break;
			etc
	*/

	private void fix() {
		DB_leftSpeeds[index] += leftDistanceCorrection;
		DB_rightSpeeds[index] += rightDistanceCorrection;
	}
	
}




/*
 * runDrive puts the values in the ArrayList to the motors then takes those values out of the ArrayList
 * This is necessary because it prevents the loop in the other file from calling the first value endlessly
 *		
public double getLeftDeltaError() {
	return leftDeltaError;
}

public double getRightDeltaError() {
	return rightDeltaError;
}
/*
public void tuneMode() {
	tuneLeft();
	tuneRight();
}


public void tuneLeft() {
	currentLeftDistance = Feedback.getInstance().getLeftEncoder().getDistance();
	leftPID.setSetpoint(DB_leftDistances[index]);
	leftDistanceCorrection = leftPID.calculate(currentLeftDistance) - currentLeftDistance;
	DB_leftSpeeds[index] += leftDistanceCorrection;
}

public void tuneRight() {
	currentRightDistance = Feedback.getInstance().getRightEncoder().getDistance();
	rightPID.setSetpoint(DB_rightDistances[index]);
	rightDistanceCorrection = rightPID.calculate(currentRightDistance) - currentRightDistance;
	DB_rightSpeeds[index] +=rightDistanceCorrection;
}

public void SmartDashboard() {
	SmartDashboard.putNumber("leftDeltaError", leftDeltaError);
	SmartDashboard.putNumber("rightDeltaError", rightDeltaError);
}
private void tuneMode(){
	currentLeftDistance = Feedback.getInstance().getLeftEncoder().getDistance();
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

}

private void fix() {
	if(leftError > 4) {
		leftDistanceCorrection = 
				DB_leftSpeed * leftError / Feedback.getInstance().getLeftEncoder().getRate();
	}
	if(rightError > 4) {
		rightDistanceCorrection = 
				-1 * (DB_rightSpeed * rightError / Feedback.getInstance().getRightEncoder().getRate());
	}
	
}
*/
/*
private void tuneMode() {
	currentLeftSpeed = Feedback.getInstance().getLeftEncoder().getRate(); //NEED TO MAKE A CONVERSION
	currentRightSpeed = Feedback.getInstance().getRightEncoder().getRate(); //FACTOR  
	//These are currently in units fps, naturally in units radians/rpm
	//actually I think naturally the enc
	expectedLeftSpeed = DB_leftEncoderSpeeds[index]; 
	expectedRightSpeed = DB_rightEncoderSpeeds[index]; 
	//These are encoder readings as well
	leftError = expectedLeftSpeed - currentLeftSpeed;
	rightError = expectedRightSpeed - currentRightSpeed;
	//This needs to be in units from -1 to 1 but for that we need to convert rate values
	//right now they would basically always be -1 or 1, but they would be opposite of intended as well
	leftPercentError = leftError / expectedLeftSpeed;
	rightPercentError = rightError / expectedRightSpeed;
	//percent off it is
	if(leftPercentError > -1 && rightPercentError > -1)
		DB_leftEncoderSpeeds[index] *= 1 + leftPercentError;
		DB_rightEncoderSpeeds[index] *= 1 + rightPercentError;
	//need to change it if percent error is less than 0
}

private void fix() {
	
}
*/
	