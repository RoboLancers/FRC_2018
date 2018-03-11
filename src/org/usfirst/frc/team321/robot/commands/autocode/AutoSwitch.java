package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.commands.DSolenoidToggle;
import org.usfirst.frc.team321.robot.commands.UseIntake.IntakePower;
import org.usfirst.frc.team321.robot.commands.UseLinearSlides;
import org.usfirst.frc.team321.robot.commands.auto.MoveRobot;
import org.usfirst.frc.team321.robot.commands.auto.UseIntake;
import org.usfirst.frc.team321.robot.subsystems.IntakePivot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoSwitch extends CommandGroup {
	
	public AutoSwitch() {

		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if (gameData.charAt(0) == 'R') {
			addParallel(new UseLinearSlides(0.8), 2);
			addSequential(new MoveRobot(0.8, 0.8), 2);
			addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakepivot, DoubleSolenoid.Value.kForward));
			addSequential(new UseIntake(IntakePower.OUTTAKE.power), 1);
		} else {
			addSequential(new MoveRobot(0.8, 0.8), 1.5);
		}
	}
}
