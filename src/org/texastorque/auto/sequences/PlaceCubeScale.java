package org.texastorque.auto.sequences;

import org.texastorque.auto.AutoSequence;
import org.texastorque.auto.arm.ShiftPivotArm;
import org.texastorque.auto.drive.Drive;
import org.texastorque.auto.drive.Turn;

import edu.wpi.first.wpilibj.DriverStation;

public class PlaceCubeScale extends AutoSequence {
	
	private int startPos;
	private char scaleSide;
	
	public PlaceCubeScale() {
		//startPos = DriverStation.getInstance().getLocation();
		startPos = 3;
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
				commandList.add(new Drive(300, 0.125));
				commandList.add(new Turn(-90, 0.125));
			}
			else {
				System.out.println("1R");
				commandList.add(new Drive(240, 0.125));
				commandList.add(new Turn(-90, 0.125));
				commandList.add(new Drive(180, 0.125));
				commandList.add(new Turn(90, 0.125));
				commandList.add(new Drive(60, 0.125));
				commandList.add(new Turn(90, 0.125));
			}
		}
		else if (startPos == 3) {
			if (scaleSide == 'R') {
				System.out.println("3R");
				commandList.add(new Drive(260, 0.125, 5.0));
				commandList.add(new Turn(-45, 1.5, 3.0));
				commandList.add(new ShiftPivotArm(3, 3.0));
			}
			else {
				System.out.println("3L");
	//			commandList.add(new Drive(220, 0.125, 4.0));
				commandList.add(new Turn(-90, 0.125, 6.0));
	//			commandList.add(new Drive(180, 0.125, 3.0));
	//			commandList.add(new Turn(90, 0.125, 2.0));
	//			commandList.add(new Drive(60, 0.125, 2.0));
//				commandList.add(new Turn(-90, 0.125));
			}
		}
	}
	
}
