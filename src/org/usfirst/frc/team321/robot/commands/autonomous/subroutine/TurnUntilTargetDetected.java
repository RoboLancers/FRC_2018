package org.usfirst.frc.team321.robot.commands.autonomous.subroutine;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TurnUntilTargetDetected extends Command {
	
	double power;
	
	public TurnUntilTargetDetected(double power) {
		this.power = power;
		requires(Robot.camera);
		requires(Robot.drivetrain);
	}
	
	protected void initialize() {
		Robot.drivetrain.setAll(0);
	}
	
	protected void execute() {
		Robot.drivetrain.setLeft(power); 
		Robot.drivetrain.setRight(-power);
	}
	
	@Override
	protected boolean isFinished() {
		return Robot.camera.isTgtVisible();
	}
	
	@Override
	protected void end(){
		Robot.drivetrain.stop();
	}
}
