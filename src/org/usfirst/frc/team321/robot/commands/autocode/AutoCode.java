package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.commands.auto.MoveRobotWithTime;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCode extends CommandGroup {
 
	public AutoCode () {
		addSequential (new MoveRobotWithTime(1, 1, 1.499));
	}
}