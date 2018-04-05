package org.usfirst.frc.team321.robot.commands.autonomous.subroutine;

import org.usfirst.frc.team321.robot.commands.subsystems.manipulator.UseIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RunToScaleAndDump extends CommandGroup{
	public RunToScaleAndDump(boolean isLeft) {
		if(isLeft) {
			addSequential(new MoveRobot(0.8f, 0.0f), 2.2);
			addParallel(new MoveLinearSlide(0.8f), 3);
			addSequential(new MoveRobot(0, 15), 2);
			addSequential(new UseIntake(1), 2);
		}else {
			addSequential(new MoveRobot(0.8f, 0.0f), 2.5);
			addParallel(new MoveLinearSlide(0.8f), 3);
			addSequential(new MoveRobot(0.7, -15), 2);
			addSequential(new UseIntake(1), 2);
		}
	}
}
