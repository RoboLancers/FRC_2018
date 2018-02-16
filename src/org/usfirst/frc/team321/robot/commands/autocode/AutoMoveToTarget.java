package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.commands.auto.MoveTowardTarget;
import org.usfirst.frc.team321.robot.commands.auto.TurnUntilTargetDetected;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoMoveToTarget extends CommandGroup{
	
	public AutoMoveToTarget() {
		addSequential(new MoveTowardTarget(.5));
		
	}

}
