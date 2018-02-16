package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.commands.DSolenoidToggle;
import org.usfirst.frc.team321.robot.commands.UseIntake;
import org.usfirst.frc.team321.robot.commands.auto.MoveWithEncoder;
import org.usfirst.frc.team321.robot.commands.auto.MoveWithNaveedX;
import org.usfirst.frc.team321.robot.subsystems.IntakePivot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCodeRightSwitch extends CommandGroup {
	
	public final double DISTANCE_TO_AUTO_LINE = 120;
	public final double AUTO_LINE_TO_MIDDLE_OF_SWITCH = 48;
	public final double APPROACH_THE_SWITCH = 50;
	
	public AutoCodeRightSwitch() {
		addSequential(new MoveWithEncoder(DISTANCE_TO_AUTO_LINE));
		addSequential(new MoveWithEncoder(AUTO_LINE_TO_MIDDLE_OF_SWITCH));
		addSequential(new MoveWithNaveedX(0.8, -90));
		addSequential(new MoveWithEncoder(APPROACH_THE_SWITCH));
		//addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakePivot, DoubleSolenoid.Value.kReverse));
		addSequential(new UseIntake(-1), 3);
	}
}
