package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.commands.auto.MoveRobotWithTime;
import org.usfirst.frc.team321.robot.commands.auto.MoveWithNaveedX;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCode extends CommandGroup {
 
	public AutoCode () {
		//addSequential (new MoveRobotWithTime(0.5, 0.5, 1.499));
		addSequential (new MoveWithNaveedX(0, 90));
		//addSequential (new MoveRobotWithTime(0.5, 0.5, 1.499));
	}
}