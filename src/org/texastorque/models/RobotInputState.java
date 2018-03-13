package org.texastorque.models;

import org.texastorque.feedback.Feedback;
import org.texastorque.io.HumanInput;

import edu.wpi.first.wpilibj.Timer;

public class RobotInputState {
	
	/** The time during the match that the input was recorded. */
	public double time;
	
	public DriverInputState driver;
	public OperatorInputState operator;
	
	public SensorInputState sensors;
	
	public RobotInputState() { }
	
	public RobotInputState(HumanInput input, Feedback sensorInput) {
		this.time = Timer.getFPGATimestamp();
		this.driver = new DriverInputState(input.driver);
		this.operator = new OperatorInputState(input.operator, input.board);
		this.sensors = new SensorInputState(sensorInput);
	}
}
