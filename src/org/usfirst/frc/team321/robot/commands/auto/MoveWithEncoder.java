package org.usfirst.frc.team321.robot.commands.auto;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.utilities.LancerPID;

import edu.wpi.first.wpilibj.command.Command;

public class MoveWithEncoder extends Command{
	
	//sets margin of error for movement with encoders
	LancerPID pid;
	double distance;
	double startRightEncoderDistance;
	double startLeftEncoderDistance;
	
	public MoveWithEncoder(double distance) {
		this.distance = distance;
		pid = new LancerPID(0.5, 0, 0.001, 0);
		pid.setReference(distance);
		Robot.drivetrain.resetEncoder();
	}
	
	protected void initialize() {
		//Robot.drivetrain.setCoast();
		Robot.drivetrain.setAll(0); 
		Robot.drivetrain.resetEncoder();
	}
	
	protected void execute() {
		Robot.drivetrain.setLeft(pid.calcPID(-Robot.drivetrain.getLeftEncoderDistance()));
		Robot.drivetrain.setRight(pid.calcPID(Robot.drivetrain.getRightEncoderDistance()));
	}
	
	@Override
	protected boolean isFinished() {
		return (Math.abs(Robot.drivetrain.getLeftEncoderDistance()) >= Math.abs(distance) || 
				Math.abs(Robot.drivetrain.getRightEncoderDistance()) >= Math.abs(distance)) || Robot.sensors.getDistanceInMeters() < 0.2;
	}
	
	@Override
	protected void end(){
		//Robot.drivetrain.setBrake();
		Robot.drivetrain.setLeft(0);
		Robot.drivetrain.setRight(0);
	}
}
 