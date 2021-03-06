package org.usfirst.frc.team321.robot.subsystems.drivetrain;

import org.usfirst.frc.team321.robot.utilities.RobotUtil;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Transmission represents one side of the drivetrain
 * 
 * @see Drivetrain
 */
public class Transmission {

	private TalonSRX master, slave1, slave2;

	/**
	 * Initializes all the motors
	 * 
	 * @param isRight
	 *            Is it the right side?
	 * @param ports
	 *            The ports of that side of the drivetrain
	 */
	public Transmission(boolean isRight, int... ports) {
		master = new TalonSRX(ports[0]);
		slave1 = new TalonSRX(ports[1]);
		slave2 = new TalonSRX(ports[2]);

		slave1.follow(master);
		slave2.follow(master);

		master.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

		if (isRight) {
			master.setSensorPhase(false);

			master.setInverted(true);
			slave2.setInverted(true);
		} else {
			slave2.setInverted(true);
		}
	}

	/**
	 * Sets brake mode
	 * 
	 * @param brakeMode
	 *            Mode to set the motors to
	 */
	public void setMode(NeutralMode mode) {
		if (mode == NeutralMode.Brake) {
			master.setNeutralMode(mode);
			slave1.setNeutralMode(mode);
			slave2.setNeutralMode(mode);
		} else {
			master.setNeutralMode(mode);
			slave1.setNeutralMode(mode);
			slave2.setNeutralMode(mode);
		}
	}

	/**
	 * Sets power to this side of the drivetrain
	 * 
	 * @param power
	 *            The power to set it to. Will be checked to make sure it is between
	 *            -1 and 1
	 */
	public void setPower(double power) {
		master.set(ControlMode.PercentOutput, RobotUtil.range(power, 1));
	}

	/**
	 * Gets current encoder value
	 * 
	 * @return Returns the encoder value on the master talon
	 */
	public int getEncoderCount() {
		return master.getSelectedSensorPosition(0);
	}

	/**
	 * Gets the distance traveled
	 * 
	 * @return Returns the distance traveled on this side of the drivetrain
	 */
	public double getEncoderDistance() {
		return RobotUtil.DISTANCE_PER_PULSE * getEncoderCount() / 10.0;
	}

	/**
	 * Zero out the encoder
	 */
	public void resetEncoder() {
		master.setSelectedSensorPosition(0, 0, 0);
	}

	/**
	 * Add a ramp rate to prevent instantaneous change in velocity
	 * 
	 * @param rampRate
	 *            Amount of time in seconds to go from 0 to full power
	 */
	public void setRampRate(double rampRate) {
		master.configOpenloopRamp(rampRate, 0);
		slave1.configOpenloopRamp(rampRate, 0);
		slave2.configOpenloopRamp(rampRate, 0);
	}
}
