package org.usfirst.frc.team321.robot.commands.autonomous.subroutine;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.utilities.RobotUtil;

import edu.wpi.first.wpilibj.command.Command;

public class MoveInAngle extends Command {
	
	private double degrees;
	private double power;
	private double currentAngle;
	//private double distance = 0;
	private double kConst;
	
	public MoveInAngle(double power, double degrees) {
		this.degrees = degrees;
		this.power = power;
		kConst = 0.01;
	}
	
	public MoveInAngle(double power, double degrees, double kConst) {
		this.degrees = degrees;
		this.power = power;
		this.kConst = kConst;
	}

	public void initialize() {
		currentAngle = Robot.sensors.navX.getAngle();
	}
	
	public void execute() {
		Robot.drivetrain.setLeft(RobotUtil.moveToTarget(power, Robot.sensors.navX.getAngle(), degrees + currentAngle, kConst)[1]);
		Robot.drivetrain.setRight(RobotUtil.moveToTarget(power, Robot.sensors.navX.getAngle(), degrees + currentAngle, kConst)[0]);
	}
	
	public void end() {
		Robot.drivetrain.setAll(0);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
}