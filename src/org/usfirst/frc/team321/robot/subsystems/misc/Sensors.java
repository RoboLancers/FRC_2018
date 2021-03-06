package org.usfirst.frc.team321.robot.subsystems.misc;

import java.util.Arrays;

import org.usfirst.frc.team321.robot.Constants;
import org.usfirst.frc.team321.robot.utilities.RobotUtil;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Sensors extends Subsystem {

	public AHRS navX;
	public Ultrasonic ultrasonic;
	public DigitalInput topTouchSensor; 
	public DigitalInput bottomTouchSensor; 
	
	private double[] ultrasonicBuffer = {0, 0, 0};
	
	public Sensors() {
		navX = new AHRS(SerialPort.Port.kMXP);
		ultrasonic = new Ultrasonic(Constants.UlTRASONIC_TRIG, Constants.UlTRASONIC_ECHO);
		topTouchSensor = new DigitalInput(Constants.TOP_TOUCH_SENSOR);
		bottomTouchSensor = new DigitalInput(Constants.BOTTOM_TOUCH_SENSOR);
		
		navX.reset();
		navX.resetDisplacement();
		
		ultrasonic.setAutomaticMode(true);
		ultrasonic.setEnabled(true);
		ultrasonic.setDistanceUnits(Ultrasonic.Unit.kMillimeters);
	}
	
	public boolean isLinearSlideFullyExtended() {
		return !topTouchSensor.get();
	}
	
	public boolean isLinearSlideAtGround() {
		return !bottomTouchSensor.get();
	}
	
	public double getDistanceInMeters() {
        return ultrasonic.getRangeMM() / 1000;
    }
	
	public double getAverageDistanceInMeters() {
		for(int i = 0; i < ultrasonicBuffer.length; i++) {
			ultrasonicBuffer[i] = getDistanceInMeters();
		}
		
		return Arrays.stream(ultrasonicBuffer)
				.average()
				.getAsDouble();
	}
	
	public double getRobotHeading() {
		if (navX.getAngle() < 0) {
			return RobotUtil.floor(360 - Math.abs(navX.getAngle() % 360), 2);
		} else {
			return RobotUtil.floor(navX.getAngle() % 360, 2);
		}
	}
	
	public double convertToHeading(double angle) {
		if (angle < 0) {
			return RobotUtil.floor(360 - Math.abs(angle % 360), 2);
		} else {
			return RobotUtil.floor(angle % 360, 2);
		}
	}
	
	/**
	 * Calculates the required motor power to move to an angle or 
	 * move at an angle.
	 * 
	 * @param power        The power of the motors
	 * @param currentAngle The current angle of the robot
	 * @param targetAngle  The angle to turn your robot to
	 * @return An array of size 2 for left and right side drive motors
	 */
	public double[] moveToTarget(double power, double currentAngle, double targetAngle) {
		double[] motorSpeed = new double[2];
		
		motorSpeed[0] = RobotUtil.range(power - (currentAngle - targetAngle)/100, -1, 1);
	    motorSpeed[1] = RobotUtil.range(power + (currentAngle - targetAngle)/100, -1, 1);
	    
	    motorSpeed[0] = RobotUtil.floor(motorSpeed[0], 3);
	    motorSpeed[1] = RobotUtil.floor(motorSpeed[1], 3);
	    
	    return motorSpeed;
	}
	
	@Override
	protected void initDefaultCommand() {
		
	}
}

//wow is this robot even PG-13, we should
//...sensor it