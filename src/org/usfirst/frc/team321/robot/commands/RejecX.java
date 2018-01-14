package org.usfirst.frc.team321.robot.commands;
//aborted
/**package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class AutoEncoderX extends Command {

	double power;
	double targetAngle;
	double targetDistance; 
	boolean hasTargetAngle;
	
	
	public AutoEncoderX(double power, double targetDistance) {
		requires(Robot.drivetrain);
		this.power = power;
		this.targetDistance = targetDistance;
		hasTargetAngle = false;
	}
	
	public AutoEncoderX(double power, double targetAngle, double targetDistance) {
		requires(Robot.drivetrain);
		this.power = power;
		
		hasTargetAngle = true;
	}
	
	protected void initialize() {
		if (!hasTargetAngle) {
			this.targetAngle = Robot.sensors.getRobotHeading();
		}
		Robot.sensors.navX.resetDisplacement();
	}
	

	protected void execute() {
		Robot.drivetrain.setLeft(Robot.sensors.moveToTarget(power, Robot.sensors.getRobotHeading(), targetAngle)[0]);
		Robot.drivetrain.setRight(Robot.sensors.moveToTarget(power, Robot.sensors.getRobotHeading(), targetAngle)[1]);
	}
	
	@Override
	protected boolean isFinished() {
		if (Robot.drivetrain.getRawTopRightEncoderCount() <= Robot.drivetrain.getRevolutions()) {
			Robot.drivetrain.setAll(0);
		}
		return false;
	}	
	
	protected void end() {
		Robot.drivetrain.setAll(0);
	}

	protected void interrupted() {
		Robot.drivetrain.setAll(0);
	}

}
**/