package org.usfirst.frc.team321.robot.commands.auto;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.utilities.LancerPID;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class TimeLS extends Command{

	//sets margin of error, can make it lower or higher but must find perfect (P)
	LancerPID pid;
	double time;
	double power;
	Timer timer = new Timer();
	
	public TimeLS(double time, double power) {
		requires(Robot.linear);
		this.time = time;
		this.power = power;
		//this.startLineEncoderDistance = Robot.linear.getLineEncoderDistance();
	}
	
	protected void initialize() {
		Robot.linear.stop();
	}
	
	protected void execute() {
		Robot.linear.move(power);
		//Robot.linear.move(pid.calcPID(Robot.linear.getLineEncoderDistance()));
	}
	
	protected void interrupted() {
		Robot.linear.stop();
	}
	
	@Override
	protected boolean isFinished() {
		return (timer.get() > this.time);
		//return Math.abs(Robot.linear.getLineEncoderDistance() - startLineEncoderDistance) >= targetDistance;
	}
}