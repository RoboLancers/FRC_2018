package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.utilities.RobotUtil;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class TurnInPlace extends Command {
	
	private Timer timer = new Timer();
	private double degrees;
	private double seconds;
	private double currentAngle;
	
	public TurnInPlace(double degrees, double seconds) {
		this.degrees = degrees;
		this.seconds = seconds;
	}

	public void initialize() {
		currentAngle = Robot.sensors.getRobotHeading();
		timer.reset();
		timer.start();
	}
	
	public void execute() {
		Robot.drivetrain.setLeft(RobotUtil.moveToTarget(0, Robot.sensors.getRobotHeading(), degrees + currentAngle)[0]);
		Robot.drivetrain.setRight(RobotUtil.moveToTarget(0, Robot.sensors.getRobotHeading(), degrees + currentAngle)[1]);
	}
	
	public void end() {
		Robot.drivetrain.setAll(0);
	}
	
	@Override
	protected boolean isFinished() {
		return timer.get() >= seconds;
	}
}
