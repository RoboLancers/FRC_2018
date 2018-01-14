package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.RobotMap;
import org.usfirst.frc.team321.robot.commands.UseDriveTrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
//naming motor controllers + encoders
public class DriveTrain extends Subsystem {
	TalonSRX topLeft, topRight, midLeft, midRight, bottomRight,bottomLeft;
	Encoder topLeftA, topRightA, bottomLeftA, bottomRightA;
	
// creating variables for encoder
	public static final double RADIUS = 2;
	public static final double CIRCUMFERENCE = 2 * Math.PI * RADIUS;
	public static final double TPR = 256;
	public static final double kDistancePerPulse = CIRCUMFERENCE / TPR;
	// setting up drivetrain, and encoder, and allows it to encode
	public DriveTrain() {
		bottomLeft= new TalonSRX(RobotMap.BOT_LEFT_MOTOR);
		topLeft = new TalonSRX(RobotMap.TOP_LEFT_MOTOR);
		bottomRight = new TalonSRX(RobotMap.BOT_RIGHT_MOTOR);
		topRight = new TalonSRX(RobotMap.TOP_RIGHT_MOTOR);
		midRight = new TalonSRX(RobotMap.MID_RIGHT_MOTOR);
		midLeft = new TalonSRX(RobotMap.MID_LEFT_MOTOR);
		
		topLeftA = new Encoder(0, 1, true, EncodingType.k4X);
		topRightA = new Encoder(2, 3, true, EncodingType.k4X); 
		bottomLeftA = new Encoder(4, 5, true, EncodingType.k4X);
		bottomRightA = new Encoder(6, 7, true, EncodingType.k4X);
	
		topLeftA.setDistancePerPulse(kDistancePerPulse);
		topRightA.setDistancePerPulse(kDistancePerPulse);
		bottomLeftA.setDistancePerPulse(kDistancePerPulse);
		bottomRightA.setDistancePerPulse(kDistancePerPulse);
	
	}
//start left motors
	public void setLeft(double power) {
		topLeft.set(ControlMode.PercentOutput, power);
		midLeft.set(ControlMode.PercentOutput, power);
		bottomLeft.set(ControlMode.PercentOutput, power);
		
	}
	//pretty obvious
	public void setRight(double power) {
		topRight.set(ControlMode.PercentOutput, -power);
		midRight.set(ControlMode.PercentOutput, -power);
		bottomRight.set(ControlMode.PercentOutput, -power);
	}
	//set both
	public void setAll(double power) {
		setLeft(power);
		setRight(power);
	}
	//			VVVVVVVVV
	public void stopMotors() {
		setAll(0);
	}
	//method to get amount of inches traveled per revolution of wheel (by encoder)
	public double inchesPerRev() {
		return (TPR / CIRCUMFERENCE);
	}
	//gets Total revolutions required to travel to your target distance
	public double getRevolutions(double targetDistance){
		return (targetDistance / Robot.drivetrain.inchesPerRev()); 
	}
	//					VVVVVVVVVVVVVVVVVVV
	public double getTopLeftEncoderDistance(){
		return topLeftA.getDistance();
	}
	public double getTopRightEncoderDistance(){
		return topRightA.getDistance();
	}
	public double getBottomLeftEncoderDistance(){
		return bottomLeftA.getDistance();
	}
	public double getBottomRightEncoderDistance(){
		return bottomRightA.getDistance();
	}
	//				VVVVVVVVVVVVVVVVVVVV
	public int getRawTopLeftEncoderCount(){
		return topLeftA.get();	
	}
	public int getRawTopRightEncoderCount(){
		return topRightA.get();
	}
	public int getRawBottomLeftEncoderCount(){
		return bottomLeftA.get();
	}
	public int getRawBottomRightEncoderCount(){
		return bottomRightA.get();
	}
	//needed
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new UseDriveTrain());

		// TODO Auto-generated method stub
		
	}

}
