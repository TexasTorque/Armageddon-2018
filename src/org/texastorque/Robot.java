package org.texastorque;

import org.texastorque.subsystems.Drivebase;
import org.texastorque.subsystems.Arm;
import org.texastorque.subsystems.Claw;
import org.texastorque.subsystems.WheelIntake;
import org.texastorque.subsystems.*;
import org.texastorque.subsystems.Drivebase.DriveType;

import java.util.ArrayList;

import org.texastorque.feedback.Feedback;
import org.texastorque.auto.AutoManager;
import org.texastorque.io.HumanInput;
import org.texastorque.io.Input;
import org.texastorque.io.RobotOutput;
import org.texastorque.torquelib.base.TorqueIterative;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TorqueIterative {

	private ArrayList<Subsystem> subsystems;
	private double time;
	private boolean hasStarted = false;
	
	@Override
	public void robotInit() {
		SmartDashboard.putNumber("AUTOMODE", 0);
//		CameraServer.getInstance().startAutomaticCapture(0);
		Input.getInstance();
		HumanInput.getInstance();
		RobotOutput.getInstance();
		Feedback.getInstance();
		subsystems = new ArrayList<Subsystem>();
		subsystems.add(Drivebase.getInstance());
		subsystems.add(Pivot.getInstance());
		subsystems.add(Arm.getInstance());
		subsystems.add(WheelIntake.getInstance());
		subsystems.add(Claw.getInstance());
		AutoManager.init();
	}
	
	//String autoSelected = SmartDashboard.getString("Auto Selector", "Default"); switch(autoSelected) { case "My Auto": autonomousCommand  = new MyAutoCommand(); break; case "Default Auto": default:
	//autonomousCommand = new ExampleCommand(); break; }
	
	//schedule the autonomous command (example)

	@Override
	public void alwaysContinuous() {
		Feedback.getInstance().update();
		for (Subsystem system : subsystems) {
			system.smartDashboard();
		}
		if(!isDisabled()) {
			SmartDashboard.putNumber("Time", time++);
		}
		
		Feedback.getInstance().smartDashboard();
		AutoManager.smartDashboard();
	}
	
	@Override
	public void autonomousInit() {
		Feedback.getInstance().resetDBGyro();
		Feedback.getInstance().resetDriveEncoders();
		time = 0;
		for (Subsystem system : subsystems) {
			system.autoInit();
			system.setInput(Input.getInstance());
		}
		AutoManager.beginAuto();
		hasStarted = true;
	}
	
	@Override
	public void autonomousContinuous(){
		if(!hasStarted && AutoManager.commandsDone()) {
			AutoManager.beginAuto();
			hasStarted = true;
		}
		Feedback.getInstance().update();
		for (Subsystem system : subsystems) {
			system.autoContinuous();
		}
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
	public void teleopContinuous() {
		Feedback.getInstance().update();
		HumanInput.getInstance().update();
		for(Subsystem system : subsystems) {
			system.teleopContinuous();
		}
	}
	
	@Override
	public void disabledInit() {
		for (Subsystem system : subsystems) {
			system.disabledInit();
			system.setInput(HumanInput.getInstance());
		}
	}
	
	@Override
	public void disabledContinuous() {
		hasStarted = false;
		for (Subsystem system : subsystems ) {
			system.disabledContinuous();
		}
	}
}
