package org.usfirst.frc.team321.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCodeGyro extends CommandGroup{
	public AutoCodeGyro(){
		addSequential(new MoveWithNaveedX(.7,0,2));
	}
}
