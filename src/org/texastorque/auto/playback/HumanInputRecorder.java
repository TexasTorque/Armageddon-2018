package org.texastorque.auto.playback;

import java.util.ArrayList;
import java.util.List;

import org.texastorque.feedback.Feedback;
import org.texastorque.io.HumanInput;
import org.texastorque.io.Input;
import org.texastorque.models.RobotInputState;
import org.texastorque.torquelib.util.TorqueToggle;
import org.texastorque.util.FileUtils;

public class HumanInputRecorder extends Input {
	
	private static final String RECORDING_DIR = "/home/lvuser";
	
	private HumanInput humanInput;
	private Feedback robotSensors;
	private String outputFile;
	private TorqueToggle recordingToggle;
	private List<RobotInputState> inputHistory;
	private boolean prevRecordingStatus = false;
	private static HumanInputRecorder instance;
	private static String currentFieldConfig;
	
	public static HumanInputRecorder getInstance(){
		return instance == null ? instance = new HumanInputRecorder() : instance;
	}
	
	public HumanInputRecorder() {
		this.humanInput = HumanInput.getInstance();
		this.robotSensors = Feedback.getInstance();
		this.outputFile = createJSONFile();
		this.recordingToggle = new TorqueToggle();
		this.inputHistory = new ArrayList<RobotInputState>();
	}
	
	public void setCurrentFieldConfig(String str) {
		currentFieldConfig = str;
	}
	
	public void update(){
		humanInput.update();  // Make sure input state is up to date.

		// Toggle recording if driver is pressing down on DPad.
		recordingToggle.calc(humanInput.driver.getDPADDown());
		if(prevRecordingStatus != recordingToggle.get())
			System.out.println(recordingToggle.get());
		prevRecordingStatus = recordingToggle.get();
		// Save the output to file if driver is pressing up on DPad.
		if(humanInput.driver.getDPADUp()) {
			FileUtils.writeToJSON(this.outputFile, this.inputHistory);
			System.out.println("That's a print");
		}
		
		recordRobotState();
	}
	
	public void recordRobotState() {
		if (!recordingToggle.get()) return;  // Guard design pattern.
		inputHistory.add(new RobotInputState(this.humanInput, this.robotSensors));
	}

	public boolean getRecording() {
		return recordingToggle.get();
	}
	
	private static String createJSONFile() {
		String recording = "recording" + currentFieldConfig;
		return FileUtils.createTimestampedFilepath(RECORDING_DIR, "recording", "json");
	}
}
