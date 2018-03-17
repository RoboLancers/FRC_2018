package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.Constants;
import org.usfirst.frc.team321.robot.commands.UseLinearSlides;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
								
public class LinearSlide extends Subsystem {
	
	public TalonSRX masterLine, slaveLine;
	
	public static final double RADIUS = 3.75;
	public static final double CIRCUMFERENCE = 2 * Math.PI * RADIUS;
	public static final double TPR = 4096;
	public static final double kDistancePerPulse = CIRCUMFERENCE / TPR;

	public LinearSlide() {
		masterLine = new TalonSRX(Constants.LINE_A);
		slaveLine = new TalonSRX(Constants.LINE_B);
		
		masterLine.setNeutralMode(NeutralMode.Brake);
		slaveLine.setNeutralMode(NeutralMode.Brake);
		
		slaveLine.set(ControlMode.Follower, masterLine.getDeviceID());
		
		masterLine.configOpenloopRamp(0.5, 0);
		
		masterLine.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		masterLine.setSelectedSensorPosition(0, 0, 0);
	}
	
	public void up() {
		masterLine.set(ControlMode.PercentOutput, 1);
	}
	
	public void down() {
		masterLine.set(ControlMode.PercentOutput, -1);
	}
	
	public void stop() {
		masterLine.set(ControlMode.PercentOutput, 0);
	}
	
	public void move(double power) {
		masterLine.set(ControlMode.PercentOutput, power);
	}
	
	/*
	public double getLineEncoderDistance(){
		return linearEncoder.getDistance();
	}

	public int getRawLineEncoderCount(){
		return linearEncoder.get();	
	}
	
	public double getTicksPerInch() {
		return (TPR / CIRCUMFERENCE);
	}
	
	public double ticksPerTargetDistance() {
		return (targetDistance * getTicksPerInch());
	}
	*/
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new UseLinearSlides());
	}
}
