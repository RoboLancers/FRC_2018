package org.usfirst.frc.team321.robot.commands.autonomous.subroutine;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.utilities.RobotUtil;

import edu.wpi.first.wpilibj.command.Command;

public class MoveRobot extends Command {

	private double degrees, currentAngle;
	private double power, distance = 0;

	public MoveRobot(double power, double degrees) {
		this.degrees = degrees;
		this.power = power;
	}

	public MoveRobot(double power, double degrees, double distance) {
		this.degrees = degrees;
		this.power = power;
		this.distance = distance;
	}

	public void initialize() {
		currentAngle = Robot.sensors.navX.getAngle();
	}

	public void execute() {
		Robot.drivetrain.setLeft(RobotUtil.moveToTarget(power, Robot.sensors.navX.getAngle(), degrees + currentAngle, 0.01)[1]);
		Robot.drivetrain.setRight(RobotUtil.moveToTarget(power, Robot.sensors.navX.getAngle(), degrees + currentAngle, 0.01)[0]);
	}

	public void end() {
		Robot.drivetrain.stop();
	}

	@Override
	protected boolean isFinished() {
		if (distance == 0)
			return false;

		return Math.abs(Robot.drivetrain.getLeft().getEncoderDistance()) >= distance
				|| Math.abs(Robot.drivetrain.getRight().getEncoderDistance()) >= distance;
	}
}
