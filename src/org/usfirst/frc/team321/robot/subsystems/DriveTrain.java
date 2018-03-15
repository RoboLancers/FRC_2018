package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.Constants;
import org.usfirst.frc.team321.robot.commands.UseArcadeDrive;
import org.usfirst.frc.team321.robot.commands.VelocityControl;
import org.usfirst.frc.team321.robot.utilities.RobotUtil;

import com.ctre.phoenix.motorcontrol.ControlFrame;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	
	public TalonSRX leftMaster, leftSlave1, leftSlave2;
	public TalonSRX rightMaster, rightSlave1, rightSlave2;
	
	public static final double RADIUS = 3.1;
	public static final double CIRCUMFERENCE = 2 * Math.PI * RADIUS;
	public static final double TPR = 4096;
	public static final double kDistancePerPulse = CIRCUMFERENCE / TPR;
	public double maxMotorPower = 1;
	
	public DriveTrain() {
		leftMaster = new TalonSRX(Constants.LEFT_MASTER_MOTOR);
		leftSlave1 = new TalonSRX(Constants.LEFT_SLAVE_A);
		leftSlave2 = new TalonSRX(Constants.LEFT_SLAVE_B);
		
		rightMaster = new TalonSRX(Constants.RIGHT_MASTER_MOTOR);
		rightSlave1 = new TalonSRX(Constants.RIGHT_SLAVE_A);
		rightSlave2 = new TalonSRX(Constants.RIGHT_SLAVE_B);
		
		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightMaster.setSensorPhase(false);
		
		leftSlave1.set(ControlMode.Follower, leftMaster.getDeviceID());
		leftSlave2.set(ControlMode.Follower, leftMaster.getDeviceID());
		
		rightSlave1.set(ControlMode.Follower, rightMaster.getDeviceID());
		rightSlave2.set(ControlMode.Follower, rightMaster.getDeviceID());
		
		leftSlave2.setInverted(true);
		rightMaster.setInverted(true);
		rightSlave2.setInverted(true);
		
		//F gain = ([Percent Output] x 1023) / [Velocity]
		
		rightMaster.config_kF(0, 0, 0);
		rightMaster.config_kP(0, 0.1, 0);
		rightMaster.config_kD(0, 0, 0);
		
		leftMaster.config_kF(0, 0, 0);
		leftMaster.config_kP(0, 0.1, 0);
		leftMaster.config_kD(0, 0, 0);
		
		this.setBrake();
		this.resetEncoder();
	}
	
	public void setLeft(double power) {
		power *= 0.95;
		power = RobotUtil.range(power, -1, 1) * maxMotorPower;
		
		leftMaster.set(ControlMode.PercentOutput, power);
	}
	
	public void setRight(double power) {
		power = RobotUtil.range(power, -1, 1) * maxMotorPower;
		
		rightMaster.set(ControlMode.PercentOutput, power);
	}
	
	public void setMotors(double leftPower, double rightPower) {
		setLeft(leftPower);
		setRight(rightPower);
	}
	
	public void setAll(double power) {
		setLeft(power);
		setRight(power);
	}
	
	public void stopMotors() {
		setAll(0);
	}
	
	public void setMaxMotorSpeed(double power) {
		maxMotorPower = RobotUtil.range(Math.abs(power), 0, 1);
	}
	
	public void setCoast(){
		leftMaster.setNeutralMode(NeutralMode.Coast);
		leftSlave1.setNeutralMode(NeutralMode.Coast);
		leftSlave2.setNeutralMode(NeutralMode.Coast);
		
		rightMaster.setNeutralMode(NeutralMode.Coast);
		rightSlave1.setNeutralMode(NeutralMode.Coast);
		rightSlave2.setNeutralMode(NeutralMode.Coast);
	}
	
	public void setBrake(){
		leftMaster.setNeutralMode(NeutralMode.Brake);
		leftSlave1.setNeutralMode(NeutralMode.Brake);
		leftSlave2.setNeutralMode(NeutralMode.Brake);
		
		rightMaster.setNeutralMode(NeutralMode.Brake);
		rightSlave2.setNeutralMode(NeutralMode.Brake);
		rightSlave1.setNeutralMode(NeutralMode.Brake);
	}
	
	public void setMotorModes(NeutralMode mode) {	
		rightMaster.setNeutralMode(mode);
		rightSlave1.setNeutralMode(mode);
		rightSlave2.setNeutralMode(mode);
		
		leftMaster.setNeutralMode(mode);
		leftSlave1.setNeutralMode(mode);
		leftSlave2.setNeutralMode(mode);
	}
	
	public void enableRamping (boolean ramp) {
		if (ramp) {
			leftMaster.configOpenloopRamp(0.15, 0);
			rightMaster.configOpenloopRamp(0.15, 0);
		} else {
			leftMaster.configOpenloopRamp(0, 0);
			rightMaster.configOpenloopRamp(0, 0);
		}
	}
	
	//returns total ticks required to travel to your target distance in meters
	public static double getTargetTicks(double targetDistance){
		return (targetDistance * 10 / kDistancePerPulse); 
	}
	
	public double getLeftEncoderDistance(){
		return kDistancePerPulse * Robot.drivetrain.getRawLeftEncoderCount() / 10.0;
	}
	
	public double getRightEncoderDistance(){
		return kDistancePerPulse * Robot.drivetrain.getRawRightEncoderCount() / 10.0;
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
	
	//Returns the error in velocity from left and right (leftVelocity - rightVelocity)
	public double getDriveError() {
		return Math.abs(leftMaster.getSelectedSensorVelocity(0)) - Math.abs(rightMaster.getSelectedSensorVelocity(0));
	}
	
	public void resetForPath() {
        DrivetrainProfiling.isProfileFinished = false;
        resetEncoder();
        Robot.sensors.navX.reset();
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new UseArcadeDrive());
		//setDefaultCommand(new VelocityControl());
	}
	
	public static class DrivetrainProfiling {
        public static double kp = 0.8; //1.2;
        public static double ki = 0.0;
        public static double kd = 0.0;
        
        public static double gp = 0.0375;
        public static double gd = 0.0;

        //Gyro logging for motion profiling
        public static double last_gyro_error = 0.0;
        public static double path_angle_offset = 0.0;
        
        public static final double max_velocity = 5.0; //4 is real
        public static final double kv = 1.0 / max_velocity;
        public static final double max_acceleration = 3.8; // Estimated #
        public static final double ka = 0.05; //0.015
        public static final double max_jerk = 16.0;
        public static final double wheel_diameter = 0.125;

        public static final double wheel_base_width = 0.72;
        public static final int ticks_per_rev = 4096;
        public static final double dt = 0.02;
        
        public static boolean isProfileFinished = false;
	}
}
