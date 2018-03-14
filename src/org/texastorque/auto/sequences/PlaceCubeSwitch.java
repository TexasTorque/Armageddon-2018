package org.texastorque.auto.sequences;

import org.texastorque.auto.AutoSequence;
import org.texastorque.auto.arm.*;
import org.texastorque.auto.drive.Drive;
import org.texastorque.auto.drive.Turn;

import edu.wpi.first.wpilibj.DriverStation;

public class PlaceCubeSwitch extends AutoSequence {

	private char switchSide;
	private int startPos;
	
	public PlaceCubeSwitch(int loc) {
		try {
			startPos = loc;
			switchSide = DriverStation.getInstance().getGameSpecificMessage().charAt(0);
		} catch (Exception e) {
			startPos = 0;
			switchSide = 'X';
			System.out.println("damn");
		}
		init();
	}
	
	@Override
	public void init() {
		System.out.println("init PlaceCubeScale");
		if (startPos == 1) {
			if (switchSide == 'L') {
				commandList.add(new ShiftPivotArm(1, 5.0, false, 0.0));
				commandList.add(new Drive(150, 0.125, 3.0, true));
				commandList.add(new Turn(90, 1.5, 2.25, true));
				commandList.add(new Drive(36, .125, 1, true));
			}
			else if (switchSide == 'R') {
				commandList.add(new ShiftPivotArm(1, 5.0, false, 2.0));
				commandList.add(new Drive(220, 0.125, 3.25, true));
				commandList.add(new Turn(90, 1.5, 2.25, true));
				commandList.add(new Drive(180, 0.125, 3.25, true));
				commandList.add(new Turn(180, 1.5, 2.25, true));
				commandList.add(new Drive(24, 0.125, 1.25, true));
			}
		}
		else if(startPos == 2) {
			commandList.add(new Drive(6, 0.125, .75, true));
			if(switchSide == 'L') {
				commandList.add(new ShiftPivotArm(1, 5.0, false, 2.0));
	//			commandList.add(new Drive(15, 0.125, 1.25, true));
				
				commandList.add(new Turn(-27.5, 1.5, 2.0, true));
				commandList.add(new Drive(108, 0.125, 2.25, true));
			}
			else if(switchSide == 'R') {
				commandList.add(new ShiftPivotArm(1, 5.0, false, 2.0));
				commandList.add(new Turn(22.5, 1.5, 2.0, true));
				commandList.add(new Drive(108, 0.125, 2.25, true));
			}
		}
		else if (startPos == 3) {
			
			if (switchSide == 'L') {
				commandList.add(new ShiftPivotArm(1, 5.0, false, 2.0));
				commandList.add(new Drive(220, 0.125, 3.25, true));
				commandList.add(new Turn(-90, 1.5, 2.25, true));
				commandList.add(new Drive(180, 0.125, 3.25, true));
				commandList.add(new Turn(-180, 1.5, 2.25, true));
				commandList.add(new Drive(24, 0.125, 1.25, true));
			}
			else if (switchSide == 'R') {
				commandList.add(new ShiftPivotArm(1, 5.0, false, 0.0));
				commandList.add(new Drive(150, 0.125, 3.0, true));
				commandList.add(new Turn(-90, 1.5, 2.25, true));
				commandList.add(new Drive(36, .125, 1, true));
			}
		}
		else if (startPos == 0 || switchSide == 'X') {
			commandList.add(new Drive(220, .125, 3.25, true));
		}
		commandList.add(new SetClaw(false));
		
	}
}
