package org.usfirst.frc.team321.robot.commands.subsystems.manipulator;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseIntake extends Command {

	double power;
	boolean rumble = false;
	
	public UseIntake(double power) {
		requires(Robot.manipulator.getIntake());
		this.power = power;
	}
	
	public UseIntake(double power, boolean rumble) {
		requires(Robot.manipulator.getIntake());
		this.power = power;
		this.rumble = rumble;
	}
	
	protected void initialize() {
		Robot.manipulator.getIntake().stop();
	}
	
	protected void execute() {
		Robot.manipulator.getIntake().setLeft(-power);
		Robot.manipulator.getIntake().setRight(power);
		
		if (rumble) {
			Robot.oi.xboxController.setRumble(true);
		}
	}
	
	protected void interrupted() {
		end();
	}
	
	protected void end() {
		Robot.manipulator.getIntake().stop();
		Robot.oi.xboxController.setRumble(false);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
}
