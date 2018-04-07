package org.usfirst.frc.team321.robot.commands.subsystems.misc;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DSolenoidToggle extends Command {

	private DoubleSolenoid doubleSolenoid;
	private boolean hasFinished = false;
	Value value = null;

	public DSolenoidToggle(Subsystem subsystem, DoubleSolenoid doubleSolenoid) {
		this(subsystem, doubleSolenoid, null);
	}

	public DSolenoidToggle(Subsystem subsystem, DoubleSolenoid doubleSolenoid, Value value) {
		requires(subsystem);
		this.doubleSolenoid = doubleSolenoid;
		this.value = value;
	}

	protected void initialize() {
		if (value == null) {
			if (doubleSolenoid.get() == DoubleSolenoid.Value.kForward) {
				doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
			} else if (doubleSolenoid.get() == DoubleSolenoid.Value.kReverse) {
				doubleSolenoid.set(DoubleSolenoid.Value.kForward);
			}
		} else {
			doubleSolenoid.set(value);
		}

		hasFinished = true;
	}

	protected boolean isFinished() {
		return hasFinished;
	}
}
