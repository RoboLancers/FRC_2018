package org.usfirst.frc.team321.robot.commands.autonomous.subroutine;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.utilities.LancerPID;

import edu.wpi.first.wpilibj.command.Command;

public class MoveLinearSlide extends Command {
	
	LancerPID pid = new LancerPID();
	boolean useEncoders;
	
	double distance;
	double power;
	
	//USE ENCODER TICKS AS DISTANCE
	public MoveLinearSlide(double distance) {
		this.distance = distance;
		pid = new LancerPID(0.800, 0, 0);
		pid.setReference(distance);
		useEncoders = true;
	}
	
	public MoveLinearSlide(float power) {
		this.power = power;
		useEncoders = false;
	}
	
	protected void initialize() {
		//Robot.drivetrain.setCoast();
		Robot.drivetrain.setAll(0); 
		Robot.drivetrain.resetEncoders();
	}
	
	protected void execute() {
		Robot.manipulator.getLinearSlide().move((pid.calcPID(Robot.manipulator.getLinearSlide().getEncoder())));
	}
	
	@Override
	protected boolean isFinished() {
		if (!useEncoders) return Robot.sensors.isLinearSlideFullyExtended() || Robot.sensors.isLinearSlideFullyExtended();
		
		return Math.abs(Robot.manipulator.getLinearSlide().getEncoder()) >= Math.abs(distance)
				|| Robot.sensors.isLinearSlideFullyExtended() || Robot.sensors.isLinearSlideFullyExtended(); 
	}
	
	@Override
	protected void end(){
		Robot.manipulator.getLinearSlide().move(0);
	}
}
