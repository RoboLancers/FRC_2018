package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.RobotMap;
import org.usfirst.frc.team321.robot.commands.UseDriveTrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	TalonSRX topLeft, topRight, bottomRight,bottomLeft;
	
	public DriveTrain() {
		
		bottomLeft= new TalonSRX(RobotMap.BOT_LEFT_MOTOR);
		topLeft = new TalonSRX(RobotMap.TOP_LEFT_MOTOR);
		bottomRight = new TalonSRX(RobotMap.BOT_RIGHT_MOTOR);
		topRight = new TalonSRX(RobotMap.TOP_RIGHT_MOTOR);
		
	}

	public void setLeft(double power) {
		topLeft.set(ControlMode.PercentOutput, power);
		bottomLeft.set(ControlMode.PercentOutput, power);
	}
	
	public void setRight(double power) {
		topRight.set(ControlMode.PercentOutput, -power);
		bottomRight.set(ControlMode.PercentOutput, -power);
	}
	
	public void setAll(double power) {
		setLeft(power);
		setRight(power);
	}
	
	public void stopMotors() {
		setAll(0);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new UseDriveTrain());

		// TODO Auto-generated method stub
		
	}

}
