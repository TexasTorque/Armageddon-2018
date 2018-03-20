package org.texastorque.auto;

import edu.wpi.first.wpilibj.DriverStation;
import java.util.ArrayList;
import java.util.LinkedList;

import org.texastorque.auto.drive.BackupDrive;
import org.texastorque.auto.sequences.PlaceCubeScale;
import org.texastorque.auto.sequences.PlaceCubeSwitch;
import org.texastorque.feedback.Feedback;
<<<<<<< HEAD
import org.texastorque.io.RobotOutput;
=======
>>>>>>> lubecki-lonestar
import org.texastorque.subsystems.Arm;
import org.texastorque.subsystems.Drivebase;
import org.texastorque.subsystems.Pivot;
import org.texastorque.subsystems.Subsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoManager {

	private static AutoManager instance;
	private static LinkedList<AutoCommand> commandList;
	private static ArrayList<Subsystem> subsystems;
	private static double aggregateTime;

	private static boolean commandsDone = false;
	private static volatile boolean setPointReached;

<<<<<<< HEAD
	private static int autoMode;
	
	public AutoManager() {
		commandList = new LinkedList<>();
=======
	public static void init() {
		SmartDashboard.putNumber("AUTOMODE", 0);
		commandList = new LinkedList<>();

>>>>>>> lubecki-lonestar
		subsystems = new ArrayList<>();
		subsystems.add(Drivebase.getInstance());
		subsystems.add(Arm.getInstance());
		subsystems.add(Pivot.getInstance());
		setAutoMode(0);
	}
<<<<<<< HEAD
	
	public AutoManager(int auto) {
		commandList = new LinkedList<>();
		subsystems = new ArrayList<>();
		subsystems.add(Drivebase.getInstance());
		subsystems.add(Arm.getInstance());
		subsystems.add(Pivot.getInstance());
		setAutoMode(auto);
		
	}
=======
>>>>>>> lubecki-lonestar

	public static void beginAuto() {
		System.out.println("beginAuto");
		setPointReached = false;
		commandsDone = false;
		analyzeAutoMode();
	}

<<<<<<< HEAD
	private void setAutoMode(int auto) {
		autoMode = auto;
	}
	
	public static void analyzeAutoMode() {
			switch (autoMode) {
=======
	public static void analyzeAutoMode() {
		int autoMode = (int) SmartDashboard.getNumber("AUTOMODE", 0);

		while (autoMode > 0) {
			switch (autoMode % 10) {
>>>>>>> lubecki-lonestar
			case 0:
				System.out.println("0");
				break;
			case 1:
<<<<<<< HEAD
				double startTime = Timer.getFPGATimestamp();
				if (Timer.getFPGATimestamp() < startTime + 1.5) {
					RobotOutput.getInstance().setDrivebaseSpeed(.65, .65);
				} else RobotOutput.getInstance().setDrivebaseSpeed(0.0, 0.0);
			
//				commandList.add(new BackupDrive(2.0, true));
=======
				commandList.add(new BackupDrive(2.0, true));
>>>>>>> lubecki-lonestar
				break;

			case 2:
				commandList.addAll(new PlaceCubeScale(1).getCommands());
				break;

			case 3:
				commandList.addAll(new PlaceCubeScale(3).getCommands());
				break;

			case 4:
				commandList.addAll(new PlaceCubeSwitch(1).getCommands());
				break;

			case 5:
				commandList.addAll(new PlaceCubeSwitch(3).getCommands());
				break;

			case 6:
				commandList.addAll(new PlaceCubeSwitch(2).getCommands());
				break;

			default:
				break;

		}
<<<<<<< HEAD
			while(DriverStation.getInstance().isAutonomous() && !commandList.isEmpty()) {
				commandList.remove(0).run();
				System.out.println("end");
			}
			commandsDone = true;

			for (Subsystem system : subsystems) {
				system.disabledContinuous();
			}
		
		
	}

	private static String reverse(String str) {
		String reverse = "";
		while (str.length() > 0) {
			reverse = str.substring(0, 1) + reverse;
			str = str.substring(1);
		}
		return reverse;
	}

=======

		while (DriverStation.getInstance().isAutonomous() && !commandList.isEmpty()) {
			commandList.remove(0).run();
			System.out.println("end");
		}
		commandsDone = true;

		for (Subsystem system : subsystems) {
			system.disabledContinuous();
		}
	}

>>>>>>> lubecki-lonestar
	public static void pause(double time) {
		double startTime = Timer.getFPGATimestamp();
		time = Math.abs(time);

<<<<<<< HEAD
		while (DriverStation.getInstance().isAutonomous() && !setPointReached 
=======
		while (DriverStation.getInstance().isAutonomous() && !setPointReached
>>>>>>> lubecki-lonestar
				&& Timer.getFPGATimestamp() - startTime < time) {
			Feedback.getInstance().update();
			Feedback.getInstance().smartDashboard();
			AutoManager.smartDashboard();

<<<<<<< HEAD
			for(Subsystem system : subsystems) {
=======
			for (Subsystem system : subsystems) {
>>>>>>> lubecki-lonestar
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

	public static boolean commandsDone() {
		return commandsDone;
	}

	public static void smartDashboard() {
		SmartDashboard.putNumber("A_AGGREGATETIME", aggregateTime);
	}

	public static AutoManager getInstance() {
		return instance == null ? instance = new AutoManager() : instance;
	}
	
	public static AutoManager getInstance(int auto) {
		return instance == null ? instance = new AutoManager(auto) : instance;
	}
}
