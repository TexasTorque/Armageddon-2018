package org.texastorque.auto;

import edu.wpi.first.wpilibj.DriverStation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.texastorque.auto.drive.BackupDrive;
import org.texastorque.auto.drive.Drive;
import org.texastorque.auto.sequences.PlaceCubeScale;
import org.texastorque.auto.sequences.PlaceCubeSwitch;
import org.texastorque.auto.sequences.PlaceTwoCubeScale;
import org.texastorque.auto.sequences.PlaceTwoCubeSwitch;
import org.texastorque.feedback.Feedback;
import org.texastorque.io.RobotOutput;
import org.texastorque.subsystems.Arm;
import org.texastorque.subsystems.Claw;
import org.texastorque.subsystems.Drivebase;
import org.texastorque.subsystems.Pivot;
import org.texastorque.subsystems.Subsystem;
import org.texastorque.subsystems.WheelIntake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoManager {

	private static AutoManager instance;
	private static LinkedList<AutoCommand> commandList;
	private static ArrayList<Subsystem> subsystems;
	private static double aggregateTime;
	private static ArrayList<AutoSequence> autoModes = new ArrayList<AutoSequence>();

	private static boolean commandsDone = false;
	private static volatile boolean setPointReached;

	private static int autoMode;
	
	
	public AutoManager() {
		commandList = new LinkedList<>();
		subsystems = new ArrayList<>();
		subsystems.add(Drivebase.getInstance());
		subsystems.add(Arm.getInstance());
		subsystems.add(Pivot.getInstance());
		subsystems.add(Claw.getInstance());
		subsystems.add(WheelIntake.getInstance());
		initAutoList();
		
	}
	
	private void initAutoList() {
		autoModes.add((new PlaceCubeScale(1)));
		autoModes.add((new PlaceCubeScale(1)));
		autoModes.add((new PlaceCubeScale(1)));
		autoModes.add((new PlaceCubeScale(3)));
		autoModes.add((new PlaceCubeSwitch(1)));
		autoModes.add((new PlaceCubeSwitch(3)));
		autoModes.add((new PlaceCubeSwitch(2)));
		autoModes.add(new PlaceTwoCubeScale(1));
		autoModes.add(new PlaceTwoCubeScale(3));
		autoModes.add(new PlaceTwoCubeSwitch());

	}

	public static void beginAuto() {
		setPointReached = false;
		commandsDone = false;
		analyzeAutoMode();
	}

	public void setAutoMode(int auto) {
		autoMode = auto;
	}
	
	public static void analyzeAutoMode() {
		commandList.addAll(autoModes.get(autoMode).getCommands());
		while(DriverStation.getInstance().isAutonomous() && !commandList.isEmpty()) {
				commandList.remove(0).run();
				System.out.println("end");
			}
			commandsDone = true;

			for (Subsystem system : subsystems) {
				system.disabledContinuous();
			}
		
		
	}

	public static void pause(double time) {
		double startTime = Timer.getFPGATimestamp();
		time = Math.abs(time);

		while (DriverStation.getInstance().isAutonomous() && !setPointReached
				&& Timer.getFPGATimestamp() - startTime < time) {
			Feedback.getInstance().update();
			Feedback.getInstance().smartDashboard();
			AutoManager.smartDashboard();

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

	public static boolean commandsDone() {
		return commandsDone;
	}

	public static void smartDashboard() {
		SmartDashboard.putNumber("A_AGGREGATETIME", aggregateTime);
	}

	public static AutoManager getInstance() {
		return instance == null ? instance = new AutoManager() : instance;
	}
	
}
