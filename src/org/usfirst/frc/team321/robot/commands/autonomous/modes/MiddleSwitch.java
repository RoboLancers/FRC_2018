package org.usfirst.frc.team321.robot.commands.autonomous.modes;

import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveInAngle;
import org.usfirst.frc.team321.robot.commands.subsystems.manipulator.UseIntake;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class MiddleSwitch extends CommandGroup {
	public MiddleSwitch() {
		String gameData = DriverStation.getInstance().getGameSpecificMessage();

		addSequential(new DoNothingAndReset(), 0.25);
		addSequential(new MoveInAngle(0.8f, 0.0f), 0.4);

		if (gameData.charAt(0) == 'R') {
			addSequential(new MoveInAngle(0.7f, 50f, 0.001), 1.35);
			addSequential(new MoveInAngle(0.7f, -50f, 0.001), 1.5);
		} else {
			addSequential(new MoveInAngle(0.7f, -40f, 0.001), 1.2);
			addSequential(new MoveInAngle(0.7f, 40f, 0.001), 1.5);
		}
		
		addSequential(new UseIntake(-0.7), 2);
	}
}
	