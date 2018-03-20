package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseLinearSlides extends Command {
	
	double power;
	
	boolean useJoystick;
	
	public UseLinearSlides() {
		requires(Robot.linearSlide);
		useJoystick = true;
	}
	
	public UseLinearSlides(double power) {
		requires(Robot.linearSlide);
		this.power = power;
		
		useJoystick = false;
	}
	
	protected void initialize() {
		Robot.linearSlide.stop();
	}
	
	protected void execute() {
		if(useJoystick){
			if(Math.abs(Robot.sensors.navX.getRoll()) < 30 || Math.abs(Robot.sensors.navX.getPitch()) < 30){
				
				if ((-Robot.oi.flightController.getYAxisValue() > 0 && Robot.sensors.isLinearSlideFullyExtended()) ||
						(-Robot.oi.flightController.getYAxisValue() < 0 && Robot.sensors.isLinearSlideAtGround())) {
					Robot.linearSlide.move(0);
				} else {
					Robot.linearSlide.move(Robot.oi.flightController.getYAxisValue());
				}
			} else {
				//Robot is too tipsy, readjusting
				Robot.linearSlide.move(-0.5);
			}
		} else {
			if ((power > 0 && Robot.sensors.isLinearSlideFullyExtended()) || 
					(power < 0 && Robot.sensors.isLinearSlideAtGround())) {
				Robot.linearSlide.move(0);
				return;
			}
			
			Robot.linearSlide.move(power);
		}
	}

	protected void interrupted() {
		Robot.linearSlide.stop();
	}
	
	protected void end() {
		Robot.linearSlide.stop();
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
}

