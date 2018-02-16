package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakePivot extends Subsystem {
	
	public TalonSRX pivot;
	
	private static final int MAX_MOTOR_POSITION = 256;
	private static final int MIN_MOTOR_POSITION = 256;
	
	public IntakePivot(){
		pivot = new TalonSRX(RobotMap.PIVOT_MOTOR);

		pivot.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		pivot.setSelectedSensorPosition(0, 0, 0);
	}
	
	public void setPivotPower(double power) {
		pivot.set(ControlMode.PercentOutput, power);
	}
	
	public void setPivotPower(double power, boolean safe) {
		if (power > 0) {
			if (pivot.getSelectedSensorPosition(0) < MAX_MOTOR_POSITION) {
				pivot.set(ControlMode.PercentOutput, power);
			} else {
				pivot.set(ControlMode.PercentOutput, 0);
			}
		} else if (power < 0) {
			if (pivot.getSelectedSensorPosition(0) > MIN_MOTOR_POSITION) {
				pivot.set(ControlMode.PercentOutput, power);
			} else {
				pivot.set(ControlMode.PercentOutput, 0);
			}
		} else {
			pivot.set(ControlMode.PercentOutput, 0);
		}
	}
	
	@Override
	public void initDefaultCommand() {
	
	}
}

/*
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
*/