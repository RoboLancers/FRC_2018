package org.usfirst.frc.team321.robot.subsystems.manipulator;

import org.usfirst.frc.team321.robot.Constants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakePivot extends Subsystem{

	public static DoubleSolenoid intakePivot;
	
	public IntakePivot(){
		intakePivot = new DoubleSolenoid(Constants.PIVOT_FORWARD, Constants.PIVOT_REVERSE);
		intakePivot.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setUp() {
		intakePivot.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setDown() {
		intakePivot.set(DoubleSolenoid.Value.kForward);
	}
	
	public boolean isUp() {
		return intakePivot.get() == DoubleSolenoid.Value.kReverse;
	}

	@Override
	protected void initDefaultCommand() {
		
	}
}