//package vision;
//
////import org.texastorque.auto.AutoManager;
////import org.texastorque.auto.AutonomousCommand;
//import org.texastorque.feedback.Feedback;
//import org.texastorque.io.Input;
////import org.texastorque.subsystem.DriveBase.DriveType;
//
//public class RunVisionTurn extends AutonomousCommand {
//
//	protected Input i = Input.getInstance();
//	
//	private double theta;
//	private double precision;
//	
//	private final double TCONSTANT = .25;
//	private final double DPRECISION = .0625;
//	public RunVisionTurn(double theta, double precision) {
//		this.theta = theta;
//		this.precision = precision;
//	}
//	public RunVisionTurn(double theta) {
//		this.theta = theta;
//		precision = DPRECISION;
//	}
//	
////	@Override
//	public void run() {
////		Feedback.getInstance().resetDB_gyro();
////		Feedback.getInstance().resetDB_encoders();
////		driveBase.setType(DriveType.AUTOVISIONTURN);
//		i.setDB_turnSetpoint(Feedback.getInstance().getPX_HorizontalDegreeOff(), precision);
////		AutoManager.pause(1.5);
////		driveBase.setType(DriveType.WAIT);
//	}
//	
////	@Override
//	public void reset() {
//		theta = 0;
//	}
//	
//}
