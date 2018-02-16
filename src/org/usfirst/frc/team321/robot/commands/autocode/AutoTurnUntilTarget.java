package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.commands.auto.TurnUntilTargetDetected;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoTurnUntilTarget extends CommandGroup {
	public AutoTurnUntilTarget() {
		addSequential(new TurnUntilTargetDetected(-0.9));
	}
}
