package org.texastorque.auto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import org.texastorque.auto.sequences.PlaceCubeScale;
import org.texastorque.auto.sequences.PlaceCubeScaleDebug;
import org.texastorque.auto.sequences.PlaceCubeSwitch;
import org.texastorque.auto.sequences.PlaceTwoCubeScale;
import org.texastorque.auto.sequences.PlaceTwoCubeSwitch;
import org.texastorque.auto.sequences.TeamPlayer;
import org.texastorque.feedback.Feedback;
import org.texastorque.subsystems.Arm;
import org.texastorque.subsystems.Claw;
import org.texastorque.subsystems.Drivebase;
import org.texastorque.subsystems.Pivot;
import org.texastorque.subsystems.Subsystem;
import org.texastorque.subsystems.WheelIntake;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class AutoManager {

	private static volatile AutoManager instance;
	private static LinkedList<AutoCommand> commandList;
	private static ArrayList<Subsystem> subsystems;
	private static ArrayList<AutoSequence> autoModes = new ArrayList<AutoSequence>();

	private static boolean commandsDone = false;
	private static volatile boolean setPointReached;
	private String fieldConfig;
	
	private static int autoMode;

	private AutoManager() {
		commandList = new LinkedList<>();
		subsystems = new ArrayList<>();
		subsystems.add(Drivebase.getInstance());
		subsystems.add(Arm.getInstance());
		subsystems.add(Pivot.getInstance());
		subsystems.add(Claw.getInstance());
		subsystems.add(WheelIntake.getInstance());
	}

	public void initAutoList() {
		autoModes.add(new PlaceCubeScale(1));
		autoModes.add(new PlaceCubeScale(1));
		autoModes.add(new PlaceCubeScale(1));
		autoModes.add(new PlaceCubeScale(3));
		autoModes.add(new PlaceCubeSwitch(1));
		autoModes.add(new PlaceCubeSwitch(3));
		autoModes.add(new PlaceCubeSwitch(2));
		autoModes.add(new PlaceTwoCubeScale(1));
		autoModes.add(new PlaceTwoCubeScale(3));
		autoModes.add(new PlaceTwoCubeSwitch());
		autoModes.add(new TeamPlayer(1));
		autoModes.add(new TeamPlayer(3));
	}

	public static void beginAuto() {
		setPointReached = false;
		commandsDone = false;
		analyzeAutoMode();
	}

	public void setAutoMode(int auto) {
		autoMode = auto;
		autoModes.get(auto).setFieldConfig(DriverStation.getInstance().getGameSpecificMessage());
		autoModes.get(auto).init();
	}

	public String getFieldConfig() {
		return fieldConfig;
	}
	
	public static void analyzeAutoMode() {
		commandList.addAll(autoModes.get(autoMode).getCommands());
		while (DriverStation.getInstance().isAutonomous() && !commandList.isEmpty()) {
			commandList.remove(0).run();
			// System.out.println("end");
		}
		commandsDone = true;

		for (Subsystem system : subsystems) {
			system.disabledContinuous();
		}
	}

	private static volatile String lastThread = "";
	private static final SimpleDateFormat DEBUG_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
	
	public static synchronized void pause(double time) {
		double startTime = Timer.getFPGATimestamp();
		time = Math.abs(time);

		while (DriverStation.getInstance().isAutonomous()
				&& !setPointReached
				&& (Timer.getFPGATimestamp() - startTime < time || time == 0)) {
			
			Feedback.getInstance().update();
			Feedback.getInstance().smartDashboard();
			
			for (Subsystem system : subsystems) {
				system.autoContinuous();
				system.smartDashboard();
			}
		}

		setPointReached = false;
	}

	public static void setStepDone() {
		setPointReached = true;
	}

	public static boolean commandsDone() {
		return commandsDone;
	}

	public static void smartDashboard() {
	}
	
	private static void debugThread() {
		String threadName = Thread.currentThread().getName();
		
		if (!threadName.equals(lastThread)) {
			System.out.println("Pausing thread: " + threadName + " Timestamp: " + DEBUG_DATE_FORMAT.format(new Date()));
			lastThread = threadName;
		}
	}
	
	public static synchronized AutoManager getInstance() {
		return instance == null ? instance = new AutoManager() : instance;
	}

}
