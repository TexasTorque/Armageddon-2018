package org.texastorque.io;

import org.texastorque.feedback.Feedback;
import org.texastorque.torquelib.util.TorqueToggle;

public class Input {

	private static Input instance;
	
	protected double DB_leftSpeed;
	protected double DB_rightSpeed;
	
	protected volatile double DB_setpoint;
	protected volatile double DB_turnSetpoint;
	protected volatile double DB_precision;

	protected double AM_speed;
	
	protected int PT_index;
	protected double[] PT_setpoints = new double[] {1, 2, 3, 4, 5, 6}; //TBD
	
	protected TorqueToggle CL_closed;
	
	public Input(){
		DB_leftSpeed = 0d;
		DB_rightSpeed = 0d;
		AM_speed = 0d;
		CL_closed = new TorqueToggle();
	}
	
	//Drivebase
	public double getDBLeftSpeed() {
		return DB_leftSpeed;
	}
	
	public double getDBRightSpeed() {
		return DB_rightSpeed;
	}
	
	public double getDBSetpoint() {
		return DB_setpoint;
	}

	public double getDBTurnSetpoint() {
		return DB_turnSetpoint;
	}
	
	public double getDBPrecision() {
		return DB_precision;
	}
	
	//Arm
	public double getArmSpeed() {
		return AM_speed;
	}
	
	//Claw
	public boolean getClawClosed() {
		return CL_closed.get();
	}
	
	//Pivot
	public double getPTSetpoint() {
		return PT_setpoints[PT_index];
	}
	
	public static Input getInstance() {
		return instance == null ? instance = new Input() : instance;
	}
	
	public void setDB_driveSetpoint(double setpoint, double precision) {
		DB_setpoint = setpoint;
		DB_precision = precision;
	}
	
	public void setDB_turnSetpoint(double setpoint, double precision) {
		DB_turnSetpoint = setpoint + Feedback.getInstance().getDB_angle();
		DB_precision = precision;
	}
	
	
}
