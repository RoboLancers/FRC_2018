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
		//this.startLeftEncoderDistance = Robot.drivetrain.getLeftEncoderDistance();
		//this.startRightEncoderDistance = Robot.drivetrain.getRightEncoderDistance();
		pid = new LancerPID(0.2, 0, 0, 0);
		pid.setReference(distance);
		Robot.drivetrain.resetEncoder();
	}
	
	protected void initialize() {
		//Robot.drivetrain.setCoast();
		Robot.drivetrain.setAll(0); 
		Robot.drivetrain.resetEncoder();
	}
	
	protected void execute() {
		Robot.drivetrain.setLeft(pid.calcPID(Robot.drivetrain.getLeftEncoderDistance()));
		Robot.drivetrain.setRight(pid.calcPID(Robot.drivetrain.getLeftEncoderDistance()));
	}
	
	@Override
	protected boolean isFinished() {
		if(distance < 0){
			return distance - Robot.drivetrain.getLeftEncoderDistance() >= 0;
		} else {
			return distance - Robot.drivetrain.getLeftEncoderDistance() <= 0;
		}
		// TODO Auto-generated method stub
		/*
		return Math.abs(Robot.drivetrain.getRightEncoderDistance() - startRightEncoderDistance) >= distance  ||
				Math.abs(Robot.drivetrain.getLeftEncoderDistance() - startLeftEncoderDistance) >= distance;
				*/
		/*return Math.abs(Robot.drivetrain.getRightEncoderDistance()) >= Math.abs(distance)  ||
				Math.abs(Robot.drivetrain.getLeftEncoderDistance()) >= Math.abs(distance); */
	}
	
	@Override
	protected void end(){
		//Robot.drivetrain.setBrake();
		Robot.drivetrain.setLeft(0);
		Robot.drivetrain.setRight(0);
	}
}
 