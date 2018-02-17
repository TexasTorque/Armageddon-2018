package org.texastorque.feedback;

import org.texastorque.constants.Ports;
import org.texastorque.feedback.*;
import org.texastorque.io.Input;
import org.texastorque.torquelib.component.TorqueEncoder;
import com.kauailabs.navx.frc.AHRS;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.math.*;

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
	
	private Pixy pixy;
	
	private double PX_x1;
	private double PX_y1;
	private double PX_surfaceArea1;
	private double PX_height;
	private double PX_x2;
	private double PX_y2;
	private double PX_surfaceArea2;
	
	private final double PX_CONVERSIONH = .234;
	private final double PX_CONVERSIONV = .235;
	
	private boolean PX_goodPacket = false;
	
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
		pixy = new Pixy();
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
	
		try {
			PixyPacket one = pixy.readPacket(1);
			PX_x1 = one.X;
			PX_y1 = one.Y;
			PX_surfaceArea1 = one.SurfaceArea;
			PX_height = one.Height;
			
			//should not need to read in a second packet ever
			//if it doesn't work on the first, it shouldn't ever
//			PixyPacket two = pixy.readPacket(1);
//			PX_y2 = two.Y - 100;
//			PX_x2 = two.X - 160;
			PX_goodPacket = true;
		} catch (Exception e) {
			PX_goodPacket = false;
		}
	}
	
	//also should not need this. just for debugging
	private void PX_clearData() {
		PX_x1 = -999;
		PX_x2 = -999;
		PX_y1 = -999;
		PX_y2 = -999;
		PT_encoder.calc();
		
		
		}
	
	public double getDBDistance() {
		return DB_distance;
	}
	//important, but need to ask Glen what this does
	public double getPX_HorizontalDegreeOff() {
		return ((PX_x1 + PX_x2) / 2)*PX_CONVERSIONH;
	}
	
	public double SR_getPX_HorizontalDegreeOff() {
		return (Math.tan(Math.abs(PX_x1-640)/((12/PX_height)*800)))*360/3.14159;
	}
	
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}

	public void SmartDashboard() {
		SmartDashboard.putNumber("Left_Encoder_Distance", leftDrivebase.getDistance());
		SmartDashboard.putNumber("Right_Encoder_Distance", rightDrivebase.getDistance()*0.0062);
		SmartDashboard.putNumber("Left_Encoder_Speed", leftDrivebase.getRate());
		SmartDashboard.putNumber("Right_Encoder_Speed", rightDrivebase.getRate()*0.0062);
		SmartDashboard.putNumber("Pivot_Encoder_Distance", PT_encoder.getDistance());
		SmartDashboard.putNumber("Time", Timer.getFPGATimestamp());
		
		SmartDashboard.putNumber("PIXYX_1", PX_x1);
		SmartDashboard.putNumber("PIXYY_1", PX_y1);
		SmartDashboard.putNumber("PIXYHEIGHT_1", PX_height);
		SmartDashboard.putNumber("DB_LEFTPOSITION", DB_leftDistance);
		SmartDashboard.putNumber("DB_RIGHTPOSITION", DB_rightDistance);
		SmartDashboard.putNumber("DB_GYRO", DB_angle);
		SmartDashboard.putNumber("DB_GYRORATE", DB_angleRate);
		SmartDashboard.putNumber("GYROX", DB_gyro.getAngle());

	}
		
	public TorqueEncoder getLeftEncoder() {
		return leftDrivebase;
	}
	
	public TorqueEncoder getRightEncoder() {
		return rightDrivebase;
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
	
}
