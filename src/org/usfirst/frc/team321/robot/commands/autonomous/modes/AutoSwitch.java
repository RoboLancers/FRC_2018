package org.usfirst.frc.team321.robot.commands.autonomous.modes;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveRobot;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveTowardTarget;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.TurnUntilTargetDetected;
import org.usfirst.frc.team321.robot.commands.subsystems.manipulator.UseIntake;
import org.usfirst.frc.team321.robot.commands.subsystems.manipulator.UseLinearSlides;
import org.usfirst.frc.team321.robot.commands.subsystems.misc.DSolenoidToggle;
import org.usfirst.frc.team321.robot.subsystems.manipulator.IntakePivot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoSwitch extends CommandGroup {
	
	public AutoSwitch(boolean useCamera) {

		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if(useCamera) {
			addSequential(new MoveRobot(0.75, 0), 1);
			
			if (gameData.charAt(0) == 'R') {
				addSequential(new MoveRobot(0.7, 18, 1.3));
				addSequential(new TurnUntilTargetDetected(0.75));
			} else {
				addSequential(new MoveRobot(0.7, -18, 1.3));
				addSequential(new TurnUntilTargetDetected(-0.75));
			}
			
			addParallel(new UseLinearSlides(1), 2);
			addSequential(new MoveTowardTarget(0.75), 5);
			addSequential(new DSolenoidToggle(Robot.manipulator.getIntakePivot(), IntakePivot.intakePivot, DoubleSolenoid.Value.kForward));
			addSequential(new UseIntake(1), 2);
		}else {
			
			addSequential(new MoveRobot(0.7, 0, 1));

			if (gameData.charAt(0) == 'R') {
				addSequential(new MoveRobot(0.7, 18, 1));
				addParallel(new UseLinearSlides(1), 2);
				addSequential(new MoveRobot(0.7, -18), 3);
			} else {
				addSequential(new MoveRobot(0.7, -18, 1));
				addParallel(new UseLinearSlides(1), 2);
				addSequential(new MoveRobot(0.7, 18), 3);
			}

			addSequential(new DSolenoidToggle(Robot.manipulator.getIntakePivot(), IntakePivot.intakePivot, DoubleSolenoid.Value.kForward));
			addSequential(new UseIntake(1), 2);
		}
	}
}
