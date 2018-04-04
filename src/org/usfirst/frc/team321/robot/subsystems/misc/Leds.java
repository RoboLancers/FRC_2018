package org.usfirst.frc.team321.robot.subsystems.misc;

import org.usfirst.frc.team321.robot.commands.subsystems.misc.ChangeLeds;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Leds extends Subsystem{
	Spark led;
	
	public Leds() {
		led = new Spark(0);
	}
	
	public void setColor(LedColors colors) {
		led.set(colors.color);
	}
	
	public enum LedColors {
		LAWN_GREEN(0.71), LIME(0.75), SKY_BLUE(0.83), DARK_BLUE(0.85);
		
		public double color;
		
		LedColors(double color) {
			this.color = color;
		}
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ChangeLeds());
	}
}
