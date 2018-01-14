package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeFlap extends Subsystem {
	
	public static DoubleSolenoid intakeflap;
	
	public IntakeFlap(){
		intakeflap = new DoubleSolenoid(RobotMap.INTAKE_FORWARD,RobotMap.INTAKE_REVERSE);
		intakeflap.set(DoubleSolenoid.Value.kForward);
	}
	
	public void setBallIntake() {
		intakeflap.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setGearIntake() {
		intakeflap.set(DoubleSolenoid.Value.kForward);
	}
	
	public boolean isBallIntaking() {
		return (intakeflap.get() == DoubleSolenoid.Value.kForward ? true : false);
	}
	
	@Override
	public void initDefaultCommand() {
		
	}
}
