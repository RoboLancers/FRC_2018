package org.usfirst.frc.team321.robot.commands.subsystems.manipulator;

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
					//Negative is up
					//Positive is down
					double joyVal = Robot.oi.flightController.getYAxisValue();
					double encoderVal = Robot.manipulator.getLinearSlide().getEncoder();
					double ratio;
					
					if (joyVal < 0) {
						if (Robot.manipulator.getLinearSlide().getEncoder() < 80000) {
							ratio = 1;
						} else if (Robot.manipulator.getLinearSlide().getEncoder() >= 80000 &&
								Robot.manipulator.getLinearSlide().getEncoder() <= 90000) {
							ratio = 0.375 * Math.cos(Math.PI * (encoderVal - 70000.0 / 20000.0)) + 0.625;
						} else {
							ratio = 0.25;
						}
					} else if (joyVal > 0) {
						if (Robot.manipulator.getLinearSlide().getEncoder() > 20000) {
							ratio = 1;
						} else if (Robot.manipulator.getLinearSlide().getEncoder() >= 10000 &&
								Robot.manipulator.getLinearSlide().getEncoder() <= 30000) {
							ratio = -0.375 * Math.sin(Math.PI * (encoderVal / 20000.0)) + 0.625;
						} else {
							ratio = 0.25;
						}
					} else {
						ratio = 0;
					}
					
					Robot.manipulator.getLinearSlide().move(ratio * joyVal);
					
					//Robot.manipulator.getLinearSlide().move(joyVal);
				}
			} else {
				Robot.manipulator.getLinearSlide().move(0.5);
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
