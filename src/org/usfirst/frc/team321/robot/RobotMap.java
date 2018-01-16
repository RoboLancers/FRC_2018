package org.usfirst.frc.team321.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//matches ports to objects
	public static final int TOP_LEFT_MOTOR = 1;
	public static final int TOP_RIGHT_MOTOR = 4;
	
	public static final int MID_LEFT_MOTOR = 2;
	public static final int MID_RIGHT_MOTOR = 5;
	
	public static final int BOT_LEFT_MOTOR = 3;
	public static final int BOT_RIGHT_MOTOR = 6;
	
	public static final int COMPRESSOR = 0;	
	public static final int LINE_LEFT = 7;
	public static final int LINE_RIGHT = 8;
	
	public static final int TOP_LEFT_ENCODER = 0;
	public static final int TOP_RIGHT_ENCODER = 1;
	public static final int BOT_LEFT_ENCODER = 2;
	public static final int BOT_RIGHT_ENCODER = 3;
	public static final int LINE_LEFT_ENCODER = 4;
	public static final int LINE_RIGHT_ENCODER = 5;
	//ports are just placeholders
	public static final int GEARSHIFTER_FORWARD = 0;
	public static final int GEARSHIFTER_REVERSE = 1;
	//ports are just placeholders
	public static final int INTAKE_FORWARD = 2;
	public static final int INTAKE_REVERSE = 5; 
	//DIGITAL INPUT
	public static final int TOUCH_SENSOR_TOP = 0;
	public static final int TOUCH_SENSOR_BOTTOM = 1;
	
	
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
