package org.usfirst.frc.team321.robot;

import org.usfirst.frc.team321.robot.subsystems.drivetrain.Drivetrain;
import org.usfirst.frc.team321.robot.subsystems.manipulator.Manipulator;
import org.usfirst.frc.team321.robot.subsystems.misc.Camera;
import org.usfirst.frc.team321.robot.subsystems.misc.Leds;
import org.usfirst.frc.team321.robot.subsystems.misc.Pneumatics;
import org.usfirst.frc.team321.robot.subsystems.misc.Sensors;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {

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
		camera = new Camera(true);

		drivetrain = new Drivetrain();
		leds = new Leds();
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
		
		camera.setCamVisionProcMode();

		autonomousCommand = oi.getAutoCommand(oi.getAutoMode());

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

		camera.setCamHumanDriverMode();

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
