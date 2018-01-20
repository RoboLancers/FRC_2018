package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoEncoderX extends Command {

	//Listing Variables
	double power;
	double targetAngle;
	boolean hasTargetAngle;
	double targetDistance;

	//Constructor to input values, formula for addSequential
	public AutoEncoderX(double power, double targetAngle, double targetDistance) {
		requires(Robot.drivetrain);
		this.power = power;
		this.targetDistance = targetDistance;
		Robot.sensors.navX.reset();
	}
	
	//if there's no(!) target angle, reset NavX/gyro
	protected void initialize() {
		if (!hasTargetAngle) {
			this.targetAngle = Robot.sensors.getRobotHeading();
		}
		Robot.sensors.navX.resetDisplacement();
	}
	
	//execute for gyro to auto-adjust to target angle
	protected void execute() {
		Robot.drivetrain.setLeft(Robot.sensors.moveToTarget(power, Robot.sensors.getRobotHeading(), targetAngle)[0]);
		Robot.drivetrain.setRight(Robot.sensors.moveToTarget(power, Robot.sensors.getRobotHeading(), targetAngle)[1]);
	}
	//makes robot stop when ticks match up
	@Override
	protected boolean isFinished() {
		if (Robot.drivetrain.getRawTopRightEncoderCount() >= Robot.drivetrain.getRevolutions(targetDistance)) {
			Robot.drivetrain.setAll(0);
		}
		return true;
	}	
	//stop it
	protected void end() {
		Robot.drivetrain.setAll(0);
	}
	//get some help/ if something is wrong, stop it
	protected void interrupted() {
		Robot.drivetrain.setAll(0);
	}
}
