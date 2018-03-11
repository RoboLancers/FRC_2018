package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.Constants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakePivot extends Subsystem{

	//allows for change of gears
	public static DoubleSolenoid intakepivot;
	
	public IntakePivot(){
		intakepivot = new DoubleSolenoid(Constants.PIVOT_FORWARD, Constants.PIVOT_REVERSE);
		intakepivot.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setUp() {
		intakepivot.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setDown() {
		intakepivot.set(DoubleSolenoid.Value.kForward);
	}
	
	public boolean isUp() {
		return intakepivot.get() == DoubleSolenoid.Value.kReverse;
	}

	@Override
	protected void initDefaultCommand() {
		
	}
}