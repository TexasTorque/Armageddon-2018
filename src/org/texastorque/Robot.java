package org.texastorque;

import java.util.ArrayList;


import org.texastorque.auto.AutoManager;
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
//import org.texastorque.torquelib.torquelog.TorqueLog;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TorqueIterative {

	private ArrayList<Subsystem> subsystems;
	private double time;
	private boolean hasStarted = false;
	private SendableChooser<String> autoSelector = new SendableChooser<>();
	private SendableChooser<String> recordingNameSelector = new SendableChooser<>();
	String config = DriverStation.getInstance().getGameSpecificMessage();

	@Override
	public void robotInit() {
		Input.getInstance();
	//	CameraServer.getInstance().startAutomaticCapture(1);
		HumanInput.getInstance();
		RobotOutput.getInstance();
		Feedback.getInstance();
		initSubsystems();
		initAutoSelector();
		initRecordingNameSelector();
		SmartDashboard.putData(autoSelector);
		SmartDashboard.putData(recordingNameSelector);
		SmartDashboard.putNumber("AUTO_MENU_WORKING", 0.0);
	}

	private void initSubsystems() {
		subsystems = new ArrayList<Subsystem>();
		subsystems.add(Drivebase.getInstance());
		subsystems.add(Pivot.getInstance());
		subsystems.add(Arm.getInstance());
		subsystems.add(WheelIntake.getInstance());
		subsystems.add(Claw.getInstance());
	}
	
	private void initAutoSelector() {
		autoSelector.addDefault("DriveForward", "DriveForward");
		autoSelector.addObject("DoNothing", "DoNothing");
		autoSelector.addObject("LeftScaleOneCube", "LeftScaleOneCube");
		autoSelector.addObject("RightScaleOneCube", "RightScaleOneCube");
		autoSelector.addObject("LeftSwitchNoRecording", "LeftSwitchNoRecording");
		autoSelector.addObject("RightSwitchNoRecording", "RightSwitchNoRecording");
		autoSelector.addObject("LeftScaleTwoCube", "LeftScaleTwoCube");
		autoSelector.addObject("RightScaleTwoCube", "RightScaleTwoCube");
		autoSelector.addObject("CenterSwitch", "CenterSwitch");
		autoSelector.addObject("LeftRecording", "LeftRecording");
		autoSelector.addObject("RightRecording", "RightRecording");
		autoSelector.addObject("CenterSwitchTwoCube", "CenterSwitchTwoCube");
	}
	
	private void initRecordingNameSelector() {
		recordingNameSelector.addObject("LLL", "LLL");
		recordingNameSelector.addObject("LRL", "LRL");
		recordingNameSelector.addObject("RLR", "RLR");
		recordingNameSelector.addObject("RRR", "RRR");
	}
	// String autoSelected = SmartDashboard.getString("Auto Selector",
	// "Default"); switch(autoSelected) { case "My Auto": autonomousCommand =
	// new MyAutoCommand(); break; case "Default Auto": default:
	// autonomousCommand = new ExampleCommand(); break; }

	// schedule the autonomous command (example)


	@Override
	public void autonomousInit() {
		String currentMode = autoSelector.getSelected();
		//TorqueLog.startLog();
		Feedback.getInstance().resetDBGyro();
		Feedback.getInstance().resetDriveEncoders();
		time = 0;
		for (Subsystem system : subsystems) {
			system.autoInit();
			system.setInput(Input.getInstance());
		}
		
		switch(currentMode) {
		case "CenterSwitchTwoCube":
			AutoManager.getInstance(9);
			AutoManager.beginAuto();
			break;
		case "DoNothing":
			AutoManager.getInstance(0);
			AutoManager.beginAuto();
			break;
		case "LeftScaleOneCube":
			//if(config.equals("LLL") || config.equals("RLR")) {
			//	AutoManager.getInstance(2);
			//} else 
			AutoManager.getInstance(2);
			AutoManager.beginAuto();
			break;
		case "RightScaleOneCube":
			AutoManager.getInstance(3);
			AutoManager.beginAuto();
			break;
		case "LeftScaleTwoCube":
			AutoManager.getInstance(7);
			AutoManager.beginAuto();
			break;
		case "RightScaleTwoCube":
			AutoManager.getInstance(8);
			AutoManager.beginAuto();
			break;
		case "LeftSwitchNoRecording":
			AutoManager.getInstance(4);
			AutoManager.beginAuto();
			break;
		case "RightSwitchNoRecording":
			AutoManager.getInstance(5);
			AutoManager.beginAuto();
			break;
		case "CenterSwitch":
			AutoManager.getInstance(6);
			AutoManager.beginAuto();
			break;
		case "LeftRecording":
			break;
		case "RightRecording":
			break;
		default:
			AutoManager.getInstance(1);
			
		}
		hasStarted = true;
	}
	
	private void setRecordingAutoType() {
		for (Subsystem system : subsystems) {
			system.changeAutoType();
		}
		
	}

	@Override
	public void teleopInit() {
		CameraServer.getInstance().startAutomaticCapture(0);
	//	TorqueLog.startLog();
		Drivebase.getInstance().setType(DriveType.TELEOP);

		for (Subsystem system : subsystems) {
			system.teleopInit();
			system.setInput(HumanInput.getInstance());
		}
		HumanInputRecorder.getInstance().setCurrentFieldConfig(recordingNameSelector.getSelected());
		
	}
	
	@Override
	public void alwaysContinuous() {
		Feedback.getInstance().update();
		Feedback.getInstance().smartDashboard();
		for (Subsystem system : subsystems) {
			system.smartDashboard();
		}
		if (isEnabled()) {
			SmartDashboard.putNumber("Time", time++);
		}		
		if(SmartDashboard.getNumber("AUTO_MENU_WORKING", 0) != 0.0) {
			SmartDashboard.putData(autoSelector);
			SmartDashboard.putNumber("AUTO_MENU_WORKING", 0.0);
		}
		Feedback.getInstance().smartDashboard();
		AutoManager.smartDashboard();
		
	}

	@Override
	public void autonomousContinuous() {
		
	//	PlaybackAutoManager.getInstance().getMode().getInstance().update();
		for (Subsystem system : subsystems) {
			system.autoContinuous();
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
		HumanInputRecorder.getInstance().setCurrentFieldConfig(recordingNameSelector.getSelected());
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
