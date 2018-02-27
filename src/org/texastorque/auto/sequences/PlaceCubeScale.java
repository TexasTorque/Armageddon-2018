package org.texastorque.auto.sequences;

import org.texastorque.auto.AutoSequence;
import org.texastorque.auto.arm.SetClaw;
import org.texastorque.auto.arm.ShiftPivotArm;
import org.texastorque.auto.drive.Drive;
import org.texastorque.auto.drive.Turn;

import edu.wpi.first.wpilibj.DriverStation;

public class PlaceCubeScale extends AutoSequence {
	
	private int startPos;
	private char scaleSide;
	
	public PlaceCubeScale() {
		//startPos = DriverStation.getInstance().getLocation();
		startPos = 1;
		//scaleSide = DriverStation.getInstance().getGameSpecificMessage().charAt(1);
		scaleSide = 'L';
		init();
	}
	
	@Override
	public void init() {
		System.out.println("init PlaceCubeScale");
		if (startPos == 1) {
			if (scaleSide == 'L') {
				System.out.println("1L");
				commandList.add(new ShiftPivotArm(4, 5.0, false, 0.0));
				commandList.add(new Drive(260, 0.125, 5.0, true));
				commandList.add(new Turn(45, 1.5, 2.0, true));
				commandList.add(new Drive(32, .125, 1, true));
			}
			else {
				System.out.println("1R");
				commandList.add(new ShiftPivotArm(4, 5.0, false, 4.0));
				commandList.add(new Drive(224, 0.125, 3.25, true));
				commandList.add(new Turn(90, 1.5, 2.0, true));
				commandList.add(new Drive(190, 0.125, 3.25, true));
				commandList.add(new Turn(0, 1.5, 2.0, true));
				commandList.add(new Drive(58, 0.125, 2.0, true));
			}
		}
		else if (startPos == 3) {
			if (scaleSide == 'R') {
				System.out.println("3R");
				commandList.add(new ShiftPivotArm(4, 5.0, false, 0.0));
				commandList.add(new Drive(260, 0.125, 5.0, true));
				commandList.add(new Turn(-45, 1.5, 2.0, true));
				commandList.add(new Drive(32, .125, 1, true));
			}
			else {
				System.out.println("3L");
				commandList.add(new ShiftPivotArm(4, 5.0, false, 4.0));
				commandList.add(new Drive(224, 0.125, 3.25, true));
				commandList.add(new Turn(-90, 1.5, 2.0, true));
				commandList.add(new Drive(190, 0.125, 3.25, true));
				commandList.add(new Turn(0, 1.5, 2.0, true));
				commandList.add(new Drive(58, 0.125, 2.0, true));
			}
		}
		commandList.add(new SetClaw(false));
		
	}
	
}
