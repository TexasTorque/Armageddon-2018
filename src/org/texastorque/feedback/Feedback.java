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
	
	private TorqueEncoder pivot;
	
	private Pixy pixy;
	
	private double PX_x1;
	private double PX_y1;
	private double PX_surfaceArea1;
	private double PX_x2;
	private double PX_y2;
	private double PX_surfaceArea2;
	
	private final double PX_CONVERSIONH = .234;
	private final double PX_CONVERSIONV = .235;
	
	private boolean PX_goodPacket = false;
	
	public Feedback() {
		leftDrivebase = new TorqueEncoder(Ports.DB_LEFTENCODER_A, Ports.DB_LEFTENCODER_B, false, EncodingType.k4X);
		rightDrivebase = new TorqueEncoder(Ports.DB_RIGHTENCODER_A, Ports.DB_RIGHTENCODER_B, false, EncodingType.k4X);
		pivot = new TorqueEncoder(1,2,false,EncodingType.k4X);
		leftDrivebase.reset();
		rightDrivebase.reset();
		pivot.reset();
		pixy = new Pixy();
	}
	
	public void update() {
		leftDrivebase.calc();
		rightDrivebase.calc();
		pivot.calc();
		
		try {
			PixyPacket one = pixy.readPacket(1);
			PX_x1 = one.X - 160;
			PX_y1 = one.Y - 100;
			PX_surfaceArea1 = one.Width * one.Height;
			
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
	}
	
	public void reset() {
		leftDrivebase.reset();
		rightDrivebase.reset();
		pivot.reset();
	}
	
	//important, but need to ask Glen what this does
	public double getPX_HorizontalDegreeOff() {
		return ((PX_x1 + PX_x2) / 2)*PX_CONVERSIONH;
	}
	
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}

	public void SmartDashboard() {
		SmartDashboard.putNumber("Left_Encoder_Distance", leftDrivebase.getDistance());
		SmartDashboard.putNumber("Right_Encoder_Distance", rightDrivebase.getDistance()*0.0062);
		SmartDashboard.putNumber("Left_Encoder_Speed", leftDrivebase.getRate());
		SmartDashboard.putNumber("Right_Encoder_Speed", rightDrivebase.getRate()*0.0062);
		SmartDashboard.putNumber("Pivot_Encoder_Distance", pivot.getDistance());
		SmartDashboard.putNumber("Time", Timer.getFPGATimestamp());
	}

}
