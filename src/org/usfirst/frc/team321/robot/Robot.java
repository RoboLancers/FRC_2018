package org.usfirst.frc.team321.robot;

import org.usfirst.frc.team321.robot.commands.autonomous.modes.AutoMiddle;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.AutoStill;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.AutoSwitch;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.AutoSwitchLeft;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.AutoSwitchRight;
import org.usfirst.frc.team321.robot.commands.autonomous.modes.CrossAutoLine;
import org.usfirst.frc.team321.robot.commands.pathfinder.FollowPath;
import org.usfirst.frc.team321.robot.subsystems.drivetrain.DriveTrain;
import org.usfirst.frc.team321.robot.subsystems.drivetrain.GearShifter;
import org.usfirst.frc.team321.robot.subsystems.manipulator.Intake;
import org.usfirst.frc.team321.robot.subsystems.manipulator.IntakePivot;
import org.usfirst.frc.team321.robot.subsystems.manipulator.LinearSlide;
import org.usfirst.frc.team321.robot.subsystems.misc.Camera;
import org.usfirst.frc.team321.robot.subsystems.misc.Pneumatics;
import org.usfirst.frc.team321.robot.subsystems.misc.Sensors;

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
		camera.setCamVisionProcMode();
		
		autonomousCommand = oi.getAutoCommand();
		
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	@Override
	public void autonomousPeriodic() {
		gearshifter.setLowGear();
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		camera.setCamHumanDriverMode();
		
		drivetrain.resetEncoder();
		drivetrain.enableRamping(true);
		
		gearshifter.setLowGear();
		intakepivot.setDown();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {}
}
