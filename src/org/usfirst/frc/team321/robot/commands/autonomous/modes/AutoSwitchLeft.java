package org.usfirst.frc.team321.robot.commands.autonomous.modes;

import org.usfirst.frc.team321.robot.commands.UseIntake;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveInAngle;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveRobot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoSwitchLeft extends CommandGroup {

	public AutoSwitchLeft() {
		
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if (Character.toUpperCase(gameData.charAt(0)) == 'L') {
			//addParallel(new UseLinearSlides(1), 2);
			addSequential(new MoveRobot(0.8f, 0.0f), 2);
			addSequential(new MoveInAngle(0.7, 90), 2);
			//addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakepivot, DoubleSolenoid.Value.kForward));
			addSequential(new UseIntake(1), 2);
		} else {
			addSequential(new MoveRobot(0.8f, 0.0f), 2);
			/*
			addSequential(new MoveWithEncoder(3.44));
			addSequential(new MoveInAngle(0, 90), 2);
			addSequential(new MoveWithEncoder(5.9436));
			addParallel(new UseLinearSlides(0.8));
			addSequential(new MoveInAngle(0, -90), 2);
			addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakepivot, DoubleSolenoid.Value.kForward));
			addSequential(new UseIntake(1), 2);
			*/
		}
		
	}
}
