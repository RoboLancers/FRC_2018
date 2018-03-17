package org.usfirst.frc.team321.robot;

import org.usfirst.frc.team321.robot.commands.autocode.AutoMeters;
import org.usfirst.frc.team321.robot.commands.autocode.AutoMiddle;
import org.usfirst.frc.team321.robot.commands.autocode.AutoScaleLeft;
import org.usfirst.frc.team321.robot.commands.autocode.AutoScaleRight;
import org.usfirst.frc.team321.robot.commands.autocode.AutoStill;
import org.usfirst.frc.team321.robot.commands.autocode.AutoSwitch;
import org.usfirst.frc.team321.robot.commands.autocode.AutoTurn;
import org.usfirst.frc.team321.robot.commands.autocode.CrossAutoLine;
import org.usfirst.frc.team321.robot.commands.pathfinder.FollowPath;
import org.usfirst.frc.team321.robot.subsystems.Camera;
import org.usfirst.frc.team321.robot.subsystems.DriveTrain;
import org.usfirst.frc.team321.robot.subsystems.GearShifter;
import org.usfirst.frc.team321.robot.subsystems.Intake;
import org.usfirst.frc.team321.robot.subsystems.IntakePivot;
import org.usfirst.frc.team321.robot.subsystems.LinearSlide;
import org.usfirst.frc.team321.robot.subsystems.Pneumatics;
import org.usfirst.frc.team321.robot.subsystems.Sensors;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public static DriveTrain drivetrain;
	public static LinearSlide linear;
	public static Intake intake;
	public static IntakePivot intakepivot;
	public static GearShifter gearshifter;
	public static Pneumatics pneumatics; 
	
	public static Sensors sensors;
	public static Camera camera;
	
	public static OI oi;
	
	Command autonomousCommand;
	SendableChooser<String> chooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		sensors = new Sensors();
		camera = new Camera(true);
		
		drivetrain = new DriveTrain();
		linear = new LinearSlide();
		intake = new Intake(); 
		intakepivot = new IntakePivot();
		
		pneumatics = new Pneumatics();
		gearshifter = new GearShifter();
		
		oi = new OI();

		chooser.addDefault("Cross Auto Line (Time)", "CrossAutoLine");
		chooser.addObject("TestEncoder", "TestEncoder");
		chooser.addObject("TestGyro", "TestGyro");
		chooser.addObject("Cross Auto Line (Gyro)", "CrossAutoLineGyro");
		chooser.addObject("Auto Middle Switch", "AutoSwitch");
		//chooser.addObject("Auto Middle", "AutoMiddle");
		chooser.addObject("Auto Scale Left", "AutoScaleLeft");
		chooser.addObject("Auto Scale Right", "AutoScaleRight");
		chooser.addObject("TestPathfinder", "TestPathfinder");

		SmartDashboard.putData("Auto mode", chooser);
	}
	
	public void setDashboardValues() {
		try {
			SmartDashboard.putNumber("Robot pitch", Robot.sensors.navX.getPitch());
			SmartDashboard.putNumber("Robot roll", Robot.sensors.navX.getRoll());
			SmartDashboard.putNumber("Gyro", Robot.sensors.navX.getAngle());
			SmartDashboard.putNumber("Left Encoder Distance", Robot.drivetrain.getLeftEncoderDistance());
			SmartDashboard.putNumber("Right Encoder Distance", Robot.drivetrain.getRightEncoderDistance());
			SmartDashboard.putNumber("Ultrasonic Sensor", Robot.sensors.getDistanceInMeters());
			
			SmartDashboard.putBoolean("Slide Fully Extended", Robot.sensors.isLinearSlideFullyExtended());
			SmartDashboard.putBoolean("Slide Grounded", Robot.sensors.isLinearSlideAtGround());
			
			SmartDashboard.putNumber("Left Velocity", Robot.drivetrain.leftMaster.getSelectedSensorVelocity(0));
			SmartDashboard.putNumber("Right Velocity", Robot.drivetrain.rightMaster.getSelectedSensorVelocity(0));
			
			//SmartDashboard.putNumber("Linear Encoder", linear.masterLine.getSelectedSensorPosition(0));
			
			SmartDashboard.putNumber("Linear Slide Encoder value", Robot.linear.masterLine.getSelectedSensorPosition(0));
			//SmartDashboard.putNumber("LinearSlide Encoder distance", Robot.linear.getLineEncoderDistance());
			
			SmartDashboard.putBoolean("isTargetDetected", Robot.camera.isTgtVisible());
			SmartDashboard.putNumber("Vision Target Angle", Robot.camera.getTgtAngle_Deg());
			
			NetworkTableInstance.getDefault()
		        .getEntry("/CameraPublisher/JeVois/streams")
		        .setStringArray(new String[]{"mjpeg:http://roborio-321-frc.local:1180/?action=stream"});
	        
		} catch (Exception e) {}
	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		switch (chooser.getSelected()) {
			//Competition Codes
			case "CrossAutoLine":
				autonomousCommand = new CrossAutoLine(false);
				break;
			case "CrossAutoLineGyro":
				autonomousCommand = new CrossAutoLine(true);
				break;
			case "AutoSwitch":
				autonomousCommand = new AutoSwitch();
				break;
			case "AutoScaleLeft" :
				autonomousCommand = new AutoScaleLeft();
				break;
			case "AutoScaleRight" :
				autonomousCommand = new AutoScaleRight();
				break;
				
			//Test Codes
			case "AutoMiddle":
				autonomousCommand = new AutoMiddle();
				break;
			case "TestEncoder":
				autonomousCommand = new AutoMeters();
				break;
			case "TestGyro":
				autonomousCommand = new AutoTurn();
				break;
			case "TestPathfinder":
				autonomousCommand = new FollowPath(org.usfirst.frc.team321.robot.commands.pathfinder.Paths.test);
				break;
			default:
				autonomousCommand = new AutoStill();
				break;
		}
		
		if (autonomousCommand != null)
			autonomousCommand.start();
		
		drivetrain.enableRamping(false);
		
		//camera.setCamVisionProcMode();
	}

	@Override
	public void autonomousPeriodic() {
		gearshifter.setLowGear();
		setDashboardValues();
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		//camera.setCamHumanDriverMode();
		drivetrain.resetEncoder();
		drivetrain.enableRamping(true);
		gearshifter.setLowGear();
		intakepivot.setDown();
	}

	@Override
	public void teleopPeriodic() {
		setDashboardValues();
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {}
}
