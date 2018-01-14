package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LinearSlide extends Subsystem {
	
	TalonSRX lineLeft, lineRight;
	Encoder lineLeftA, lineRightA;
	
	//public static final double GEARRATIO = 
	
	
	public LinearSlide() { 
		lineLeft = new TalonSRX(RobotMap.LINE_LEFT);
		lineRight = new TalonSRX(RobotMap.LINE_RIGHT);
		
		lineLeftA = new Encoder(8, 9, true, EncodingType.k4X);
		lineRightA = new Encoder(10, 11, true, EncodingType.k4X);
	}
	

	public void up(double power) {
		lineLeft.set(ControlMode.PercentOutput, 1);
		lineRight.set(ControlMode.PercentOutput, -1);
	}
	
	public void down(double power) {
		lineLeft.set(ControlMode.PercentOutput, -1);
		lineRight.set(ControlMode.PercentOutput, 1);
	}
	
	public void stop() {
		lineLeft.set(ControlMode.PercentOutput, 0);
		lineRight.set(ControlMode.PercentOutput, 0);
	}
	
	public double getLineLeftEncoderDistance(){
		return lineLeftA.getDistance();
	}
	public double getLineRightEncoderDistance(){
		return lineRightA.getDistance();
	}

	public int getRawLineLeftEncoderCount(){
		return lineLeftA.get();	
	}
	public int getRawLineRightEncoderCount(){
		return lineRightA.get();
	}
	
//	public double inchesPerSec() {
//		return 
//	}
	
//	public double targetLSD() {
		
//	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
