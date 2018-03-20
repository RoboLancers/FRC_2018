package org.usfirst.frc.team321.robot.subsystems.manipulator;

import org.usfirst.frc.team321.robot.Constants;
import org.usfirst.frc.team321.robot.utilities.RobotUtil;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {
	
	private TalonSRX intakeLeft, intakeRight;
	
	public Intake() {
		intakeLeft = new TalonSRX(Constants.INTAKE_LEFT);
		intakeRight = new TalonSRX(Constants.INTAKE_RIGHT);	
		
		intakeLeft.setNeutralMode(NeutralMode.Brake);
		intakeRight.setNeutralMode(NeutralMode.Brake);
	}
	
	public void setLeft(double power) {
		intakeLeft.set(ControlMode.PercentOutput, RobotUtil.range(power, 1));
	}
	
	public void setRight(double power) {
		intakeRight.set(ControlMode.PercentOutput, RobotUtil.range(power, 1));
	}
	
	public void setAll(double power) {
		setLeft(power);
		setRight(power);
	}
	
	public void stop() {
		setAll(0);
	}
	
	@Override
	protected void initDefaultCommand() {
	}
}
