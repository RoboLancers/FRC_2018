package org.usfirst.frc.team321.robot.subsystems.drivetrain;

import org.usfirst.frc.team321.robot.Constants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearShifter extends Subsystem {
	
	//allows for change of gears
	public static DoubleSolenoid gearShifter;
	
	public GearShifter(){
		gearShifter = new DoubleSolenoid(Constants.GEARSHIFTER_FORWARD, Constants.GEARSHIFTER_REVERSE);
		gearShifter.set(DoubleSolenoid.Value.kForward);
	}
	
	public void setHighGear() {
		gearShifter.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setLowGear() {
		gearShifter.set(DoubleSolenoid.Value.kForward);
	}

	@Override
	protected void initDefaultCommand() {
		
	}
}