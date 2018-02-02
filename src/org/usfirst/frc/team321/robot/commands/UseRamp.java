package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseRamp extends Command {
	
double power;
	
	public UseRamp(double power) {
		requires(Robot.ramp);
		this.power = power;
	}
	
	protected void initialize() {
		Robot.ramp.stopAll();
	}
	
	protected void execute() {
		Robot.ramp.setUp(power);
		Robot.ramp.setDown(power);
	}
	
	protected void interrupted() {
		Robot.ramp.stopAll();
	}
	
	protected void end() {
		Robot.ramp.stopAll();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
