package org.texastorque.auto.sequences;

import java.util.ArrayList;

import org.texastorque.auto.AutoCommand;
import org.texastorque.auto.AutoSequence;
import org.texastorque.auto.arm.SetClaw;
import org.texastorque.auto.arm.ShiftPivotArm;
import org.texastorque.auto.drive.Drive;
import org.texastorque.auto.drive.Turn;

import edu.wpi.first.wpilibj.DriverStation;

public class PlaceCubeScale extends AutoSequence {

	private final int startPos;
	private char scaleSide;

	public PlaceCubeScale(int start) {
		startPos = start;
		scaleSide = fieldConfig.charAt(1);
	//	init();
	}

	
	@Override
	public void init() {
		scaleSide = fieldConfig.charAt(1);
		System.out.println("init PlaceCubeScale");
		if (startPos == 1) {
			handleStartOnLeft();
		} else if (startPos == 3) {
			handleStartOnRight();
		} else if (startPos == -1 || scaleSide == 'X') {
			commandList.add(new Drive(220, .125, 3.25, true));
		}

		commandList.add(new SetClaw(true, true)); // Open claw after any sequence.
		commandList.add(new Drive(1, .125, 1.0, true));
		commandList.add(new Drive(-24, .125, 2.0, true));
		commandList.add(new ShiftPivotArm(0, 5.0, true, 0));
	}
	
	@Override
	public void initCommandLists() {
		// TODO Auto-generated method stub
		
	}
	
	private void handleStartOnLeft() {
		if (scaleSide == 'L') {
			driveToScale(true);
		} else {
			driveAndCross(true);
		}
	}

	private void handleStartOnRight() {
		if (scaleSide == 'R') {
			driveToScale(false);
		} else {
			driveAndCross(false);
		}
	}
	
	private void driveToScale(boolean startSideLeft) {
		double turnSign = startSideLeft ? 1 : -1;

		commandList.add(new ShiftPivotArm(4, 5.0, false, 0.0));
		commandList.add(new Drive(260, 0.125, 4.0, true));
		commandList.add(new Turn(turnSign * 45, 1.5, 1.5, true));
		commandList.add(new Drive(28, .125, 1.0, true));
	}

	private void driveAndCross(boolean startSideLeft) {
		double turnSign = startSideLeft ? 1 : -1;

		commandList.add(new ShiftPivotArm(4, 5.0, false, 6.0));
		commandList.add(new Drive(228, 0.125, 3.25, true));
		commandList.add(new Turn(turnSign * 88, 1.5, 1.5, true));
		commandList.add(new Drive(178, 0.125, 3.25, true));
		commandList.add(new Turn(0, 1.5, 2.0, true));
		commandList.add(new Drive(48, 0.125, 1.75, true));
	}
	
	@Deprecated
	private void handlePosition1() {

		if (scaleSide == 'L') {
			commandList.add(new ShiftPivotArm(4, 5.0, false, 0.0));
			commandList.add(new Drive(260, 0.125, 4.0, true));
			commandList.add(new Turn(45, 1.5, 1.5, true));
			commandList.add(new Drive(28, .125, 1.0, true));
		} else {
			commandList.add(new ShiftPivotArm(4, 5.0, false, 6.0));
			commandList.add(new Drive(220, 0.125, 3.25, true));
			commandList.add(new Turn(90, 1.5, 1.5, true));
			commandList.add(new Drive(178, 0.125, 3.25, true));
			commandList.add(new Turn(0, 1.5, 2.0, true));
			commandList.add(new Drive(44, 0.125, 1.75, true));
		}
	}
	
	@Deprecated
	private void handlePosition3() {
		if (scaleSide == 'R') {
			commandList.add(new ShiftPivotArm(4, 5.0, false, 0.0));
			commandList.add(new Drive(260, 0.125, 4.0, true));
			commandList.add(new Turn(-45, 1.5, 1.5, true));
			commandList.add(new Drive(28, .125, 1.0, true));
		} else {
			commandList.add(new ShiftPivotArm(4, 5.0, false, 6.0));
			commandList.add(new Drive(220, 0.125, 3.25, true));
			commandList.add(new Turn(-90, 1.5, 1.5, true));
			commandList.add(new Drive(178, 0.125, 3.25, true));
			commandList.add(new Turn(0, 1.5, 2.0, true));
			commandList.add(new Drive(44, 0.125, 1.75, true));
		}
	}
	
}
