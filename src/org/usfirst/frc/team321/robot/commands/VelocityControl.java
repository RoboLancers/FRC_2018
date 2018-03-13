package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class VelocityControl extends Command {
	
	double tolerance = 0.15;
	
	public VelocityControl() {
		requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
		Robot.drivetrain.stopMotors();
	}

	@Override
	protected void execute() {
		double throttle = Robot.oi.xboxController.getLeftYAxisValue() * 500.0 * 4096 /600;
		double rotate = Robot.oi.xboxController.getRightXAxisValue()  * 500.0 * 4096 /600;
		
		double leftMotorSpeed, rightMotorSpeed;
		
		leftMotorSpeed = Math.abs(throttle + rotate) >= tolerance ? throttle + rotate : 0;
	    rightMotorSpeed = Math.abs(throttle - rotate) >= tolerance ? throttle - rotate : 0;	
	    
	    Robot.drivetrain.leftMaster.set(ControlMode.Velocity, leftMotorSpeed);
	    Robot.drivetrain.rightMaster.set(ControlMode.Velocity, rightMotorSpeed);
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
