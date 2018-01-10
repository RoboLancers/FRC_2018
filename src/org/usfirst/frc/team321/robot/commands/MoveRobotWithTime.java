package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class MoveRobotWithTime extends Command{
	Timer timer = new Timer();
	double leftMotors;
	double rightMotors;
	double time;
	
	
	public MoveRobotWithTime(double leftMotors, double rightMotors, double time){
		requires(Robot.drivetrain);
		this.leftMotors = leftMotors;
		this.rightMotors = rightMotors;
		this.time = time;
		
	}
	protected void initialize(){
		Robot.drivetrain.setAll(0);
		timer.reset();
		timer.start();
	}
	
	protected void execute(){
		Robot.drivetrain.setLeft(leftMotors);
		Robot.drivetrain.setRight(rightMotors);
	
		}
	
	@Override
	protected boolean isFinished() {
		return (timer.get() > this.time);
	}
	
	protected void end(){
		Robot.drivetrain.setAll(0);
	}
	
	protected void interrupted(){
		Robot.drivetrain.setAll(0);
	}
}


