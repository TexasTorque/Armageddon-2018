package org.texastorque;

import java.util.ArrayList;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.PlaybackAutoManager;
import org.texastorque.auto.playback.HumanInputRecorder;
import org.texastorque.feedback.Feedback;
import org.texastorque.io.HumanInput;
import org.texastorque.io.Input;
import org.texastorque.io.RobotOutput;
import org.texastorque.subsystems.Arm;
import org.texastorque.subsystems.Claw;
import org.texastorque.subsystems.Drivebase;
import org.texastorque.subsystems.Drivebase.DriveType;
import org.texastorque.subsystems.Pivot;
import org.texastorque.subsystems.Subsystem;
import org.texastorque.subsystems.WheelIntake;
import org.texastorque.torquelib.base.TorqueIterative;
//import org.texastorque.torquelib.//TorqueLog.//TorqueLog;

import edu.wpi.first.wpilibj.CameraServer;
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
		subsystems.add(Pivot.getInstance());
		subsystems.add(Arm.getInstance());
		subsystems.add(WheelIntake.getInstance());
		subsystems.add(Claw.getInstance());
		PlaybackAutoManager.getInstance();
	}

	// String autoSelected = SmartDashboard.getString("Auto Selector",
	// "Default"); switch(autoSelected) { case "My Auto": autonomousCommand =
	// new MyAutoCommand(); break; case "Default Auto": default:
	// autonomousCommand = new ExampleCommand(); break; }

	// schedule the autonomous command (example)

	@Override
	public void alwaysContinuous() {
		Feedback.getInstance().update();
		Feedback.getInstance().smartDashboard();
		for (Subsystem system : subsystems) {
			system.smartDashboard();
		}
<<<<<<< HEAD
		
		if(!isDisabled()) {
=======
		if (!isDisabled()) {
>>>>>>> lubecki-and-lowe
			SmartDashboard.putNumber("Time", time++);
			//TorqueLog.logData();
		}
<<<<<<< HEAD
		
=======

		Feedback.getInstance().smartDashboard();
>>>>>>> lubecki-and-lowe
		AutoManager.smartDashboard();

		
	}

	@Override
	public void autonomousInit() {
		//TorqueLog.startLog();
		Feedback.getInstance().resetDBGyro();
		Feedback.getInstance().resetDriveEncoders();
		time = 0;
		for (Subsystem system : subsystems) {
			system.autoInit();
			system.setInput(Input.getInstance());
		}
		hasStarted = true;
	}

	@Override
	public void autonomousContinuous() {
		
		PlaybackAutoManager.getInstance().getMode().getInstance().update();
		for (Subsystem system : subsystems) {
			system.autoContinuous();
		}
	}

	@Override
	public void teleopInit() {
<<<<<<< HEAD
		//TorqueLog.startLog();
		Drivebase.getInstance().setType(DriveType.TELEOP);
=======
		CameraServer.getInstance().startAutomaticCapture(0);
>>>>>>> lubecki-and-lowe
		
		Drivebase.getInstance().setType(DriveType.TELEOP);

		for (Subsystem system : subsystems) {
			system.teleopInit();
			system.setInput(HumanInput.getInstance());
		}
	}

	@Override
	public void teleopContinuous() {
		Feedback.getInstance().update();
		HumanInput.getInstance().update();
		HumanInputRecorder.getInstance().update();
		for (Subsystem system : subsystems) {
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
		for (Subsystem system : subsystems) {
			system.disabledContinuous();
		}
	}
}
