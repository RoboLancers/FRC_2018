package org.usfirst.frc.team321.robot.commands;

import org.usfirst.frc.team321.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class LinearSlidesWithEncoders extends Command{

	double power;
	double targetDistance;
	double targetAngle;
	String gameData;
	
	public LinearSlidesWithEncoders(double power, double targetAngle, double targetDistance) {
		requires(Robot.linear);
		this.power = power;
		this.targetDistance = targetDistance;
		this.targetAngle = targetAngle;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		Robot.sensors.navX.reset();
	}
	
	protected void initialize() {
		Robot.linear.stop();
	}
	
	
	protected void execute() {
		if(gameData.charAt(0) == 'L') {
			Robot.linear.up(1);
		} else {
			Robot.linear.stop();
			}
		}
		//Robot.linear.up(1);
	
	@Override
	protected boolean isFinished() {
		if (Robot.linear.getRawLineLeftEncoderCount() >= Robot.linear.asdfghjkl(targetDistance)) {
			Robot.drivetrain.setAll(0);
		}
		return false;
	}

}
/*String gameData;
gameData = DriverStation.getInstance().getGameSpecificMessage();
if(gameData.charAt(0) == 'L')
{
	//Put left auto code here
} else {
	//Put right auto code here
}*/