package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakePivot extends Subsystem {
	
	public static DoubleSolenoid intakePivot, intakePivot2; 
	
	public IntakePivot(){
		intakePivot = new DoubleSolenoid(RobotMap.INTAKE_FORWARD,RobotMap.INTAKE_REVERSE);

		intakePivot.set(DoubleSolenoid.Value.kForward);
	}
	
	public void setIntakeUp() {
		intakePivot.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setIntakeDown() {
		intakePivot.set(DoubleSolenoid.Value.kForward);
	}
	
	public boolean isCubeIntaking() {
		return (intakePivot.get() == DoubleSolenoid.Value.kForward ? true : false);
	}
	
	@Override
	public void initDefaultCommand() {
	
	}
}
