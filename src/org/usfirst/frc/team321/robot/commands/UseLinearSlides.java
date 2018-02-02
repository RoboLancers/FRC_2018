package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UseLinearSlides extends Command {
	
	double power;
	
	public UseLinearSlides() {
		requires(Robot.linear);
	}
	
	protected void initialize() {
		Robot.linear.stop();
	}
	
	protected void execute() {
		//Robot.linear.moveSafe(Robot.oi.maniStick.getRawAxis(3));
	}

	protected void interrupted() {
		Robot.linear.stop();
	}
	
	protected void end() {
		Robot.linear.stop();
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
}

