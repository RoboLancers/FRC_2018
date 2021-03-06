package org.usfirst.frc.team321.robot;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team321.robot.subsystems.drivetrain.Drivetrain;
import org.usfirst.frc.team321.robot.subsystems.manipulator.Manipulator;
import org.usfirst.frc.team321.robot.subsystems.misc.Camera;
import org.usfirst.frc.team321.robot.subsystems.misc.GripPipeline;
import org.usfirst.frc.team321.robot.subsystems.misc.Leds;
import org.usfirst.frc.team321.robot.subsystems.misc.Pneumatics;
import org.usfirst.frc.team321.robot.subsystems.misc.Sensors;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {

	public static Manipulator manipulator;
	public static Drivetrain drivetrain;
	public static Leds leds;
	public static Pneumatics pneumatics;
	public static Sensors sensors;
	public static Camera camera;

	public static OI oi;
	private Command autonomousCommand;

	@Override
	public void robotInit() {
		sensors = new Sensors();
		//camera = new Camera(true);
		
		new Thread(() -> {
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			camera.setResolution(320, 240);
			camera.setExposureManual(50);
			
			CvSink cvSink = CameraServer.getInstance().getVideo();
			CvSource cvSource = CameraServer.getInstance().putVideo("Cube", 320, 240);
			
			Mat source = new Mat();
			Mat output = new Mat();
			
			GripPipeline gripPipeline = new GripPipeline();
			
			while(!Thread.interrupted()){
				cvSink.grabFrame(source);
				
				if(!source.empty()){
					gripPipeline.process(source);
					for(int i = 0; i < gripPipeline.findContoursOutput().size(); i++){
						Imgproc.drawContours(source, gripPipeline.findContoursOutput(), i, new Scalar(0,0,0));
					}
				}
				
				cvSource.putFrame(source);
			}
		}).start();
		
		
		drivetrain = new Drivetrain();
		manipulator = new Manipulator();

		pneumatics = new Pneumatics();

		oi = new OI();
		oi.putAutoModes();
	}

	@Override
	public void robotPeriodic() {
		OI.setDashboardValues();
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
		drivetrain.enableRamping(false);
		drivetrain.getGearShifter().setLowGear();

		autonomousCommand = oi.getAutoCommand(oi.getAutoMode());
		manipulator.getIntakePivot().setUp();

		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		drivetrain.resetEncoders();
		drivetrain.enableRamping(true);
		drivetrain.getGearShifter().setLowGear();

		manipulator.getIntakePivot().setDown();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}
