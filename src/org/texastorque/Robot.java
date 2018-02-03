package org.texastorque;

import org.texastorque.subsystems.Drivebase;
import org.texastorque.subsystems.Arm;
import org.texastorque.subsystems.Claw;

import java.util.ArrayList;

import org.texastorque.feedback.Feedback;
import org.texastorque.auto.AutoManager;
import org.texastorque.io.HumanInput;
import org.texastorque.io.Input;
import org.texastorque.io.RobotOutput;

import org.texastorque.subsystems.Subsystem;
import org.texastorque.subsystems.Drivebase.DriveType;
import org.texastorque.torquelib.base.TorqueIterative;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TorqueIterative {

	private ArrayList<Subsystem> subsystems;
	private double time;
	private boolean hasStarted = false;
	
	@Override
	public void robotInit() {
		SmartDashboard.putNumber("AUTOMODE", 0);
		Input.getInstance();
		HumanInput.getInstance();
		RobotOutput.getInstance();
		Feedback.getInstance();
		
		subsystems = new ArrayList<Subsystem>();
		subsystems.add(Drivebase.getInstance());
	}
	
	@Override
	public void alwaysContinuous() {
		Feedback.getInstance().update();
		Feedback.getInstance().smartDashboard();
		Drivebase.getInstance().smartDashboard();
	}
	
	@Override
	public void autonomousInit() {
		//Drivebase.getInstance().setType(DriveType.AUTODRIVE);
		
		time = 0;
		for(Subsystem system : subsystems) {
			system.autoInit();
			system.setInput(Input.getInstance());
		}
		AutoManager.init();
		AutoManager.beginAuto();
		hasStarted = true;

	}
	
			
	@Override
	public void autonomousContinuous(){
		Feedback.getInstance().update();
		Drivebase.getInstance().autoContinuous();
	}
	
	@Override
	public void teleopInit() {
		Drivebase.getInstance().setType(DriveType.TELEOP);
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
	
	@Override
	public void disabledInit() {
		for(Subsystem system : subsystems) {
			system.disabledInit();
			system.setInput(HumanInput.getInstance());
		}
	}
	
	@Override
	public void disabledContinuous() {
		hasStarted = false;
		for(Subsystem system : subsystems ) {
			system.disabledContinuous();
		}
	}
	
	@Override
	public void disabledPeriodic() {
	
	}
}
