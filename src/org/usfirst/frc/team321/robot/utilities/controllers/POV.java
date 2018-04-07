package org.usfirst.frc.team321.robot.utilities.controllers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

public class POV extends Trigger {

	Joystick joystick;
	int pov;

	public POV(Joystick joystick, int pov){
		this.joystick = joystick;
		this.pov = pov;
	}
	public boolean get() {
		return joystick.getPOV() == pov;
	}
	
	public void whileHeld(final Command command) {
	    whileActive(command);
	}
	
	public void whenPressed(final Command command) {
	    whenActive(command);
	}
}
