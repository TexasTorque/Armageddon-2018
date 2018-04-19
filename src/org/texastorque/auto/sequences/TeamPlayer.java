package org.texastorque.auto.sequences;

import java.util.ArrayList;
import java.util.LinkedList;

import org.texastorque.auto.AutoCommand;
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
	private String fieldString;
	private LinkedList<AutoCommand> leftScale= new LinkedList<AutoCommand>();
	private LinkedList<AutoCommand> leftSwitch = new LinkedList<AutoCommand>();
	private LinkedList<AutoCommand> leftBaseline = new LinkedList<AutoCommand>();
	private LinkedList<AutoCommand> rightScale= new LinkedList<AutoCommand>();
	private LinkedList<AutoCommand> rightSwitch = new LinkedList<AutoCommand>();
	private LinkedList<AutoCommand> rightBaseline = new LinkedList<AutoCommand>();
	
	public TeamPlayer(int start) {
		startPos = start;
		switchSide = fieldConfig.substring(0, 1);
		scaleSide = fieldConfig.substring(1, 2);
		fieldString = switchSide + scaleSide;
	//	init();
	}

	@Override
	public void init() {
		switchSide = fieldConfig.substring(0, 1);
		scaleSide = fieldConfig.substring(1, 2);
		fieldString = switchSide + scaleSide;
		System.out.println("init PlaceCubeScale");
		if (startPos == 1) {
			handlePosition1();
		} else if (startPos == 3) {
			handlePosition3();
		} else if (startPos == -1 || scaleSide.equals("X")) {
			commandList.add(new Drive(220, .125, 3.25, true));
		
		}
	}

	@Override
	public void initCommandLists() {
		
	}
	
	private void initPosition1() {
		switch(fieldString) {
		case "LL":	
		case "RL":
			leftScale.add(new ShiftPivotArm(4, 5.0, false, 0.0));
			leftScale.add(new Drive(312, 0.125, 5.0, true));
			leftScale.add(new Turn(90, 1.5, 2.25, true));
			leftScale.add(new Drive(18, .125, 1, true));
			leftScale.add(new SetClaw(true, true)); // Open claw after any sequence.
			leftScale.add(new Drive(1, .125, 1.0, true));
			leftScale.add(new Drive(-24, .125, 2.0, true));
			leftScale.add(new ShiftPivotArm(0, 5.0, true, 0));
			break;
		case "LR":				
			leftSwitch.add(new ShiftPivotArm(1, 5.0, false, 0.0));
			leftSwitch.add(new Drive(168, 0.125, 3.0, true));
			leftSwitch.add(new Turn(90, 1.5, 2.25, true));
			leftSwitch.add(new Drive(30, .125, 1, true));
			leftSwitch.add(new SetClaw(true, true)); // Open claw after any sequence.
			leftSwitch.add(new Drive(1, .125, 1.0, true));
			leftSwitch.add(new Drive(-24, .125, 2.0, true));
			leftSwitch.add(new ShiftPivotArm(0, 5.0, true, 0));
			break;
		case "RR":
			leftBaseline.add(new Drive(244, .125, 3.5, true));
			leftBaseline.add(new Turn(90, 1.5, 2.25, true));
			leftBaseline.add(new Drive(72, .125, 1, true));
			break;
		}
	}

	private void initPosition3() {
		switch(fieldString) {
		case "LL":		
			rightBaseline.add(new Drive(244, .125, 3.5, true));
			rightBaseline.add(new Turn(-90, 1.5, 2.25, true));
			rightBaseline.add(new Drive(72, .125, 1, true));
			
			break;
		case "RR":
		case "LR":			
			rightScale.add(new ShiftPivotArm(4, 5.0, false, 0.0));
			rightScale.add(new Drive(312, 0.125, 5.0, true));
			rightScale.add(new Turn(-90, 1.5, 2.25, true));
			rightScale.add(new Drive(30, .125, 1, true));
			rightScale.add(new SetClaw(true, true)); // Open claw after any sequence.
			rightScale.add(new Drive(1, .125, 1.0, true));
			rightScale.add(new Drive(-24, .125, 2.0, true));
			rightScale.add(new ShiftPivotArm(0, 5.0, true, 0));
			break;
		case "RL":
			rightSwitch.add(new ShiftPivotArm(1, 5.0, false, 0.0));
			rightSwitch.add(new Drive(168, 0.125, 3.0, true));
			rightSwitch.add(new Turn(-90, 1.5, 2.25, true));
			rightSwitch.add(new Drive(30, .125, 1, true));
			rightSwitch.add(new SetClaw(true, true)); // Open claw after any sequence.
			rightSwitch.add(new Drive(1, .125, 1.0, true));
			rightSwitch.add(new Drive(-24, .125, 2.0, true));
			rightSwitch.add(new ShiftPivotArm(0, 5.0, true, 0));
			break;
		}
		
		private void processPosition1() {
			switch(fieldString) {
			case "LL":	
			case "RL":
				commandList = leftScale;
				break;
			case "LR":				
				commandList.add(new ShiftPivotArm(1, 5.0, false, 0.0));
				commandList.add(new Drive(168, 0.125, 3.0, true));
				commandList.add(new Turn(90, 1.5, 2.25, true));
				commandList.add(new Drive(30, .125, 1, true));
				commandList.add(new SetClaw(true, true)); // Open claw after any sequence.
				commandList.add(new Drive(1, .125, 1.0, true));
				commandList.add(new Drive(-24, .125, 2.0, true));
				commandList.add(new ShiftPivotArm(0, 5.0, true, 0));
				break;
			case "RR":
				commandList.add(new Drive(244, .125, 3.5, true));
				commandList.add(new Turn(90, 1.5, 2.25, true));
				commandList.add(new Drive(72, .125, 1, true));
				
				break;
			}
		}

		private void processPosition3() {
			switch(fieldString) {
			case "LL":		
				commandList.add(new Drive(244, .125, 3.5, true));
				commandList.add(new Turn(-90, 1.5, 2.25, true));
				commandList.add(new Drive(72, .125, 1, true));
				
				break;
			case "RR":
			case "LR":			
				commandList.add(new ShiftPivotArm(4, 5.0, false, 0.0));
				commandList.add(new Drive(312, 0.125, 5.0, true));
				commandList.add(new Turn(-90, 1.5, 2.25, true));
				commandList.add(new Drive(30, .125, 1, true));
				commandList.add(new SetClaw(true, true)); // Open claw after any sequence.
				commandList.add(new Drive(1, .125, 1.0, true));
				commandList.add(new Drive(-24, .125, 2.0, true));
				commandList.add(new ShiftPivotArm(0, 5.0, true, 0));
				break;
			case "RL":
				commandList.add(new ShiftPivotArm(1, 5.0, false, 0.0));
				commandList.add(new Drive(168, 0.125, 3.0, true));
				commandList.add(new Turn(-90, 1.5, 2.25, true));
				commandList.add(new Drive(30, .125, 1, true));
				commandList.add(new SetClaw(true, true)); // Open claw after any sequence.
				commandList.add(new Drive(1, .125, 1.0, true));
				commandList.add(new Drive(-24, .125, 2.0, true));
				commandList.add(new ShiftPivotArm(0, 5.0, true, 0));
				break;
			}
		
	}

}
