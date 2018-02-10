
package org.usfirst.frc.team321.robot;

import org.usfirst.frc.team321.robot.commands.auto.MoveWithEncoder;
import org.usfirst.frc.team321.robot.commands.autocode.AutoStill;
import org.usfirst.frc.team321.robot.commands.autocode.AutoCode;
import org.usfirst.frc.team321.robot.commands.autocode.AutoCodeEncoder;
import org.usfirst.frc.team321.robot.commands.autocode.AutoCodeGyro;
import org.usfirst.frc.team321.robot.subsystems.Ramp;
import org.usfirst.frc.team321.robot.subsystems.Camera;
import org.usfirst.frc.team321.robot.subsystems.DriveTrain;
import org.usfirst.frc.team321.robot.subsystems.GearShifter;
import org.usfirst.frc.team321.robot.subsystems.Intake;
import org.usfirst.frc.team321.robot.subsystems.IntakePivot;
import org.usfirst.frc.team321.robot.subsystems.LinearSlide;
import org.usfirst.frc.team321.robot.subsystems.Pneumatics;
import org.usfirst.frc.team321.robot.subsystems.Sensors;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static DriveTrain drivetrain;
	public static LinearSlide linear;
	public static IntakePivot intakepivot;
	public static Intake intake;
	public static Ramp ramp;

	public static Compressor compressor; 
	public static Pneumatics pneumatics;
	public static GearShifter gearshifter;
	
	public static Sensors sensors;
	
	public static OI oi;
	
	Command autonomousCommand;
	SendableChooser<String> chooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		sensors = new Sensors();
		drivetrain = new DriveTrain();
		pneumatics = new Pneumatics(); 
		compressor = new Compressor();
		ramp = new Ramp();
		
		linear = new LinearSlide();
		gearshifter = new GearShifter();
		intakepivot = new IntakePivot(); 
		intake = new Intake(); 
		oi = new OI();

		chooser.addDefault("No Auto", "AutoStill");
		chooser.addObject("Autonhomas", "AutoCode");
		chooser.addObject("GyroCode", "AutoCodeGyro");
		chooser.addObject("AutoWithEncoder", "AutoCodeEncoder");
		SmartDashboard.putData("Auto mode", chooser);
	}
	
	public void setDashboardValues() {
		try {
			SmartDashboard.putNumber("Gyro", Robot.sensors.getRobotHeading());
			SmartDashboard.putNumber("LinearSlide Encoder value/position auto", Robot.linear.getLineEncoderDistance());
			SmartDashboard.putNumber("Left Encoder Distance", Robot.drivetrain.getLeftEncoderDistance());
			SmartDashboard.putNumber("Raw Left Encoder Value", Robot.drivetrain.getRawLeftEncoderCount());
			SmartDashboard.putNumber("Distance", Robot.drivetrain.getLeftEncoderDistance());
			SmartDashboard.putNumber("LinearSlide Encoder value/position", Robot.linear.getLineEncoderDistance());
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
