package org.texastorque.torquelib.controlLoop;

import java.util.Arrays;

import org.texastorque.util.ArrayUtils;

public class ScheduledPID {
	
	@Override
	public String toString() {
		return "ScheduledPID [gainDivisions=" + Arrays.toString(gainDivisions) + ", pGains=" + Arrays.toString(pGains)
				+ ", iGains=" + Arrays.toString(iGains) + ", dGains=" + Arrays.toString(dGains) + "]";
	}

	private final double[] gainDivisions;
	private final double[] pGains;
	private final double[] iGains;
	private final double[] dGains;

	private ScheduledPID(int count) {
		this.gainDivisions = new double[count - 1];
		this.pGains = new double[count];
		this.iGains = new double[count];
		this.dGains = new double[count];
	}
	
	public static class Builder {
		
		private final ScheduledPID pid;
		
		public Builder(int count) {
			this.pid = new ScheduledPID(count);
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
		
		public ScheduledPID build() {
			return this.pid;
		}
	}
}
