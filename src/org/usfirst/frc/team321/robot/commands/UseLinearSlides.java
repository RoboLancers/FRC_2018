package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseLinearSlides extends Command {
	
	double power;
	
	boolean useJoystick;
	
	public UseLinearSlides() {
		requires(Robot.linear);
		useJoystick = true;
	}
	
	public UseLinearSlides(double power) {
		requires(Robot.linear);
		this.power = power;
		
		useJoystick = false;
	}
	
	protected void initialize() {
		Robot.linear.stop();
	}
	
	protected void execute() {
		if(useJoystick){
			if ((-Robot.oi.flightController.getYAxisValue() > 0 && Robot.sensors.isLinearSlideFullyExtended()) ||
					(-Robot.oi.flightController.getYAxisValue() < 0 && Robot.sensors.isLinearSlideAtGround())) {
				Robot.linear.move(0);
				return;
			}
			
			Robot.linear.move(-Robot.oi.flightController.getYAxisValue());
		}else{
			if ((power > 0 && Robot.sensors.isLinearSlideFullyExtended()) || 
					(power < 0 && Robot.sensors.isLinearSlideAtGround())) {
				Robot.linear.move(0);
				return;
			}
			
			Robot.linear.move(power);
		}
	}

	protected void interrupted() {
		Robot.linear.stop();
	}
	
	protected void end() {
		Robot.linear.stop();
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
}

