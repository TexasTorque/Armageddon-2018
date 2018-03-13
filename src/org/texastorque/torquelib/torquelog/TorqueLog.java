package org.texastorque.torquelib.torquelog;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.texastorque.torquelib.torquelog.LogData.Priority;
import org.texastorque.util.FileUtils;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TorqueLog {
	
	private static String fileName;
	
	private static ArrayList<LogData> logKeys = new ArrayList<LogData>(){{
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
		
	// call once per cycle to log data
	public static void logData() {
		// check if comparisons need to be made
		if(!needUpdate()) return;
		
		try(FileWriter fW = new FileWriter(fileName, true)){
			for(Priority p : Priority.values()) {
				if(cycleNum % p.CYCLEHZ == 0) {
					fW.append(String.valueOf(cycleNum));
					fW.append(CD);
					fW.append(String.valueOf(Timer.getFPGATimestamp()));
					fW.append(CD);
					fW.append(p.name());
					fW.append(CD);
					for(LogData data : logKeys) {
						fW.append((data.P == p) ? String.valueOf(SmartDashboard.getData(data.KEY)) : "");
						fW.append(CD);
					}
					fW.append(NLS);
				}
			}
		} catch (IOException e) {
			System.out.println("Failed to Create Log File at: "+fileName);
		}
		
		cycleNum++;		
	}
	
	public static void startLog() {
		fileName = FileUtils.createTimestampedFilepath("/home/lvuser", "TorqueLog", "xls");
		try(FileWriter fW = new FileWriter(fileName, true)){
			fW.append(FH);
			fW.append(NLS);
		} catch (IOException e) {
			System.out.println("Failed to Create Log File at: "+fileName);
		}
		
	}
	
	public static boolean needUpdate() {
		if(logKeys.size() == 0) return false;
		Priority[] priorities = Priority.values();
		for(Priority p : priorities) {
			if(cycleNum % p.CYCLEHZ == 0) return true;
		}
		return false;
	}
}
