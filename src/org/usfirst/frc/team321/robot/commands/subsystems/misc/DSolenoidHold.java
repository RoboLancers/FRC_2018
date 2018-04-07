package org.usfirst.frc.team321.robot.commands.subsystems.misc;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DSolenoidHold extends Command {

	private DoubleSolenoid doubleSolenoid;
	private boolean hasFinished = false;
	Value value = null;

	public DSolenoidHold(Subsystem subsystem, DoubleSolenoid doubleSolenoid, Value defaultValue) {
		requires(subsystem);
		this.doubleSolenoid = doubleSolenoid;
		this.value = defaultValue;
	}

	protected void initialize() {
		doubleSolenoid.set(
				value == DoubleSolenoid.Value.kForward ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);

		hasFinished = false;
	}

	protected boolean isFinished() {
		return hasFinished;
	}

	protected void interrupted() {
		doubleSolenoid.set(value);
		hasFinished = true;
	}
}