package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.commands.auto.MoveRobotWithTime;
import org.usfirst.frc.team321.robot.commands.auto.MoveTowardTarget;
import org.usfirst.frc.team321.robot.commands.auto.TurnUntilTargetDetected;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoLeftForward extends CommandGroup {
	
	public AutoLeftForward() {
		addSequential(new TurnUntilTargetDetected(-1)); 
		addSequential(new MoveTowardTarget(1));
		addSequential(new MoveRobotWithTime(-0.9, -0.9, 1.3));
	}

}
