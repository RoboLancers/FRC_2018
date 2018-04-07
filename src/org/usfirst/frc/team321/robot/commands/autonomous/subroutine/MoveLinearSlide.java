package org.usfirst.frc.team321.robot.commands.autonomous.subroutine;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.utilities.LancerPID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveLinearSlide extends Command {
	
	LancerPID pid = new LancerPID();
	boolean useEncoders;
	
	int encoderTick;
	double power;
	
	public MoveLinearSlide(int encoderTicks) {
		this.encoderTick = encoderTicks;
		pid = new LancerPID(0.00002, 0, 0.0001);
		pid.setReference(encoderTicks);
		useEncoders = true;
	}
	
	protected void initialize() {
		if (useEncoders) {
			Robot.manipulator.getLinearSlide().resetEncoder();
		}
		
		Robot.manipulator.getLinearSlide().move(0);
	}
	
	protected void execute() {
		if (useEncoders) {
			power = pid.calcPID(Robot.manipulator.getLinearSlide().getEncoder());
			Robot.manipulator.getLinearSlide().move(-power * 0.8);
			SmartDashboard.putNumber("Power Linear Slide lol i can type", -power);
		} else {
			Robot.manipulator.getLinearSlide().move(-power * 0.8);
		}
	}
	
	@Override
	protected boolean isFinished() {
		if (useEncoders) {
			return Math.abs(Robot.manipulator.getLinearSlide().getEncoder()) >= Math.abs(encoderTick) ||
			Robot.manipulator.getLinearSlide().isSafeToMove(power);
		}
		
		return Robot.manipulator.getLinearSlide().isSafeToMove(power); 
	}
	
	@Override
	protected void end(){
		Robot.manipulator.getLinearSlide().move(0);
	}
}
