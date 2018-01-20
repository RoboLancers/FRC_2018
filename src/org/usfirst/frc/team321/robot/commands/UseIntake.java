package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseIntake extends Command {

	double power;
	
	public UseIntake(double power) {
		requires(Robot.intake);
		this.power = power;
	}
	
	protected void initialize() {
		
	}
	
	protected void execute() {
		Robot.intake.setLeft(power);
		Robot.intake.setRight(power);
	}
	
	protected void interrupted() {
		Robot.intake.stopIntake();
	}
	
	protected void end() {
		Robot.intake.stopIntake();
	}
	
	
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
