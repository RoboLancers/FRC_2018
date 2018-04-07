package org.usfirst.frc.team321.robot.commands.autonomous.modes;

import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveInAngle;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveLinearSlide;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveRobot;
import org.usfirst.frc.team321.robot.commands.subsystems.manipulator.UseIntake;
import org.usfirst.frc.team321.robot.commands.subsystems.manipulator.UseIntake.IntakePower;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class OppositeSideScale extends CommandGroup{
	public OppositeSideScale(boolean isLeft) {
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if(gameData.charAt(1) == 'L' && !isLeft) {
			addSequential(new MoveRobot(0.8f, 0.0f), 2.7);
			addSequential(new MoveInAngle(0, 90), 4);
			addParallel(new MoveLinearSlide(82000), 2.5);
			addSequential(new MoveInAngle(0, -90), 2.5);
			addSequential(new UseIntake(IntakePower.OUTAKE));
		}else if(gameData.charAt(1) == 'R' && isLeft) {
			addSequential(new MoveRobot(0.8f, 0.0f), 2.7);
			addSequential(new MoveInAngle(0, 90), 4);
			addParallel(new MoveLinearSlide(82000), 2.5);
			addSequential(new MoveInAngle(0, -90), 2.5);
			addSequential(new UseIntake(IntakePower.OUTAKE));
		}else {
			addSequential(new CrossAutoLine(isLeft));
		}
	}
}
