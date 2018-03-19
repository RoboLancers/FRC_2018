package org.usfirst.frc.team321.robot;

import org.usfirst.frc.team321.robot.commands.DSolenoidHold;
import org.usfirst.frc.team321.robot.commands.UseIntake;
import org.usfirst.frc.team321.robot.commands.UseIntake.IntakePower;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.AutoMiddle;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.AutoStill;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.AutoSwitch;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.AutoSwitchLeft;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.AutoSwitchRight;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.CrossAutoLine;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.TestGyro;
import org.usfirst.frc.team321.robot.subsystems.drivetrain.GearShifter;
import org.usfirst.frc.team321.robot.subsystems.manipulator.IntakePivot;
import org.usfirst.frc.team321.robot.utilities.controllers.FlightController;
import org.usfirst.frc.team321.robot.utilities.controllers.XboxController;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {

	public XboxController xboxController;
	public FlightController flightController;
	
	SendableChooser<String> chooser = new SendableChooser<>();
	
	public OI() {
		xboxController = new XboxController(0);
		flightController = new FlightController(1);
		
		xboxController.rightTrigger.whileHeld(new DSolenoidHold(Robot.gearshifter, GearShifter.gearShifter, DoubleSolenoid.Value.kForward));
		
		xboxController.leftBumper.whileHeld(new UseIntake(IntakePower.INTAKE.power, true));
		xboxController.rightBumper.whileHeld(new UseIntake(IntakePower.OUTTAKE.power, false));
		
		flightController.farBottom.whileHeld(new DSolenoidHold(Robot.intakepivot, IntakePivot.intakepivot, DoubleSolenoid.Value.kForward));
	}
	
	public static void setDashboardValues() {
		try {
			SmartDashboard.putNumber("Robot pitch", Robot.sensors.navX.getPitch());
			SmartDashboard.putNumber("Robot roll", Robot.sensors.navX.getRoll());
			SmartDashboard.putNumber("Gyro", Robot.sensors.navX.getAngle());
			SmartDashboard.putNumber("Left Encoder Distance", Robot.drivetrain.getLeftEncoderDistance());
			SmartDashboard.putNumber("Right Encoder Distance", Robot.drivetrain.getRightEncoderDistance());
			SmartDashboard.putNumber("Ultrasonic Sensor", Robot.sensors.getAverageDistanceInMeters());
			
			SmartDashboard.putBoolean("Slide Fully Extended", Robot.sensors.isLinearSlideFullyExtended());
			SmartDashboard.putBoolean("Slide Grounded", Robot.sensors.isLinearSlideAtGround());
			
			SmartDashboard.putNumber("Left Velocity", Robot.drivetrain.leftMaster.getSelectedSensorVelocity(0));
			SmartDashboard.putNumber("Right Velocity", Robot.drivetrain.rightMaster.getSelectedSensorVelocity(0));
			
			SmartDashboard.putNumber("Flight Stick Value", Robot.oi.flightController.getYAxisValue());
			
			SmartDashboard.putNumber("Linear Encoder", Robot.linear.master.getSelectedSensorPosition(0));
			
			SmartDashboard.putBoolean("isTargetDetected", Robot.camera.isTgtVisible());
			SmartDashboard.putNumber("Vision Target Angle", Robot.camera.getTgtAngle_Deg());
			
			NetworkTableInstance.getDefault()
		        .getEntry("/CameraPublisher/JeVois/streams")
		        .setStringArray(new String[]{"mjpeg:http://roborio-321-frc.local:1180/?action=stream"});
	        
		} catch (Exception e) {}
	}
	
	public void putAutoModes(){
		chooser.addDefault("Cross Auto Line (Time)", "CrossAutoLine");
		chooser.addObject("Cross Auto Line (Gyro)", "CrossAutoLineGyro");
		
		chooser.addObject("Cross Auto Line with Switch Left", "CrossAutoLineLeft");
		chooser.addObject("Cross Auto Line with Switch Right", "CrossAutoLineRight");
		
		chooser.addObject("Auto Switch (Camera)", "AutoSwitch");
		chooser.addObject("Auto Switch (No Camera)", "AutoMiddle");
		
		chooser.addObject("Auto Switch Left", "AutoSwitchLeft");
		chooser.addObject("Auto Switch Right", "AutoSwitchRight");
		
		chooser.addObject("TestGyro", "TestGyro");

		SmartDashboard.putData("Auto mode", chooser);
	}
	
	private String getAutoMode(){
		return chooser.getSelected();
	}
	
	public Command getAutoCommand(){
		switch (getAutoMode()) {
			case "CrossAutoLine":
				return new CrossAutoLine(false);
			case "CrossAutoLineGyro":
				return new CrossAutoLine(true);
			case "CrossAutoLineLeft":
				return new CrossAutoLine(true, true);
			case "CrossAutoLineRight":
				return new CrossAutoLine(true, false);
			case "AutoSwitch":
				return new AutoSwitch();
			case "AutoSwitchLeft" :
				return new AutoSwitchLeft();
			case "AutoSwitchRight" :
				return new AutoSwitchRight();
			case "AutoMiddle":
				return new AutoMiddle();
			case "TestGyro":
				return new TestGyro(0.1f, 90f);
			default:
				return new AutoStill();
		}
	}
}