package org.texastorque.auto.sequences;

import org.texastorque.auto.AutoSequence;
import org.texastorque.auto.arm.SetClaw;
import org.texastorque.auto.arm.ShiftPivotArm;
import org.texastorque.auto.drive.Drive;
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
			commandList.add(new ShiftPivotArm(4, 5.0, false, 0.0));
			commandList.add(new Drive(172, 0.125, 2.75, true));
			commandList.add(new Turn(20, 1.5, 0.75, true));
			commandList.add(new Drive(96, .125, 1.5, true));
			commandList.add(new Turn(0, 1.5, 1.25, true));
			commandList.add(new SetClaw(true));
			
			commandList.add(new ShiftPivotArm(8, 2.0, true, 0.0));
			commandList.add(new Drive(-52, 0.125, 2.0, true));
			commandList.add(new SetClaw(false));
			
			commandList.add(new ShiftPivotArm(4, 2.5, false, 0.0));
			commandList.add(new Drive(52, 0.125, 2.5, true));
			commandList.add(new SetClaw(false));
		} else {
			commandList.add(new ShiftPivotArm(4, 5.0, false, 6.0));
			commandList.add(new Drive(220, 0.125, 3.25, true));
			commandList.add(new Turn(90, 1.5, 2.25, true));
			commandList.add(new Drive(178, 0.125, 3.25, true));
			commandList.add(new Turn(0, 1.5, 2.25, true));
			commandList.add(new Drive(48, 0.125, 2.0, true));
			commandList.add(new SetClaw(false));
			
			commandList.add(new ShiftPivotArm(8, 4.0, false, 0.0));
			commandList.add(new Drive(-72, 0.125, 4.0, true));
			commandList.add(new SetClaw(true));
			
			commandList.add(new ShiftPivotArm(4, 4.0, false, 0.0));
			commandList.add(new Drive(72, 0.125, 4.0, true));
			commandList.add(new SetClaw(false));
		}
	}

	private void handlePosition3() {
		if (scaleSide == 'R') {
			
			commandList.add(new ShiftPivotArm(4, 5.0, false, 0.0));
			commandList.add(new Drive(172, 0.125, 4.0, true));
			commandList.add(new Turn(-25, 1.5, 2.25, true));
			commandList.add(new Drive(108, .125, 1, true));
			commandList.add(new Turn(0, 1.5, 2.25, true));
			commandList.add(new SetClaw(false));
			commandList.add(new SetClaw(true));
			commandList.add(new ShiftPivotArm(8, 4.0, false, 0.0));
			commandList.add(new Drive(-72, 0.125, 4.0, true));
			commandList.add(new SetClaw(true));
			
			commandList.add(new ShiftPivotArm(4, 4.0, false, 0.0));
			commandList.add(new Drive(72, 0.125, 4.0, true));
			commandList.add(new SetClaw(false));
		} else {
			commandList.add(new ShiftPivotArm(4, 5.0, false, 6.0));
			commandList.add(new Drive(220, 0.125, 3.25, true));
			commandList.add(new Turn(-90, 1.5, 2.25, true));
			commandList.add(new Drive(178, 0.125, 3.25, true));
			commandList.add(new Turn(0, 1.5, 2.25, true));
			commandList.add(new Drive(48, 0.125, 2.0, true));
			commandList.add(new SetClaw(false));
			
			commandList.add(new ShiftPivotArm(8, 4.0, false, 0.0));
			commandList.add(new Drive(-72, 0.125, 4.0, true));
			commandList.add(new SetClaw(true));
			
			commandList.add(new ShiftPivotArm(4, 4.0, false, 0.0));
			commandList.add(new Drive(72, 0.125, 4.0, true));
			commandList.add(new SetClaw(false));
	
		}
	}

}
