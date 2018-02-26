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
	
	public static Feedback instance;
	
	//constants
	public static final double DISTANCE_CONVERSION = 0.07383;
	public static final double DISTANCE_PER_PULSE_IN_FEET= 0.02618;
	public static final double ANGLE_CONVERSION = 360/250;
	public static final int AM_CONVERSION = 10150;

	private double sum;
	private double duration;
	private double lastTime;
	
	//sensors
	private TorqueEncoder DB_leftEncoder;
	private TorqueEncoder DB_rightEncoder;
	private AHRS DB_gyro;
	private TorqueEncoder PT_encoder;
	private TorqueEncoder AM_encoder;
	
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
	
	private double AM_distance;
	
	public Feedback() {

		DB_leftEncoder = new TorqueEncoder(Ports.DB_LEFT_ENCODER_A, Ports.DB_LEFT_ENCODER_B, false, EncodingType.k4X);
		DB_rightEncoder = new TorqueEncoder(Ports.DB_RIGHT_ENCODER_A, Ports.DB_RIGHT_ENCODER_B, false, EncodingType.k4X);
		PT_encoder = new TorqueEncoder(Ports.PT_ENCODER_A, Ports.PT_ENCODER_B, false, EncodingType.k4X);
		AM_encoder = new TorqueEncoder(Ports.AM_ENCODER_A, Ports.AM_ENCODER_B, true, EncodingType.k4X);
		DB_gyro = new AHRS(SPI.Port.kMXP);
		resetEncoders();
	}
		
	public void resetEncoders() {
		DB_leftEncoder.reset();
		DB_rightEncoder.reset();
		PT_encoder.reset();
		AM_encoder.reset();
	}
	
	public void resetDriveEncoders() {
		DB_leftEncoder.reset();
		DB_rightEncoder.reset();
	}
	
	public void resetPivot() {
		PT_encoder.reset();
	}

	public void update() {
		DB_leftEncoder.calc();
		DB_rightEncoder.calc();
		PT_encoder.calc();
		AM_encoder.calc();
		
		//Drivebase
		DB_leftDistance = DB_leftEncoder.getDistance() * DISTANCE_CONVERSION;
		DB_rightDistance = DB_rightEncoder.getDistance() * DISTANCE_CONVERSION;
		DB_leftRate = DB_leftEncoder.getRate() * DISTANCE_CONVERSION;
		DB_rightRate = DB_rightEncoder.getRate() * DISTANCE_CONVERSION;
		
		DB_angle = DB_gyro.getAngle();
		DB_angleRate = DB_gyro.getVelocityX();
		
		//Pivot
		PT_angle = PT_encoder.getDistance() * ANGLE_CONVERSION;
		PT_angleRate = PT_encoder.getRate() * ANGLE_CONVERSION;
		
		AM_distance = AM_encoder.getDistance();
	}
	
	public double getDBDistance() {
		return DB_distance;
	}
	
	public TorqueEncoder getLeftEncoder() {
		return DB_leftEncoder;
	}
	
	public TorqueEncoder getRightEncoder() {
		return DB_rightEncoder;
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
	
	public double getArmDistance() {
		return AM_distance;
	}
	
	public TorqueEncoder getArmEncoder() {
		return AM_encoder;
	}
	
	public void resetDBGyro() {
		DB_gyro.reset();
	}
	
	public void smartDashboard() {
		duration += (Timer.getFPGATimestamp() - lastTime);
		lastTime = Timer.getFPGATimestamp();
		
		SmartDashboard.putNumber("Left_Encoder_Distance", DB_leftDistance);
		SmartDashboard.putNumber("Right_Encoder_Distance", DB_rightDistance);
		SmartDashboard.putNumber("Left_Encoder_Speed", DB_leftEncoder.getRate() * DISTANCE_CONVERSION);
		SmartDashboard.putNumber("Right_Encoder_Speed", DB_rightEncoder.getRate() * DISTANCE_CONVERSION);
		SmartDashboard.putNumber("Time", Timer.getFPGATimestamp());
		SmartDashboard.putNumber("Rate", DB_leftEncoder.getRate());
		SmartDashboard.putNumber("Gyro", DB_gyro.getAngle());
		SmartDashboard.putNumber("PT", PT_encoder.getDistance());
		SmartDashboard.putNumber("AM", AM_encoder.getDistance());
		SmartDashboard.putNumber("X", DB_gyro.getDisplacementX());
		SmartDashboard.putNumber("Y", DB_gyro.getDisplacementY());
		SmartDashboard.putNumber("Z", DB_gyro.getDisplacementZ());
		SmartDashboard.putNumber("Yaw", DB_gyro.getYaw());
		SmartDashboard.putNumber("Roll", DB_gyro.getRoll());
	}
	
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}
}