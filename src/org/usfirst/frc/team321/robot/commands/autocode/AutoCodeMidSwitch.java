package org.usfirst.frc.team321.robot.commands.autocode;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.commands.DSolenoidToggle;
import org.usfirst.frc.team321.robot.commands.UseIntake;
import org.usfirst.frc.team321.robot.commands.auto.MoveWithEncoder;
import org.usfirst.frc.team321.robot.commands.auto.MoveWithNaveedX;
import org.usfirst.frc.team321.robot.subsystems.IntakePivot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCodeMidSwitch extends CommandGroup {
	
	public final double MOVE_TO_SWITCH = 50;
	public final double MOVE_UNTIL_CAM_DETECTS = 0;
	public final double MOVE_WITH_VISION = 0;
	public final double APPROACH_SWITCH = 15;
	
	String gameData = DriverStation.getInstance().getGameSpecificMessage();

	public AutoCodeMidSwitch(){
		
		if (gameData.charAt(0) == 'L') {
			addSequential(new MoveWithNaveedX(0.8, -45));
			addSequential(new MoveWithEncoder(MOVE_TO_SWITCH)); 
			//addSequential(new MOVE UNTIL CAM DETECTS());
			//addSequential(new MOVE WITH VISION());
			addSequential(new MoveWithEncoder(APPROACH_SWITCH));
			addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakePivot, DoubleSolenoid.Value.kReverse));
			addSequential(new UseIntake(-1));
		} else if ((gameData.charAt(1) == 'R')) {
			addSequential(new MoveWithNaveedX(0.8, 45));
			addSequential(new MoveWithEncoder(MOVE_TO_SWITCH)); 
			//addSequential(new MOVE UNTIL CAM DETECTS());
			//addSequential(new MOVE WITH VISION());
			addSequential(new MoveWithEncoder(APPROACH_SWITCH));
			addSequential(new DSolenoidToggle(Robot.intakepivot, IntakePivot.intakePivot, DoubleSolenoid.Value.kReverse));
			addSequential(new UseIntake(-1));
		}
	}
}
