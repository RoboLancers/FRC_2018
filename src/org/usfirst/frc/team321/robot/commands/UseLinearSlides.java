package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseLinearSlides extends Command {

	private double power;
	private boolean useJoystick;

	public UseLinearSlides() {
		requires(Robot.manipulator.getLinearSlide());
		useJoystick = true;
	}

	public UseLinearSlides(double power) {
		requires(Robot.manipulator.getLinearSlide());
		this.power = power;

		useJoystick = false;
	}

	protected void initialize() {
		Robot.manipulator.getLinearSlide().stop();
	}

	protected void execute() {
		if (useJoystick) {
			if (Math.abs(Robot.sensors.navX.getRoll()) < 30 || Math.abs(Robot.sensors.navX.getPitch()) < 30) {

				if ((-Robot.oi.flightController.getYAxisValue() > 0 && Robot.sensors.isLinearSlideFullyExtended())
						|| (-Robot.oi.flightController.getYAxisValue() < 0 && Robot.sensors.isLinearSlideAtGround())) {
					Robot.manipulator.getLinearSlide().stop();
				} else {
					Robot.manipulator.getLinearSlide().move(Robot.oi.flightController.getYAxisValue());
				}
			} else {
				Robot.manipulator.getLinearSlide().move(-0.5);
				Robot.oi.xboxController.setRumble(Math.abs(Robot.sensors.navX.getPitch()) / 100);
			}
		} else {
			if ((power > 0 && Robot.sensors.isLinearSlideFullyExtended())
					|| (power < 0 && Robot.sensors.isLinearSlideAtGround())) {
				Robot.manipulator.getLinearSlide().stop();
				return;
			}

			Robot.manipulator.getLinearSlide().move(power);
		}
	}

	protected void interrupted() {
		end();
	}

	protected void end() {
		Robot.manipulator.getLinearSlide().stop();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
