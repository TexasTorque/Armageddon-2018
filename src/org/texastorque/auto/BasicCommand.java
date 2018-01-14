package org.texastorque.auto;

import org.texastorque.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * use an arraylist of threads in the autocontinuous method, each thread has its own time stamp
 * to start at and it can only do one subsystem at a time. There will be a clock in the autocontinuous
 * method that allows all the threads to line up properly. 
 *
 * In order for the commands to read, maybe make HumanInput more method-centric and make it read
 * inputs through the controller, or write code to read the motors and solenoids and stuff. Make
 * all the numbers attributes so they are adjustable in the XML file
 * 
 * Important to note that it must be object oriented to be able to read into the XML file
 *
 * Starting recording process has not been thought about yet; put it on a button? if on button
 * need to think more about the code associated with it though.
 *
 * If we do this successfully, need for encoders in drivebase is erased
 *
 * 
 *
 */

/*
public class ExampleCommand extends Command {
	public ExampleCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.exampleSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
*/