package org.usfirst.frc.team321.robot.commands.subsystems.misc;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RegulateCompressor extends Command {

	private boolean hasFinished;

	public RegulateCompressor() {
		requires(Robot.pneumatics);
		hasFinished = false;
	}

	protected void initialize() {
		hasFinished = false;
	}

	protected void execute() {
		Robot.pneumatics.regulateCompressor();
	}

	@Override
	protected boolean isFinished() {
		return hasFinished;
	}

	protected void end() {
		hasFinished = true;
	}

	protected void interrupted() {
		hasFinished = true;
	}
}
