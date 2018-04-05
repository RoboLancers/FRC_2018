package org.usfirst.frc.team321.robot.commands.autonomous.modes;

import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveRobot;
import org.usfirst.frc.team321.robot.commands.subsystems.manipulator.UseIntake;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CrossAutoLine extends CommandGroup {

	public CrossAutoLine() {
		addSequential(new AutoStill());
		addSequential(new MoveRobot(0.8, 0.0), 2.1);
	}

	public CrossAutoLine(boolean isLeft) {

		String gameData = DriverStation.getInstance().getGameSpecificMessage();

		if (Character.toUpperCase(gameData.charAt(0)) == 'L' && isLeft) {
			addSequential(new MoveRobot(0.6, 90), 1.5);
			addSequential(new UseIntake(-0.6), 2);
		} else if (Character.toUpperCase(gameData.charAt(0)) == 'R' && !isLeft) {
			addSequential(new MoveRobot(0.6, -90), 1.5);
			addSequential(new UseIntake(-0.6), 2);
		}
	}
}