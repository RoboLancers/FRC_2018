package org.usfirst.frc.team321.robot.commands.auto;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.utilities.LancerPID;

import edu.wpi.first.wpilibj.command.Command;

public class LinearSlidesWithEncoders extends Command{

	LancerPID pid;
	double targetDistance;
	double startLineEncoderDistance;
	public LinearSlidesWithEncoders(double targetDistance) {
		requires(Robot.linear);
		this.targetDistance = targetDistance;
		this.startLineEncoderDistance = Robot.linear.getLineEncoderDistance();
		pid = new LancerPID(0, 0, 0);
		pid.setSetpoint(targetDistance);
	}
	
	protected void initialize() {
		Robot.linear.stop();
	}
	
	protected void execute() {
		Robot.linear.move(pid.getOutput(Robot.linear.getLineEncoderDistance()));
	}
	
	protected void interrupted() {
		Robot.linear.stop();
	}
	
	
	@Override
	protected boolean isFinished() {

	
		return Math.abs(Robot.linear.getLineEncoderDistance() - startLineEncoderDistance) >= targetDistance;
	}
}