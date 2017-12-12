package org.texastorque.torquelib;

import org.texastorque.torquelib.base.TorqueClass;

public interface TorqueSubsystem extends TorqueClass {

	public void autoInit();
	
	public void teleopInit();
	
	public void disabledInit();
	
	public void disabledContinuous();
	
	public void autoContinuous();
	
	public void teleopContinuous();
	
}
