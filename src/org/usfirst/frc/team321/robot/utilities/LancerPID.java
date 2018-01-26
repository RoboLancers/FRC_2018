package org.usfirst.frc.team321.robot.utilities;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LancerPID {
	private double setpoint, integral, error, lastError;
	
	private double kP, kI, kD;
	
	public LancerPID(double kP, double kI, double kD) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
	}
	
	public void setSetpoint(double setpoint){
		this.setpoint = setpoint;
		
		integral = 0;
		lastError = 0;
	}
	
	public double getOutput(double current){
		double derivative, output;
		
		error = setpoint - current;
		
		derivative = error - lastError;
		
		output = (kP * error) + (kI * integral) + (kD * derivative);

		if(output > 1){
			output = 1;
		}else if(output < -1){
			output = -1;
		}else{
			integral += error;
		}
		
		lastError = error;
		
		SmartDashboard.putNumber("Output", output);
		SmartDashboard.putNumber("integral", integral);
		SmartDashboard.putNumber("error", error);
		
		return output;
	}
	
	public double getError(){
		return error;
	}
}
