package org.texastorque.auto.drive;


import org.texastorque.auto.AutonomousMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Timer;

public class ForwardMode extends AutonomousMode {
private Alliance alliance;
	
	public ForwardMode() {
		alliance = DriverStation.getInstance().getAlliance();
		init();
	}

	public ForwardMode(double time) {
		alliance = DriverStation.getInstance().getAlliance();
	}
	
	@Override
	public void init() {
		commandList.add(new Drive(50, .125, 1.5));
	}
	
	public void init(double time) {
		while(Timer.< time) {
		}
			
	}
}
