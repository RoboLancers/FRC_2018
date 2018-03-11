package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.commands.auto.MoveRobot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CrossAutoLine extends CommandGroup {
 
	public CrossAutoLine (boolean useGyro) {
		addSequential(new AutoStill());
		if(useGyro){
			addSequential(new MoveRobot(0.7f, 0.0f), 1.5);
		}else{
			addSequential(new MoveRobot(0.1), 15);
		}
	}
}