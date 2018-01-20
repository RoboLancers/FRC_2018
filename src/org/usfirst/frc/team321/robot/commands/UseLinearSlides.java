package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseLinearSlides extends Command {
	
	double power;
	
	public UseLinearSlides(double power) {
		requires(Robot.linear);
		this.power = power;
	}
	
	protected void initialize() {
		Robot.linear.stop();
	}
	
	protected void execute() {
		Robot.linear.move(power);
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
			Robot.linear.up(0);
		} else if(Robot.sensors.touchSensorBottom.get() == true) {
			Robot.linear.down(0);
		}
		return false;
	}
}	
