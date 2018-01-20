package org.usfirst.frc.team321.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//TalonSRXs
	public static final int TOP_LEFT_MOTOR = 5;
	public static final int TOP_RIGHT_MOTOR = 8;
	public static final int MID_LEFT_MOTOR = 1;
	public static final int MID_RIGHT_MOTOR = 4;
	public static final int BOT_LEFT_MOTOR = 3;
	public static final int BOT_RIGHT_MOTOR = 2;
	
	public static final int LINE_A = 0;
	public static final int LINE_B = 9;
	
	public static final int INTAKE_LEFT = 9;
	public static final int INTAKE_RIGHT = 10;
	
	//Compressor
	public static final int COMPRESSOR = 0;	
	
	//Pneumatics 
	public static final int GEARSHIFTER_FORWARD = 2;
	public static final int GEARSHIFTER_REVERSE = 3;

	public static final int INTAKE_FORWARD = 0;
	public static final int INTAKE_REVERSE = 1; 

	//Touch Sensors
	public static final int TOUCH_SENSOR_TOP = 0;
	public static final int TOUCH_SENSOR_BOTTOM = 1;
	
	//Encoders
	public static final int LINE_ENCODER_A = 2;
	public static final int LINE_ENCODER_B = 3;
	public static final int LEFT_ENCODER_A = 4;
	public static final int LEFT_ENCODER_B = 5;
	public static final int RIGHT_ENCODER_A = 6;
	public static final int RIGHT_ENCODER_B = 7;
	
	
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
