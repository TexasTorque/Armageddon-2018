package org.texastorque.models;

import org.texastorque.feedback.Feedback;

public class SensorInputState {
	
	private double driveEncoderRateLeft;
	private double driveEncoderRateRight;
	
	public SensorInputState() { }

	public SensorInputState(Feedback sensorInput) {
		this.driveEncoderRateLeft = sensorInput.getDBLeftRate();
		this.driveEncoderRateRight = sensorInput.getDBRightRate();
	}

}
