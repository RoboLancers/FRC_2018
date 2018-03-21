package org.usfirst.frc.team321.robot.commands.subsystems.drivetrain;

import org.usfirst.frc.team321.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class UseSlowArcadeDrive extends Command {

	public UseSlowArcadeDrive() {
		requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
		Robot.drivetrain.stop();
	}

	@Override
	protected void execute() {
		double throttle = Robot.oi.xboxController.getLeftYAxisValue() * 0.5;
		double rotate = Robot.oi.xboxController.getRightXAxisValue() * 0.5;

		Robot.drivetrain.setLeft(throttle + rotate);
		Robot.drivetrain.setRight(throttle - rotate);
	}

	@Override
	protected void end() {
		Robot.drivetrain.stop();
	}

	@Override
	protected void interrupted() {
		Robot.drivetrain.stop();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
