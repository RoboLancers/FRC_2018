package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UseArcadeDrive extends Command {
	
	double tolerance = 0.10;
	
	public UseArcadeDrive() {
		requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
		Robot.drivetrain.stopMotors();
	}

	@Override
	protected void execute() {
		
		double throttle = Math.abs(Robot.oi.driveStick.getRawAxis(1)) >= tolerance ? -Robot.oi.driveStick.getRawAxis(1) : 0;
		double rotate = Math.abs(Robot.oi.driveStick.getRawAxis(4)) >= tolerance ? Robot.oi.driveStick.getRawAxis(4) : 0;
		
		double leftMotorSpeed = Math.abs(throttle + rotate) >= tolerance ? throttle + rotate : 0;
	    double rightMotorSpeed = Math.abs(throttle - rotate) >= tolerance ? throttle - rotate : 0;	
	    
	    Robot.drivetrain.setLeft(leftMotorSpeed);
	    Robot.drivetrain.setRight(rightMotorSpeed);
	}

	@Override
	protected void end() {
		Robot.drivetrain.stopMotors();
	}

	@Override
	protected void interrupted() {
		Robot.drivetrain.stopMotors();
	}

	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
