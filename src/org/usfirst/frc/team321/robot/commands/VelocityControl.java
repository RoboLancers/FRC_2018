package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		double throttle = Robot.oi.xboxController.getLeftYAxisValue();
		double rotate = Robot.oi.xboxController.getRightXAxisValue();
		
		double leftMotorSpeed, rightMotorSpeed;
		
		leftMotorSpeed = Math.abs(throttle + rotate) >= tolerance ? throttle + rotate : 0;
	    rightMotorSpeed = Math.abs(throttle - rotate) >= tolerance ? throttle - rotate : 0;	
	    
	    /* Convert 500 RPM to units / 100ms.
		 * 4096 Units/Rev * 500 RPM / 600 100ms/min in either direction:
		 * velocity setpoint is in units/100ms
		 */
	    leftMotorSpeed *= (500.0 * 4096 /600);
	    rightMotorSpeed *= (500.0 * 4096 /600);
	    
	    SmartDashboard.putNumber("Left Motor Velocity", leftMotorSpeed);
	    SmartDashboard.putNumber("Right Motor Velocity", rightMotorSpeed);
	    
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
