package org.texastorque.io;

import org.texastorque.torquelib.util.GenericController;
import org.texastorque.torquelib.util.TorqueToggle;

public class HumanInput extends Input{

	public static HumanInput instance;

	private GenericController driver;
	private GenericController operator;
	
	protected TorqueToggle recording;
	
	
	public HumanInput(){
		init();
		
	}
	
	public void update(){
		updateDrive();
		updateRecording();
	}
	
	public void updateDrive(){
		DB_leftSpeed = -driver.getLeftYAxis() + driver.getRightXAxis();
		DB_rightSpeed = -driver.getLeftYAxis() - driver.getRightXAxis();
	
	}

	public void updateRecording() {
		recording.calc(driver.getDPADDown());
		if(recording.get()) {
			System.out.println("Recording..");
		}
		
		if(driver.getDPADUp())
			recorded = true;
			
		
	}
	
	public void init(){
		driver = new GenericController(0 ,.1);
		operator = new GenericController(1, .1);
		recording = new TorqueToggle();
	}
	
	
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
}
