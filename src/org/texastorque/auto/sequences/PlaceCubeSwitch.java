package org.texastorque.auto.sequences;

import org.texastorque.auto.AutoSequence;
import org.texastorque.auto.arm.*;
import org.texastorque.auto.drive.Drive;
import org.texastorque.auto.drive.Turn;

import edu.wpi.first.wpilibj.DriverStation;

public class PlaceCubeSwitch extends AutoSequence {

	private int startPos;
	private char switchSide;
	
	public PlaceCubeSwitch() {
		//startPos = DriverStation.getInstance().getLocation();
		startPos = 3;
		//scaleSide = DriverStation.getInstance().getGameSpecificMessage().charAt(0);
		switchSide = 'L';
		init();
	}
	
	@Override
	public void init() {
		System.out.println("init PlaceCubeScale");
		if (startPos == 1) {
			if (switchSide == 'L') {
				System.out.println("1L");
				commandList.add(new ShiftPivotArm(1, 5.0, false, 0.0));
				commandList.add(new Drive(150, 0.125, 5.0, true));
				commandList.add(new Turn(90, 1.5, 2.0, true));
				commandList.add(new Drive(36, .125, 1, true));
			}
			else {
				System.out.println("1R");
				commandList.add(new ShiftPivotArm(1, 5.0, false, 2.0));
				commandList.add(new Drive(224, 0.125, 3.25, true));
				commandList.add(new Turn(90, 1.5, 2.0, true));
				commandList.add(new Drive(180, 0.125, 3.25, true));
				commandList.add(new Turn(180, 1.5, 2.0, true));
				commandList.add(new Drive(20, 0.125, .25, true));
			}
		}
		else if (startPos == 3) {
			if (switchSide == 'R') {
				System.out.println("3R");
				commandList.add(new ShiftPivotArm(1, 5.0, false, 0.0));
				commandList.add(new Drive(150, 0.125, 5.0, true));
				commandList.add(new Turn(-90, 1.5, 2.0, true));
				commandList.add(new Drive(36, .125, 1, true));
			}
			else {
				System.out.println("3L");
				commandList.add(new ShiftPivotArm(1, 5.0, false, 2.0));
				commandList.add(new Drive(224, 0.125, 3.25, true));
				commandList.add(new Turn(-90, 1.5, 2.0, true));
				commandList.add(new Drive(180, 0.125, 3.25, true));
				commandList.add(new Turn(-180, 1.5, 2.0, true));
				commandList.add(new Drive(20, 0.125, .25, true));
			}
		}
		commandList.add(new SetClaw(false));
		
	}
}
