package org.usfirst.frc.team321.robot.subsystems;

import org.usfirst.frc.team321.robot.Constants;
import org.usfirst.frc.team321.robot.commands.RegulateCompressor;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatics extends Subsystem {
	
	public Compressor compressor;
	
	public Pneumatics(){
		compressor = new Compressor(Constants.COMPRESSOR);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new RegulateCompressor());
	}
	
	public void regulateCompressor(){
    	if(!compressor.getPressureSwitchValue() && !compressor.enabled()
    			&& isCompressorSafeToUse()){
    		compressor.start();
    	}else if(compressor.getPressureSwitchValue() && compressor.enabled() 
    			|| !isCompressorSafeToUse()){
    		compressor.stop();
    	}
    }
	
	public boolean isCompressorSafeToUse(){	
		if((compressor.getCompressorCurrentTooHighFault() && !compressor.getCompressorCurrentTooHighStickyFault()) ||
  			(compressor.getCompressorNotConnectedFault() && !compressor.getCompressorNotConnectedStickyFault()) || 
  			(compressor.getCompressorShortedFault() && !compressor.getCompressorShortedStickyFault())){
			return false;
	   	}else{
	   		return true;
		}
	}	
	
	public boolean getPressure(){
		return compressor.getPressureSwitchValue();
	}
	
}
