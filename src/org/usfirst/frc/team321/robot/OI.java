package org.usfirst.frc.team321.robot;

import org.usfirst.frc.team321.robot.commands.DSolenoidHold;
import org.usfirst.frc.team321.robot.commands.UseIntake;
import org.usfirst.frc.team321.robot.commands.UseIntake.IntakePower;
import org.usfirst.frc.team321.robot.subsystems.GearShifter;
import org.usfirst.frc.team321.robot.subsystems.IntakePivot;
import org.usfirst.frc.team321.robot.utilities.controllers.FlightController;
import org.usfirst.frc.team321.robot.utilities.controllers.XboxController;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class OI {

	public XboxController xboxController;
	public FlightController flightController;
	
	public OI() {
		xboxController = new XboxController(0);
		flightController = new FlightController(1);
		
		xboxController.rightTrigger.whileHeld(new DSolenoidHold(Robot.gearshifter, GearShifter.gearShifter, DoubleSolenoid.Value.kForward));
		
		xboxController.leftBumper.whileHeld(new UseIntake(IntakePower.INTAKE.power, true));
		xboxController.rightBumper.whileHeld(new UseIntake(IntakePower.OUTTAKE.power, false));
		
		flightController.farBottom.whileHeld(new DSolenoidHold(Robot.intakepivot, IntakePivot.intakepivot, DoubleSolenoid.Value.kForward));
	}
}