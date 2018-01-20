package org.usfirst.frc.team321.robot;

import org.usfirst.frc.team321.robot.commands.DSolenoidHold;
import org.usfirst.frc.team321.robot.commands.DSolenoidToggle;
import org.usfirst.frc.team321.robot.commands.UseIntake;
import org.usfirst.frc.team321.robot.commands.UseLinearSlides;
import org.usfirst.frc.team321.robot.subsystems.GearShifter;
import org.usfirst.frc.team321.robot.subsystems.Intake;
import org.usfirst.frc.team321.robot.subsystems.IntakePivot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI extends IterativeRobot {
	//dat stick
	public Joystick driveStick;
	public Joystick maniStick; 
	public JoystickButton[] driveBtn, maniBtn;
	
	public OI() {
		driveStick = new Joystick(0);
		maniStick = new Joystick(1);
		
		driveBtn = new JoystickButton[12];
		maniBtn = new JoystickButton[12];
		
		for (int x = 0; x < driveBtn.length; x++) {
			driveBtn[x] = new JoystickButton(driveStick, x + 1);
		}
		
		for (int x = 0; x < maniBtn.length; x++) {
			maniBtn[x] = new JoystickButton(maniStick, x + 1);
		}
				
		driveBtn[5].whileHeld(new DSolenoidHold(Robot.gearshifter, GearShifter.gearShifter, DoubleSolenoid.Value.kForward));
		maniBtn[9].whenPressed(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakePivot, DoubleSolenoid.Value.kForward));
		maniBtn[8].whenPressed(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakePivot, DoubleSolenoid.Value.kReverse));
		maniBtn[0].whileHeld(new UseIntake(1));
		maniBtn[1].whileHeld(new UseIntake(-1));
		maniBtn[10].whileHeld(new UseLinearSlides());
		maniBtn[11].whileHeld(new UseLinearSlides());
	}

	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
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

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
