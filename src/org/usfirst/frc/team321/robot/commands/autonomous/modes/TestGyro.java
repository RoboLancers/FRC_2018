package org.usfirst.frc.team321.robot.commands.autonomous.modes;

import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveInAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestGyro extends CommandGroup{
	
	public TestGyro(float power, float targetAngle){
		addSequential(new MoveInAngle(power, targetAngle));
	}
}
