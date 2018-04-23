package org.texastorque;

import java.util.ArrayList;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.PlaybackAutoManager;
import org.texastorque.feedback.Feedback;
import org.texastorque.io.HumanInput;
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
	private SendableChooser<String> autoSelector = new SendableChooser<>();
	String config = DriverStation.getInstance().getGameSpecificMessage();

	@Override
	public void robotInit() {
	//	CameraServer.getInstance().startAutomaticCapture(1);
		HumanInput.getInstance();
		RobotOutput.getInstance();
		Feedback.getInstance();
		AutoManager.getInstance();
		initSubsystems();
		initAutoSelector();
		SmartDashboard.putData(autoSelector);
		SmartDashboard.putNumber("AUTO_MENU_WORKING", 0.0);
		System.out.println(AutoManager.getInstance());
		for (Subsystem system : subsystems) {
			system.autoInit();
		}
		
		AutoManager.getInstance().initAutoList();
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
		autoSelector.addObject("TeamPlayerLeft", "TeamPlayerLeft");
		autoSelector.addObject("TeamPlayerRight", "TeamPlayerRight");
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
		
		switch(currentMode) {
		case "CenterSwitchTwoCube":
			AutoManager.getInstance().setAutoMode(9);
			AutoManager.beginAuto();
			break;
		case "DoNothing":
			break;
		case "LeftScaleOneCube":
			AutoManager.getInstance().setAutoMode(2);
			AutoManager.beginAuto();
			break;
		case "RightScaleOneCube":
			AutoManager.getInstance().setAutoMode(3);
			AutoManager.beginAuto();
			break;
		case "LeftScaleTwoCube":
			AutoManager.getInstance().setAutoMode(7);
			AutoManager.beginAuto();
			break;
		case "RightScaleTwoCube":
			AutoManager.getInstance().setAutoMode(8);
			AutoManager.beginAuto();
			break;
		case "LeftSwitchNoRecording":
			AutoManager.getInstance().setAutoMode(4);
			AutoManager.beginAuto();
			break;
		case "RightSwitchNoRecording":
			AutoManager.getInstance().setAutoMode(5);
			AutoManager.beginAuto();
			break;
		case "CenterSwitch":
			AutoManager.getInstance().setAutoMode(6);
			AutoManager.beginAuto();
			break;
		case "TeamPlayerLeft":
			AutoManager.getInstance().setAutoMode(10);
			AutoManager.beginAuto();
			break;
		case "TeamPlayerRight":
			AutoManager.getInstance().setAutoMode(11);
			AutoManager.beginAuto();
			break;
		case "LeftRecording":
			PlaybackAutoManager.getInstance();
			for (Subsystem system : subsystems) {
				system.changeAutoType();
				system.initAutoMode("LEFT");
			}
				
			break;
		case "RightRecording":
			PlaybackAutoManager.getInstance();
			for (Subsystem system : subsystems) {
				system.changeAutoType();
				system.initAutoMode("RIGHT");
			}
			break;
			
		default:
			break;
		}
		
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
			system.setInput(HumanInput.getInstance());
			system.teleopInit();
		}
//		HumanInputRecorder.getInstance().setCurrentFieldConfig();
		
	}
	
	@Override
	public void alwaysContinuous() {
		Feedback.getInstance().update();
		Feedback.getInstance().smartDashboard();
		for (Subsystem system : subsystems) {
			system.smartDashboard();
		}
//		if (isEnabled()) {
			SmartDashboard.putNumber("Time", time++);
//		}		
		if(SmartDashboard.getNumber("AUTO_MENU_WORKING", 0) != 0.0) {
			SmartDashboard.putData(autoSelector);
			SmartDashboard.putNumber("AUTO_MENU_WORKING", 0.0);
		}
		AutoManager.smartDashboard();
	}

	@Override
	public void autonomousContinuous() {
//		if(autoSelector.getSelected().equals("LeftRecording")) {
//			PlaybackAutoManager.getInstance().getMode().getInstance("LEFT").update();
//		}
//		
//		if(autoSelector.getSelected().equals("RightRecording")) {
//			PlaybackAutoManager.getInstance().getMode().getInstance("RIGHT").update();
//		}
		
		for (Subsystem system : subsystems) {
			system.autoContinuous();
		}
	}

	@Override
	public void teleopContinuous() {
		Feedback.getInstance().update();
		HumanInput.getInstance().update();
//		HumanInputRecorder.getInstance().update();
		for (Subsystem system : subsystems) {
			system.teleopContinuous();
		}
//		HumanInputRecorder.getInstance().setCurrentFieldConfig();
	}

	@Override
	public void disabledInit() {
		for (Subsystem system : subsystems) {
			system.disabledInit();
		}
	}

	@Override
	public void disabledContinuous() {
		for (Subsystem system : subsystems) {
			system.disabledContinuous();
		}
	}
}
