package org.texastorque.models;

public class Vector2D {
	public double x;
	public double y;
	
	public Vector2D() { }
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double angle() {
		return Math.atan2(y, x);
	}
	
	public double magnitude() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
}
