package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UseArcadeDrive extends Command {
	
	double tolerance = 0.15;
	
	public UseArcadeDrive() {
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
		
		leftMotorSpeed = Math.abs(throttle + rotate) >= tolerance ? (throttle + rotate): 0;
	    rightMotorSpeed = Math.abs(throttle - rotate) >= tolerance ? (throttle - rotate) : 0;	
		
	    SmartDashboard.putNumber("Drive Error Velocity", Robot.drivetrain.getDriveError());
	    SmartDashboard.putNumber("Arcade Left Motor Speed", leftMotorSpeed);
	    SmartDashboard.putNumber("Arcade Right Motor Speed", rightMotorSpeed);
	    
	    Robot.drivetrain.setLeft(leftMotorSpeed);
	    Robot.drivetrain.setRight(rightMotorSpeed);
	    
	    if (Math.abs(Robot.sensors.navX.getPitch()) >= 30) {
	    	Robot.oi.xboxController.setRumble(Math.abs(Robot.sensors.navX.getPitch()) / 100);
	    }
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
