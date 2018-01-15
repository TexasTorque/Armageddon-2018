package org.texastorque.feedback;

import org.texastorque.constants.Ports;
import org.texastorque.io.HumanInput;
import org.texastorque.io.Input;
import org.texastorque.torquelib.component.TorqueEncoder;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Feedback {

	private static Feedback instance;

	private TorqueEncoder leftDrivebase;
	private TorqueEncoder rightDrivebase;
	
	public Feedback() {
		leftDrivebase = new TorqueEncoder(Ports.DB_LEFTENCODER_A, Ports.DB_LEFTENCODER_B, false, EncodingType.k4X);
		rightDrivebase = new TorqueEncoder(Ports.DB_RIGHTENCODER_A, Ports.DB_RIGHTENCODER_B, false, EncodingType.k4X);
	}
	
	public void update() {
		leftDrivebase.calc();
		rightDrivebase.calc();
		
	}
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}

	public void SmartDashboard() {
		SmartDashboard.putNumber("Left_Encoder_Speed", leftDrivebase.getRate()*.5);
		SmartDashboard.putNumber("Right_Encoder_Speed", rightDrivebase.getRate());
		SmartDashboard.putNumber("Time", Timer.getFPGATimestamp());
	}

}
