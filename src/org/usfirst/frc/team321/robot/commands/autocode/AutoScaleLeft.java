package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.commands.DSolenoidToggle;
import org.usfirst.frc.team321.robot.commands.UseIntake;
import org.usfirst.frc.team321.robot.commands.UseLinearSlides;
import org.usfirst.frc.team321.robot.commands.auto.MoveWithEncoder;
import org.usfirst.frc.team321.robot.subsystems.IntakePivot;
import org.usfirst.frc.team321.robot.commands.auto.MoveInAngle;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoScaleLeft extends CommandGroup {

	public AutoScaleLeft() {
		
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if (gameData.charAt(0) == 'L') {
			addParallel(new UseLinearSlides(1), 1.5);
			addSequential(new MoveWithEncoder(6.1));
			addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakepivot, DoubleSolenoid.Value.kForward));
			addSequential(new UseIntake(1), 2);
		} else {
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
