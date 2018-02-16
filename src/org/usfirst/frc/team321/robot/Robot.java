package org.usfirst.frc.team321.robot;

import org.usfirst.frc.team321.robot.commands.autocode.AutoStill;
import org.usfirst.frc.team321.robot.commands.autocode.AutoCode;
import org.usfirst.frc.team321.robot.commands.autocode.AutoCodeEncoder;
import org.usfirst.frc.team321.robot.commands.autocode.AutoCodeGyro;
import org.usfirst.frc.team321.robot.commands.autocode.AutoLeftForward;
import org.usfirst.frc.team321.robot.commands.autocode.AutoMoveToTarget;
import org.usfirst.frc.team321.robot.commands.autocode.AutoMoveTowardTarget;
import org.usfirst.frc.team321.robot.commands.autocode.AutoTurnUntilTarget;
import org.usfirst.frc.team321.robot.subsystems.Ramp;
import org.usfirst.frc.team321.robot.subsystems.Camera;
import org.usfirst.frc.team321.robot.subsystems.DriveTrain;
import org.usfirst.frc.team321.robot.subsystems.GearShifter;
import org.usfirst.frc.team321.robot.subsystems.Intake;
import org.usfirst.frc.team321.robot.subsystems.IntakePivot;
import org.usfirst.frc.team321.robot.subsystems.LinearSlide;
import org.usfirst.frc.team321.robot.subsystems.Pneumatics;
import org.usfirst.frc.team321.robot.subsystems.Sensors;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public static DriveTrain drivetrain;
	public static LinearSlide linear;
	//public static IntakePivot intakepivot;
	public static Intake intake;
	public static Ramp ramp;

	public static Pneumatics pneumatics; 
	public static GearShifter gearshifter;
	
	public static Sensors sensors;
	public static Camera camera;
	
	public static OI oi;
	
	Command autonomousCommand;
	SendableChooser<String> chooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		sensors = new Sensors();
		camera = new Camera();
		
		drivetrain = new DriveTrain();
		linear = new LinearSlide();
		intake = new Intake(); 
		ramp = new Ramp();

		pneumatics = new Pneumatics();
		gearshifter = new GearShifter();
		//intakepivot = new IntakePivot(); 
		
		oi = new OI();

		chooser.addDefault("No Auto", "AutoStill");
		chooser.addObject("Autonhomas", "AutoCode");
		chooser.addObject("GyroCode", "AutoCodeGyro");
		chooser.addObject("AutoWithEncoder", "AutoCodeEncoder");
		chooser.addObject("AutoMoveTowardTarget", "AutoMoveTowardTarget");
		chooser.addObject("AutoMoveToTarget", "AutoMoveToTarget");
		chooser.addObject("AutoTurnUntilTarget", "AutoTurnUntilTarget");
		chooser.addObject("AutoLeftForward", "AutoLeftForward");
		SmartDashboard.putData("Auto mode", chooser);
	}
	
	public void setDashboardValues() {
		try {
			SmartDashboard.putNumber("Gyro", Robot.sensors.getRobotHeading());
			SmartDashboard.putNumber("LinearSlide Encoder value/position auto", Robot.linear.getLineEncoderDistance());
			SmartDashboard.putNumber("Left Encoder Distance", Robot.drivetrain.getLeftEncoderDistance());
			SmartDashboard.putNumber("Right Encoder Distance", Robot.drivetrain.getRightEncoderDistance());
			SmartDashboard.putNumber("Distance", Robot.drivetrain.getLeftEncoderDistance());
			SmartDashboard.putNumber("Linear Slide Encoder value/position", Robot.linear.getLineEncoderDistance());
			SmartDashboard.putBoolean("isTargetDetected", Robot.camera.isTgtVisible());
			SmartDashboard.putNumber("Vision Target Angle", Robot.camera.getTgtAngle_Deg());
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
			case "AutoMoveToTarget":
				autonomousCommand = new AutoMoveToTarget();
				break;
			case "AutoMoveTowardTarget":
				autonomousCommand = new AutoMoveTowardTarget();
				break;
			case "AutoTurnUntilTarget":
				autonomousCommand = new AutoTurnUntilTarget();
				break;
			case "AutoLeftForward":
				autonomousCommand = new AutoLeftForward();
				break;
			default:
				autonomousCommand = new AutoStill();
				break;
		
		}
		
		if (autonomousCommand != null)
			autonomousCommand.start();
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
