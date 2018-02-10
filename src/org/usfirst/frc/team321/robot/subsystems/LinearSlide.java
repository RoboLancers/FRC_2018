package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
								
public class LinearSlide extends Subsystem {
	
	public TalonSRX masterLine, slaveLine;
	public Encoder linearEncoder;

	double targetDistance;
	
	public static final double RADIUS = 3.75;
	public static final double CIRCUMFERENCE = 2 * Math.PI * RADIUS;
	public static final double TPR = 256;
	public static final double kDistancePerPulse = CIRCUMFERENCE / TPR;

	public LinearSlide() {
		masterLine = new TalonSRX(RobotMap.LINE_A);
		slaveLine = new TalonSRX(RobotMap.LINE_B);
		
		slaveLine.set(ControlMode.Follower, masterLine.getDeviceID());
		
		linearEncoder = new Encoder(RobotMap.LINE_ENCODER_A, RobotMap.LINE_ENCODER_B, true, EncodingType.k4X);
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
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
}
