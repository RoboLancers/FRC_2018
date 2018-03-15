package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.commands.auto.MoveInAngle;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoMiddle extends CommandGroup {
	public AutoMiddle() {
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		addSequential(new MoveInAngle(0.7, 0, 1));
		
		if (gameData.charAt(0) == 'R') {
			addSequential(new MoveInAngle(0, 18), 1);
			addSequential(new MoveInAngle(0.7, 0, 1));
			addSequential(new MoveInAngle(0, -18), 1);
		} else {
			addSequential(new MoveInAngle(0, -18), 1);
			addSequential(new MoveInAngle(0.7, 0, 1));
			addSequential(new MoveInAngle(0, 18), 1);
		}
		
		addSequential(new MoveInAngle(0.7, 0, 3));
	}
}
