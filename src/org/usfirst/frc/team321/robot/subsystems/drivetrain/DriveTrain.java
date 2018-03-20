package org.usfirst.frc.team321.robot.subsystems.drivetrain;

import static org.usfirst.frc.team321.robot.Constants.*;
import org.usfirst.frc.team321.robot.commands.UseArcadeDrive;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	
	Transmission leftTransmission, rightTransmission;
	
	public DriveTrain() {
		leftTransmission = new Transmission(false, LEFT_MASTER_MOTOR, LEFT_SLAVE_1, LEFT_SLAVE_2);
		rightTransmission = new Transmission(false, RIGHT_MASTER_MOTOR, RIGHT_SLAVE_1, RIGHT_SLAVE_2);
		
		this.setMode(NeutralMode.Brake);
		this.resetEncoders();
	}
	
	public void setLeft(double power) {
		leftTransmission.setPower(power * 0.95);
	}
	
	public void setRight(double power) {
		rightTransmission.setPower(power * 0.95);
	}
	
	public void setMotors(double leftPower, double rightPower) {
		setLeft(leftPower);
		setRight(rightPower);
	}
	
	public void setAll(double power) {
		setMotors(power, power);
	}
	
	public void setMode(NeutralMode mode){
		leftTransmission.setMode(mode);
		rightTransmission.setMode(mode);
	}
	
	public void enableRamping (boolean ramp) {
		if (ramp) {
			leftTransmission.setRampRate(0.15);
			rightTransmission.setRampRate(0.15);
		} else {
			leftTransmission.setRampRate(0);
			rightTransmission.setRampRate(0);
		}
	}
	
	public void resetEncoders(){
		leftTransmission.resetEncoder();
		rightTransmission.resetEncoder();
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new UseArcadeDrive());
	}
}
