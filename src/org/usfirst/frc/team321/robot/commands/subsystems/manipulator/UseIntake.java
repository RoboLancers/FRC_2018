package org.usfirst.frc.team321.robot.commands.subsystems.manipulator;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseIntake extends Command {

	double power;
	boolean rumble = false;
	boolean useJoystick;
	boolean wasIntaking = false;
	
	public UseIntake(){
		requires(Robot.manipulator.getIntake());
		useJoystick = true;
	}
	
	public UseIntake(double power) {
		requires(Robot.manipulator.getIntake());
		this.power = power;
		
		useJoystick = false;
	}
	
	public UseIntake(double power, boolean rumble) {
		this(power);
		this.rumble = rumble;
	}
	
	public UseIntake(IntakePower intakePower){
		this(intakePower.power);
	}
	
	protected void initialize() {
		Robot.manipulator.getIntake().stop(false);
	}
	
	protected void execute() {
		if(useJoystick && Robot.oi.xboxController.rightBumper.get()){
			power = -0.7;
		}else if(useJoystick){
			power = -Math.abs(Math.abs(Robot.oi.flightController.getRotateAxisValue()) > 0.5 ? Robot.oi.flightController.getRotateAxisValue() : 0) * 0.25;
		}
		
		Robot.manipulator.getIntake().setAll(power);
		
		if (rumble) {
			Robot.oi.xboxController.setRumble(true);
		}
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
	
	public enum IntakePower {
		INTAKE(0.87), OUTAKE(-0.7), STOP(0);
		
		public double power;
		
		IntakePower(double power){
			this.power = power;
		}
	}
}
