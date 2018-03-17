package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.commands.DSolenoidToggle;
import org.usfirst.frc.team321.robot.commands.UseLinearSlides;
import org.usfirst.frc.team321.robot.commands.auto.MoveTowardTarget;
import org.usfirst.frc.team321.robot.commands.auto.MoveWithEncoder;
import org.usfirst.frc.team321.robot.commands.auto.MoveInAngle;
import org.usfirst.frc.team321.robot.commands.auto.TurnUntilTargetDetected;
import org.usfirst.frc.team321.robot.commands.auto.UseIntake;
import org.usfirst.frc.team321.robot.subsystems.IntakePivot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoSwitch extends CommandGroup {
	
	public AutoSwitch() {

		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		addSequential(new MoveWithEncoder(1));
		
		if (gameData.charAt(0) == 'R') {
			addSequential(new MoveInAngle(0.7, 18, 1.3));
			addSequential(new TurnUntilTargetDetected(1));
		} else {
			addSequential(new MoveInAngle(0.7, -18, 1.3));
			addSequential(new TurnUntilTargetDetected(-1));
		}
		
		addParallel(new UseLinearSlides(1), 4);
		addSequential(new MoveTowardTarget(0.75), 5);
		addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakepivot, DoubleSolenoid.Value.kForward));
		addSequential(new UseIntake(1), 2);
	}
}
