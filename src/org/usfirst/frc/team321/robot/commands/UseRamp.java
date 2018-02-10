package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseRamp extends Command {
	
	//setting up ramp
	double power;
	
	public UseRamp(double power) {
		requires(Robot.ramp);
		this.power = power;
	}
	
	protected void initialize() {
		Robot.ramp.stop();
	}
	
	protected void execute() {
		Robot.ramp.moveRamp(power);
	}
	
	protected void interrupted() {
		Robot.ramp.stop();
	}
	
	protected void end() {
		Robot.ramp.stop();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}
