package org.texastorque.auto.sequences;

import org.texastorque.auto.AutoSequence;
import org.texastorque.auto.arm.SetClaw;
import org.texastorque.auto.arm.ShiftPivotArm;
import org.texastorque.auto.drive.Drive;
import org.texastorque.auto.drive.Turn;

import edu.wpi.first.wpilibj.DriverStation;

public class PlaceCubeScaleDebug extends AutoSequence {

	private static final double DEBUG_DRIVE = 10;
	private static final int SWITCH_INDEX = 0;

	private final int startPos;
	private char scaleSide;

	public PlaceCubeScaleDebug(int start) {
		startPos = start;
		try {
			scaleSide = DriverStation.getInstance().getGameSpecificMessage().charAt(1);
		} catch (Exception e) {
			scaleSide = 'X';
		}
		init();
	}

	@Override
	public void init() {
		System.out.println("init PlaceCubeScaleDebug");
		if (startPos == 1) {
			handleStartOnLeft();
		} else if (startPos == 3) {
			handleStartOnRight();
		} else if (startPos == -1 || scaleSide == 'X') {
			commandList.add(new Drive(DEBUG_DRIVE, .125, 3.25, true));
		}

		commandList.add(new SetClaw(true, true)); // Open claw after any sequence.
		commandList.add(new Drive(DEBUG_DRIVE, .125, 1.0, true));
		commandList.add(new Drive(-DEBUG_DRIVE, .125, 2.0, true));
		commandList.add(new ShiftPivotArm(SWITCH_INDEX, 5.0, true, 0));
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
		commandList.add(new Drive(DEBUG_DRIVE, 0.125, 4.0, true));
		commandList.add(new Turn(turnSign * 45, 1.5, 1.5, true));
		commandList.add(new Drive(DEBUG_DRIVE, .125, 1.0, true));
	}

	private void driveAndCross(boolean startSideLeft) {
		double turnSign = startSideLeft ? 1 : -1;

		commandList.add(new ShiftPivotArm(4, 5.0, false, 6.0));
		commandList.add(new Drive(DEBUG_DRIVE, 0.125, 3.25, true));
		commandList.add(new Turn(turnSign * 88, 1.5, 1.5, true));
		commandList.add(new Drive(DEBUG_DRIVE, 0.125, 3.25, true));
		commandList.add(new Turn(0, 1.5, 2.0, true));
		commandList.add(new Drive(DEBUG_DRIVE, 0.125, 1.75, true));
	}

	@Override
	public void initCommandLists() {
		// TODO Auto-generated method stub
		
	}
}
