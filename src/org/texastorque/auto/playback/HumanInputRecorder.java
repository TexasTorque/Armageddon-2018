package org.texastorque.auto.playback;

import org.texastorque.io.Input;

public class HumanInputRecorder extends Input {
//	
//	private static final String RECORDING_DIR = "/home/lvuser";
//	
//	private HumanInput humanInput;
//	private Feedback robotSensors;
//	private String outputFile;
//	private TorqueToggle recordingToggle;
//	private List<RobotInputState> inputHistory;
//	private boolean prevRecordingStatus = false;
//	private static HumanInputRecorder instance;
//	private static String currentFieldConfig;
//	
//	public static HumanInputRecorder getInstance(){
//		return instance == null ? instance = new HumanInputRecorder() : instance;
//	}
//	
//	public HumanInputRecorder() {
//		this.humanInput = HumanInput.getInstance();
//		this.robotSensors = Feedback.getInstance();
//		this.outputFile = createJSONFile();
//		this.recordingToggle = new TorqueToggle();
//		this.inputHistory = new ArrayList<RobotInputState>();
//	}
//	
//	public void setCurrentFieldConfig() {
//		currentFieldConfig = DriverStation.getInstance().getGameSpecificMessage();
//	}
//	
//	public void update(){
//		humanInput.update();  // Make sure input state is up to date.
//
//		// Toggle recording if driver is pressing down on DPad.
//		recordingToggle.calc(humanInput.operator.getLeftCenterButton());
//		if(prevRecordingStatus != recordingToggle.get())
//			System.out.println(recordingToggle.get());
//		prevRecordingStatus = recordingToggle.get();
//		// Save the output to file if driver is pressing up on DPad.
//		if(humanInput.operator.getRightCenterButton()) {
//	//		FileUtils.writeToJSON(this.outputFile, this.inputHistory);
//			System.out.println("That's a print");
//		}
//		
//		recordRobotState();
//	}
//	
//	public void recordRobotState() {
//		if (!recordingToggle.get()) return;  // Guard design pattern.
//		inputHistory.add(new RobotInputState(this.humanInput, this.robotSensors));
//	}
//
//	public boolean getRecording() {
//		return recordingToggle.get();
//	}
//	
//	private static String createJSONFile() {
//		String recording = "";
//		if(DriverStation.getInstance().getAlliance().equals(Alliance.Red))
//			recording = "recording_LEFT" + currentFieldConfig;
//		else recording = "recording_RIGHT" + currentFieldConfig;
//		return FileUtils.createTimestampedFilepath(RECORDING_DIR, recording, "json");
//	}
}
