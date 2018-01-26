package org.texastorque.auto;

import java.util.ArrayList;
import java.util.LinkedList;

import org.texastorque.io.HumanInput;
import org.texastorque.io.RobotOutput;
import org.texastorque.feedback.Feedback;

import org.texastorque.auto.drive.*;
import org.texastorque.subsystems.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoManager {
	
	private static LinkedList<AutonomousCommand> commandList;
	private static ArrayList<Subsystem> subsystems;
	private static double aggregateTime;
	
	private static boolean commandsDone = false;
	private static volatile boolean setPointReached;
	
	public static void init() {
		SmartDashboard.putNumber("AUTOMODE", 0);
		commandList = new LinkedList<>();
		subsystems = new ArrayList<Subsystem>();
		subsystems.add(Drivebase.getInstance());
	}
	
	public static void beginAuto() {
		setPointReached = false;
		commandsDone = false;
		analyzeAutoMode();
	}
	
	/**
	 * 0 = null, 1 = forward
	 */
	public static void analyzeAutoMode() {
		int autoMode = reverse((int)(SmartDashboard.getNumber("AUTOMODE", 0)));
		
		while (autoMode > 0) {
			switch (autoMode % 10) {
				case 0:
					break;
				case 1:
					commandList.addAll(new ForwardMode().getCommands());
					break;
				default:
					break;
			}
			autoMode /= 10;
		}
		while(DriverStation.getInstance().isAutonomous() && !commandList.isEmpty()) {
			commandList.remove(0).run();
		}
		commandsDone = true;
		
		for (Subsystem system : subsystems) {
			system.disabledContinuous();
		}
	}
	
	public static void pause(double time) {
		double startTime = Timer.getFPGATimestamp();
		time = Math.abs(time);
		
		while (DriverStation.getInstance().isAutonomous() && !setPointReached && Timer.getFPGATimestamp() - startTime < time) {
			Feedback.getInstance().update();
			HumanInput.getInstance().smartDashboard();
			Feedback.getInstance().smartDashboard();
			AutoManager.SmartDashboard();
			
			for(Subsystem system : subsystems) {
				system.autoContinuous();
				system.smartDashboard();
			}
		}
		setPointReached = false;
		aggregateTime = Timer.getFPGATimestamp() - startTime;
	}
	
	public static void interruptThread() {
		setPointReached = true;
	}
	
	public static boolean areCommandsDone() {
		return commandsDone;
	}
	
	public static void SmartDashboard() {
		SmartDashboard.putNumber("A_AGGREGATETIME", aggregateTime);
	}
	
	private static int reverse(int num) {
		int temp = num;
		while (num > 0) {
			temp = (10 * temp) + (num % 10);
			num /= 10;
		}
		return temp;
	}
	
}
