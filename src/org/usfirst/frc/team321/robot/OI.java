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
		flightController.bottomLeft.whileHeld(new DSolenoidHold(Robot.ramprelease, RampRelease.rampRelease, DoubleSolenoid.Value.kReverse));
		
		//flightController.trigger.whileHeld(new UseIntake(0.9));
		//flightController.shooter.whileHeld(new UseIntake(-0.65));
		
		//xboxController.A.whileHeld(new UseIntake(0.9));
		//xboxController.B.whileHeld(new UseIntake(-0.65));
		
		xboxController.leftBumper.whileHeld(new UseIntake(0.80, true));
		xboxController.rightBumper.whileHeld(new UseIntake(-0.65, false));
		
//		flightController.farBottom.whileHeld(new UseLinearSlides(0.5));
//		flightController.innerBottom.whileHeld(new UseLinearSlides(-0.5));
		
		flightController.farBottom.whileHeld(new DSolenoidHold(Robot.intakepivot, IntakePivot.intakepivot, DoubleSolenoid.Value.kForward));
		
		//xboxController.A.whileHeld(new UseRamp(-1));
		//xboxController.B.whileHeld(new UseRamp(1));
	}
}