package org.texastorque.feedback;

import org.texastorque.constants.Ports;
import org.texastorque.io.HumanInput;
import org.texastorque.io.Input;
import org.texastorque.torquelib.component.TorqueEncoder;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Feedback {

	private static Feedback instance;

	private TorqueEncoder leftDrivebase;
	private TorqueEncoder rightDrivebase;
	private final double DISTANCE_CONVERSION = 0.0744;
	private AHRS DB_gyro;
	
	public Feedback() {
		leftDrivebase = new TorqueEncoder(Ports.DB_LEFTENCODER_A, Ports.DB_LEFTENCODER_B, false, EncodingType.k4X);
		rightDrivebase = new TorqueEncoder(Ports.DB_RIGHTENCODER_A, Ports.DB_RIGHTENCODER_B, false, EncodingType.k4X);
		leftDrivebase.reset();
		rightDrivebase.reset();
		
	}
	
	public void update() {
		leftDrivebase.calc();
		rightDrivebase.calc();
	}
	
	public void reset() {
		leftDrivebase.reset();
		rightDrivebase.reset();
	}
	
	public TorqueEncoder getLeftEncoder() {
		return leftDrivebase;
	}
	
	public TorqueEncoder getRightEncoder() {
		return rightDrivebase;
	}
	
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}

	public void SmartDashboard() {
		SmartDashboard.putNumber("Left_Encoder_Distance", leftDrivebase.getDistance() * DISTANCE_CONVERSION);
		SmartDashboard.putNumber("Right_Encoder_Distance", rightDrivebase.getDistance()* DISTANCE_CONVERSION);
		SmartDashboard.putNumber("Left_Encoder_Speed", leftDrivebase.getRate());
		SmartDashboard.putNumber("Right_Encoder_Speed", rightDrivebase.getRate()*DISTANCE_CONVERSION);
	}

}
