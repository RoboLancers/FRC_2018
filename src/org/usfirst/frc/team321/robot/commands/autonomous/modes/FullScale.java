package org.usfirst.frc.team321.robot.commands.autonomous.modes;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class FullScale extends CommandGroup{
	public FullScale(boolean isLeft){
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if(gameData.charAt(1) == 'L' && isLeft || gameData.charAt(1) == 'R' && !isLeft) {
			addSequential(new SameSideScale(isLeft));
		}else {
			addSequential(new OppositeSideScale(isLeft));
		}
	}
}
