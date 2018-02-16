package org.texastorque.feedback;

import org.texastorque.constants.Ports;
import org.texastorque.io.Input;
import org.texastorque.torquelib.component.TorqueEncoder;
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
	private final double DISTANCE_CONVERSION = 0.07383;
	private final double ANGLE_CONVERSION = 360 / 250;
	
	//sensors
	private TorqueEncoder DB_leftEncoder;
	private TorqueEncoder DB_rightEncoder;
	private AHRS DB_gyro;
	
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

		DB_leftEncoder = new TorqueEncoder(Ports.DB_LEFT_ENCODER_A, Ports.DB_LEFT_ENCODER_B, false, EncodingType.k4X);
		DB_rightEncoder = new TorqueEncoder(Ports.DB_RIGHT_ENCODER_A, Ports.DB_RIGHT_ENCODER_B, false, EncodingType.k4X);
		//PT_encoder = new TorqueEncoder(1, 2, false, EncodingType.k4X);
		DB_gyro = new AHRS(SPI.Port.kMXP);
		resetEncoders();
	}
		
	public void resetEncoders() {
		DB_leftEncoder.reset();
		DB_rightEncoder.reset();
		//PT_encoder.reset();
	}
	
	public void update() {
		DB_leftEncoder.calc();
		DB_rightEncoder.calc();
		//PT_encoder.calc();
		
		//Drivebase
		DB_leftDistance = DB_leftEncoder.getDistance() * DISTANCE_CONVERSION;
		DB_rightDistance = DB_rightEncoder.getDistance() * DISTANCE_CONVERSION;
		DB_leftRate = DB_leftEncoder.getRate() * DISTANCE_CONVERSION;
		DB_rightRate = DB_rightEncoder.getRate() * DISTANCE_CONVERSION;
		
		DB_angle = DB_gyro.getAngle();
		DB_angleRate = DB_gyro.getVelocityX();
		/*
		//Pivot
		PT_angle = PT_encoder.getDistance() * ANGLE_CONVERSION;
		PT_angleRate = PT_encoder.getRate() * ANGLE_CONVERSION;
		*/
	}
	
	public double getDBDistance() {
		return DB_distance;
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
