package org.usfirst.frc.team321.robot.commands.autonomous.modes;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DoNothingAndReset extends CommandGroup {
	public DoNothingAndReset() {
		Robot.sensors.navX.reset();
		Robot.sensors.navX.resetDisplacement();

		Robot.drivetrain.stop();
		Robot.drivetrain.resetEncoders();
	}
}