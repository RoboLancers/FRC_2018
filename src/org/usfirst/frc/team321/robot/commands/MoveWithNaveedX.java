package org.usfirst.frc.team321.robot.commands;

import java.sql.Time;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class MoveWithNaveedX extends Command {

	double power;
	double targetAngle;
	double time;
	Timer timer = new Timer();
		
	public MoveWithNaveedX (double power, double targetAngle,  double time) {
		
		this.power = power;
		this.targetAngle = targetAngle;
		this.time = time;
		this.timer = new Timer();
		timer.reset();
		Robot.sensors.navX.reset();
	
	}
	
	
	@Override 
	protected void execute() {
		Robot.drivetrain.setLeft(Robot.sensors.moveToTarget(power, Robot.sensors.getRobotHeading(), targetAngle)[0]);
		Robot.drivetrain.setRight(Robot.sensors.moveToTarget(power, Robot.sensors.getRobotHeading(), targetAngle)[1]);
	
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return (timer.get() > this.time);
	
	}

}