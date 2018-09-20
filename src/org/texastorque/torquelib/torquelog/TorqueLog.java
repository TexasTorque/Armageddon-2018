package org.texastorque.torquelib.torquelog;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.texastorque.torquelib.torquelog.LogData.Priority;
import org.texastorque.util.FileUtils;
import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TorqueLog {

	private static String fileName;
	
	private static ArrayList<LogData> logKeys = new ArrayList<LogData>(){{
		add(new LogData("Left_Encoder_Speed", Priority.HIGH));
	}};	
	
	//Delimiter used in CSV file
	private static final String CD = ",";
	private static final String NLS = "\n";
	//CSV file header
	private static String FH;
	static {
		FH = "Cycle,Time,Priority,";
		for(LogData data : logKeys) {
			FH += data.KEY + ",";
		}
	}

	private static int cycleNum = 0;
	private static String getLogValue(String key) {
		NetworkTableType getType = SmartDashboard.getEntry(key).getType();
		switch (getType) {
		case kBoolean:
			return String.valueOf(SmartDashboard.getBoolean(key, false));
		case kDouble:
			return String.valueOf(SmartDashboard.getNumber(key, -1));
		case kString:
			return SmartDashboard.getString(key, "NA");
		default:
			System.out.println(getType);
			return "";
		}
	}

	public static void startLog() {
//		fileName = FileUtils.createTimestampedFilepath("/home/lvuser/TorqueLog", "TorqueLog", "csv");

		try (FileWriter fW = new FileWriter(fileName, true)) {
			fW.append(FH);
			fW.append(NLS);
		} catch (IOException e) {
			System.out.println("Failed to Create Log File at: " + fileName);
		}

	}

	public static boolean needUpdate() {
		cycleNum++;

		if (logKeys.size() == 0)
			return false;
		Priority[] priorities = Priority.values();
		for (Priority p : priorities) {
			if (cycleNum % p.CYCLEHZ == 0)
				return true;
		}
		return false;
	}
}
