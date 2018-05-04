package org.usfirst.frc.team321.robot.commands.subsystems.manipulator;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseIntakeJoystick extends Command {

	double power;
	boolean rumble = false;
	boolean wasIntaking = false;
	
	public UseIntakeJoystick() {
		requires(Robot.manipulator.getIntake());
	}
	
	protected void initialize() {
		Robot.manipulator.getIntake().stop(false);
	}
	
	protected void execute() {
		if (Robot.oi.xboxController.leftBumper.get()) {
			power = 0.87;
		} else if (Robot.oi.xboxController.rightBumper.get()){
			power = -0.7;
		} else {
			power = -Math.abs(Math.abs(Robot.oi.flightController.getRotateAxisValue()) > 0.5 ? Robot.oi.flightController.getRotateAxisValue() : 0) * 0.25;
		}
		
		if (power > 0) {
			wasIntaking = true;
		} else if (power < 0) {
			wasIntaking = false;
		}
		
		if (wasIntaking && power == 0) {
			power = 0.2;
		}
		
		Robot.manipulator.getIntake().setAll(power);
		
		Robot.oi.xboxController.setRumble(power > 0 && !wasIntaking);
	}
	
	protected void interrupted() {
		end();
	}
	
	protected void end() {
		Robot.oi.xboxController.setRumble(false);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
}
