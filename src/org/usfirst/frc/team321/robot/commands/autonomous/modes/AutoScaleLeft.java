package org.usfirst.frc.team321.robot.commands.autonomous.modes;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.commands.DSolenoidToggle;
import org.usfirst.frc.team321.robot.commands.UseIntake;
import org.usfirst.frc.team321.robot.commands.UseLinearSlides;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveInAngle;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveRobot;
import org.usfirst.frc.team321.robot.commands.autonomous.subroutine.MoveWithEncoder;
import org.usfirst.frc.team321.robot.subsystems.manipulator.IntakePivot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoScaleLeft extends CommandGroup{
	public AutoScaleLeft(){
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if (gameData.charAt(1) == 'L') {
			addParallel(new UseLinearSlides(1), 4);
			addSequential(new MoveRobot(0.8f, 0.0f), 2);
			addSequential(new MoveInAngle(0.7, 90), 2);
			addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakepivot, DoubleSolenoid.Value.kForward));
			addSequential(new UseIntake(1), 2);
		} else {
			addSequential(new MoveRobot(0.8f, 0.0f), 2);
			
			addSequential(new MoveWithEncoder(3.44));
			addSequential(new MoveInAngle(0, 90), 2);
			addSequential(new MoveWithEncoder(5.9436));
			addParallel(new UseLinearSlides(0.8));
			addSequential(new MoveInAngle(0, -90), 2);
			addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakepivot, DoubleSolenoid.Value.kForward));
			addSequential(new UseIntake(1), 2);
			
		}
	}
}
