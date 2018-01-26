package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.commands.auto.MoveWithNaveedX;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCodeGyro extends CommandGroup{
	public AutoCodeGyro(){
		addSequential(new MoveWithNaveedX(0, 50), 15);
		addSequential(new MoveWithNaveedX(0, 290), 15);
		addSequential(new MoveWithNaveedX(0, 96), 15);
	}
}
