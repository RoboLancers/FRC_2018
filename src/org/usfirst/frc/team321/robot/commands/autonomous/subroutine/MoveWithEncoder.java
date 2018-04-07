package org.usfirst.frc.team321.robot.commands.autonomous.subroutine;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.utilities.LancerPID;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class MoveWithEncoder extends Command{
	
	//sets margin of error for movement with encoders
	LancerPID pid;
	double distance;
	double startRightEncoderDistance;
	double startLeftEncoderDistance;
	
	public MoveWithEncoder(double distance) {
		this.distance = distance;
		pid = new LancerPID(0.8, 0.0045, 0, 0);
		pid.setReference(distance);
		Robot.drivetrain.resetEncoders();
	}
	
	protected void initialize() {
		//Robot.drivetrain.setCoast();
		Robot.drivetrain.setAll(0); 
		Robot.drivetrain.resetEncoders();
	}
	
	protected void execute() {
		Robot.drivetrain.setLeft(pid.calcPID(Robot.drivetrain.getLeft().getEncoderDistance()));
		Robot.drivetrain.setRight(pid.calcPID(Robot.drivetrain.getRight().getEncoderDistance()));
	}
	
	@Override
	protected boolean isFinished() {
		return ((Math.abs(Robot.drivetrain.getLeft().getEncoderDistance()) >= Math.abs(distance) && 
				Math.abs(Robot.drivetrain.getRight().getEncoderDistance()) >= Math.abs(distance))) || Robot.sensors.getDistanceInMeters() < 0.2;
	}
	
	@Override
	protected void end(){
		//Robot.drivetrain.setBrake();
		Robot.drivetrain.setLeft(0);
		Robot.drivetrain.setRight(0);
	}
}