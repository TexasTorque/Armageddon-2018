package org.texastorque.auto;

import java.util.LinkedList;

public abstract class AutoSequence {

	protected LinkedList<AutoCommand> commandList = new LinkedList<>();

	public abstract void init();

	public LinkedList<AutoCommand> getCommands() {
		return commandList;
	}
}
