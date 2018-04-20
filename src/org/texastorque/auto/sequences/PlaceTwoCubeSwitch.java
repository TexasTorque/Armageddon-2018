package org.texastorque.auto.sequences;

import java.util.ArrayList;
import java.util.LinkedList;

import org.texastorque.auto.AutoCommand;
import org.texastorque.auto.AutoSequence;
import org.texastorque.auto.arm.SetClaw;
import org.texastorque.auto.arm.SetIntake;
import org.texastorque.auto.arm.ShiftPivotArm;
import org.texastorque.auto.drive.Drive;
import org.texastorque.auto.drive.Turn;

import edu.wpi.first.wpilibj.DriverStation;

public class PlaceTwoCubeSwitch extends AutoSequence {

	private char switchSide;
	private LinkedList<AutoCommand> leftCenter= new LinkedList<AutoCommand>();
	private LinkedList<AutoCommand> rightCenter= new LinkedList<AutoCommand>();

	public PlaceTwoCubeSwitch() {
		switchSide = fieldConfig.charAt(0);
		initCommandLists();
	}
	
	@Override
	public void init() {
		switchSide = fieldConfig.charAt(0);
		System.out.println("inittwocubeswitch" + switchSide);	
		rightCenter.add(new Drive(6, 0.125, 1, true));
			if (switchSide == 'L') {
				commandList = leftCenter;
			} else if (switchSide == 'R') {
				commandList = rightCenter;
			}
			System.out.println(commandList + "   " + rightCenter);

	}
	
	@Override
	public void initCommandLists() {
		leftCenter.add(new Drive(6, 0.125, 1, true));
		leftCenter.add(new ShiftPivotArm(1, 5.0, false, 2.0));
		leftCenter.add(new Turn(-27, 1.5, 0.75, true));
		leftCenter.add(new Drive(108, 0.125, 2.5, true));
		leftCenter.add(new SetClaw(true, true));
		leftCenter.add(new ShiftPivotArm(0, 5.0, false, 0.75));
		leftCenter.add(new Drive(-8, 0.125, 1.0, true));
		leftCenter.add(new SetIntake(true));
		leftCenter.add(new Turn(78, 1.5, 2.0, true));
		leftCenter.add(new ShiftPivotArm(10, 5.0, false, 1.0));
		leftCenter.add(new SetClaw(true, true));
		leftCenter.add(new Drive(24, 0.125, 1.75, true));
		leftCenter.add(new SetClaw(false, true));
		leftCenter.add(new ShiftPivotArm(1, 5.0, false, 0.75));
		leftCenter.add(new SetIntake(false, true));
		leftCenter.add(new Drive(-36, 0.125, 1.25, true));
		leftCenter.add(new SetIntake(false));
		leftCenter.add(new Turn(0, 1.5, 1.5, true));
		leftCenter.add(new Drive(28, 0.125, 1.25, true));
		leftCenter.add(new SetClaw(true, false));			
//===================================================================================================
//===================================================================================================
		rightCenter.add(new Drive(6, 0.125, 1, true));
		rightCenter.add(new ShiftPivotArm(1, 5.0, false, 2.0));
		rightCenter.add(new Turn(22, 1.5, 0.75, true));
		rightCenter.add(new Drive(108, 0.125, 2.5, true));
		rightCenter.add(new SetClaw(true, true));
		rightCenter.add(new ShiftPivotArm(0, 5.0, false, 0.75));
		rightCenter.add(new Drive(-16, 0.125, 2.25, true));
		rightCenter.add(new SetIntake(true));
		rightCenter.add(new Turn(-50, 1.5, 1.5, true));
		rightCenter.add(new ShiftPivotArm(10, 5.0, false, 1.0));
		rightCenter.add(new SetClaw(true, true));
		rightCenter.add(new Drive(24, 0.125, 1.75, true));
		rightCenter.add(new SetClaw(false, true));
		rightCenter.add(new ShiftPivotArm(1, 5.0, false, 0.75));
		rightCenter.add(new SetIntake(false, true));
		rightCenter.add(new Drive(-32, 0.125, 1.25, true));
		rightCenter.add(new SetIntake(false));
		rightCenter.add(new Turn(0, 1.5, 1.25, true));
		rightCenter.add(new Drive(26, 0.125, 1.25, true));
		rightCenter.add(new SetClaw(true, false));			
		
	}
}
