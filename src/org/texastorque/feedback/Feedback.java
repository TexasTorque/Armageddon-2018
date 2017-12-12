package org.texastorque.feedback;

import org.texastorque.io.HumanInput;
import org.texastorque.io.Input;

public class Feedback {

	public static Feedback instance;

	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}

}
