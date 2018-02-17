//package vision;
//
//import org.texastorque.auto.AutonomousSequence;
//
//import org.texastorque.auto.gear.PlaceGearSequence;
//import org.texastorque.auto.intake.RunFloorIntake;
//import org.texastorque.auto.intake.RunFloorIntake.IntakeState;
//import org.texastorque.auto.shooter.RunGate;
//import org.texastorque.auto.shooter.RunShooter;
//import org.texastorque.auto.shooter.RunShooter.Setpoints;
//import org.texastorque.auto.twinsters.RunTwinster;
//import org.texastorque.auto.util.Side;
//
//import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.DriverStation.Alliance;
//
//public class ThirtyThreeTen extends AutonomousSequence {
//
//	private Alliance alliance;
//	
//	public ThirtyThreeTen() {
//		alliance = DriverStation.getInstance().getAlliance();
//
//		init();
//	}
//
//	@Override
//	public void init() {
//		switch(alliance) {
//		case Blue:
//			commandList.addAll(new AirShipSide(true, true, Side.LEFT).getCommands());
//			commandList.add(new RunShooter(Setpoints.GEAR));	
//			commandList.add(new RunDrive(50, .125, 1.5));
//			commandList.add(new RunVisionTurn(10));		
//			break;
//		case Red:
//			commandList.addAll(new AirShipSide(true, true, Side.RIGHT).getCommands());					
//			commandList.add(new RunShooter(Setpoints.GEAR));
//			commandList.add(new RunDrive(50, .125, 1.5));
//			commandList.add(new RunVisionTurn(-8));	
//			break;
//		}
//
//		commandList.add(new RunTwinster(1));
//		commandList.add(new RunGate(1));
//		commandList.add(new RunFloorIntake(IntakeState.INTAKE));
//
//		
//	}
//	
//}
