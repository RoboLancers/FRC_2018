package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakePivot extends Subsystem {
	//setting up intake flappers
	public static DoubleSolenoid intakePivot, intakePivot2; 
	//flappers gotta flap up and down
	public IntakePivot(){
		intakePivot = new DoubleSolenoid(RobotMap.INTAKE_FORWARD,RobotMap.INTAKE_REVERSE);

		intakePivot.set(DoubleSolenoid.Value.kForward);
	}
	//sometimes flappers go backwards
	public void setIntakeUp() {
		intakePivot.set(DoubleSolenoid.Value.kReverse);
	}
	//sometimes flappers go forwards
	public void setIntakeDown() {
		intakePivot.set(DoubleSolenoid.Value.kForward);
	}
	
	//glorofoed if stootment; if true return first value, if not, return 2nd value
	public boolean isCubeIntaking() {
		return (intakePivot.get() == DoubleSolenoid.Value.kForward ? true : false);
		
	}
	
	@Override
	public void initDefaultCommand() {
	
	}
}
