package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.commands.auto.LinearSlidesWithEncoders;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCodeLSD extends CommandGroup{

	public AutoCodeLSD(){
		addSequential(new LinearSlidesWithEncoders(10));
	}
}
