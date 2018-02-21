package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.commands.UseIntake;
import org.usfirst.frc.team321.robot.commands.auto.LinearSlidesWithEncoders;
import org.usfirst.frc.team321.robot.commands.auto.MoveWithEncoder;
import org.usfirst.frc.team321.robot.commands.auto.MoveWithNaveedX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCodeLeftSwitchOrScale extends CommandGroup{
	
	public final double DISTANCE_TO_AUTO_LINE = 120;
	public final double AUTO_LINE_TO_MIDDLE_OF_SWITCH = 48;
	public final double APPROACH_SWITCH = 50;
	public final double TOP_OF_LINEAR_SLIDE = 96;
	public final double AUTO_LINE_TO_MIDDLE_OF_SCALE = 204;
	String gameData = DriverStation.getInstance().getGameSpecificMessage();
	
	public AutoCodeLeftSwitchOrScale(){
		addSequential(new MoveWithEncoder(DISTANCE_TO_AUTO_LINE));
		if (gameData.charAt(0) == 'L') {
			addSequential(new MoveWithEncoder(AUTO_LINE_TO_MIDDLE_OF_SWITCH));
			addSequential(new MoveWithNaveedX(0.8, 90));
			addSequential(new MoveWithEncoder(APPROACH_SWITCH));
			//addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakePivot, DoubleSolenoid.Value.kReverse));
			addSequential(new UseIntake(-1), 3);
		} else if ((gameData.charAt(1) == 'L') && (gameData.charAt(0) == 'L')) {
			addSequential(new MoveWithEncoder(AUTO_LINE_TO_MIDDLE_OF_SWITCH));
			addParallel(new LinearSlidesWithEncoders(TOP_OF_LINEAR_SLIDE));
			addSequential(new MoveWithEncoder(AUTO_LINE_TO_MIDDLE_OF_SCALE));
			addSequential(new MoveWithNaveedX(0.8, 90));
			addSequential(new MoveWithEncoder(APPROACH_SWITCH));
			//addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakePivot, DoubleSolenoid.Value.kReverse));
			addSequential(new UseIntake(-1), 3);
			addSequential(new MoveWithEncoder(-APPROACH_SWITCH));
			//addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakePivot, DoubleSolenoid.Value.kForward));
			addSequential(new LinearSlidesWithEncoders(-TOP_OF_LINEAR_SLIDE));
		}
	}
}
