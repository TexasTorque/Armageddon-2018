package org.texastorque.auto.sequences;

import org.texastorque.auto.AutoSequence;
import org.texastorque.auto.arm.SetClaw;
import org.texastorque.auto.arm.ShiftPivotArm;
import org.texastorque.auto.drive.Drive;
import org.texastorque.auto.drive.Turn;

import edu.wpi.first.wpilibj.DriverStation;

public class TeamPlayer extends AutoSequence {

	private final int startPos;
	private String switchSide;
	private String scaleSide;
	private String fieldConfig;
	public TeamPlayer(int start) {
		startPos = start;
		try {
			switchSide = DriverStation.getInstance().getGameSpecificMessage().substring(0, 1);
			scaleSide = DriverStation.getInstance().getGameSpecificMessage().substring(1, 2);
			fieldConfig = switchSide + scaleSide;
		} catch (Exception e) {
			scaleSide = "X";
			switchSide = "X";
		}
		init();
	}

	@Override
	public void init() {
		System.out.println("init PlaceCubeScale");
		if (startPos == 1) {
			handlePosition1();
		} else if (startPos == 3) {
			handlePosition3();
		} else if (startPos == -1 || scaleSide.equals("X")) {
			commandList.add(new Drive(220, .125, 3.25, true));
		}

	}

	private void handlePosition1() {
		switch(fieldConfig) {
		case "LL":
			
		case "RL":
			commandList.add(new ShiftPivotArm(4, 5.0, false, 0.0));
			commandList.add(new Drive(300, 0.125, 3.0, true));
			commandList.add(new Turn(90, 1.5, 2.25, true));
			commandList.add(new Drive(36, .125, 1, true));
			break;
		case "LR":				
			commandList.add(new ShiftPivotArm(1, 5.0, false, 0.0));
			commandList.add(new Drive(150, 0.125, 3.0, true));
			commandList.add(new Turn(90, 1.5, 2.25, true));
			commandList.add(new Drive(36, .125, 1, true));
			break;
		case "RR":
			commandList.add(new Drive(220, .125, 3.25, true));
			break;
		}
	}

	private void handlePosition3() {
		switch(fieldConfig) {
		case "LL":		
			commandList.add(new Drive(220, .125, 3.25, true));
			break;
		case "RR":
		case "LR":			
			commandList.add(new ShiftPivotArm(4, 5.0, false, 0.0));
			commandList.add(new Drive(300, 0.125, 3.0, true));
			commandList.add(new Turn(-90, 1.5, 2.25, true));
			commandList.add(new Drive(36, .125, 1, true));
			break;
		case "RL":
			commandList.add(new ShiftPivotArm(1, 5.0, false, 0.0));
			commandList.add(new Drive(150, 0.125, 3.0, true));
			commandList.add(new Turn(-90, 1.5, 2.25, true));
			commandList.add(new Drive(36, .125, 1, true));
			break;
		}
		
	}

}
