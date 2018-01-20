package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
								
						//extends Subreddit
public class LinearSlide extends Subsystem {
	
	//setting up motors and encoders
	TalonSRX lineA, lineB;
	Encoder linearEncoder;
	public DigitalInput touchSensorTop;
	public DigitalInput touchSensorBottom;
	
	//setting up tooch censors
	public LinearSlide() {
		touchSensorTop = new DigitalInput(RobotMap.TOUCH_SENSOR_TOP);
		touchSensorBottom = new DigitalInput(RobotMap.TOUCH_SENSOR_BOTTOM);
		
		lineA = new TalonSRX(RobotMap.LINE_A);
		lineB = new TalonSRX(RobotMap.LINE_B);
		
		linearEncoder = new Encoder(RobotMap.LINE_ENCODER_A, RobotMap.LINE_ENCODER_B, true, EncodingType.k4X);
	}
	
	//set power to talon to go up
	public void up(double power) {
		lineA.set(ControlMode.PercentOutput, 1);
		lineB.set(ControlMode.PercentOutput, -1);
	}
	//same thing but its goin down
	public void down(double power) {
		lineA.set(ControlMode.PercentOutput, -1);
		lineB.set(ControlMode.PercentOutput, 1);
	}
	//stop
	public void stop() {
		lineA.set(ControlMode.PercentOutput, 0);
		lineB.set(ControlMode.PercentOutput, 0);
	}
	//controller-friendly, can be any power (.1,.5, ekcetera(
	public void move(double power) {
		lineA.set(ControlMode.PercentOutput, power);
		lineB.set(ControlMode.PercentOutput, -power);
	}
	
	public double getLineEncoderDistance(){
		return linearEncoder.getDistance();
	}

	public int getRawLineEncoderCount(){
		return linearEncoder.get();	
	}
	
	public void moveSafeUp(double power) {
		if (Robot.sensors.touchSensorTop.get() == true) {
			stop();
		} else {
			up(power);
		}
	}
	
	public void moveSafeDown(double power) {
		if(Robot.sensors.touchSensorBottom.get() == true) {
			stop();
		} else {
			down(power);
		}
	}
	
	public void moveSafe(double power) {
		if (power < 0) {
			if (Robot.sensors.touchSensorBottom.get() == true) {
				stop();
			} else {
				move(power);
			} 
		} else if (power > 0) {
			if(Robot.sensors.touchSensorTop.get() == true) {
				stop();
			} else {
				move(power);
			} 
		} else {
			stop();
		}
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

}
