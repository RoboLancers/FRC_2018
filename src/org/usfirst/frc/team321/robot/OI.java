package org.usfirst.frc.team321.robot;

import org.usfirst.frc.team321.robot.commands.DSolenoidToggle;
import org.usfirst.frc.team321.robot.commands.UseIntake;
import org.usfirst.frc.team321.robot.commands.UseLinearSlides;
import org.usfirst.frc.team321.robot.commands.UsePivot;
import org.usfirst.frc.team321.robot.commands.UseRamp;
import org.usfirst.frc.team321.robot.subsystems.GearShifter;
import org.usfirst.frc.team321.robot.subsystems.IntakePivot;
import org.usfirst.frc.team321.robot.utilities.controllers.FlightController;
import org.usfirst.frc.team321.robot.utilities.controllers.XboxController;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class OI {

	public XboxController xboxController = new XboxController(0);
	public FlightController flightController = new FlightController(1);
	
	public OI() {
		xboxController.rightBumper.whenPressed(new DSolenoidToggle(Robot.gearshifter, GearShifter.gearShifter));
		//flightController.farMiddle.whenPressed(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakePivot, DoubleSolenoid.Value.kForward));
		//flightController.innerMiddle.whenPressed(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakePivot, DoubleSolenoid.Value.kReverse));
		
		//xboxController.Dpad_up.whileActive(new UsePivot(1));
		//xboxController.Dpad_down.whileActive(new UsePivot(-1));
		
		xboxController.A.whileHeld(new UseIntake(1));
		xboxController.B.whileHeld(new UseIntake(-1));
		
		flightController.farBottom.whileHeld(new UseLinearSlides(0.8));
		flightController.innerBottom.whileHeld(new UseLinearSlides(-0.8));
		//xboxController.A.whileHeld(new UseRamp(-1));
		//xboxController.B.whileHeld(new UseRamp(1));
	}
}
