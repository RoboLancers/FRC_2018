package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {
	
	TalonSRX intakeLeft, intakeRight;
	
	public Intake() {
		intakeLeft = new TalonSRX(RobotMap.INTAKE_LEFT);
		intakeRight = new TalonSRX(RobotMap.INTAKE_RIGHT);
	}
	
	public void setLeft(double power) {
		intakeLeft.set(ControlMode.PercentOutput,power);
	}
	
	public void setRight(double power) {
		intakeRight.set(ControlMode.PercentOutput, -power);
	}
	public void setAll(double power) {
		setLeft(power);
		setRight(power);
	}
	
	public void stopIntake() {
		intakeLeft.set(ControlMode.PercentOutput, 0);
		intakeRight.set(ControlMode.PercentOutput, 0);
	}
	
	@Override
	protected void initDefaultCommand() {
	}
}
