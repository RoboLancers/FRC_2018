package org.usfirst.frc.team321.robot.commands.autonomous.modes;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.commands.DSolenoidToggle;
import org.usfirst.frc.team321.robot.commands.UseIntake;
import org.usfirst.frc.team321.robot.commands.UseLinearSlides;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveInAngle;
import org.usfirst.frc.team321.robot.subsystems.manipulator.IntakePivot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoMiddle extends CommandGroup {
	public AutoMiddle() {
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		addSequential(new MoveInAngle(0.7, 0, 1));
		
		if (gameData.charAt(0) == 'R') {
			addSequential(new MoveInAngle(0.7, 18, 1));
			addParallel(new UseLinearSlides(1), 2);
			addSequential(new MoveInAngle(0.7, -18), 3);
		} else {
			addSequential(new MoveInAngle(0.7, -18, 1));
			addParallel(new UseLinearSlides(1), 2);
			addSequential(new MoveInAngle(0.7, 18), 3);
		}
		
		addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakepivot, DoubleSolenoid.Value.kForward));
		addSequential(new UseIntake(1), 2);
	}
}
