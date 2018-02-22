package org.texastorque.io;

import org.texastorque.feedback.Feedback;
import org.texastorque.torquelib.util.TorqueToggle;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Input {

	private static Input instance;
	
	//Drivebase
	protected double DB_leftSpeed;
	protected double DB_rightSpeed;
		
	protected double IN_speed;
	protected TorqueToggle IN_down;
	protected TorqueToggle IN_out;
	protected TorqueToggle MAXIMUM_OVERDRIVE;
	
	protected double AM_speed;
	protected double AM_setpoint;
	protected int AM_index;
	protected volatile double[] AM_setpoints = 
		{0, 10, 540, 500, 500, 500, 500, 500, 500};
	protected static final int AM_CONVERSION = 10150;
	
	protected volatile double DB_driveSetpoint;
	protected volatile double DB_turnSetpoint;
	protected volatile double DB_precision;
	
	
	//Pivot
	protected double PT_speed;
	protected double PT_setpoint;
	protected int PT_index;
	protected volatile double[] PT_setpoints = 
		{0.0, 45.0, 90.0, 130.0, 150.0, 100.0, 110.0, 110, 110, 110, 110, 110}; //TBD
	
	//Claw
	protected TorqueToggle CL_closed;
	
	public Input() {
		DB_leftSpeed = 0d;
		DB_rightSpeed = 0d;
		AM_speed = 0d;
		CL_closed = new TorqueToggle();
		IN_down = new TorqueToggle();
		IN_out = new TorqueToggle();
		MAXIMUM_OVERDRIVE = new TorqueToggle();
		CL_closed.set(false);
		IN_down.set(false);
		IN_out.set(false);
		PT_index = 0;
		AM_index = 0;
	}
	
	//Drivebase
	public double getDBLeftSpeed() {
		return DB_leftSpeed;
	}
	
	public double getDBRightSpeed() {
		return DB_rightSpeed;
	}
	
	public double getDBDriveSetpoint() {
		return DB_driveSetpoint;
	}

	public double getDBTurnSetpoint() {
		return DB_turnSetpoint;
	}
	
	public double getDBPrecision() {
		return DB_precision;
	}
	
	public void setDBDriveSetpoint(double setpoint, double precision) {
		DB_driveSetpoint = setpoint;
		DB_precision = precision;
	}
	
	public void setDBTurnSetpoint(double setpoint, double precision) {
		DB_turnSetpoint = setpoint + Feedback.getInstance().getDBAngle();
		DB_precision = precision;
	}
	
	//Arm
	public double getArmSpeed() {
		return AM_speed;
	}
	
	public void setArmSpeed(double speed) {
		AM_speed = speed;
	}
	
	public double getArmSetpoint() {
		return AM_setpoints[AM_index];
	}
	//Claw
	public boolean getClawClosed() {
		return CL_closed.get();
	}
	
	public double getINSpeed() {
		return IN_speed;
	}
	
	public boolean getINDown() {
		return IN_down.get();
	}
	//Pivot
	public double getPTSetpoint() {
		return PT_setpoints[PT_index];
	}
	
	public boolean getINOut() {
		return IN_out.get();
	}


	public static Input getInstance() {
		return instance == null ? instance = new Input() : instance;
	
	}
}