package org.texastorque.auto;

import edu.wpi.first.wpilibj.DriverStation;
import java.util.ArrayList;
import java.util.LinkedList;

import org.texastorque.auto.drive.BackupDrive;
import org.texastorque.auto.sequences.PlaceCubeScale;
import org.texastorque.auto.sequences.PlaceCubeSwitch;
import org.texastorque.feedback.Feedback;
import org.texastorque.io.RobotOutput;
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

	public static void init() {
		SmartDashboard.putNumber("AUTOMODE", 0);
		commandList = new LinkedList<>();

		subsystems = new ArrayList<>();
		subsystems.add(Drivebase.getInstance());
		subsystems.add(Arm.getInstance());
		subsystems.add(Pivot.getInstance());
	}

	public static void beginAuto() {
		System.out.println("beginAuto");
		setPointReached = false;
		commandsDone = false;
		analyzeAutoMode();
	}

	public static void analyzeAutoMode() {
		int autoMode = Integer.parseInt(reverse(Integer.toString(
				(int)(SmartDashboard.getNumber("AUTOMODE", 0)))));

		while (autoMode > 0) {
			switch (autoMode % 10) {
			case 0:
				System.out.println("0");
				break;
			case 1:
				/*double startTime = Timer.getFPGATimestamp();
				if (Timer.getFPGATimestamp() < startTime + 1.5) {
					RobotOutput.getInstance().setDrivebaseSpeed(.65, .65);
				} else RobotOutput.getInstance().setDrivebaseSpeed(0.0, 0.0);
<<<<<<< HEAD

=======
				*/
>>>>>>> dlzou
				commandList.add(new BackupDrive(2.0, true));
				break;

			case 2:
				commandList.addAll(new PlaceCubeSwitch().getCommands());
				//commandList.addAll(new PlaceCubeScale(1).getCommands());
				break;

			case 3:
				//commandList.addAll(new PlaceCubeScale(3).getCommands());
				break;
				
			case 4:
				//commandList.addAll(new PlaceCubeSwitch(1).getCommands());
				break;

			case 5:
				//commandList.addAll(new PlaceCubeSwitch(3).getCommands());
				break;
				
			case 6: 
				//commandList.addAll(new PlaceCubeSwitch(2).getCommands());
				break;

			default:
				break;

			}

			while(DriverStation.getInstance().isAutonomous() && !commandList.isEmpty()) {
				commandList.remove(0).run();
				System.out.println("end");
			}
			commandsDone = true;

			for (Subsystem system : subsystems) {
				system.disabledContinuous();
			}
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
