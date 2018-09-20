// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc1477.Robot;

import org.usfirst.frc1477.Robot.commands.BlockIntake;
import org.usfirst.frc1477.Robot.commands.Compress;
import org.usfirst.frc1477.Robot.commands.MoveArm;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public JoystickButton yButton;
    public JoystickButton xButton;
    public JoystickButton bButton;
    public JoystickButton aButton;
    public Joystick joystick1;
    public Joystick joystick2;
    public Joystick operatorConsole;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        operatorConsole = new Joystick(2);
        
        joystick2 = new Joystick(1);
        
        joystick1 = new Joystick(0);
        
        aButton = new JoystickButton(joystick1, 1);
        aButton.whenPressed(new MoveArm(3));
        bButton = new JoystickButton(joystick1, 2);
        bButton.whenPressed(new MoveArm(4));
        xButton = new JoystickButton(joystick1, 3);
        xButton.whenPressed(new BlockIntake(1));
        yButton = new JoystickButton(joystick1, 4);
        yButton.whenPressed(new BlockIntake(2));


        // SmartDashboard Buttons
        SmartDashboard.putData("Compress", new Compress());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        SmartDashboard.putNumber("Auto Mode", 0);
        SmartDashboard.putNumber("Arm Rotation Setpoint", Robot.arm.getRotatorSetpoint());
        SmartDashboard.putNumber("Arm Extension Setpoint", Robot.arm.getExtensionSetpoint());
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getJoystick1() {
        return joystick1;
    }

    public Joystick getJoystick2() {
        return joystick2;
    }

    public Joystick getOperatorConsole() {
        return operatorConsole;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}
