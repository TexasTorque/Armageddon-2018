package org.texastorque.util.interfaces;

public interface Stopwatch {

	
	public double elapsed();
	
	public double lapTime();
	
	public void startLap();
	
	public double timeSince(double lastTime);
	
	public double start();
	
	public boolean isRunning();
}
