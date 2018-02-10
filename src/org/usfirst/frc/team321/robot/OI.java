package org.usfirst.frc.team321.robot;

import org.usfirst.frc.team321.robot.commands.DSolenoidToggle;
import org.usfirst.frc.team321.robot.commands.UseIntake;
import org.usfirst.frc.team321.robot.commands.UseLinearSlides;
import org.usfirst.frc.team321.robot.commands.UseRamp;
import org.usfirst.frc.team321.robot.subsystems.GearShifter;
import org.usfirst.frc.team321.robot.subsystems.IntakePivot;
import org.usfirst.frc.team321.robot.utilities.controllers.FlightController;
import org.usfirst.frc.team321.robot.utilities.controllers.XboxController;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI extends IterativeRobot {

	public XboxController xboxController;
	public FlightController flightController;
	
	public OI() {
		xboxController.rightBumper.whenPressed(new DSolenoidToggle(Robot.gearshifter, GearShifter.gearShifter));
		flightController.farMiddle.whenPressed(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakePivot, DoubleSolenoid.Value.kForward));
		flightController.innerMiddle.whenPressed(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakePivot, DoubleSolenoid.Value.kReverse));
		flightController.shooter.whileHeld(new UseIntake(1));
		flightController.trigger.whileHeld(new UseIntake(-1));
		flightController.farBottom.whileHeld(new UseLinearSlides());
		flightController.innerBottom.whileHeld(new UseLinearSlides());
		xboxController.A.whileHeld(new UseRamp(-1));
		xboxController.B.whileHeld(new UseRamp(1));
	}
}
