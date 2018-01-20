package org.usfirst.frc.team321.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DSolenoidHold extends Command {

	private DoubleSolenoid ds;
	private boolean hasFinished = false;
	Value value = null;

	public DSolenoidHold(Subsystem sub, DoubleSolenoid ds, Value defaultValue){
		requires(sub);
		this.ds = ds;
		this.value = defaultValue;
	}

	protected void initialize() {
		ds.set(value == DoubleSolenoid.Value.kForward ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
		
		hasFinished = false;
	}

	protected void execute() {
		
	}

	protected boolean isFinished() {
		return hasFinished;
	}

	protected void end() {

	}

	protected void interrupted() {
		ds.set(value);
		
		hasFinished = true;
	}
}
