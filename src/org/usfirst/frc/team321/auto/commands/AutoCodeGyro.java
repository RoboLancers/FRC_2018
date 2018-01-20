package org.usfirst.frc.team321.auto.commands;

import org.usfirst.frc.team321.robot.commands.MoveWithNaveedX;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCodeGyro extends CommandGroup{
	public AutoCodeGyro(){
		addSequential(new MoveWithNaveedX(.7,0,2));
	}
}
