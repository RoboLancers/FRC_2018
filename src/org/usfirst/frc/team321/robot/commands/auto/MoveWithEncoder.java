package org.usfirst.frc.team321.robot.commands.auto;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.utilities.LancerPID;

import edu.wpi.first.wpilibj.command.Command;

public class MoveWithEncoder extends Command{
	
	LancerPID pid;
	double distance;
	double startRightEncoderDistance;
	double startLeftEncoderDistance;
	
	public MoveWithEncoder(double distance) {
		this.distance = distance;
		this.startLeftEncoderDistance = Robot.drivetrain.getLeftEncoderDistance();
		this.startRightEncoderDistance = Robot.drivetrain.getRightEncoderDistance();
		pid = new LancerPID(0.208, 0, 0);
		pid.setSetpoint(distance);
	}
	
	protected void initialize() {
		Robot.drivetrain.setAll(0); 
		Robot.drivetrain.resetEncoder();
	
	}
	
	
	protected void execute() {
		Robot.drivetrain.setLeft(pid.getOutput(Robot.drivetrain.getLeftEncoderDistance()));
		Robot.drivetrain.setRight(pid.getOutput(Robot.drivetrain.getRightEncoderDistance()));
		
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return Math.abs(Robot.drivetrain.getRightEncoderDistance() - startRightEncoderDistance) >= distance  ||
				Math.abs(Robot.drivetrain.getLeftEncoderDistance() - startLeftEncoderDistance) >= distance;
	}

}
 