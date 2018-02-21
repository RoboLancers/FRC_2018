package org.usfirst.frc.team321.robot;

import org.usfirst.frc.team321.robot.commands.DSolenoidHold;
import org.usfirst.frc.team321.robot.commands.DSolenoidToggle;
import org.usfirst.frc.team321.robot.commands.UseIntake;
import org.usfirst.frc.team321.robot.commands.UseLinearSlides;
import org.usfirst.frc.team321.robot.subsystems.GearShifter;
import org.usfirst.frc.team321.robot.subsystems.IntakePivot;
import org.usfirst.frc.team321.robot.subsystems.RampRelease;
import org.usfirst.frc.team321.robot.utilities.controllers.FlightController;
import org.usfirst.frc.team321.robot.utilities.controllers.XboxController;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class OI {

	public XboxController xboxController = new XboxController(0);
	public FlightController flightController = new FlightController(1);
	
	public OI() {
		xboxController.rightTrigger.whileHeld(new DSolenoidHold(Robot.gearshifter, GearShifter.gearShifter, DoubleSolenoid.Value.kReverse));
		flightController.bottomLeft.whenPressed(new DSolenoidHold(Robot.ramprelease, RampRelease.rampRelease, DoubleSolenoid.Value.kReverse));
		
		//flightController.trigger.whileHeld(new UseIntake(0.9));
		//flightController.shooter.whileHeld(new UseIntake(-0.65));
		
		//xboxController.A.whileHeld(new UseIntake(0.9));
		//xboxController.B.whileHeld(new UseIntake(-0.65));
		
		xboxController.leftBumper.whileHeld(new UseIntake(0.65));
		xboxController.rightBumper.whileHeld(new UseIntake(-0.8, true));
		
		flightController.farBottom.whileHeld(new UseLinearSlides(0.5));
		flightController.innerBottom.whileHeld(new UseLinearSlides(-0.5));
		
		flightController.Dpad_up.whenPressed(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakepivot));
		
		//xboxController.A.whileHeld(new UseRamp(-1));
		//xboxController.B.whileHeld(new UseRamp(1));
	}
}
