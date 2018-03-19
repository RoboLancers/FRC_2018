package org.usfirst.frc.team321.robot.commands.autonomous.modes;

import org.usfirst.frc.team321.robot.commands.UseIntake;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveInAngle;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveRobot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CrossAutoLine extends CommandGroup {
 
	public CrossAutoLine (boolean useGyro) {
		addSequential(new AutoStill());
		if(useGyro){
			addSequential(new MoveRobot(0.8f, 0.0f), 2.1);
		}else{
			addSequential(new MoveRobot(0.8), 2.1);
		}
	}
	
	public CrossAutoLine (boolean useGyro, boolean isLeft) {
		this(true);
		
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if(Character.toUpperCase(gameData.charAt(0)) == 'L' && isLeft){	
			addSequential(new MoveInAngle(0.6, 90), 1.5);
			addSequential(new UseIntake(-0.6), 2);
		}else if(Character.toUpperCase(gameData.charAt(0)) == 'R' && !isLeft){
			addSequential(new MoveInAngle(0.6 , -90), 1.5);
			addSequential(new UseIntake(-0.6), 2);
		}
	}
}