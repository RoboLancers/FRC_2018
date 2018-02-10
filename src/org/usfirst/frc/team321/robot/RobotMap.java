package org.usfirst.frc.team321.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//TalonSRXs
	public static final int LEFT_MASTER_MOTOR = 1;
	public static final int LEFT_SLAVE_A = 5;
	public static final int LEFT_SLAVE_B = 10;
	
	public static final int RIGHT_MASTER_MOTOR = 9;
	public static final int RIGHT_SLAVE_A = 4;
	public static final int RIGHT_SLAVE_B = 8;
	
	public static final int LINE_A = 2;
	public static final int LINE_B = 6;
	
	public static final int INTAKE_LEFT = 3;
	public static final int INTAKE_RIGHT = 5;
	
	public static final int LEFT_RAMP = 11;
	public static final int RIGHT_RAMP = 12;
	
	//Compressor
	public static final int COMPRESSOR = 0;	
	
	//Pneumatics 
	public static final int GEARSHIFTER_FORWARD = 2;
	public static final int GEARSHIFTER_REVERSE = 3;

	public static final int INTAKE_FORWARD = 0;
	public static final int INTAKE_REVERSE = 1; 
	
	//Encoders
	public static final int LINE_ENCODER_A = 2;
	public static final int LINE_ENCODER_B = 3;
	public static final int LEFT_ENCODER_A = 0;
	public static final int LEFT_ENCODER_B = 1;
	public static final int RIGHT_ENCODER_A = 6;
	public static final int RIGHT_ENCODER_B = 7;
}
