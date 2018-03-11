package org.usfirst.frc.team321.robot.commands.auto;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.utilities.LancerPID;
import org.usfirst.frc.team321.robot.utilities.RobotUtil;

import edu.wpi.first.wpilibj.command.Command;

public class MoveRobot extends Command{
	
	double leftPower, rightPower;
	
	boolean useAngle;
	double angle, anglePower;
	
	LancerPID lancerPID;
	
	public MoveRobot(double power){
		this(power, power);
	}
	
	public MoveRobot(double leftPower, double rightPower){
		requires(Robot.drivetrain);
		this.leftPower = leftPower;
		this.rightPower = rightPower;
		useAngle = false;
	}
	
	public MoveRobot(float power, float angle){
		requires(Robot.drivetrain);
		this.angle = angle;
		anglePower = power;
		useAngle = true;
		
		lancerPID = new LancerPID(0.1, 0.0, 0.0);
	}
	
	protected void initialize(){
		Robot.drivetrain.setAll(0);
		Robot.sensors.navX.reset();
	}
	
	protected void execute(){
		Robot.drivetrain.setLeft(useAngle ? RobotUtil.range(anglePower + lancerPID.calcPID(Robot.sensors.navX.getAngle()), 1) : leftPower);
		Robot.drivetrain.setRight(useAngle ? RobotUtil.range(anglePower - lancerPID.calcPID(Robot.sensors.navX.getAngle()), 1) : rightPower);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	protected void end(){
		Robot.drivetrain.setAll(0);
	}
	
	protected void interrupted(){
		Robot.drivetrain.setAll(0);
	}
}


