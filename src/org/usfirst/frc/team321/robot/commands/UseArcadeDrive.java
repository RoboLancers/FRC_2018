package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseArcadeDrive extends Command {
		
	double tolerance = 0.15;
	
	public UseArcadeDrive() {
		requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
		Robot.drivetrain.setAll(0);
	}

	@Override
	protected void execute() {
		
		//Formulas for leftMotorSpeed and rightMotorSpeed
		double leftMotorSpeed = -Robot.oi.driveStick.getRawAxis(1) + Robot.oi.driveStick.getRawAxis(4);
	    double rightMotorSpeed = -Robot.oi.driveStick.getRawAxis(1) - Robot.oi.driveStick.getRawAxis(4);	
	     
	    if((Robot.oi.driveStick.getRawAxis(1) > tolerance) || (Robot.oi.driveStick.getRawAxis(1) < -tolerance)) {
	    	Robot.drivetrain.setLeft(leftMotorSpeed);
		    Robot.drivetrain.setRight(rightMotorSpeed);
	    } else if ((Robot.oi.driveStick.getRawAxis(4) > tolerance) || (Robot.oi.driveStick.getRawAxis(4) < -tolerance)) {
	    	Robot.drivetrain.setLeft(leftMotorSpeed);
		    Robot.drivetrain.setRight(rightMotorSpeed);
	    } else if ((Math.abs(Robot.oi.driveStick.getRawAxis(1)) > tolerance) && (Math.abs(Robot.oi.driveStick.getRawAxis(4))) > tolerance){
	    	Robot.drivetrain.setLeft(leftMotorSpeed);
		    Robot.drivetrain.setRight(rightMotorSpeed);
	    } else {
	    	Robot.drivetrain.setLeft(0);
		    Robot.drivetrain.setRight(0);
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
