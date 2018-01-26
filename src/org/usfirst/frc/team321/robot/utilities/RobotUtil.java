package org.usfirst.frc.team321.robot.utilities;

import edu.wpi.first.wpilibj.Timer;

public class RobotUtil {

	private static Timer timer = new Timer();
	private static boolean isTimeTracking = false;
	
	public static double range(double input, double min, double max){
		if(input > max) {
			return max;
		} else if(input < min) {
			return min;
		} else {
			return input;
		}
	}

	public static double floor(double num, int places) {
		double multiplier = 1;
		
		for(int x = 0; x < places; x++) {
			multiplier *= 10;
		}
		
		num *= multiplier;
		
		return (int)num / multiplier;
	}
	
	public static double squareKeepSign(double num){	
		if(num < 0){
			return -(num * num);
		}else{
			return num * num;
		}
	}
	
	public static double sqrtKeepSign(double num) {
		if(num < 0){
			return -(Math.sqrt(-num));
		}else{
			return Math.sqrt(num);
		}
	}
	
	/**
	 * Calculates the robot's motor speed to move to a target/angle
	 * 
	 * Note: Set power to 0 to move in place
	 */
	public static double[] moveToTarget(double power, double currentAngle, double targetAngle) {
		double[] motorSpeed = new double[2];
		
		motorSpeed[0] = RobotUtil.range(power + (currentAngle - targetAngle) * 0.9, -1, 1);
	    motorSpeed[1] = RobotUtil.range(power - (currentAngle - targetAngle) * 0.9, -1, 1);
	    
	    motorSpeed[0] = RobotUtil.floor(motorSpeed[0], 2);
	    motorSpeed[1] = RobotUtil.floor(motorSpeed[1], 2);
	    
	    return motorSpeed;
	}
	
	public static void startTimer() {
		timer.reset();
		timer.start();
		isTimeTracking = true;
	}
	
	public static void stopTimer() {
		timer.stop();
		timer.reset();
		isTimeTracking = false;
	}
	
	public static boolean isTimeTracking() {
		return isTimeTracking;
	}
	
	public static boolean isTimePassed(double seconds) {
		if (timer.get() >= seconds) {
			stopTimer();
			return true;
		}

		return false;
	}
	
	public static double[] arcadeDrive(double moveValue, double rotateValue) {
	    double leftMotorSpeed = range(-moveValue + rotateValue, -1, 1);
	    double rightMotorSpeed = range(-moveValue - rotateValue, -1, 1);
	    
	    /*
	    left = y_axis + x_axis
		right = y_axis - x_axis
	    */
	    double[] values = {leftMotorSpeed, rightMotorSpeed};
	    
	    return values;
	}
}
