package org.usfirst.frc.team321.robot.commands.auto;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.utilities.RobotUtil;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveWithNaveedX extends Command {

	double power;
	double targetAngle;
	double time;
	Timer timer = new Timer();
		
	public MoveWithNaveedX (double power, double targetAngle,  double time) {
		
		this.power = power;
		this.targetAngle = targetAngle + Robot.sensors.navX.getAngle();
		this.time = time;
		this.timer = new Timer();
		timer.reset();
	}
	
	@Override
	protected void initialize() {
		timer.start();
	}
	
	@Override 
	protected void execute() {
		double[] powers = Robot.sensors.moveToTarget(power, Robot.sensors.navX.getAngle(), targetAngle);
		
		powers[0] = RobotUtil.range(powers[0] * 1.21, -1, 1);
		powers[1] = RobotUtil.range(powers[1] * 1.21, -1, 1);
		
		SmartDashboard.putNumber("targetAngle", targetAngle);
		SmartDashboard.putNumber("leftPowers", powers[0]);
		SmartDashboard.putNumber("rightpowers", powers[1]);
		
		Robot.drivetrain.setLeft(powers[0]);
		Robot.drivetrain.setRight(powers[1]);
	
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return (timer.get() > this.time);
	
	}

}