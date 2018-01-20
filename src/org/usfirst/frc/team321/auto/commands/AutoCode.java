package org.usfirst.frc.team321.auto.commands;

import org.usfirst.frc.team321.robot.commands.AutoEncoderX;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCode extends CommandGroup {
 
	public AutoCode () {
		addSequential (new AutoEncoderX(0.7, 0, 30));
	}
}