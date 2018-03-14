package tests;

import org.texastorque.util.interfaces.Stopwatch;

public class TestingTimer implements Stopwatch {
	
	private double startTime;
	private double lastTime;
	private boolean started = false;
	
	public TestingTimer() {
		start();
	}

	@Override
	public double elapsed() {
		return ((double) System.currentTimeMillis() - this.startTime) / 1000;
	}

	@Override
	public double lapTime() {
		return ((double) System.currentTimeMillis() - this.lastTime) / 1000;
	}

	@Override
	public void startLap() {
		this.lastTime = (double) System.currentTimeMillis();
		
	}

	@Override
	public double timeSince(double lastTime) {
		return ((double) System.currentTimeMillis() - lastTime) / 1000;
	}

	@Override
	public double start() {
		this.startTime = (double) System.currentTimeMillis();
		this.lastTime = this.startTime;
		this.started = true;
		return this.startTime;
	}

	@Override
	public boolean isRunning() {
		return this.started;
	}
}
