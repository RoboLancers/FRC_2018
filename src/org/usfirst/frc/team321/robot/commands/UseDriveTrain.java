package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseDriveTrain extends Command {
	
	public UseDriveTrain() {
		requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		if(Math.abs(Robot.oi.driveStick.getRawAxis(1)) > 0.1) {
			Robot.drivetrain.setLeft(Robot.oi.driveStick.getRawAxis(1));
		} else {
			Robot.drivetrain.setLeft(0);
		}
		
		if(Math.abs(Robot.oi.driveStick.getRawAxis(3)) > 0.1) {
			Robot.drivetrain.setRight(-Robot.oi.driveStick.getRawAxis(3));
		} else {
			Robot.drivetrain.setRight(0);
		}
		
	}

	@Override
	protected void end() {
		Robot.drivetrain.stopMotors();
	}

	@Override
	protected void interrupted() {
		
	}

	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
