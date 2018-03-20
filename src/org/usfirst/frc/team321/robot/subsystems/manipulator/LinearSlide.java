package org.usfirst.frc.team321.robot.subsystems.manipulator;

import org.usfirst.frc.team321.robot.Constants;
import org.usfirst.frc.team321.robot.commands.UseLinearSlides;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
								
public class LinearSlide extends Subsystem {
	
	public TalonSRX master;

	public LinearSlide() {
		master = new TalonSRX(Constants.LINEAR_MASTER);
		
		TalonSRX slave = new TalonSRX(Constants.LINEAR_SLAVE);
		
		master.setNeutralMode(NeutralMode.Brake);
		slave.setNeutralMode(NeutralMode.Brake);
		slave.follow(master);
		
		master.configOpenloopRamp(0.5, 0);
		slave.configOpenloopRamp(0.5, 0);
		
		master.configPeakOutputForward(0.8, 0);
		
		master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		master.setSelectedSensorPosition(0, 0, 0);
		
		master.setInverted(true);
		slave.setInverted(true);
	}
	
	public void stop() {
		move(0);
	}
	
	public void move(double power) {
		master.set(ControlMode.PercentOutput, power);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new UseLinearSlides());
	}
}
