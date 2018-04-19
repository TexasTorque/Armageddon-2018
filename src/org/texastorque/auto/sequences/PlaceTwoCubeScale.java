package org.texastorque.auto.sequences;

import org.texastorque.auto.AutoSequence;
import org.texastorque.auto.arm.SetClaw;
import org.texastorque.auto.arm.SetIntake;
import org.texastorque.auto.arm.ShiftPivotArm;
import org.texastorque.auto.drive.Drive;
import org.texastorque.auto.drive.FirstTurn;
import org.texastorque.auto.drive.Turn;

import edu.wpi.first.wpilibj.DriverStation;

public class PlaceTwoCubeScale extends AutoSequence {

	private final int startPos;
	private char scaleSide;

	public PlaceTwoCubeScale(int start) {
		startPos = start;
		scaleSide = fieldConfig.charAt(1);
	}

	@Override
	public void init() {
		scaleSide = getFieldConfig().charAt(1);
		System.out.println("init PlaceTwoCubeScale" + scaleSide);
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
			commandList.add(new Drive(-224, 0.125, 3.5, true));
			commandList.add(new Turn(28, 1.25, .75, true));
			commandList.add(new Drive(-54, .125, 1.5, true));
			commandList.add(new SetClaw(true, true));
			commandList.add(new ShiftPivotArm(0, 4.0, false, 0.5));
			commandList.add(new SetIntake(true));
			commandList.add(new Turn(-9, 1.5, 1.5, true));
			commandList.add(new Drive(71, 0.125, 2.0, true));
			commandList.add(new Turn(-5, .125, 1.0, true));
			commandList.add(new ShiftPivotArm(10, 1.5, true, 0));
			commandList.add(new SetClaw(false, true));			
			commandList.add(new SetIntake(false, true));
			commandList.add(new ShiftPivotArm(0, .25, true, 0));
			commandList.add(new Drive(-68, 0.125, 2.5, false));
			commandList.add(new ShiftPivotArm(6, 2.0, true, 0));
			commandList.add(new SetClaw(true, true));	
			} else {
			commandList.add(new ShiftPivotArm(6, 5.0, false, 4.0));
			commandList.add(new FirstTurn(true, false));
			commandList.add(new Turn(0, 0.125, 1.5, true));
			commandList.add(new Drive(-72, .125, 2.0, true));
			commandList.add(new SetClaw(true, true));
			commandList.add(new ShiftPivotArm(0, 4.0, false, 0.5));
			commandList.add(new SetIntake(true));
			commandList.add(new Turn(2, 1.5, 1.5, true));
			commandList.add(new Drive(81, 0.125, 2.0, true));
			commandList.add(new Turn(-5, .125, 1.0, true));
//						
		}
	}

	private void handlePosition3() {
		if (scaleSide == 'R') {
			commandList.add(new ShiftPivotArm(6, 5.0, false, 1.5));
			commandList.add(new Drive(-224, 0.125, 3.5, true));
			commandList.add(new Turn(-28, 1.25, .75, true));
			commandList.add(new Drive(-54, .125, 1.5, true));
			commandList.add(new SetClaw(true, true));
			commandList.add(new ShiftPivotArm(0, 4.0, false, 0.5));
			commandList.add(new SetIntake(true));
			commandList.add(new Turn(9, 1.5, 1.5, true));
			commandList.add(new Drive(71, 0.125, 2.0, true));
			commandList.add(new Turn(5, .125, 1.0, true));
			commandList.add(new ShiftPivotArm(10, 1.5, true, 0));
			commandList.add(new SetClaw(false, true));			
			commandList.add(new SetIntake(false, true));
			commandList.add(new ShiftPivotArm(0, .25, true, 0));
			commandList.add(new Drive(-68, 0.125, 2.5, false));
			commandList.add(new ShiftPivotArm(6, 2.0, true, 0));
			commandList.add(new SetClaw(true, true));
		} else {
			commandList.add(new ShiftPivotArm(6, 5.0, false, 4.0));
			commandList.add(new FirstTurn(false, false));
			commandList.add(new Turn(0, 0.125, 1.5, true));
			commandList.add(new Drive(-72, .125, 2.0, true));
			commandList.add(new SetClaw(true, true));
			commandList.add(new ShiftPivotArm(0, 4.0, false, 0.5));
			commandList.add(new SetIntake(true));
			commandList.add(new Turn(-2, 1.5, 1.5, true));
			commandList.add(new Drive(81, 0.125, 2.0, true));
			commandList.add(new Turn(5, .125, 1.0, true));
//			commandList.add(new Turn(-75, 1.5, 1.5, true));
//			commandList.add(new Drive(-194, 0.125, 3, true));
//			commandList.add(new Turn(0, 1.5, 1.5, true));
//			commandList.add(new Drive(-52, 0.125, 2.0, true));
//			commandList.add(new SetClaw(true, true));
//			commandList.add(new ShiftPivotArm(0, 4.0, false, 0.0));
//			commandList.add(new Drive(-65, 0.125, 4.0, true));
//			commandList.add(new SetClaw(true));
			
		}
	}

}
