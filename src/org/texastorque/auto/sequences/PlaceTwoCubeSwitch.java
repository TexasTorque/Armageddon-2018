package org.texastorque.auto.sequences;

import org.texastorque.auto.AutoSequence;
import org.texastorque.auto.arm.SetClaw;
import org.texastorque.auto.arm.SetIntake;
import org.texastorque.auto.arm.ShiftPivotArm;
import org.texastorque.auto.drive.Drive;
import org.texastorque.auto.drive.Turn;

import edu.wpi.first.wpilibj.DriverStation;

public class PlaceTwoCubeSwitch extends AutoSequence {

	private char switchSide;

	public PlaceTwoCubeSwitch() {
		try {
			switchSide = DriverStation.getInstance().getGameSpecificMessage().charAt(0);
		} catch (Exception e) {
			switchSide = 'X';
			System.out.println("damn");
		}
		init();
	}

	@Override
	public void init() {
		System.out.println("init PlaceCubeScale");
			commandList.add(new Drive(6, 0.125, .75, true));
			if (switchSide == 'L') {
				commandList.add(new ShiftPivotArm(1, 5.0, false, 2.0));
				commandList.add(new Turn(-27.5, 1.5, 2.0, true));
				commandList.add(new Drive(108, 0.125, 2.25, true));
				//==========================================================
				commandList.add(new SetClaw(true, false));
				commandList.add(new Drive(-12, 0.125, 2.25, true));
		  		commandList.add(new Turn(90, 1.5, 2.0, true));
				commandList.add(new SetIntake(true));
				commandList.add(new Drive(24, 0.125, 1.25, true));
				commandList.add(new SetClaw(false, false));
				commandList.add(new ShiftPivotArm(1, 5.0, false, 1.0));
				commandList.add(new Drive(-30, 0.125, 1.25, true));
				commandList.add(new SetIntake(false));
				commandList.add(new Turn(0, 1.5, 1.5, true));
	//			commandList.add(new Drive(24, 0.125, 1.25, true));
				 
			} else if (switchSide == 'R') {
				commandList.add(new ShiftPivotArm(1, 5.0, false, 2.0));
				commandList.add(new Turn(30, 1.5, 2.0, true));
				commandList.add(new Drive(108, 0.125, 2.25, true));
				commandList.add(new SetClaw(true, false));
				commandList.add(new Drive(-12, 0.125, 2.25, true));
		  		commandList.add(new Turn(-90, 1.5, 2.0, true));
				commandList.add(new SetIntake(true));
				commandList.add(new Drive(24, 0.125, 1.25, true));
				commandList.add(new SetClaw(false, false));
				commandList.add(new ShiftPivotArm(1, 5.0, false, 1.0));
				commandList.add(new Drive(-30, 0.125, 1.25, true));
				commandList.add(new SetIntake(false));
				commandList.add(new Turn(0, 1.5, 1.5, true));
				//final drive command
				
				//=======================================================
		/*		commandList.add(new SetClaw(false));
		 * 		commandList.add(new Drive(-12, 0.125, 2.25, true));
		 * 		commandList.add(new Turn(-90, 1.5, 2.0, true));
		 * 		setIntakes(true)
		 * 		commandList.add(new Drive(24, 0.125, 1.25, true));
		 * 		setClaw(open)
		 * 		setPivotArm(1, delay 1)
		 * 		commandList.add(new Drive(-24, 0.125, 1.25, true));
		 * 		setIntakes(false)
		 * 		commandList.add(new Turn(0, 1.5, 1.5, true));
		 * 		commandList.add(new Drive(-24, 0.125, 1.25, true));
		 * 		
		 * 		
		 */
				
			}
	}
}
