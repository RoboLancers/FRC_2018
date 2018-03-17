package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.commands.DSolenoidToggle;
import org.usfirst.frc.team321.robot.commands.UseIntake;
import org.usfirst.frc.team321.robot.commands.UseLinearSlides;
import org.usfirst.frc.team321.robot.commands.auto.MoveInAngle;
import org.usfirst.frc.team321.robot.commands.auto.MoveWithEncoder;
import org.usfirst.frc.team321.robot.subsystems.IntakePivot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoScaleRight extends CommandGroup {

	public AutoScaleRight() {
		
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if (gameData.charAt(0) == 'R') {
			addParallel(new UseLinearSlides(1), 4);
			addSequential(new MoveWithEncoder(6.1));
			addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakepivot, DoubleSolenoid.Value.kForward));
			addSequential(new UseIntake(1), 2);
		} else {
			addSequential(new MoveWithEncoder(3.44));
			addSequential(new MoveInAngle(0, -90), 3);
			addSequential(new MoveWithEncoder(5.9436));
			addParallel(new UseLinearSlides(0.8));
			addSequential(new MoveInAngle(0, 90), 3);
			addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakepivot, DoubleSolenoid.Value.kForward));
			addSequential(new UseIntake(1), 2);
		}
		
	}
}
