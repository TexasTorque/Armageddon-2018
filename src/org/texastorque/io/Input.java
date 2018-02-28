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
		{0, 0, 1208.0, 1855, 2040, 2110, 2110, 1540, 915, 200};
	protected static final int AM_CONVERSION = 17142;
	
	protected boolean climbing;
	protected boolean pickingUp;
	
	protected volatile double DB_driveSetpoint;
	protected volatile double DB_turnSetpoint;
	protected volatile double DB_precision;
	
	//Pivot
	protected double PT_speed;
	protected double PT_setpoint;
	protected int PT_index;
	protected volatile double[] PT_setpoints = 
		{0.0, 50.0, 82.0, 82.0, 90.0, 95.0, 110.0, 125, 130, 150}; //TBD
	
	protected boolean movingLeft;
	protected boolean movingRight;
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
		pickingUp = false;
		CL_closed.set(false);
		IN_down.set(false);
		IN_out.set(false);
		initIndexes();
	}
	
	public void initIndexes() {
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
		DB_turnSetpoint = setpoint;
		DB_precision = precision;
	}
	
	//Arm
	public double getArmSpeed() {
		return AM_speed;
	}
	
	public boolean getClimbing() {
		return climbing;
	}
	
	public  boolean getPickingUp() {
		return pickingUp;
	}
	
	public boolean getMovingLeft() {
		return movingLeft;
	}
	
	public boolean getMovingRight() {
		return movingRight;
	}
	
	public void setArmSpeed(double speed) {
		AM_speed = speed;
	}
	
	public double getArmSetpoint() {
		return AM_setpoint;
	}
	
	public void setArmSetpoint(int setpointIndex) {
		AM_index = setpointIndex;
		AM_setpoint = AM_setpoints[AM_index];
	}
		
	//Intake
	public void setIntakeDown(boolean down) {
		IN_down.set(down);
	}
	
	public double getINSpeed() {
		return IN_speed;
	}
	
	public boolean getINDown() {
		return IN_down.get();
	}
	
	public boolean getINOut() {
		return IN_out.get();
	}
	
	//Pivot
	public double getPTSetpoint() {
		return PT_setpoint;
	}
	
	public void setPTSetpoint(int setpointIndex) {
		PT_index = setpointIndex;
		PT_setpoint = PT_setpoints[PT_index];
	}
	
	//Claw
	public boolean getClawClosed() {
		return CL_closed.get();
	}
	
	public void toggleClaw() {
		CL_closed.calc(true);
	}
	
	public static Input getInstance() {
		return instance == null ? instance = new Input() : instance;
	}
}