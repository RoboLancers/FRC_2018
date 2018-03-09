package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.utilities.LancerPID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UseArcadeDrive extends Command {
	
	double tolerance = 0.18;
	LancerPID straightDrive = new LancerPID(0.01, 0.0001, 0);
	double targetAngle;
	
	public UseArcadeDrive() {
		requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
		Robot.drivetrain.stopMotors();
		targetAngle = Robot.sensors.navX.getCompassHeading();
		straightDrive.setReference(Robot.sensors.navX.getCompassHeading());
	}

	@Override
	protected void execute() {
		double throttle = Robot.oi.xboxController.getLeftYAxisValue();
		double rotate = Robot.oi.xboxController.getRightXAxisValue();
		
		double leftMotorSpeed, rightMotorSpeed;
		
		/*
		if (rotate != 0) {
			targetAngle = Robot.sensors.navX.getCompassHeading();
			
			leftMotorSpeed = Math.abs(throttle + rotate) >= tolerance ? throttle + rotate : 0;
		    rightMotorSpeed = Math.abs(throttle - rotate) >= tolerance ? throttle - rotate : 0;	
		    
		    Robot.drivetrain.setLeft(leftMotorSpeed);
		    Robot.drivetrain.setRight(rightMotorSpeed);
		} else {
			double pidVal = straightDrive.calcPID(Robot.sensors.navX.getCompassHeading());
			Robot.drivetrain.setLeft(pidVal * throttle);
			Robot.drivetrain.setRight(-pidVal * throttle);
		}
		*/
		
		leftMotorSpeed = Math.abs(throttle + rotate) >= tolerance ? throttle + rotate : 0;
	    rightMotorSpeed = Math.abs(throttle - rotate) >= tolerance ? throttle - rotate : 0;	
	    
	    Robot.drivetrain.setLeft(leftMotorSpeed);
	    Robot.drivetrain.setRight(rightMotorSpeed);
		
		/*
		Robot.drivetrain.setLeft(Robot.oi.xboxController.getLeftYAxisValue());
	    Robot.drivetrain.setRight(Robot.oi.xboxController.getRightYAxisValue());
	    */
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
