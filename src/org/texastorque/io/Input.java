package org.texastorque.io;

import org.texastorque.feedback.Feedback;
import org.texastorque.torquelib.util.TorqueToggle;

public class Input {

	private static Input instance;
	
	//Drivebase
	protected double DB_leftSpeed;
	protected double DB_rightSpeed;
	
	protected double IN_speed;
	protected TorqueToggle IN_down;
	protected TorqueToggle IN_out;
	
	protected double AM_speed;
	protected volatile double DB_driveSetpoint;
	protected volatile double DB_turnSetpoint;
	protected volatile double DB_precision;
	
	
	//Pivot
	protected double PT_speed;
	
	protected volatile int PT_setpoint;
	protected volatile double[] PT_setpoints = 
		{0.0, 10.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0, 80.0, 90.0}; //TBD
	protected volatile double PT_precision;
	
	//Claw
	protected TorqueToggle CL_closed;
	
	protected int PT_index;
	
	public Input() {
		DB_leftSpeed = 0d;
		DB_rightSpeed = 0d;
		AM_speed = 0d;
		CL_closed = new TorqueToggle();
		IN_down = new TorqueToggle();
		IN_out = new TorqueToggle();
		PT_index = 0;
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
		return PT_setpoints[PT_setpoint];
	}
	
	public double getPTPrecision() {
		return PT_precision;
	}
	
	public void setPTSetpoint(int setpoint, double precision) {
		PT_setpoint = setpoint;
		PT_precision = precision;
	}
	
	public boolean getINOut() {
		return IN_out.get();
	}


	public static Input getInstance() {
		return instance == null ? instance = new Input() : instance;
	
	}
}
