package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UseArcadeDrive extends Command {

	public UseArcadeDrive() {
		requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
		Robot.drivetrain.stop();
	}

	@Override
	protected void execute() {
		double throttle = Robot.oi.xboxController.getLeftYAxisValue();
		double rotate = Robot.oi.xboxController.getRightXAxisValue();

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
