package org.usfirst.frc.team321.robot.commands.autonomous.modes;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveLinearSlide;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveRobot;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveWithEncoder;
import org.usfirst.frc.team321.robot.commands.subsystems.manipulator.UseIntake;
import org.usfirst.frc.team321.robot.commands.subsystems.manipulator.UseLinearSlides;
import org.usfirst.frc.team321.robot.commands.subsystems.misc.DSolenoidToggle;
import org.usfirst.frc.team321.robot.subsystems.manipulator.IntakePivot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoScale extends CommandGroup{
	public AutoScale(boolean useLeft){
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if (useLeft) {
			if (gameData.charAt(1) == 'L') {
				addParallel(new MoveLinearSlide(0.8f), 3);
				addSequential(new MoveRobot(0.8f, 0.0f), 2);
				addSequential(new MoveRobot(0.7, 90), 2);
				//addSequential(new DSolenoidToggle(Robot.manipulator.getIntakePivot(), IntakePivot.intakePivot, DoubleSolenoid.Value.kForward));
				addSequential(new UseIntake(1), 2);
			} else {
				addSequential(new MoveRobot(0.8f, 0.0f), 2);
			}
		} else {
			if (gameData.charAt(1) == 'R') {
				addParallel(new MoveLinearSlide(0.8f), 3);
				addSequential(new MoveRobot(0.8f, 0.0f), 2);
				addSequential(new MoveRobot(0.7, -90), 2);
				//addSequential(new DSolenoidToggle(Robot.manipulator.getIntakePivot(), IntakePivot.intakePivot, DoubleSolenoid.Value.kForward));
				addSequential(new UseIntake(1), 2);
			} else {
				addSequential(new MoveRobot(0.8f, 0.0f), 2);
			}
		}
		
	}
}
