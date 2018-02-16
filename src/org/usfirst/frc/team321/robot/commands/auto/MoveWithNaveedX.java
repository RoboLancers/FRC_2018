package org.usfirst.frc.team321.robot.commands.auto;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.utilities.LancerPID;
import org.usfirst.frc.team321.robot.utilities.RobotUtil;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveWithNaveedX extends Command {

	double power;
	double targetAngle;
	LancerPID pid;
		
	public MoveWithNaveedX (double power, double targetAngle) {
		this.power = power;
		this.targetAngle = targetAngle + Robot.sensors.navX.getAngle();
		pid = new LancerPID(0.800, 0.0, 0.0);
		pid.setReference(targetAngle);
	}
	
	@Override
	protected void initialize() {

	}
	
	@Override 
	protected void execute() {
		Robot.drivetrain.setLeft(pid.calcPID(Robot.sensors.navX.getAngle()));
		Robot.drivetrain.setRight(-pid.calcPID(Robot.sensors.navX.getAngle()));
	}
	
	@Override
	protected boolean isFinished() {
		return false;
		//return ((Robot.sensors.navX.getAngle() - targetAngle) <= 2);
	}
}