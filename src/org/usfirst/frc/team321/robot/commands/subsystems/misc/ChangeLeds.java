package org.usfirst.frc.team321.robot.commands.subsystems.misc;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.subsystems.misc.Leds.LedColors;

import edu.wpi.first.wpilibj.command.Command;

public class ChangeLeds extends Command{

	public ChangeLeds() {
		requires(Robot.leds);
	}

	@Override
	protected void execute() {
		if(Robot.manipulator.getIntakePivot().isUp()) {
			Robot.leds.setColor(LedColors.LAWN_GREEN);
		}else {
			double distance = Robot.sensors.getAverageDistanceInMeters();
			
			if(distance <= 0.1) {
				Robot.leds.setColor(LedColors.DARK_BLUE);
			}else if(distance > 0.1 && distance <= 0.2) {
				Robot.leds.setColor(LedColors.SKY_BLUE);
			}else if(distance > 0.2 && distance <= 0.3) {
				Robot.leds.setColor(LedColors.LIME);
			}else {
				Robot.leds.setColor(LedColors.LAWN_GREEN);
			}
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
