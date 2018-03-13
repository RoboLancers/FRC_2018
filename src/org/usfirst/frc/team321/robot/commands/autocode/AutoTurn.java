package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.commands.auto.MoveInAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoTurn extends CommandGroup {
	public AutoTurn() {
		addSequential(new MoveInAngle(0.7, 90), 2);
	}
}
