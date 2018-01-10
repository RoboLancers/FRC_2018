package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.utilities.RobotUtil;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Sensors extends Subsystem {

	public AHRS navX;
	
	public Sensors() {
		navX = new AHRS(SerialPort.Port.kMXP);
		
		navX.reset();
		navX.resetDisplacement();
		
		//Get how much rotation
		//Get how fast you're moving in XYZ
		//Get how much it has moved from original position
	}
	
	public double getRobotHeading() {
		if (navX.getAngle() < 0) {
			return RobotUtil.floor(360 - Math.abs(navX.getAngle() % 360), 2);
		} else {
			return RobotUtil.floor(navX.getAngle() % 360, 2);
		}
	}
	
	public double[] moveToTarget(double power, double currentAngle, double targetAngle) {
		double[] motorSpeed = new double[2];
		
		motorSpeed[0] = RobotUtil.range(power + (currentAngle - targetAngle)/100, -1, 1);
	    motorSpeed[1] = RobotUtil.range(power - (currentAngle - targetAngle)/100, -1, 1);
	    
	    motorSpeed[0] = RobotUtil.floor(motorSpeed[0], 2);
	    motorSpeed[1] = RobotUtil.floor(motorSpeed[1], 2);
	    
	    return motorSpeed;
	}
	
	@Override
	protected void initDefaultCommand() {
		
	}
}
