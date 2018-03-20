package org.texastorque.torquelib.controlLoop;

import java.util.Arrays;

import org.texastorque.util.ArrayUtils;
import org.texastorque.util.Integrator;
import org.texastorque.util.MathUtils;
import org.texastorque.util.TorqueTimer;
import org.texastorque.util.interfaces.Stopwatch;

public class ScheduledPID {

<<<<<<< HEAD
=======
	private static boolean strictModeEnabled = false;
	
>>>>>>> lubecki-lonestar
	private final double[] gainDivisions;
	private final double[] pGains;
	private final double[] iGains;
	private final double[] dGains;
	
<<<<<<< HEAD
	private int currentGainIndex;
	
	private final double setPoint;
	private final double maxOutput;
	private double lastError;
	
	private final Integrator integrator;
	private Stopwatch timer;

	private ScheduledPID(double setPoint, double maxOutput, int count) {
		if (count <= 0) {
			throw new RuntimeException("PID Schedule must have at least 1 gain, count was: " + count);
		}
=======
	private final double minOutput;
	private final double maxOutput;

	private double setpoint;
	private int currentGainIndex;
	private double lastError;

	private final Integrator integrator;
	private Stopwatch timer;
	private SafetyCheck safetyCheck;

	private ScheduledPID(double setpoint, double minOutput, double maxOutput, int count) {
		checkConstructorArguments(minOutput, maxOutput, count);
>>>>>>> lubecki-lonestar
		
		this.gainDivisions = new double[count - 1];
		this.pGains = new double[count];
		this.iGains = new double[count];
		this.dGains = new double[count];
		
<<<<<<< HEAD
		this.setPoint = setPoint;
		this.maxOutput = maxOutput;
		
		this.integrator = new Integrator();
		
		this.timer = new TorqueTimer();
	}
	
	public double calculate(double processVar) {
		handleFirstCalculation();

		double error = this.setPoint - processVar;
		updateGainIndex(error);

		double pGain = this.pGains[this.currentGainIndex];
		double iTerm = integralTerm(error);
		double dTerm = derivativeTerm(error);
		
		System.out.println(pGain * error + " " + iTerm + " " + dTerm);
		
		double output = pGain * (error + iTerm + dTerm);  // Standard PID form.
		
		this.lastError = error;

		timer.startLap();  // Measure deltaTime starting now.
		
		return MathUtils.clamp(output, -maxOutput, maxOutput);
=======
		this.setpoint = setpoint;
		this.minOutput = minOutput;
		this.maxOutput = maxOutput;
		
		this.integrator = new Integrator();
		this.timer = new TorqueTimer();
	}

	private ScheduledPID(double setpoint, double maxOutput, int count) {
		this(setpoint, -maxOutput, maxOutput, count);
	}

	private ScheduledPID(double setpoint, double maxOutput) {
		this(setpoint, -maxOutput, maxOutput, 1);
	}
	
	
	// == Private API ==
	
	private double proportionalTerm(double error) {
		double gain = this.pGains[this.currentGainIndex];
		
		return gain * error;
>>>>>>> lubecki-lonestar
	}
	
	private double integralTerm(double error) {
		double gain = this.iGains[this.currentGainIndex];
		double dt = timer.lapTime();
		double integral = integrator.calculate(error, dt);
		
		return gain * integral;
	}
	
	private double derivativeTerm(double error) {
		double dt = timer.lapTime();
		
		if (dt == 0) return 0;
		
		double gain = this.dGains[this.currentGainIndex];
		double de = error - this.lastError;
		
		return gain * (de/dt);
<<<<<<< HEAD
		
	}
	
	private void handleFirstCalculation() {
		if (timer.isRunning()) return;
		
		timer.start();
=======
	}
	
	private boolean isSafeToOutput() {
		return (this.safetyCheck == null || safetyCheck.isSafe());
	}
	
	private void startTimerIfNeeded() {
		if (!timer.isRunning()) timer.start();
	}
	
	private double startUpdate(double processVar) {
		double error = this.setpoint - processVar;
		this.currentGainIndex = calculateGainIndex(error);
		
		return error;
	}
	
	private void finishUpdate(double error) {
		this.lastError = error;
		timer.startLap();  // Measure dt from the end of the last update.
>>>>>>> lubecki-lonestar
	}
	
	/** Calculates the index for the current gain values.
	 * 
	 * @param error The current error in the process variable.
	 * @return The integer index for the gain values.
	 */
<<<<<<< HEAD
	private int updateGainIndex(double error) {
		if (gainDivisions.length <= 1 || error <= this.gainDivisions[0]) {
			this.currentGainIndex = 0;
			return this.currentGainIndex;
		}
		
		for (int i = 0; i < gainDivisions.length - 2; i++) {
=======
	private int calculateGainIndex(double error) {
		if (gainDivisions.length == 0 || error <= this.gainDivisions[0]) {
			return 0;
		}
		
		for (int i = 0; i < gainDivisions.length - 1; i++) {
>>>>>>> lubecki-lonestar
			double leftBound = this.gainDivisions[i];
			double rightBound = this.gainDivisions[i + 1];
			
			if (leftBound <= error && error < rightBound) {
<<<<<<< HEAD
				this.currentGainIndex = i + 1;
				return this.currentGainIndex;
			}
		}

		this.currentGainIndex = gainDivisions.length;
		return this.currentGainIndex;
=======
				return i + 1;
			}
		}

		return gainDivisions.length;
	}
	
	private static void checkConstructorArguments(double minOutput, double maxOutput, int count) {
		if (count <= 0) {
			String errorMessage = "PID Schedule must have at least 1 gain, count was: " + count;
			if (ScheduledPID.strictModeEnabled) {
				throw new IllegalArgumentException(errorMessage);
			} else {
				System.err.println(errorMessage);
			}
		}
		
		if (minOutput > maxOutput) {
			String errorMessage = "Min output (" + minOutput + ") >= max output (" + maxOutput + ").";
			if (ScheduledPID.strictModeEnabled) {
				throw new IllegalArgumentException(errorMessage);
			} else {
				System.err.println(errorMessage);
			}
		}
	}
	
	
	// == Public API ==
	
	public double calculate(double processVar) {
		if (!isSafeToOutput()) {
			timer.reset();
			integrator.reset();
			return (this.safetyCheck == null ? 0 : safetyCheck.getSafetyModeOutput());
		}

		startTimerIfNeeded();
		
		double error = startUpdate(processVar);
		double pTerm = proportionalTerm(error);
		double iTerm = integralTerm(error);
		double dTerm = derivativeTerm(error);
		double output = pTerm + iTerm + dTerm;  // Ideal PID form.
		
		finishUpdate(error);
		
		return MathUtils.clamp(output, minOutput, maxOutput);
	}
	
	public void reset() {
		integrator.reset();
		timer.reset();
	}
	
	public void changeSetpoint(double newSetpoint) {
		this.setpoint = newSetpoint;
		
		reset();
>>>>>>> lubecki-lonestar
	}
	
	@Override
	public String toString() {
		return "ScheduledPID [\n\tgainDivisions=" + Arrays.toString(gainDivisions) + ", \n\tpGains=" + Arrays.toString(pGains)
				+ ", \n\tiGains=" + Arrays.toString(iGains) + ", dGains=" + Arrays.toString(dGains) + ", \n\tcurrentGainIndex="
<<<<<<< HEAD
				+ currentGainIndex + ", \n\tsetPoint=" + setPoint + ", \n\tmaxOutput=" + maxOutput + ", \n\tlastError=" + lastError
				+ ", \n\tintegrator=" + integrator + ", \n\ttimer=" + timer + "\n]";
	}


=======
				+ currentGainIndex + ", \n\tsetpoint=" + setpoint + ", \n\tmaxOutput=" + maxOutput + ", \n\tlastError=" + lastError
				+ ", \n\tintegrator=" + integrator + ", \n\ttimer=" + timer + "\n]";
	}

