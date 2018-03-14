package org.usfirst.frc.team321.robot.commands.pathfinder;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.subsystems.DriveTrain.DrivetrainProfiling;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;

public class FollowPathBackward extends Command{
	Waypoint[] path;
    EncoderFollower[] followers;

    public FollowPathBackward(Waypoint[] path) {
        requires(Robot.drivetrain);
        this.path = path;
        setInterruptible(false);
        followers = PathUtils.pathSetup(path);
    }

    protected void initialize() {
        Robot.drivetrain.resetForPath();
        PathUtils.pathFollow(followers, true);
    }

    protected void execute() {
    	PathUtils.pathFollow(followers, true);
    }

    protected boolean isFinished() {
        return DrivetrainProfiling.isProfileFinished;
    }

    protected void end() {
        Robot.drivetrain.setAll(0);
    }

    protected void interrupted() {
        end();
    }
}
