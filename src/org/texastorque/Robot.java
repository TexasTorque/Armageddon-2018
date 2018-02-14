package org.texastorque;

import org.texastorque.subsystems.Drivebase;
import org.texastorque.subsystems.Arm;
import org.texastorque.subsystems.Claw;
import org.texastorque.subsystems.WheelIntake;
import org.texastorque.subsystems.*;
import org.texastorque.subsystems.Drivebase.DriveType;

import java.util.ArrayList;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutoMode;
import org.texastorque.feedback.Feedback;
import org.texastorque.auto.AutoManager;
import org.texastorque.io.HumanInput;
import org.texastorque.io.Input;
import org.texastorque.io.InputRecorder;
import org.texastorque.io.HumanInput;
import org.texastorque.io.RobotOutput;
import org.texastorque.torquelib.base.TorqueIterative;
import org.texastorque.subsystems.Drivebase;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TorqueIterative {


	ArrayList<Subsystem> subsystems;
	private String fieldConfig;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	@Override
	public void robotInit() {
		SmartDashboard.putNumber("AUTOMODE", 0);
		Input.getInstance();
		HumanInput.getInstance();
		InputRecorder.getInstance();
		RobotOutput.getInstance();
		Feedback.getInstance();
		subsystems = new ArrayList<Subsystem>();
		subsystems.add(Drivebase.getInstance());
		subsystems.add(Pivot.getInstance());
		subsystems.add(Arm.getInstance());
		subsystems.add(WheelIntake.getInstance());
		subsystems.add(Claw.getInstance());
	}
	
	

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
	
		AutoManager.getInstance().resetAuto();
		
	
	}	
	
	@Override
	public void teleopPeriodic() {
		
	}
	
	@Override
	public void autonomousPeriodic() {
	
		
	}
	
	public void alwaysContinuous() {
		Feedback.getInstance().update();
		Feedback.getInstance().SmartDashboard();
	}
	
	@Override
	public void autonomousContinuous(){
		AutoManager.getInstance().getRunningMode().run();
	//	AutoManager.getInstance().getRunningMode().SmartDashboard();
	}
	
	@Override
	public void teleopInit() {
		HumanInput.getInstance();
		InputRecorder.getInstance();
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
		InputRecorder.getInstance().update();
		for(Subsystem s: subsystems)
			s.teleopContinuous();
		Drivebase.getInstance().teleopContinuous();
		
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
		for (Subsystem system : subsystems ) {
			system.disabledContinuous();
		}
	}
	
}
