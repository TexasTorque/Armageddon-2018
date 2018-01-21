package org.texastorque;

import org.texastorque.subsystems.Drivebase;


import java.util.ArrayList;

import org.texastorque.feedback.Feedback;
import org.texastorque.auto.AutoManager;
import org.texastorque.io.HumanInput;
import org.texastorque.io.Input;
import org.texastorque.io.RobotOutput;

import org.texastorque.subsystems.Subsystem;

import org.texastorque.torquelib.base.TorqueIterative;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TorqueIterative {

	ArrayList<Subsystem> subsystems;
	
	@Override
	public void robotInit() {
		Input.getInstance();
		HumanInput.getInstance();
		RobotOutput.getInstance();
		Feedback.getInstance();
		
		subsystems = new ArrayList<Subsystem>();
		subsystems.add(Drivebase.getInstance());
		
		AutoManager.init();
	}
	
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
	
	}
	
	@Override
	public void autonomousInit() {
	
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
	}
	
	@Override
	public void disabledContinuous() {
		
	}
	
	public void alwaysContinuous() {
		
	}
	
	@Override
	public void autonomousContinuous(){
		
	}
	
	@Override
	public void teleopInit() {
		for(Subsystem system : subsystems) {
			system.teleopInit();
			system.setInput(HumanInput.getInstance());
			
		}
	}

	@Override
	public void teleopContinuous(){
		HumanInput.getInstance().update();
		for(Subsystem s: subsystems)
			s.teleopContinuous();
		Drivebase.getInstance().teleopContinuous();
	}
}
