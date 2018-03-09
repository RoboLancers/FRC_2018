package org.usfirst.frc.team321.robot;

import org.usfirst.frc.team321.robot.commands.autocode.AutoCode;
import org.usfirst.frc.team321.robot.commands.autocode.AutoCodeEncoder;
import org.usfirst.frc.team321.robot.commands.autocode.AutoCodeGyro;
import org.usfirst.frc.team321.robot.commands.autocode.AutoLeftForward;
import org.usfirst.frc.team321.robot.commands.autocode.AutoMoveToTarget;
import org.usfirst.frc.team321.robot.commands.autocode.AutoMoveTowardTarget;
import org.usfirst.frc.team321.robot.commands.autocode.AutoSimple;
import org.usfirst.frc.team321.robot.commands.autocode.AutoStill;
import org.usfirst.frc.team321.robot.commands.autocode.AutoTurnUntilTarget;
import org.usfirst.frc.team321.robot.subsystems.Camera;
import org.usfirst.frc.team321.robot.subsystems.DriveTrain;
import org.usfirst.frc.team321.robot.subsystems.GearShifter;
import org.usfirst.frc.team321.robot.subsystems.Intake;
import org.usfirst.frc.team321.robot.subsystems.IntakePivot;
import org.usfirst.frc.team321.robot.subsystems.LinearSlide;
import org.usfirst.frc.team321.robot.subsystems.Pneumatics;
import org.usfirst.frc.team321.robot.subsystems.RampRelease;
import org.usfirst.frc.team321.robot.subsystems.Sensors;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public static DriveTrain drivetrain;
	public static LinearSlide linear;
	public static Intake intake;
	public static IntakePivot intakepivot; 
	public static RampRelease ramprelease;
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
		ramprelease = new RampRelease();
		
		pneumatics = new Pneumatics();
		gearshifter = new GearShifter();
		
		oi = new OI();

		chooser.addDefault("No Auto", "AutoStill");
		chooser.addObject("AutoCode", "AutoCode");
		chooser.addObject("AutoCodeGyro", "AutoCodeGyro");
		chooser.addObject("AutoWithEncoder", "AutoCodeEncoder");
		chooser.addObject("AutoLeftForward", "AutoLeftForward");
		chooser.addObject("AutoForwardAndOuttake", "AutoForwardAndOuttake");
		SmartDashboard.putData("Auto mode", chooser);
	}
	
	public void setDashboardValues() {
		try {
			SmartDashboard.putNumber("Gyro", Robot.sensors.getRobotHeading());
			//SmartDashboard.putNumber("LinearSlide Encoder value/position auto", Robot.linear.getLineEncoderDistance());
			SmartDashboard.putNumber("Left Encoder Distance", Robot.drivetrain.getLeftEncoderDistance());
			SmartDashboard.putNumber("Right Encoder Distance", Robot.drivetrain.getRightEncoderDistance());
			SmartDashboard.putNumber("Linear Slide Encoder value/position", Robot.linear.masterLine.getSelectedSensorPosition(0));
			
			SmartDashboard.putBoolean("isTargetDetected", Robot.camera.isTgtVisible());
			SmartDashboard.putNumber("Vision Target Angle", Robot.camera.getTgtAngle_Deg());
			
			NetworkTableInstance.getDefault()
		        .getEntry("/CameraPublisher/JeVois/streams")
		        .setStringArray(new String[]{"mjpeg:http://roborio-321-frc.local:1180/?action=stream"});
	        
	        if(SmartDashboard.getBoolean("Send Values", false)){
	        	Robot.camera.setLowerHSV(Robot.camera.getLowerHSV()[0], Robot.camera.getLowerHSV()[1], Robot.camera.getLowerHSV()[2]);
	        	Robot.camera.setUpperHSV(Robot.camera.getUpperHSV()[0], Robot.camera.getUpperHSV()[1], Robot.camera.getUpperHSV()[0]);
	        	SmartDashboard.putBoolean("Send Values", false);
	        }
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
			case "AutoCode":
				autonomousCommand = new AutoCode();
				break;
			case "AutoGyroCode":
				autonomousCommand = new AutoCodeGyro();
				break;
			case "AutoCodeEncoder":
				autonomousCommand = new AutoCodeEncoder();
				break;
			case "AutoLeftForward":
				autonomousCommand = new AutoLeftForward();
				break;
			case "AutoForwardAndOuttake":
				autonomousCommand = new AutoSimple();
				break;
			default:
				autonomousCommand = new AutoStill();
				break;
		}
		
		if (autonomousCommand != null)
			autonomousCommand.start();
		
		camera.setCamVisionProcMode();
	}

	@Override
	public void autonomousPeriodic() {
		setDashboardValues();
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		camera.setCamHumanDriverMode();
		drivetrain.resetEncoder();
	}

	@Override
	public void teleopPeriodic() {
		setDashboardValues();
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