	/** Enables strict handling of invalid constructor arguments.
	 * 
	 * @param enabled True if invalid constructor arguments should throw errors.
	 */
	public static void enableStrictMode(boolean enabled) {
		ScheduledPID.strictModeEnabled = enabled;
	}
	
	// == PID Construction ==
>>>>>>> lubecki-lonestar

	public static class Builder {
		
		private final ScheduledPID pid;
		
<<<<<<< HEAD
		public Builder(double setPoint, double maxOutput, int count) {
			this.pid = new ScheduledPID(setPoint, maxOutput, count);
=======
		public Builder(double setpoint, double maxOutput) {
			this.pid = new ScheduledPID(setpoint, maxOutput);
		}
		
		public Builder(double setpoint, double maxOutput, int count) {
			this.pid = new ScheduledPID(setpoint, maxOutput, count);
		}
		
		public Builder(double setpoint, double minOutput, double maxOutput, int count) {
			this.pid = new ScheduledPID(setpoint, minOutput, maxOutput, count);
>>>>>>> lubecki-lonestar
		}
		
		public Builder setRegions(double... regions) {
			ArrayUtils.bufferAndFill(regions, pid.gainDivisions);
			
			// Divisions should always be specified in ascending order.
			if (!ArrayUtils.isSorted(regions, true)) {
				Arrays.sort(pid.gainDivisions);
				System.out.println("Gain schedule was not ordered correctly.");
			}
			
			return this;
		}
		
		public Builder setPGains(double... pGains) {
			ArrayUtils.bufferAndFill(pGains, pid.pGains);
			return this;
		}
		
		public Builder setIGains(double... iGains) {
			ArrayUtils.bufferAndFill(iGains, pid.iGains);
			return this;
		}
		
		public Builder setDGains(double... dGains) {
			ArrayUtils.bufferAndFill(dGains, pid.dGains);
			return this;
		}
		
		public Builder overrideFPGATimer(Stopwatch timer) {
			this.pid.timer = timer;
			return this;
		}
		
<<<<<<< HEAD
=======
		public Builder setSafetyMechanism(SafetyCheck safety) {
			this.pid.safetyCheck = safety;
			return this;
		}
		
>>>>>>> lubecki-lonestar
		public ScheduledPID build() {
			return this.pid;
		}
	}
<<<<<<< HEAD
=======
	
	public interface SafetyCheck {
		public boolean isSafe();
		public double getSafetyModeOutput();
	}
>>>>>>> lubecki-lonestar
}
