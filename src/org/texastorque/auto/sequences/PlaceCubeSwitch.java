package org.texastorque.auto.sequences;

import org.texastorque.auto.AutoSequence;
import org.texastorque.auto.arm.SetClaw;
import org.texastorque.auto.arm.SetIntake;
import org.texastorque.auto.arm.ShiftPivotArm;
import org.texastorque.auto.drive.Drive;
import org.texastorque.auto.drive.Turn;

import edu.wpi.first.wpilibj.DriverStation;

public class PlaceCubeSwitch extends AutoSequence {

	private char switchSide;
	private int startPos;

	public PlaceCubeSwitch(int loc) {
		startPos = loc;
		switchSide = fieldConfig.charAt(0);
		//init();
	}

	@Override
	public void init() {
		switchSide = fieldConfig.charAt(0);
		System.out.println("init PlaceCubeScale");
		if (startPos == 1) {
			if (switchSide == 'L') {
				commandList.add(new ShiftPivotArm(1, 5.0, false, 0.0));
				commandList.add(new Drive(150, 0.125, 3.0, true));
				commandList.add(new Turn(90, 1.5, 2.25, true));
				commandList.add(new Drive(36, .125, 1, true));
			} else if (switchSide == 'R') {
				commandList.add(new ShiftPivotArm(1, 5.0, false, 2.0));
				commandList.add(new Drive(220, 0.125, 3.25, true));
				commandList.add(new Turn(90, 1.5, 2.25, true));
				commandList.add(new Drive(180, 0.125, 3.25, true));
				commandList.add(new Turn(180, 1.5, 2.25, true));
				commandList.add(new Drive(24, 0.125, 1.25, true));
			}
		} else if (startPos == 2) {
			commandList.add(new Drive(6, 0.125, .75, true));
			if (switchSide == 'L') {
				commandList.add(new ShiftPivotArm(1, 5.0, false, 2.0));
				commandList.add(new Turn(-27, 1.5, 2.0, true));
				commandList.add(new Drive(108, 0.125, 2.25, true));
			} else if (switchSide == 'R') {
				commandList.add(new ShiftPivotArm(1, 5.0, false, 2.0));
				commandList.add(new Turn(24, 1.5, 2.0, true));
				commandList.add(new Drive(108, 0.125, 2.25, true));
				
			}
		} else if (startPos == 3) {

			if (switchSide == 'L') {
				commandList.add(new ShiftPivotArm(1, 5.0, false, 2.0));
				commandList.add(new Drive(220, 0.125, 3.25, true));
				commandList.add(new Turn(-90, 1.5, 2.25, true));
				commandList.add(new Drive(180, 0.125, 3.25, true));
				commandList.add(new Turn(-180, 1.5, 2.25, true));
				commandList.add(new Drive(24, 0.125, 1.25, true));
			} else if (switchSide == 'R') {
				commandList.add(new ShiftPivotArm(1, 5.0, false, 0.0));
				commandList.add(new Drive(150, 0.125, 3.0, true));
				commandList.add(new Turn(-90, 1.5, 2.25, true));
				commandList.add(new Drive(36, .125, 1, true));
			}
		} else if (startPos == 0 || switchSide == 'X') {
			commandList.add(new Drive(220, .125, 3.25, true));
		}
		commandList.add(new SetClaw(true, false));
		//commandList.add(new Drive(-0, .125, 1, true));
		//commandList.add(new Drive(-18, .125, 1, true));
		
		// commandList.add(new Drive(-0, .125, 1.25, true));
		// commandList.add(new Drive(-18, .125, 1, true));

	}
}
