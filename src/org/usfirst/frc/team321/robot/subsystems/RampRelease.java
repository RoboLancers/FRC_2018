package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RampRelease extends Subsystem {
	
	public static DoubleSolenoid rampRelease;

	public RampRelease(){
		rampRelease = new DoubleSolenoid(RobotMap.RAMP_RELEASE_FORWARD, RobotMap.RAMP_RELEASE_REVERSE);
		rampRelease.set(DoubleSolenoid.Value.kReverse);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
