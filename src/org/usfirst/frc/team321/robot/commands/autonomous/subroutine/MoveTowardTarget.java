package org.usfirst.frc.team321.robot.commands.autonomous.subroutine;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.utilities.LancerPID;

import edu.wpi.first.wpilibj.command.Command;

public class MoveTowardTarget extends Command{

	LancerPID pid;
	double power;
	
	public MoveTowardTarget(double power) {
		this.power = power;
		requires(Robot.camera);
		requires(Robot.drivetrain);
		pid = new LancerPID(0.04, 0, 0, 0);
		pid.setReference(0);
	}
	
	protected void initialize() {

	}
	
	protected void execute() {
		if (Robot.camera.isTgtVisible() == true){
			Robot.drivetrain.setLeft(power - pid.calcPID(Robot.camera.getTgtAngle_Deg()));
			Robot.drivetrain.setRight(power + pid.calcPID(Robot.camera.getTgtAngle_Deg()));
		} else {
			Robot.drivetrain.stop();
		}
	}
	
	@Override
	protected boolean isFinished() {
		return (Robot.camera.isTgtVisible() == false);
	}
	
	@Override
	protected void end(){
		Robot.drivetrain.stop();
	}
}
