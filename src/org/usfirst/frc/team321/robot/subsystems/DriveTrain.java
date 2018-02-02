package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.RobotMap;
import org.usfirst.frc.team321.robot.commands.UseArcadeDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

//naming motor controllers + encoders
public class DriveTrain extends Subsystem {
	public TalonSRX leftMaster, rightMaster, midLeftSlave, midRightSlave, botRightSlave, botLeftSlave;
	Encoder leftEncoder, rightEncoder;
	
// creating variables for encoder
	public static final double RADIUS = 3.75;
	public static final double CIRCUMFERENCE = 2 * Math.PI * RADIUS;
	public static final double TPR = 256;
	public static final double kDistancePerPulse = CIRCUMFERENCE / TPR;
	
	// setting up drivetrain, and encoder, and allows it to encode
	public DriveTrain() {
		botLeftSlave= new TalonSRX(RobotMap.BOT_LEFT_MOTOR);
		leftMaster = new TalonSRX(RobotMap.TOP_LEFT_MOTOR);
		botRightSlave = new TalonSRX(RobotMap.BOT_RIGHT_MOTOR);
		rightMaster = new TalonSRX(RobotMap.TOP_RIGHT_MOTOR);
		midRightSlave = new TalonSRX(RobotMap.MID_RIGHT_MOTOR);
		midLeftSlave = new TalonSRX(RobotMap.MID_LEFT_MOTOR);
		
		midLeftSlave.set(ControlMode.Follower, leftMaster.getDeviceID());
		botLeftSlave.set(ControlMode.Follower, leftMaster.getDeviceID());
		midRightSlave.set(ControlMode.Follower, rightMaster.getDeviceID());
		botRightSlave.set(ControlMode.Follower, rightMaster.getDeviceID());
		
		leftEncoder = new Encoder(RobotMap.LEFT_ENCODER_A, RobotMap.LEFT_ENCODER_B);
		rightEncoder = new Encoder(RobotMap.RIGHT_ENCODER_A, RobotMap.RIGHT_ENCODER_B); 
	
		leftEncoder.setDistancePerPulse(kDistancePerPulse);
		rightEncoder.setDistancePerPulse(kDistancePerPulse);
		
		botLeftSlave.setNeutralMode(NeutralMode.Brake);
		botRightSlave.setNeutralMode(NeutralMode.Brake);
		midRightSlave.setNeutralMode(NeutralMode.Brake);
		midLeftSlave.setNeutralMode(NeutralMode.Brake);
		leftMaster.setNeutralMode(NeutralMode.Brake);
		rightMaster.setNeutralMode(NeutralMode.Brake);
	}
	
	//start left motors
	public void setLeft(double power) {
		leftMaster.set(ControlMode.PercentOutput, -power);
	}
	
	//start right motors
	public void setRight(double power) {
		rightMaster.set(ControlMode.PercentOutput, power);
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
	public double getTicksPerInch() {
		return (TPR / CIRCUMFERENCE);
	}
	
	//gets Total ticks required to travel to your target distance
	public double targetTicks(double targetDistance){
		return (targetDistance * getTicksPerInch()); 
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
	
	public void resetEncoder(){
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	//needed
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new UseArcadeDrive());
	}
}
