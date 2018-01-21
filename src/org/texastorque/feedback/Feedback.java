package org.texastorque.feedback;

import org.texastorque.constants.Ports;
import org.texastorque.torquelib.component.TorqueEncoder;
import org.texastorque.torquelib.util.TorqueMathUtil;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.texastorque.io.Input;
import org.texastorque.torquelib.component.TorqueEncoder;

import java.util.ArrayList;

public class Feedback {

	public static Feedback instance;
	
	//constants
	private final double DB_DISTANCE_CONVERSION = 0.04927988; //incorrect
	
	//sensors
	private TorqueEncoder DB_leftEncoder;
	private TorqueEncoder DB_rightEncoder;
	private AHRS DB_gyro;
	
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
	
	public Feedback() {
		init();
	}
	
	public void init() {
		DB_leftEncoder = new TorqueEncoder(Ports.DB_LEFT_ENCODER_A, Ports.DB_LEFT_ENCODER_B, false, EncodingType.k4X);
		DB_rightEncoder = new TorqueEncoder(Ports.DB_RIGHT_ENCODER_A, Ports.DB_RIGHT_ENCODER_B, false, EncodingType.k4X);
		DB_gyro = new AHRS(SPI.Port.kMXP);
	}
	
	public void update() {
		DB_leftEncoder.calc();
		DB_rightEncoder.calc();
		
		DB_leftDistance = DB_leftEncoder.getDistance() * DB_DISTANCE_CONVERSION;
		DB_rightDistance = DB_rightEncoder.getDistance() * DB_DISTANCE_CONVERSION;
		DB_leftRate = DB_leftEncoder.getRate() * DB_DISTANCE_CONVERSION;
		DB_rightRate = DB_rightEncoder.getRate() * DB_DISTANCE_CONVERSION;
		
		DB_angle = DB_gyro.getAngle();
		DB_angleRate = DB_gyro.getVelocityX();
	}
	
	public double getDB_distance() {
		return DB_distance;
	}
	
	public double getDB_leftDistance() {
		return DB_leftDistance;
	}
	
	public double getDB_rightDistance() {
		return DB_rightDistance;
	}
	
	public double getDB_leftRate() {
		return DB_leftRate;
	}
	
	public double getDB_rightRate() {
		return DB_rightRate;
	}
	
	public double getDB_angle() {
		return DB_angle;
	}
	
	public double getDB_angleRate() {
		return DB_angleRate;
	}
	
	public void resetDB_encoders() {
		DB_leftEncoder.reset();
		DB_rightEncoder.reset();
	}
	
	public void resetDB_gyro() {
		DB_gyro.reset();
	}
	
	public void smartDashBoard() {
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
