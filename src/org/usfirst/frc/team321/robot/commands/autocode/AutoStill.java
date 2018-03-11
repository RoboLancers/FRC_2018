package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoStill extends CommandGroup {
	public AutoStill(){
		Robot.sensors.navX.reset();
		Robot.sensors.navX.resetDisplacement();
		
		Robot.drivetrain.stopMotors();
		Robot.drivetrain.resetEncoder();
	}
}