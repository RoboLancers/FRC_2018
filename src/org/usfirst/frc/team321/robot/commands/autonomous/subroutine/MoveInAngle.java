package org.usfirst.frc.team321.robot.commands.autonomous.subroutine;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.utilities.RobotUtil;

import edu.wpi.first.wpilibj.command.Command;

public class MoveInAngle extends Command {
	
	private double degrees;
	private double power;
	private double currentAngle;
	private double distance = 0;
	
	public MoveInAngle(double power, double degrees) {
		this.degrees = degrees;
		this.power = power;
	}
	
	public MoveInAngle(double power, double degrees, double distance) {
		this.degrees = degrees;
		this.power = power;
		this.distance = distance;
	}

	public void initialize() {
		currentAngle = Robot.sensors.navX.getAngle();
	}
	
	public void execute() {
		Robot.drivetrain.setLeft(RobotUtil.moveToTarget(power, Robot.sensors.navX.getAngle(), degrees + currentAngle, 0.025)[0]);
		Robot.drivetrain.setRight(RobotUtil.moveToTarget(power, Robot.sensors.navX.getAngle(), degrees + currentAngle, 0.025)[1]);
	}
	
	public void end() {
		Robot.drivetrain.setAll(0);
	}
	
	@Override
	protected boolean isFinished() {
		if (distance == 0) return false;
		
		return Math.abs(Robot.drivetrain.getLeftEncoderDistance()) >= distance || Math.abs(Robot.drivetrain.getRightEncoderDistance()) >= distance;
	}
}
