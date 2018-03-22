package org.texastorque.auto.sequences;

import org.texastorque.auto.AutoSequence;
import org.texastorque.auto.arm.SetClaw;
import org.texastorque.auto.arm.ShiftPivotArm;
import org.texastorque.auto.drive.Drive;
import org.texastorque.auto.drive.Turn;

import edu.wpi.first.wpilibj.DriverStation;

public class PlaceCubeSwitchAndScale extends AutoSequence {

	private final int startPos;
	private char scaleSide;

	public PlaceCubeSwitchAndScale(int start) {
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
		System.out.println("init PlaceCubeSwitchAndScale");
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
		} else {
		}
	}

	private void handlePosition3() {
		if (scaleSide == 'R') {
		} else {
		}
	}
	
	private void pressY() {
		
	}
	
	private void releaseY() {
		
	};

}
