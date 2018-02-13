package org.texastorque.feedback;

import org.texastorque.constants.Ports;
import org.texastorque.io.Input;
import org.texastorque.torquelib.component.TorqueEncoder;
import com.kauailabs.navx.frc.AHRS;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

import java.util.ArrayList;

public class Feedback {

	private double sum;
	private double duration;
	private double lastTime;
	private TorqueEncoder leftDrivebase;
	private TorqueEncoder rightDrivebase;
	private final double DISTANCE_CONVERSION = 0.0744;
	private final double DISTANCE_PER_PULSE_IN_FEET= 0.02618;
	private final double ANGLE_CONVERSION = 360/250;
	private AHRS DB_gyro;
	public static Feedback instance;
	
	//constants
	
	private TorqueEncoder PT_encoder;
	
	//related values
	private double DB_distance;
	private double DB_leftDistance;
	private double DB_rightDistance;
	
	private double DB_leftRate;
	private double DB_rightRate;

	private double DB_leftAcceleration;
	private double DB_rightAcceleration;
	
	private double DB_angle;
	private double DB_angleRate;
	
	private double PT_angle;
	private double PT_angleRate;
	
	public Feedback() {

		leftDrivebase = new TorqueEncoder(Ports.DB_LEFT_ENCODER_A, Ports.DB_LEFT_ENCODER_B, false, EncodingType.k4X);
		rightDrivebase = new TorqueEncoder(Ports.DB_RIGHT_ENCODER_A, Ports.DB_RIGHT_ENCODER_B, false, EncodingType.k4X);
		PT_encoder = new TorqueEncoder(Ports.PT_ENCODER_A, Ports.PT_ENCODER_B, false, EncodingType.k4X);
		DB_gyro = new AHRS(SPI.Port.kMXP);
		resetEncoders();
	}
		
	public void resetEncoders() {
		leftDrivebase.reset();
		rightDrivebase.reset();
		PT_encoder.reset();
	}
	
	public void update() {
		leftDrivebase.calc();
		rightDrivebase.calc();
		PT_encoder.calc();
		
		//Drivebase
		DB_leftDistance = leftDrivebase.getDistance() * DISTANCE_CONVERSION;
		DB_rightDistance = rightDrivebase.getDistance() * DISTANCE_CONVERSION;
		DB_leftRate = leftDrivebase.getRate() * DISTANCE_CONVERSION;
		DB_rightRate = rightDrivebase.getRate() * DISTANCE_CONVERSION;
		
		DB_angle = DB_gyro.getAngle();
		DB_angleRate = DB_gyro.getVelocityX();
		
		//Pivot
		PT_angle = PT_encoder.getDistance() * ANGLE_CONVERSION;
		PT_angleRate = PT_encoder.getRate() * ANGLE_CONVERSION;
	}
	
	public double getDBDistance() {
		return DB_distance;
	}
	
	public TorqueEncoder getLeftEncoder() {
		return leftDrivebase;
	}
	
	public TorqueEncoder getRightEncoder() {
		return rightDrivebase;
	}
	
	public void SmartDashboard() {
		duration += (Timer.getFPGATimestamp() - lastTime);
		lastTime = Timer.getFPGATimestamp();
		
		SmartDashboard.putNumber("Left_Encoder_Distance", leftDrivebase.getDistance());
		SmartDashboard.putNumber("Right_Encoder_Distance", rightDrivebase.getDistance()* DISTANCE_CONVERSION);
		SmartDashboard.putNumber("Left_Encoder_Speed", leftDrivebase.getRate());
		SmartDashboard.putNumber("Right_Encoder_Speed", rightDrivebase.getRate()*DISTANCE_CONVERSION);
		SmartDashboard.putNumber("Time", Timer.getFPGATimestamp());
		SmartDashboard.putNumber("Rate", leftDrivebase.getRate());
	}
	
		public double getDBLeftDistance() {
		return DB_leftDistance;
	}
	
	public double getDBRightDistance() {
		return DB_rightDistance;
	}
	
	public double getDBLeftRate() {
		return DB_leftRate;
	}
	
	public double getDBRightRate() {
		return DB_rightRate;
	}
	
	public double getDBAngle() {
		return DB_angle;
	}
	
	public double getDBAngleRate() {
		return DB_angleRate;
	}
	
	public double getPTAngle() {
		return PT_angle;
	}
	
	public double getPTAngleRate() {
		return PT_angleRate;
	}
	
	public void resetDBGyro() {
		DB_gyro.reset();
	}
	
	public void smartDashboard() {
		SmartDashboard.putNumber("DB_LEFTPOSITION", DB_leftDistance);
		SmartDashboard.putNumber("DB_RIGHTPOSITION", DB_rightDistance);
		SmartDashboard.putNumber("DB_GYRO", DB_angle);
		SmartDashboard.putNumber("DB_GYRORATE", DB_angleRate);
		SmartDashboard.putNumber("GYROX", DB_gyro.getAngle());

	}
	
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}
}
