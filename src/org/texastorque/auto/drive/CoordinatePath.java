package org.texastorque.auto.drive;

import java.util.ArrayList;

import org.texastorque.auto.AutoSequence;
public class CoordinatePath extends AutoSequence {

	private final ArrayList<int[]> coordinates;


	public CoordinatePath(ArrayList<int[]> coordinates) {
		this.coordinates = coordinates;
		init();
	}

	@Override
	public void init() {
		for (int i = 1; i < coordinates.size(); i++) {
			double deltaX = coordinates.get(1)[0] - coordinates.get(0)[0];
			double deltaY = coordinates.get(1)[1] - coordinates.get(0)[1];

			double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
			double angle = Math.toDegrees(Math.asin(deltaY / distance));

			commandList.add(new Drive(distance, 0.125));
			commandList.add(new Turn(angle, 0.125));
		}
	}
}
