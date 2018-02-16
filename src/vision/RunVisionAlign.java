package vision;

import org.texastorque.auto.AutonomousCommand;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RunVisionAlign extends AutonomousCommand {

	private double turnAngle;
	
	@Override
	public void run() {
 		System.out.println("RUNNING VISION AUTO...");
 		do {
 			SmartDashboard.putString("GOODPACKET", "FALSE");
 		} while (!feedback.getPX_goodPacket());
 		turnAngle = feedback.getPX_HorizontalDegreeOff();
	}

	public double getAngle() {
		return turnAngle;
	}
	
	@Override
	public void reset() {
	}
	
}
