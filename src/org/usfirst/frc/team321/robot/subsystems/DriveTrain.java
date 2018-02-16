package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.RobotMap;
import org.usfirst.frc.team321.robot.commands.UseArcadeDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	
	public TalonSRX leftMaster, rightMaster, midLeftSlave, 
		midRightSlave, botRightSlave, botLeftSlave;

	//top left, right motors are backwards
	//midleftslave is backwards
	//botrightslave is backwards
	
	public static final double RADIUS = 3.775;
	public static final double CIRCUMFERENCE = 2 * Math.PI * RADIUS;
	public static final double TPR = 256 * 4;
	public static final double kDistancePerPulse = CIRCUMFERENCE / TPR;
	
	public double maxMotorPower = 1;
	
	// setting up drivetrain, and encoder, and allows it to encode
	public DriveTrain() {
		leftMaster = new TalonSRX(RobotMap.LEFT_MASTER_MOTOR);
		midLeftSlave = new TalonSRX(RobotMap.LEFT_SLAVE_A);
		botLeftSlave = new TalonSRX(RobotMap.LEFT_SLAVE_B);
		
		rightMaster = new TalonSRX(RobotMap.RIGHT_MASTER_MOTOR);
		midRightSlave = new TalonSRX(RobotMap.RIGHT_SLAVE_A);
		botRightSlave = new TalonSRX(RobotMap.RIGHT_SLAVE_B);
		
		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		
		/*
		midLeftSlave.set(ControlMode.Follower, leftMaster.getDeviceID());
		botLeftSlave.set(ControlMode.Follower, leftMaster.getDeviceID());
		midRightSlave.set(ControlMode.Follower, rightMaster.getDeviceID());
		botRightSlave.set(ControlMode.Follower, rightMaster.getDeviceID());
		*/

		this.setBrake();
	}
	
	public void setCoast(){
		botLeftSlave.setNeutralMode(NeutralMode.Coast);
		botRightSlave.setNeutralMode(NeutralMode.Coast);
		midRightSlave.setNeutralMode(NeutralMode.Coast);
		midLeftSlave.setNeutralMode(NeutralMode.Coast);
		leftMaster.setNeutralMode(NeutralMode.Coast);
		rightMaster.setNeutralMode(NeutralMode.Coast);
	}
	
	public void setBrake(){
		botLeftSlave.setNeutralMode(NeutralMode.Brake);
		botRightSlave.setNeutralMode(NeutralMode.Brake);
		midRightSlave.setNeutralMode(NeutralMode.Brake);
		midLeftSlave.setNeutralMode(NeutralMode.Brake);
		leftMaster.setNeutralMode(NeutralMode.Brake);
		rightMaster.setNeutralMode(NeutralMode.Brake);
	}
	
	public void setMotorModes(NeutralMode mode) {
		botLeftSlave.setNeutralMode(mode);
		botRightSlave.setNeutralMode(mode);
		midRightSlave.setNeutralMode(mode);
		midLeftSlave.setNeutralMode(mode);
		leftMaster.setNeutralMode(mode);
		rightMaster.setNeutralMode(mode);
	}
	
	public void setLeft(double power) {
		power *= maxMotorPower;
		leftMaster.set(ControlMode.PercentOutput, power);
		midLeftSlave.set(ControlMode.PercentOutput, power);
		botLeftSlave.set(ControlMode.PercentOutput, -power);
	}
	
	public void setRight(double power) {
		power *= maxMotorPower;
		rightMaster.set(ControlMode.PercentOutput, -power);
		midRightSlave.set(ControlMode.PercentOutput, power);
		botRightSlave.set(ControlMode.PercentOutput, -power);
	}
	
	public void setAll(double power) {
		setLeft(power);
		setRight(power);
	}
	
	public void stopMotors() {
		setAll(0);
	}
	
	public void setMaxMotorSpeed(double power) {
		maxMotorPower = Math.abs(power);
	}
	
	//Gets amount of inches traveled per revolution of wheel (by encoder)
	public double getTicksPerInch() {
		return (TPR / CIRCUMFERENCE);
	}
	
	//returns total ticks required to travel to your target distance
	public double targetTicks(double targetDistance){
		return (targetDistance * getTicksPerInch()); 
	}
	
	public double getLeftEncoderDistance(){
		return (CIRCUMFERENCE / TPR) * Robot.drivetrain.getRawLeftEncoderCount();
	}
	
	public double getRightEncoderDistance(){
		return (CIRCUMFERENCE / TPR) * Robot.drivetrain.getRawRightEncoderCount();
	}
	
	public int getRawLeftEncoderCount(){
		return leftMaster.getSelectedSensorPosition(0);	
	}
	
	public int getRawRightEncoderCount(){
		return rightMaster.getSelectedSensorPosition(0);
	}
	
	public void resetEncoder(){
		leftMaster.setSelectedSensorPosition(0, 0, 0);
		rightMaster.setSelectedSensorPosition(0, 0, 0);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new UseArcadeDrive());
	}
}
