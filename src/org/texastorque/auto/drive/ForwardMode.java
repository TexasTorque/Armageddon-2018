package org.texastorque.auto.drive;

import org.texastorque.auto.AutonomousMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class ForwardMode extends AutonomousMode {
private Alliance alliance;
	
	public ForwardMode() {
		alliance = DriverStation.getInstance().getAlliance();
		init();
	}

	@Override
	public void init() {
		commandList.add(new Drive(50, .125, 1.5));
	}
}
