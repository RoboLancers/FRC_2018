package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseLinearSlideUp extends Command {
	
	double power;
	
	public UseLinearSlideUp(double power) {
		requires(Robot.linear);
		this.power = power;
	}
	
	protected void initialize() {
		Robot.linear.stop();
	}
	
	protected void execute() {
		Robot.linear.up(power);
	}
	
	protected void interrupted() {
		Robot.linear.stop();
	}
	
	protected void end() {
		Robot.linear.stop();
	}
	
	@Override
	protected boolean isFinished() {
		if(Robot.sensors.touchSensorTop.get() == true) {
			Robot.linear.stop();
		}
		return false;
	}
}	

