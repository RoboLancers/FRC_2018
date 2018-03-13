package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.commands.UseLinearSlides;
import org.usfirst.frc.team321.robot.commands.auto.MoveTowardTarget;
import org.usfirst.frc.team321.robot.commands.auto.MoveInAngle;
import org.usfirst.frc.team321.robot.commands.auto.TurnUntilTargetDetected;
import org.usfirst.frc.team321.robot.commands.auto.UseIntake;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoSwitch extends CommandGroup {
	
	public AutoSwitch() {

		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if (gameData.charAt(0) == 'R') {
			addSequential(new TurnUntilTargetDetected(1));
			addParallel(new UseLinearSlides(1), 4);
			addSequential(new MoveTowardTarget(1), 5);
			addSequential(new UseIntake(1), 2);
			
		} else {
			addSequential(new TurnUntilTargetDetected(-1));
			addParallel(new UseLinearSlides(1), 4);
			addSequential(new MoveTowardTarget(1), 5);
			addSequential(new UseIntake(1), 2);
		}
	}
}
