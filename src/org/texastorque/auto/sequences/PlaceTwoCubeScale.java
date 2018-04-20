package org.texastorque.auto.sequences;

import java.util.LinkedList;

import org.texastorque.auto.AutoCommand;
import org.texastorque.auto.AutoSequence;
import org.texastorque.auto.arm.SetClaw;
import org.texastorque.auto.arm.SetIntake;
import org.texastorque.auto.arm.ShiftPivotArm;
import org.texastorque.auto.drive.Drive;
import org.texastorque.auto.drive.FirstTurn;
import org.texastorque.auto.drive.Turn;
import org.texastorque.subsystems.Drivebase;

import edu.wpi.first.wpilibj.DriverStation;

public class PlaceTwoCubeScale extends AutoSequence {

	private final int startPos;
	private char scaleSide;
	private LinkedList<AutoCommand> leftClose = new LinkedList<AutoCommand>();
	private LinkedList<AutoCommand> rightClose = new LinkedList<AutoCommand>();
	private LinkedList<AutoCommand> leftFar = new LinkedList<AutoCommand>();
	private LinkedList<AutoCommand> rightFar = new LinkedList<AutoCommand>();

	public PlaceTwoCubeScale(int start) {
		startPos = start;
		scaleSide = fieldConfig.charAt(1);
		initCommandLists();
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

	@Override
	public void initCommandLists() {
		leftClose.add(new ShiftPivotArm(6, 5.0, false, 1.5));
		leftClose.add(new Drive(-224, 0.125, 3.5, true));
		leftClose.add(new Turn(35, 1.25, .75, true));
		leftClose.add(new Drive(-54, .125, 1.5, true));
		leftClose.add(new SetClaw(true, true));
		leftClose.add(new ShiftPivotArm(0, 4.0, false, 0.5));
		leftClose.add(new SetIntake(true));
		leftClose.add(new Turn(-9, 1.5, 1.5, true));
		leftClose.add(new Drive(71, 0.125, 2.0, true));
		leftClose.add(new Turn(-5, .125, 1.0, true));
		leftClose.add(new ShiftPivotArm(10, 1.5, true, 0));
		leftClose.add(new SetClaw(false, true));			
		leftClose.add(new SetIntake(false, true));
		leftClose.add(new ShiftPivotArm(0, .25, true, 0));
		leftClose.add(new Drive(-68, 0.125, 2.5, false));
		leftClose.add(new ShiftPivotArm(6, 2.0, true, 0));
		leftClose.add(new SetClaw(true, true));	
//==================
//==================
		leftFar.add(new ShiftPivotArm(6, 5.0, false, 4.0));
		leftFar.add(new FirstTurn(true, false));
		leftFar.add(new Turn(0, 0.125, 1.5, true));
		leftFar.add(new Drive(-72, .125, 2.0, true));
		leftFar.add(new SetClaw(true, true));
		leftFar.add(new ShiftPivotArm(0, 4.0, false, 0.5));
		leftFar.add(new SetIntake(true));
		leftFar.add(new Turn(2, 1.5, 1.5, true));
		leftFar.add(new Drive(81, 0.125, 2.0, true));
		leftFar.add(new Turn(-5, .125, 1.0, true));
//===================
//===================
		rightClose.add(new ShiftPivotArm(6, 5.0, false, 1.5));
		rightClose.add(new Drive(-224, 0.125, 3.5, true));
		rightClose.add(new Turn(-37, 1.25, .75, true));
		rightClose.add(new Drive(-54, .125, 1.5, true));
		rightClose.add(new SetClaw(true, true));
		rightClose.add(new ShiftPivotArm(0, 4.0, false, 0.5));
		rightClose.add(new SetIntake(true));
		rightClose.add(new Turn(9, 1.5, 1.5, true));
		rightClose.add(new Drive(71, 0.125, 2.0, true));
		rightClose.add(new Turn(5, .125, 1.0, true));
		rightClose.add(new ShiftPivotArm(10, 1.5, true, 0));
		rightClose.add(new SetClaw(false, true));			
		rightClose.add(new SetIntake(false, true));
		rightClose.add(new ShiftPivotArm(0, .25, true, 0));
		rightClose.add(new Drive(-68, 0.125, 2.5, false));
		rightClose.add(new ShiftPivotArm(6, 2.0, true, 0));
		rightClose.add(new SetClaw(true, true));
	//=]================================
	//==================================
		rightFar.add(new ShiftPivotArm(6, 5.0, false, 4.0));
		rightFar.add(new FirstTurn(false, false));
		rightFar.add(new Turn(0, 0.125, 1.5, true));
		rightFar.add(new Drive(-72, .125, 2.0, true));
		rightFar.add(new SetClaw(true, true));
		rightFar.add(new ShiftPivotArm(0, 4.0, false, 0.5));
		rightFar.add(new SetIntake(true));
		rightFar.add(new Turn(-2, 1.5, 1.5, true));
		rightFar.add(new Drive(81, 0.125, 2.0, true));
		rightFar.add(new Turn(5, .125, 1.0, true));
//		rightFar.add(new Turn(-75, 1.5, 1.5, true));
//		rightFar.add(new Drive(-194, 0.125, 3, true));
//		rightFar.add(new Turn(0, 1.5, 1.5, true));
//		rightFar.add(new Drive(-52, 0.125, 2.0, true));
//		rightFar.add(new SetClaw(true, true));
//		rightFar.add(new ShiftPivotArm(0, 4.0, false, 0.0));
//		rightFar.add(new Drive(-65, 0.125, 4.0, true));
//		rightFar.add(new SetClaw(true));

	}
	
	private void handlePosition1() {
		if (scaleSide == 'L') {
			commandList = leftClose;
		} else {
			Drivebase.getInstance().setDriftDirection(true);
			commandList = leftFar;					
		}
	}

	private void handlePosition3() {
		
		if (scaleSide == 'R') {
			commandList = rightClose;
		} else {
			Drivebase.getInstance().setDriftDirection(false);
			commandList = rightFar;		
		}
	}

}
