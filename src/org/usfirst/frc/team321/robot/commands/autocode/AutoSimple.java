package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.commands.auto.MoveRobotWithTime;
import org.usfirst.frc.team321.robot.commands.auto.TimeLS;
import org.usfirst.frc.team321.robot.commands.auto.UseIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoSimple extends CommandGroup {
	
	public AutoSimple() {
		addParallel(new TimeLS(1, 0.8));
		addSequential(new MoveRobotWithTime(0.8, 0.8, 1.5));
		addSequential(new UseIntake(-0.8), 1);
	}
}
