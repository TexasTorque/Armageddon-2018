package org.texastorque.auto.sequences;

import org.texastorque.auto.AutoSequence;
import org.texastorque.auto.arm.*;

import edu.wpi.first.wpilibj.DriverStation;

public class PlaceCube extends AutoSequence {

	
	public PlaceCube() {
		init();
	}
	
	@Override
	public void init() {
		System.out.println("init PlaceCube");
//		commandList.add(new SetClaw(true));
//		commandList.add(new ShiftPivotArm(4, 5.0));
	}
	
}
