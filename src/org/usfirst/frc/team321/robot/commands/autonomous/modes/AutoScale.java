package org.usfirst.frc.team321.robot.commands.autonomous.modes;

import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.RunToScaleAndDump;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoScale extends CommandGroup{
	public AutoScale(boolean isLeft){
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if(gameData.charAt(1) == 'L' && isLeft) {
			addSequential(new RunToScaleAndDump(isLeft));
		}else if(gameData.charAt(1) == 'R' && !isLeft) {
			addSequential(new RunToScaleAndDump(!isLeft));
		}else {
			addSequential(new CrossAutoLine(isLeft));
		}
	}
}
