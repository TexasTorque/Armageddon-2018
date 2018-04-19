package org.texastorque.auto;

import java.util.LinkedList;

public abstract class AutoSequence {

	protected LinkedList<AutoCommand> commandList = new LinkedList<>();
	protected String fieldConfig = "XXX";

	public abstract void init();

	public abstract void initCommandLists();
	
	public LinkedList<AutoCommand> getCommands() {
		return commandList;
	}
	
	public void setFieldConfig(String fieldConfig) {
		this.fieldConfig = fieldConfig; 
		System.out.println(fieldConfig);
	}
	
	public String getFieldConfig() {
		return fieldConfig;
	}
}
