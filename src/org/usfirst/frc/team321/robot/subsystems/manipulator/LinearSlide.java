package org.usfirst.frc.team321.robot.subsystems.manipulator;

import org.usfirst.frc.team321.robot.Constants;
import org.usfirst.frc.team321.robot.commands.UseLinearSlides;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
								
public class LinearSlide extends Subsystem {
	
	private TalonSRX master, slave;

	public LinearSlide() {
		master = new TalonSRX(Constants.LINEAR_MASTER);
		slave = new TalonSRX(Constants.LINEAR_SLAVE);
		
		master.setNeutralMode(NeutralMode.Brake);
		slave.setNeutralMode(NeutralMode.Brake);
		
		slave.set(ControlMode.Follower, master.getDeviceID());
		
		master.configOpenloopRamp(0.5, 0);
		
		master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		master.setSelectedSensorPosition(0, 0, 0);
	}
	
	public void up() {
		master.set(ControlMode.PercentOutput, 1);
	}
	
	public void down() {
		master.set(ControlMode.PercentOutput, -1);
	}
	
	public void stop() {
		master.set(ControlMode.PercentOutput, 0);
	}
	
	public void move(double power) {
		master.set(ControlMode.PercentOutput, power);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new UseLinearSlides());
	}
}
