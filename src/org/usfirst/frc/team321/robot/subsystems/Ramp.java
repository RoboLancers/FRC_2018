package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Ramp extends Subsystem {

	public TalonSRX leftRamp, rightRamp;
	 
	public Ramp() {
		leftRamp = new TalonSRX(RobotMap.LEFT_RAMP);
		rightRamp = new TalonSRX(RobotMap.RIGHT_RAMP);
	}
	
	public void setUp(double power) {
		leftRamp.set(ControlMode.PercentOutput, power);
		rightRamp.set(ControlMode.PercentOutput, -power);
	}
	
	public void setDown(double power){
		leftRamp.set(ControlMode.PercentOutput, -power);
		rightRamp.set(ControlMode.PercentOutput, power);
	}
	
	public void stopAll(){
		leftRamp.set(ControlMode.PercentOutput, 0);
		rightRamp.set(ControlMode.PercentOutput, 0);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
