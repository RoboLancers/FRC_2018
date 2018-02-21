package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.commands.UseIntake;
import org.usfirst.frc.team321.robot.commands.auto.LinearSlidesWithEncoders;
import org.usfirst.frc.team321.robot.commands.auto.MoveWithEncoder;
import org.usfirst.frc.team321.robot.commands.auto.MoveWithNaveedX;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCodeRightScale extends CommandGroup{

	public final double DISTANCE_TO_AUTO_LINE = 120;
	public final double AUTO_LINE_TO_MIDDLE_OF_SCALE = 204;
	public final double APPROACH_THE_SCALE = 50;
	public final double TOP_OF_LINEAR_SLIDE = 96;
	
	public AutoCodeRightScale() {
		addSequential(new MoveWithEncoder(DISTANCE_TO_AUTO_LINE));
		addParallel(new LinearSlidesWithEncoders(TOP_OF_LINEAR_SLIDE));
		addSequential(new MoveWithEncoder(AUTO_LINE_TO_MIDDLE_OF_SCALE));
		addSequential(new MoveWithNaveedX(0.8, -90));
		addSequential(new MoveWithEncoder(APPROACH_THE_SCALE));
		//addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakePivot, DoubleSolenoid.Value.kReverse));
		addSequential(new UseIntake(-1), 3);
		addSequential(new MoveWithEncoder(-APPROACH_THE_SCALE));
		//addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakePivot, DoubleSolenoid.Value.kForward));
		addSequential(new LinearSlidesWithEncoders(-TOP_OF_LINEAR_SLIDE));
	}
}