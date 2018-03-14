package org.texastorque.auto.playback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.texastorque.io.Input;
import org.texastorque.io.RobotOutput;
import org.texastorque.models.DriverInputState;
import org.texastorque.models.OperatorInputState;
import org.texastorque.models.RobotInputState;
import org.texastorque.subsystems.Arm;
import org.texastorque.subsystems.Drivebase;
import org.texastorque.subsystems.Pivot;
import org.texastorque.util.FileUtils;

import com.google.gson.reflect.TypeToken;

import edu.wpi.first.wpilibj.Timer;

public class PlaybackAutoMode extends Input {
	
	/** Java magic tricks. */
	private static final Type LIST_TYPE_JSON = new TypeToken<List<RobotInputState>>() {}.getType();
	
	private final List<RobotInputState> inputs;
	private final Interpolator interpolator;
	
	private static PlaybackAutoMode instance;
	private double referenceTime = -1;  // Should only be negative before first update.
	
	public PlaybackAutoMode(String file) {
		this.inputs = FileUtils.readFromJSON(file, LIST_TYPE_JSON);
		
		this.interpolator = createInterpolator(this.inputs);
	}
	
	public void update() {
		if (this.interpolator == null) {
			System.out.println("Your auto is bad and you should feel bad.");
			return;
		}
		double autoTime = getAutoTime();
		
		// Find the input for the current time. Bound to valid indices.
		int index = interpolator.interpolate(autoTime);
		index = Math.min(Math.max(0, index), inputs.size() - 1);
		RobotInputState currentInput = inputs.get(index);
		
		// Create references to the driver/operator.
		DriverInputState driver = currentInput.driver;
		OperatorInputState operator = currentInput.operator;
		
		// Calculate drive speeds.
		DB_leftSpeed = -driver.leftStick.y + .75 * driver.rightStick.x;
		DB_rightSpeed = -driver.leftStick.y - .75 * driver.rightStick.x;

		IN_out.calc(driver.buttonA);
		IN_down.calc(operator.buttonX);
		if(driver.bumperLeft) {
			IN_speed = 1;
		} else if(driver.bumperRight) {
			IN_speed = -1;
		} else IN_speed = 0;
		
		if (operator.buttonY) {
			setClaw(false);
			PT_index = 10;
			AM_index = 10;
			MAXIMUM_OVERDRIVE.set(false);
			PT_setpoint = PT_setpoints[PT_index];
			AM_setpoint = AM_setpoints[AM_index];
		} 
		else if (operator.buttonYReleased) {
			setClaw(true);
			Pivot.getInstance().teleopSetDelay(.5);
			Arm.getInstance().teleopSetDelay(.5);
			PT_index = 0;
			AM_index = 0;
			MAXIMUM_OVERDRIVE.set(false);
			PT_setpoint = PT_setpoints[PT_index];
			AM_setpoint = AM_setpoints[AM_index];
		}
		
		PT_setpoint = PT_setpoints[operator.currentState];
		AM_setpoint = AM_setpoints[operator.currentState];

	}
	
	private double getAutoTime() {
		double fpgaTime = Timer.getFPGATimestamp();
		
		// Create the a reference to the time that the first update happens.
		// Aligns the start time for the recorded times to the start of playback.
		if (this.referenceTime < 0) {
			this.referenceTime = fpgaTime;
		}
		
		return fpgaTime - this.referenceTime;
	}
	
	private static Interpolator createInterpolator(List<RobotInputState> inputs) {
		if (inputs == null || inputs.size() == 0) {
			return null;
		}
		
		double timeOffset = inputs.get(0).time;
		double[] times = new double[inputs.size()];
		int[] indices = new int[inputs.size()];
		for (int i = 0; i < times.length; i++) {
			// Offset is used to normalize the interpolation time scale.
			// Now all input events happen in reference to t=0.
			times[i] = inputs.get(i).time - timeOffset;
			indices[i] = i;
		}
		
		return new Interpolator(times, indices);
	}
	
	/**
	 * Interpolates times and indices of events.
	 */
	private static class Interpolator {
		
		double slope;
		double intercept;
		
		Interpolator(double[] times, int[] indices) {
			double[] regressionArguments = generateRegression(times, indices);
			
			this.slope = regressionArguments[0];
			this.intercept = regressionArguments[1];
		}
		
		/**
		 * Calculates the index of the event that happened at the specified time.
		 * @param time The time that has passed since the first update in auto.
		 * @return The index of the event for the specified time.
		 */
		int interpolate(double time) {
			return (int) (this.slope * time + this.intercept);
		}
		
		/** Performs a linear regression for time and events.
		 * 
		 * @param times The ordered times of events.
		 * @param indices The associated index of the event.
		 * @return A 2-element double array of {slope, intercept}.
		 */
		private static double[] generateRegression(double[] times, int[] indices) {
	        double sumTime = 0;
	        double sumIndex = 0;
	        for (int i = 0; i < times.length; i++) {
	            sumTime  += times[i];
	            sumIndex  += indices[i];
	        }
	        double averageTime = sumTime / times.length;
	        double averageIndex = sumIndex / indices.length;

	        double xxbar = 0;
	        double xybar = 0;
	        for (int i = 0; i < times.length; i++) {
	            xxbar += Math.pow(times[i] - averageTime, 2);
	            xybar += (times[i] - averageTime) * (indices[i] - averageIndex);
	        }
	        
	        double slope = xybar / xxbar;
	        double intercept = averageIndex - slope * averageTime;
		
	        return new double[]{slope, intercept};
		}
	}
	
	public static synchronized PlaybackAutoMode getInstance() {
		try {
		return instance == null ? instance = new PlaybackAutoMode("/home/lvuser/recording.json") : instance;
		}catch(Exception e) {
			return null;
		}
	}
}
