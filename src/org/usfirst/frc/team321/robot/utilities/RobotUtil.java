package org.usfirst.frc.team321.robot.utilities;

public class RobotUtil {

	private static final double RADIUS = 3.1;
	private static final double CIRCUMFERENCE = 2 * Math.PI * RADIUS;
	private static final double TPR = 4096;
	public static final double DISTANCE_PER_PULSE = CIRCUMFERENCE / TPR;

	/**
	 * Returns the total encoder ticks to move a distance
	 * 
	 * @param targetDistance
	 *            The distance in meters to move
	 * @return Return amount of encoder ticks
	 */
	public static double getTargetTicks(double targetDistance) {
		return (targetDistance * 10 / DISTANCE_PER_PULSE);
	}

	/**
	 * Limits value to a specified minimum and maximum
	 * 
	 * @param value
	 *            The value to check
	 * @param min
	 *            The lower bound
	 * @param max
	 *            The upper bound
	 * @return Returns a number that is between min and max inclusive
	 */
	public static double range(double value, double min, double max) {
		return Math.min(max, Math.max(min, value));
	}

	/**
	 * Helper method for case where min and max are same number but one has a
	 * negative sign
	 * 
	 * @see RobotUtil#range(double, double, double)
	 * @param value
	 *            The value to limit
	 * @param max
	 *            The maximum value the number can take
	 * @return Returns a number that is between -max and -max inclusive
	 */
	public static double range(double value, double max) {
		return range(value, -max, max);
	}

	/**
	 * Floors a number to a specific number of decimal places
	 * 
	 * @param num
	 *            The number to floor
	 * @param places
	 *            How many decimal places to use
	 * @return Returns the number floored
	 */
	public static double floor(double num, int places) {
		double multiplier = 1;

		for (int x = 0; x < places; x++) {
			multiplier *= 10;
		}

		num *= multiplier;

		return (int) num / multiplier;
	}

	/**
	 * Keep the sign when squaring
	 * 
	 * @param num
	 *            The number to square
	 * @return The number squared including signs
	 */
	public static double squareKeepSign(double num) {
		return num < 0 ? -(num * num) : (num * num);
	}

	/**
	 * Keep the sign when square rooting
	 * 
	 * @param num
	 *            The number to square root
	 * @return The number square rooted including signs
	 */
	public static double sqrtKeepSign(double num) {
		return num < 0 ? -(Math.sqrt(-num)) : Math.sqrt(num);
	}

	/**
	 * Calculates the robot's motor speed to move to a target/angle
	 * 
	 * @param power
	 *            The power to move at. If it is 0 then it will turn in place
	 * @param currentAngle
	 *            Current robot angle
	 * @param targetAngle
	 *            Target angle to move to
	 * @param kP
	 *            How fast to get to target. The P term in PID
	 * @return Returns the motor powers in an array
	 */
	public static double[] moveToTarget(double power, double currentAngle, double targetAngle, double kP) {
		double[] motorSpeed = new double[2];

		motorSpeed[0] = RobotUtil.range(power + (currentAngle - targetAngle) * kP, -1, 1);
		motorSpeed[1] = RobotUtil.range(power - (currentAngle - targetAngle) * kP, -1, 1);

		motorSpeed[0] = RobotUtil.floor(motorSpeed[0], 2);
		motorSpeed[1] = RobotUtil.floor(motorSpeed[1], 2);

		return motorSpeed;
	}
}
