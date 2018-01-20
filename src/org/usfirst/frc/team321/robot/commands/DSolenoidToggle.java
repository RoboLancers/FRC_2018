package org.usfirst.frc.team321.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DSolenoidToggle extends Command {

	private DoubleSolenoid ds;
	private boolean hasFinished = false;
	Value value = null;

	public DSolenoidToggle(Subsystem sub, DoubleSolenoid ds) {
		this(sub, ds, null);
	}

	public DSolenoidToggle(Subsystem sub, DoubleSolenoid ds, Value value){
		requires(sub);
		this.ds = ds;
		this.value = value;
	}

	protected void initialize() {

		if(value == null){
			if(ds.get() == DoubleSolenoid.Value.kForward){
				ds.set(DoubleSolenoid.Value.kReverse);
			}

			else if(ds.get() == DoubleSolenoid.Value.kReverse){
				ds.set(DoubleSolenoid.Value.kForward);
			}
		}else{
			ds.set(value);
		}
		hasFinished = true;
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return hasFinished;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
