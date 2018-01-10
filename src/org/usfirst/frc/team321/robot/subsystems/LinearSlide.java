package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class LinearSlide extends Subsystem {
	
	TalonSRX lineLeft, lineRight;

	
	public LinearSlide() { 
		lineLeft = new TalonSRX(RobotMap.LINE_LEFT);
		lineRight = new TalonSRX(RobotMap.LINE_RIGHT);
	}
	

	public void up(double power) {
		lineLeft.set(ControlMode.PercentOutput, 1);
		lineRight.set(ControlMode.PercentOutput, -1);
	}
	public void down(double power) {
		lineLeft.set(ControlMode.PercentOutput, -1);
		lineRight.set(ControlMode.PercentOutput, 1);
		
	}
	public void stop (double power) {
		lineLeft.set(ControlMode.PercentOutput, 0);
		lineRight.set(ControlMode.PercentOutput, 0);
	}

	

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
