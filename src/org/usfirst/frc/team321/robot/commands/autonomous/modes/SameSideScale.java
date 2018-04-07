package org.usfirst.frc.team321.robot.commands.autonomous.modes;

import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveInAngle;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveLinearSlide;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveRobot;
import org.usfirst.frc.team321.robot.commands.subsystems.manipulator.UseIntake;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class SameSideScale extends CommandGroup{
	public SameSideScale(boolean isLeft){
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if(gameData.charAt(1) == 'L' && isLeft) {
			addSequential(new MoveRobot(0.8f, 0.0f), 3.5);
			addSequential(new DoNothingAndReset(), 1);
			addParallel(new MoveLinearSlide(82000), 2.5);
			addSequential(new MoveInAngle(0, 35), 2.5);
			addSequential(new UseIntake(-1), 2);
			addSequential(new MoveLinearSlide(-86000), 2.5);
		}else if(gameData.charAt(1) == 'R' && !isLeft) {
			addSequential(new MoveRobot(0.8f, 0.0f), 3.5);
			addSequential(new DoNothingAndReset(), 1);
			addParallel(new MoveLinearSlide(82000), 2.5);
			addSequential(new MoveInAngle(0, -35), 2.5);
			addSequential(new UseIntake(-1), 2);
			addSequential(new MoveLinearSlide(-86000), 2.5);
		}else {
			addSequential(new CrossAutoLine(isLeft));
		}
	}
}
