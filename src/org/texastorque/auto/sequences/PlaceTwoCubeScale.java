package org.texastorque.auto.sequences;

import org.texastorque.auto.AutoSequence;
import org.texastorque.auto.arm.SetClaw;
import org.texastorque.auto.arm.SetIntake;
import org.texastorque.auto.arm.ShiftPivotArm;
import org.texastorque.auto.drive.Drive;
import org.texastorque.auto.drive.FirstTurnDrift;
import org.texastorque.auto.drive.Turn;

import edu.wpi.first.wpilibj.DriverStation;

public class PlaceTwoCubeScale extends AutoSequence {

	private final int startPos;
	private char scaleSide;

	public PlaceTwoCubeScale(int start) {
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
		System.out.println("init PlaceTwoCubeScale");
		if (startPos == 1) {
			handlePosition1();
		} else if (startPos == 3) {
			handlePosition3();
		} else if (startPos == -1 || scaleSide == 'X') {
			commandList.add(new Drive(220, .125, 3.25, true));
		}

	}

	private void handlePosition1() {
		if (scaleSide == 'L') {
			commandList.add(new ShiftPivotArm(6, 5.0, false, 1.5));
			commandList.add(new Drive(-216, 0.125, 2.75, true));
			commandList.add(new Turn(33, 1.25, 1.0, true));
			commandList.add(new Drive(-63, .125, 1.75, true));
			commandList.add(new SetClaw(true, false));
			commandList.add(new ShiftPivotArm(10, 4.0, false, 0.0));
			commandList.add(new SetIntake(true));
			commandList.add(new Turn(-12, 1.5, 1.5, true));
			commandList.add(new Drive(68, 0.125, 2.5, true));
			commandList.add(new SetClaw(false, false));
			commandList.add(new Turn(3, 1.5, 1.25, false));
			commandList.add(new SetIntake(false));
			commandList.add(new ShiftPivotArm(6, 4.0, false, 0.0));
			commandList.add(new Drive(-64, 0.125, 1.75, true));
			commandList.add(new SetClaw(true, false));
		} else {
			commandList.add(new ShiftPivotArm(6, 5.0, false, 6.0));
			commandList.add(new Drive(-214, 0.125, 3.25, true));
			commandList.add(new Turn(75, 1.5, 1.5, true));
			commandList.add(new Drive(-194, 0.125, 3, true));
			commandList.add(new Turn(0, 1.5, 1.5, true));
			commandList.add(new Drive(-52, 0.125, 2.0, true));
			commandList.add(new SetClaw(true, false));
			commandList.add(new ShiftPivotArm(10, 4.0, false, 0.0));
			commandList.add(new Drive(-65, 0.125, 4.0, true));
//			commandList.add(new SetClaw(true));
			
		}
	}

	private void handlePosition3() {
		if (scaleSide == 'R') {
			commandList.add(new ShiftPivotArm(6, 5.0, false, 1.5));
			commandList.add(new Drive(-216, 0.125, 2.75, true));
			commandList.add(new Turn(-33, 1.25, .75, true));
			commandList.add(new Drive(-63, .125, 2, true));
			commandList.add(new SetClaw(true, false));
			commandList.add(new ShiftPivotArm(10, 4.0, false, 0.0));
			commandList.add(new SetIntake(true));
			commandList.add(new Turn(12, 1.5, 1.5, true));
			commandList.add(new Drive(68, 0.125, 2.5, true));
			commandList.add(new SetClaw(false, false));
			commandList.add(new Turn(-3, 1.5, 1.25, false));
			commandList.add(new SetIntake(false));
			commandList.add(new ShiftPivotArm(6, 4.0, false, 0.0));
			commandList.add(new Drive(-64, 0.125, 1.75, true));
			commandList.add(new SetClaw(true, false));
			
			
		} else {
			commandList.add(new ShiftPivotArm(6, 5.0, false, 6.0));
			commandList.add(new Drive(-214, 0.125, 3.25, true));
			commandList.add(new Turn(-90, 1.5, 1.5, true));
			commandList.add(new Drive(-194, 0.125, 3, true));
			commandList.add(new Turn(0, 1.5, 1.5, true));
			commandList.add(new Drive(-52, 0.125, 2.0, true));
			commandList.add(new SetClaw(true, false));
			commandList.add(new ShiftPivotArm(10, 4.0, false, 0.0));
			commandList.add(new Drive(65, 0.125, 4.0, true));
			commandList.add(new SetClaw(false, true));
	
		}
	}

}
