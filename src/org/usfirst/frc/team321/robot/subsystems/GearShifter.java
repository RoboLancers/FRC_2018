package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearShifter extends Subsystem {
	
	//allows for change of gears
	public static DoubleSolenoid gearShifter;
	
	public GearShifter(){
		gearShifter = new DoubleSolenoid(RobotMap.GEARSHIFTER_FORWARD, RobotMap.GEARSHIFTER_REVERSE);
		gearShifter.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setHighGear() {
		gearShifter.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setLowGear() {
		gearShifter.set(DoubleSolenoid.Value.kForward);
	}
	
	public boolean isHighGear() {
		return gearShifter.get() == DoubleSolenoid.Value.kReverse;
	}

	@Override
	protected void initDefaultCommand() {
		
	}
}