package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.RobotMap;
import org.usfirst.frc.team321.robot.commands.UseDriveTrain;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Sparky extends Subsystem {
	Spark topLeft, topRight, midLeft, midRight, bottomLeft,bottomRight;
	
	public Sparky() {
		
		bottomLeft= new Spark(RobotMap.BOT_LEFT_MOTOR);
		topLeft = new Spark(RobotMap.TOP_LEFT_MOTOR);
		bottomRight = new Spark(RobotMap.BOT_RIGHT_MOTOR);
		topRight = new Spark(RobotMap.TOP_RIGHT_MOTOR);
		midLeft = new Spark(RobotMap.MID_LEFT_MOTOR);
		midRight = new Spark(RobotMap.MID_RIGHT_MOTOR);
		
		
	}

	public void setLeft(double power) {
		topLeft.set(power);
		bottomLeft.set(power);
		midLeft.set(power);
	}
	
	public void setRight(double power) {
		topRight.set(-power);
		bottomRight.set(-power);
		midRight.set(-power);
	}
	
	public void setAll(double power) {
		setLeft(power);
		setRight(power);
		
	}
	
	public void stopMotors() {
		setAll(0);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new UseDriveTrain());
		// TODO Auto-generated method stub
		
	}

}
