package org.texastorque.io;

import org.texastorque.torquelib.util.TorqueToggle;

public class Input {

	private static Input instance;

	// Drivebase
	protected double DB_leftSpeed;
	protected double DB_rightSpeed;

	protected double IN_speed;
	protected TorqueToggle IN_down;
	protected TorqueToggle IN_out;
	protected TorqueToggle MAXIMUM_OVERDRIVE;

	protected double AM_speed;
	protected double AM_setpoint;
	protected int AM_index;
	//protected volatile double[] AM_setpoints = 
	//	{0.0, 40.0, 80.0, 480.0, 800.0, 100.0, 790.0, 450.0, 200.0, 200.0, 105.0}; //bravo has back pickup
	protected volatile double[] AM_setpoints = { 0, 135, 250, 1400, 2050, 100, 2050, 1400, 680, 170, 245 };
	//this is charlie
	protected static final int AM_CONVERSION = 17142;

	protected boolean armFWD;
	protected boolean armBACK;

	protected boolean climbing;
	protected volatile double DB_driveSetpoint;
	protected volatile double DB_turnSetpoint;
	protected volatile double DB_precision;

	// Pivot
	protected double PT_speed;
	protected double PT_setpoint;
	protected int PT_index;
	protected volatile double[] PT_setpoints = {0, 96.5, 170, 174, 178, 190.5, 215, 215, 215, 278.5, 9};

	// Bravo Setpoints -- Do not delete. Has back pickup.
//	 protected volatile double[] PT_setpoints = {4.0, 45.0, 75.0, 75.0, 75.0,
//	 79.0, 86.0, 86.0, 150.0, 115, 4.0};

	protected boolean pivotCCW;
	protected boolean pivotCW;

	// Claw
	protected TorqueToggle CL_closed;

	protected TorqueToggle encodersDead; // arm and/or pivot encoder is unplugged

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
		encodersDead = new TorqueToggle();
		initIndexes();
	}

	public void initIndexes() {
		PT_index = 0;
		AM_index = 0;
	}

	// Drivebase
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

	// Arm
	public double getArmSpeed() {
		return AM_speed;
	}

	public boolean getClimbing() {
		return climbing;
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

	// Intake
	public void setIntakeDown(boolean down) {
		IN_down.set(down);
	}

	public void setIntakeOut(boolean out) {
		IN_out.set(out);
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

	// Pivot
	public double getPTSetpoint() {
		return PT_setpoint;
	}
	
	public int getPTSetpointIndex() {
		return PT_index;
	}

	public void setPTSetpoint(int setpointIndex) {
		PT_index = setpointIndex;
		PT_setpoint = PT_setpoints[PT_index];
	}

	// Claw
	public boolean getClawClosed() {
		return CL_closed.get();
	}

	public void toggleClaw() {
		System.out.println("togg");
		CL_closed.calc(true);
	}

	public void setClaw(boolean closed) {
		CL_closed.set(closed);
	}

	public boolean getEncodersDead() {
		return encodersDead.get();
	}

	public void startIntaking() {
		IN_speed = -.35;
	}
	
	public void startOutaking() {
		IN_speed = .35;
	}
	
	public void stopSpinning() {
		IN_speed = 0;
	}
	public static Input getInstance() {
		return instance == null ? instance = new Input() : instance;
	}
}