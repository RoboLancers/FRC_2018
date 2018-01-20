package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.RobotMap;
import org.usfirst.frc.team321.robot.commands.UseArcadeDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

//naming motor controllers + encoders
public class DriveTrain extends Subsystem {
	public TalonSRX topLeft, topRight, midLeft, midRight, bottomRight,bottomLeft;
	Encoder leftEncoder, rightEncoder;
	
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
		
		leftEncoder = new Encoder(RobotMap.LEFT_ENCODER_A, RobotMap.LEFT_ENCODER_B, true, EncodingType.k4X);
		rightEncoder = new Encoder(RobotMap.RIGHT_ENCODER_A, RobotMap.RIGHT_ENCODER_B, true, EncodingType.k4X); 
	
		leftEncoder.setDistancePerPulse(kDistancePerPulse);
		rightEncoder.setDistancePerPulse(kDistancePerPulse);

	}
	
	//start left motors
	public void setLeft(double power) {
		topLeft.set(ControlMode.PercentOutput, -power);
		midLeft.set(ControlMode.PercentOutput, -power);
		bottomLeft.set(ControlMode.PercentOutput, -power);
	}
	
	//start right motors
	public void setRight(double power) {
		topRight.set(ControlMode.PercentOutput, power);
		midRight.set(ControlMode.PercentOutput, power);
		bottomRight.set(ControlMode.PercentOutput, power);
	}
	
	//set both
	public void setAll(double power) {
		setLeft(power);
		setRight(power);
	}
	
	//stop motors
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
	
	//get left and right encoder distance
	public double getLeftEncoderDistance(){
		return leftEncoder.getDistance();
	}
	
	public double getRightEncoderDistance(){
		return rightEncoder.getDistance();
	}
	
	//get RAW left and right encoder tick count
	public int getRawLeftEncoderCount(){
		return leftEncoder.get();	
	}
	
	public int getRawRightEncoderCount(){
		return rightEncoder.get();
	}
	
	//needed
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new UseArcadeDrive());
	}
}
