package org.texastorque.io;

import org.texastorque.torquelib.util.TorqueToggle;

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
		{0.0, 185.0, 1790.0, 1890.0, 1990.0, 790.0, 790.0, 790.0, 680.0, 200.0, 275.0};
	protected static final int AM_CONVERSION = 17142;
	
	protected boolean armFWD;
	protected boolean armBACK;
	
	protected boolean climbing;
	protected boolean pickingUp; //extending to pick up a cube
	protected boolean pullingBack; //retracting after picking up a cube
	protected volatile double DB_driveSetpoint;
	protected volatile double DB_turnSetpoint;
	protected volatile double DB_precision;
	
	//Pivot
	protected double PT_speed;
	protected double PT_setpoint;
	protected int PT_index;
	protected volatile double[] PT_setpoints = 
		{0.0, 45.0, 77.0, 77.0, 77.0, 81.0, 85.0, 85.0, 85.0, 150, 5.0}; //TBD
		//	{0.0, 112.5, 195.0, 215.0, 215d, 260, 275d, 275d, 300, 350}; //TBD
	//yellow and red front is 200p, 1390a 
	
	protected boolean pivotCCW;
	protected boolean pivotCW;
	//Claw
	protected TorqueToggle CL_closed;
	
	protected TorqueToggle encodersDead; //arm and/or pivot encoder is unplugged
	
	public Input() {
		DB_leftSpeed = 0d;
		DB_rightSpeed = 0d;
		AM_speed = 0d;
		CL_closed = new TorqueToggle();
		IN_down = new TorqueToggle();
		IN_out = new TorqueToggle();
		MAXIMUM_OVERDRIVE = new TorqueToggle();
		
		pickingUp = false;
		pullingBack = false;
		
		CL_closed.set(false);
		IN_down.set(false);
		IN_out.set(false);
		encodersDead = new TorqueToggle();
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
	
	public boolean getPickingUp() {
		return pickingUp;
	}
	
	public boolean getPivotCCW() {
		return pivotCCW;
	}
	
	public boolean getPivotCW() {
		return pivotCW;
	}
	
	public boolean getArmForward() {
		return armFWD;
	}
	
	public boolean getArmBack() {
		return armBACK;
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
	
	public void setClaw(boolean closed) {
		CL_closed.set(closed);
	}
	
	public boolean getPullingBack() {
		return pullingBack;
	}
	
	public boolean getEncodersDead() {
		return encodersDead.get();
	}
	
	public static Input getInstance() {
		return instance == null ? instance = new Input() : instance;
	}
}