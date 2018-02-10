package org.usfirst.frc.team321.robot.commands.auto;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseIntake extends Command {

	double power;
	
	public UseIntake(double power) {
		this.power = power;
	}
	
	protected void initialize() {
		Robot.intake.setAll(0);
	}
	
	protected void execute() {
		Robot.intake.setAll(power);
	}
	
	protected void end() {
		Robot.intake.setAll(0);
	}
	
	protected void interrupted() {
		Robot.intake.setAll(0);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}
