package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.commands.auto.MoveWithEncoder;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCodeEncoder extends CommandGroup{
	
	public AutoCodeEncoder() {
		addSequential (new MoveWithEncoder (36));
	}
}
