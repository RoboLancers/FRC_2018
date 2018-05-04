package org.usfirst.frc.team321.robot;

import org.usfirst.frc.team321.robot.commands.autonomous.modes.MiddleSwitch;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.SameSideScale;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.SwitchThenScale;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.DoNothingAndReset;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.CrossAutoLine;
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
	
	static final String[] autoModesDisplay = {"Cross Auto Line With Left Switch", "Cross Auto Line With Right Switch", 
			"1 Cube Same Side Scale Left", "1 Cube Same Side Scale Right",
			"1 Cube Middle Switch", "1 Cube Switch Then Scale Left", "1 Cube Switch Then Scale Right"};
	
	public OI() {
		xboxController = new XboxController(0);
		flightController = new FlightController(1);
		
		xboxController.rightTrigger.whileHeld(new DSolenoidHold(Robot.drivetrain.getGearShifter(), GearShifter.gearShifter, DoubleSolenoid.Value.kForward));
		xboxController.leftTrigger.whileHeld(new UseSlowArcadeDrive());
		
		xboxController.leftBumper.whileHeld(new UseIntake(0.87, true));
		//xboxController.rightBumper.whileHeld(new UseIntake(-0.5));
		
		flightController.farBottom.whileHeld(new DSolenoidHold(Robot.manipulator.getIntakePivot(), IntakePivot.intakePivot, DoubleSolenoid.Value.kForward));
	}
	
	public static void setDashboardValues() {
		try {
			SmartDashboard.putNumber("Robot pitch", Robot.sensors.navX.getPitch());
			SmartDashboard.putNumber("Robot roll", Robot.sensors.navX.getRoll());
			SmartDashboard.putNumber("Gyro", Robot.sensors.navX.getAngle());
			//SmartDashboard.putNumber("Left Encoder Distance", Robot.drivetrain.getLeft().getEncoderDistance());
			//SmartDashboard.putNumber("Right Encoder Distance", Robot.drivetrain.getRight().getEncoderDistance());
			SmartDashboard.putNumber("Ultrasonic Sensor", Robot.sensors.getAverageDistanceInMeters());
			
			SmartDashboard.putBoolean("Slide Fully Extended", Robot.sensors.isLinearSlideFullyExtended());
			SmartDashboard.putBoolean("Slide Grounded", Robot.sensors.isLinearSlideAtGround());
			
			SmartDashboard.putNumber("Linear Encoder", Robot.manipulator.getLinearSlide().getEncoder());
			
			SmartDashboard.putBoolean("IsTargetDetected", Robot.camera.isTgtVisible());
			SmartDashboard.putNumber("Vision Target Angle", Robot.camera.getTgtAngle_Deg());
			
			NetworkTableInstance.getDefault()
		        .getEntry("/CameraPublisher/JeVois/streams")
		        .setStringArray(new String[]{"mjpeg:http://roborio-321-frc.local:1180/?action=stream"});
	        
			//Custom Dashboard information
			NetworkTableInstance.getDefault().getEntry("/SmartDashboard/drive/navx/yaw").setNumber(Robot.sensors.navX.getAngle());
			NetworkTableInstance.getDefault().getEntry("/robot/time").setNumber(DriverStation.getInstance().getMatchTime());
			NetworkTableInstance.getDefault().getEntry("/SmartDashboard/autonomous/modes").setStringArray(autoModesDisplay);
			
		} catch (Exception e) {}
	}
	
	/**
	 * Puts auto mode on SmartDashboard
	 */
	public void putAutoModes(){
		for(String mode : autoModesDisplay) {
			String noSpace = mode.replaceAll("\\s", "");
			chooser.addObject(mode, noSpace);
		}

		SmartDashboard.putData("Auto mode", chooser);
	}
	
	/**
	 * Gets the mode from Dashboard
	 * @return The mode as a string
	 */
	public String getDashboardMode() {
		return NetworkTableInstance.getDefault().getEntry("/SmartDashboard/currentlySelectedMode").getString("DoNothing");
	}
	
	/**
	 * Gets the mode from SmartDashboard
	 * @return The mode as a string
	 */
	public String getAutoMode(){
		return chooser.getSelected();
	}
	
	/**
	 * {"Cross Auto Line With Left Switch", "Cross Auto Line With Right Switch", 
			"1 Cube Same Side Scale Left", "1 Cube Same Side Scale Right",
			"1 Cube Middle Switch"}
	 */
	
	/**
	 * Uses a string and finds the corresponding autonomous mode
	 * @param mode The mode as a string that we are looking for
	 * @return The autonomous command
	 */
	public Command getAutoCommand(String mode){
		switch (mode) {
			case "CrossAutoLineWithLeftSwitch":
				return new CrossAutoLine(true);
			case "CrossAutoLineWithRightSwitch":
				return new CrossAutoLine(false);
			case "1CubeSameSideScaleLeft":
				return new SameSideScale(true);
			case "1CubeSameSideScaleRight":
				return new SameSideScale(false);
			case "1CubeMiddleSwitch":
				return new MiddleSwitch();
			case "1CubeSwitchThenScaleLeft":
				return new SwitchThenScale(true);
			case "1CubeSwitchThenScaleRight":
				return new SwitchThenScale(false);
			// Unused/Untested
			default:
				return new DoNothingAndReset();
		}
	}
}