package org.usfirst.frc.team321.robot.commands.auto;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.utilities.RobotUtil;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class MoveInAngle extends Command {
	
	private double degrees;
	private double power;
	private double currentAngle;
	
	public MoveInAngle(double power, double degrees) {
		this.degrees = degrees;
		this.power = power;
	}

	public void initialize() {
		currentAngle = Robot.sensors.navX.getAngle();
	}
	
	public void execute() {
		Robot.drivetrain.setLeft(RobotUtil.moveToTarget(power, Robot.sensors.navX.getAngle(), degrees + currentAngle, 0.025)[1]);
		Robot.drivetrain.setRight(RobotUtil.moveToTarget(power, Robot.sensors.navX.getAngle(), degrees + currentAngle, 0.025)[0]);
	}
	
	public void end() {
		Robot.drivetrain.setAll(0);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
}
