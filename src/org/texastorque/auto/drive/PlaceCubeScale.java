package org.texastorque.auto.drive;

import org.texastorque.auto.AutoSequence;

import edu.wpi.first.wpilibj.DriverStation;

public class PlaceCubeScale extends AutoSequence {
	
	private int startPos;
	private char scaleSide;
	
	public PlaceCubeScale() {
		startPos = DriverStation.getInstance().getLocation();
		//scaleSide = DriverStation.getInstance().getGameSpecificMessage().charAt(1);
		scaleSide = 'L';
	}
	
	@Override
	public void init() {
		if (startPos == 1) {
			if (scaleSide == 'L') {
				commandList.add(new Drive(300, 0.125));
				commandList.add(new Turn(-90, 0.125));
			}
			else {
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
				commandList.add(new Drive(300, 0.125));
				commandList.add(new Turn(90, 0.125));
			}
			else {
				commandList.add(new Drive(240, 0.125));
				commandList.add(new Turn(90, 0.125));
				commandList.add(new Drive(180, 0.125));
				commandList.add(new Turn(-90, 0.125));
				commandList.add(new Drive(60, 0.125));
				commandList.add(new Turn(-90, 0.125));
			}
		}
	}
	
}
