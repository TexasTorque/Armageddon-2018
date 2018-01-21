package org.texastorque.auto;

import java.util.ArrayList;
import java.util.LinkedList;

import org.texastorque.io.HumanInput;
import org.texastorque.io.RobotOutput;
import org.texastorque.feedback.Feedback;

import org.texastorque.subsystems.Subsystem;
import org.texastorque.subsystems.Drivebase;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoManager {
	
	private static ArrayList<Subsystem> subsystems;
	private static double aggregateTime;
	
	private static boolean commandsDone = false;
	private static volatile boolean setPointReached;
	
	public static void init() {
		SmartDashboard.putNumber("AUTOMODE", 0);
		subsystems = new ArrayList<Subsystem>();
		subsystems.add(Drivebase.getInstance());
	}
	
	public static void beginAuto() {
		setPointReached = false;
		commandsDone = false;
	}
	
	public static void analyzeAutoMode() {
		
	}
	
	public static void pause(double time) {
		
	}
	
	public static void interruptThread() {
		setPointReached = false;
	}
	
	public static boolean areCommandsDone() {
		return commandsDone;
	}
	
	private static void SmartDashboard() {
		SmartDashboard.putNumber("A_AGGREGATETIME", aggregateTime);
	}
}
