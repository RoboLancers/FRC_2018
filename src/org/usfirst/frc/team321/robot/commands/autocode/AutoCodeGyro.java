package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.commands.auto.MoveRobotWithTime;
import org.usfirst.frc.team321.robot.commands.auto.MoveWithNaveedX;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCodeGyro extends CommandGroup{
	public AutoCodeGyro(){
		addSequential(new MoveRobotWithTime(1, 1, 0.4));
		addSequential(new MoveWithNaveedX(0, 90), 5);
		addSequential(new MoveRobotWithTime(1, 1, 0.4));
	}
}
