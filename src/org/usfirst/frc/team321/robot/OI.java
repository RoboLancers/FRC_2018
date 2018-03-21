package org.usfirst.frc.team321.robot;

import org.usfirst.frc.team321.robot.commands.autonomous.modes.AutoStill;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.AutoSwitch;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.CrossAutoLine;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.TestGyro;
import org.usfirst.frc.team321.robot.commands.subsystems.drivetrain.UseSlowArcadeDrive;
import org.usfirst.frc.team321.robot.commands.subsystems.manipulator.UseIntake;
import org.usfirst.frc.team321.robot.commands.subsystems.misc.DSolenoidHold;
import org.usfirst.frc.team321.robot.subsystems.drivetrain.GearShifter;
import org.usfirst.frc.team321.robot.subsystems.manipulator.IntakePivot;
import org.usfirst.frc.team321.robot.utilities.controllers.FlightController;
import org.usfirst.frc.team321.robot.utilities.controllers.XboxController;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {

	public XboxController xboxController;
	public FlightController flightController;
	
	SendableChooser<String> chooser = new SendableChooser<>();
	static final String[] autoModes = {"Cross Auto Line Left", "Cross Auto Line Right", "Auto Switch Camera", "Auto Switch No Camera", "Test Gyro"};
	
	public OI() {
		xboxController = new XboxController(0);
		flightController = new FlightController(1);
		
		xboxController.rightTrigger.whileHeld(new DSolenoidHold(Robot.drivetrain.getGearShifter(), GearShifter.gearShifter, DoubleSolenoid.Value.kForward));
		xboxController.leftTrigger.whileHeld(new UseSlowArcadeDrive());
		
		xboxController.leftBumper.whileHeld(new UseIntake(0.9, true));
		xboxController.rightBumper.whileHeld(new UseIntake(0.7));
		
		flightController.farBottom.whileHeld(new DSolenoidHold(Robot.manipulator.getIntakePivot(), IntakePivot.intakePivot, DoubleSolenoid.Value.kForward));
	}
	
	public static void setDashboardValues() {
		try {
			SmartDashboard.putNumber("Robot pitch", Robot.sensors.navX.getPitch());
			SmartDashboard.putNumber("Robot roll", Robot.sensors.navX.getRoll());
			SmartDashboard.putNumber("Gyro", Robot.sensors.navX.getAngle());
			SmartDashboard.putNumber("Left Encoder Distance", Robot.drivetrain.getLeft().getEncoderDistance());
			SmartDashboard.putNumber("Right Encoder Distance", Robot.drivetrain.getRight().getEncoderDistance());
			SmartDashboard.putNumber("Ultrasonic Sensor", Robot.sensors.getAverageDistanceInMeters());
			
			SmartDashboard.putBoolean("Slide Fully Extended", Robot.sensors.isLinearSlideFullyExtended());
			SmartDashboard.putBoolean("Slide Grounded", Robot.sensors.isLinearSlideAtGround());
			
			SmartDashboard.putNumber("Flight Stick Value", Robot.oi.flightController.getYAxisValue());
			
			SmartDashboard.putNumber("Linear Encoder", Robot.manipulator.getLinearSlide().master.getSelectedSensorPosition(0));
			
			SmartDashboard.putBoolean("IsTargetDetected", Robot.camera.isTgtVisible());
			SmartDashboard.putNumber("Vision Target Angle", Robot.camera.getTgtAngle_Deg());
			
			NetworkTableInstance.getDefault()
		        .getEntry("/CameraPublisher/JeVois/streams")
		        .setStringArray(new String[]{"mjpeg:http://roborio-321-frc.local:1180/?action=stream"});
	        
			//Custom Dashboard information
			NetworkTableInstance.getDefault().getEntry("/SmartDashboard/drive/navx/yaw").setNumber(Robot.sensors.navX.getAngle());
			NetworkTableInstance.getDefault().getEntry("/robot/time").setNumber(DriverStation.getInstance().getMatchTime());
			NetworkTableInstance.getDefault().getEntry("/SmartDashboard/autonomous/modes").setStringArray(autoModes);
			
		} catch (Exception e) {}
	}
	
	public void putAutoModes(){
		for(String mode : autoModes) {
			String noSpace = mode.replaceAll("\\s", "");
			chooser.addObject(mode, noSpace);
		}

		SmartDashboard.putData("Auto mode", chooser);
	}
	
	public String getDashboardMode() {
		return NetworkTableInstance.getDefault().getEntry("/SmartDashboard/currentlySelectedMode").getString("DoNothing");
	}
	
	public String getAutoMode(){
		return chooser.getSelected();
	}
	
	public Command getAutoCommand(String mode){
		switch (mode) {
			case "CrossAutoLineLeft":
				return new CrossAutoLine(true);
			case "CrossAutoLineRight":
				return new CrossAutoLine(false);
			case "AutoSwitchCamera":
				return new AutoSwitch(true);
			case "AutoSwitchNoCamera":
				return new AutoSwitch(false);
			case "TestGyro":
				return new TestGyro(0.1f, 90f);
			default:
				return new AutoStill();
		}
	}
}