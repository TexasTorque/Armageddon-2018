package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.texastorque.torquelib.controlLoop.ScheduledPID;

public class TestPID {

	@Test
	public void testCreateScheduledPID() {
		ScheduledPID testPID = new ScheduledPID.Builder(1, 1, 5)
				.setPGains(0.5, 0.1,  0.05)
				.setIGains(0.1, 0.01, 0.001)
				.setRegions(1, 1, 1)
				.build();
		
		System.out.println(testPID.toString());
		
		assert(testPID != null);
	}

	@Test
	public void testSingleCalculation() {
		ScheduledPID testPID = new ScheduledPID.Builder(1, 1, 1)
				.setPGains(0.5)
				.setIGains(300)
				.setRegions(0.01)
				.overrideFPGATimer(new TestingTimer())
				.build();

		double currentOutput = 0;
		double newOutput = testPID.calculate(currentOutput);
		
		System.out.println(newOutput);
		System.out.println(testPID.toString());
		
		assertNotNull(testPID);
	}
}
