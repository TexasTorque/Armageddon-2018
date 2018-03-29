package org.texastorque.subsystems;

import org.texastorque.auto.playback.PlaybackAutoMode;
import org.texastorque.subsystems.Subsystem.AutoType;
import org.texastorque.torquelib.util.TorqueMathUtil;

public class Claw extends Subsystem{

	private boolean closed;
	private static Claw instance;

	@Override
	public void autoInit() {
//		auto = PlaybackAutoMode.getInstance();
	}

	@Override
	public void teleopInit() {
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledContinuous() {
	}

	@Override
	public void autoContinuous() {
		if(type.equals(AutoType.RECORDING))
			recordingAutoContin();
		else commandAutoContin();
		o.setClaw(closed);
	}
	
	private void recordingAutoContin() {
		closed = auto.getClawClosed();
		
	}
	
	private void commandAutoContin() {
		closed = i.getClawClosed();
	}

	@Override
	public void teleopContinuous() {
		closed = i.getClawClosed();
		o.setClaw(closed);
	}

	@Override
	public void smartDashboard() {
	}

	public static Claw getInstance() {
		return instance == null ? instance = new Claw() : instance;
	}

}
