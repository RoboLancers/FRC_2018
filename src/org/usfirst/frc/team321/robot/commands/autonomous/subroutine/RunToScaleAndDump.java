package org.usfirst.frc.team321.robot.commands.autonomous.subroutine;

import org.usfirst.frc.team321.robot.commands.autonomous.modes.DoNothingAndReset;
import org.usfirst.frc.team321.robot.commands.subsystems.manipulator.UseIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RunToScaleAndDump extends CommandGroup{
	public RunToScaleAndDump(boolean isLeft) {
		if(isLeft) {
			addSequential(new MoveRobot(0.8f, 0.0f), 3.5);
			addSequential(new DoNothingAndReset(), 1);
			addParallel(new MoveLinearSlide(82000), 2.5);
			addSequential(new MoveInAngle(0, 35), 2.5);
			addSequential(new UseIntake(-1), 2);
			addSequential(new MoveLinearSlide(-86000), 2.5);
		}else {
			addSequential(new MoveRobot(0.8f, 0.0f), 3.5);
			addSequential(new DoNothingAndReset(), 1);
			addParallel(new MoveLinearSlide(82000), 2.5);
			addSequential(new MoveInAngle(0, -35), 2.5);
			addSequential(new UseIntake(-1), 2);
			addSequential(new MoveLinearSlide(-86000), 2.5);
		}
	}
}
