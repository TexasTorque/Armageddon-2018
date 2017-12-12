package org.texastorque.io;

import org.texastorque.torquelib.component.TorqueMotor;
import org.texastorque.constants.Ports;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class RobotOutput {
	
	private TorqueMotor DB_leftFore;
	private TorqueMotor DB_leftRear;
	private TorqueMotor DB_rightFore;
	private TorqueMotor DB_rightRear;
	
	private DoubleSolenoid AR_armSole;

	public static RobotOutput instance;

	public RobotOutput(){
		//DB_leftFore = new TorqueMotor(new VictorSP(_PORT_NUMBER_), flipDriveTrain)
		// etc
		AR_armSole = new DoubleSolenoid(Ports.AR_ARM_0, Ports.AR_ARM_1);
	}
	
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}

	
	public void setDrivebaseSpeed(double leftSpeed, double rightSpeed){
		DB_leftFore.set(leftSpeed);
		DB_leftRear.set(leftSpeed);
		DB_rightFore.set(rightSpeed);
		DB_rightRear.set(rightSpeed);
	}
	
	public void setArmUp(boolean up) {
		AR_armSole.set(up ? Value.kForward : Value.kReverse);
	}
}
