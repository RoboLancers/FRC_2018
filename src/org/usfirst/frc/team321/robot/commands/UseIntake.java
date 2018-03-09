package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseIntake extends Command {

	double power;
	boolean rumble = false;
	
	public UseIntake(double power) {
		requires(Robot.intake);
		this.power = power;
	}
	
	public UseIntake(double power, boolean rumble) {
		requires(Robot.intake);
		this.power = power;
		this.rumble = rumble;
	}
	
	protected void initialize() {
		Robot.intake.setAll(0);
	}
	
	protected void execute() {
		Robot.intake.setLeft(-power);
		Robot.intake.setRight(power);
		
		if (rumble) {
			Robot.oi.xboxController.setRumble(true);
		}
	}
	
	protected void interrupted() {
		end();
	}
	
	protected void end() {
		Robot.intake.stopIntake();
		Robot.oi.xboxController.setRumble(false);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}
