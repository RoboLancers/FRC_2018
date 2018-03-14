package org.usfirst.frc.team321.robot.commands.pathfinder;

import java.io.File;

import org.usfirst.frc.team321.robot.Robot;
import org.usfirst.frc.team321.robot.subsystems.DriveTrain;
import org.usfirst.frc.team321.robot.subsystems.DriveTrain.DrivetrainProfiling;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class PathUtils {
	public static EncoderFollower[] pathSetup(Waypoint[] path) {
		EncoderFollower left = new EncoderFollower();
        EncoderFollower right = new EncoderFollower();
        Trajectory.Config cfg = new Trajectory.Config(Trajectory.FitMethod.HERMITE_QUINTIC, Trajectory.Config.SAMPLES_HIGH,
                DriveTrain.DrivetrainProfiling.dt, DriveTrain.DrivetrainProfiling.max_velocity, DriveTrain.DrivetrainProfiling.max_acceleration, DriveTrain.DrivetrainProfiling.max_jerk);
        String pathHash = String.valueOf(path.hashCode());
        SmartDashboard.putString("Path Hash", pathHash);
        Trajectory toFollow;// = Pathfinder.generate(path, cfg);
        File trajectory = new File("/home/lvuser/paths/" + pathHash + ".csv");
        if (!trajectory.exists()) {
            toFollow = Pathfinder.generate(path, cfg);
            Pathfinder.writeToCSV(trajectory, toFollow);
            System.out.println(pathHash + ".csv not found, wrote to file");
        } else {
            System.out.println(pathHash + ".csv read from file");
            toFollow = Pathfinder.readFromCSV(trajectory);
        }

        TankModifier modifier = new TankModifier(toFollow).modify((DrivetrainProfiling.wheel_base_width));
        DrivetrainProfiling.last_gyro_error = 0.0;
        
        left = new EncoderFollower(modifier.getLeftTrajectory());
        right = new EncoderFollower(modifier.getRightTrajectory());
        
        left.configureEncoder(Robot.drivetrain.leftMaster.getSelectedSensorPosition(0), DrivetrainProfiling.ticks_per_rev, DrivetrainProfiling.wheel_diameter);
        right.configureEncoder(Robot.drivetrain.rightMaster.getSelectedSensorPosition(0), DrivetrainProfiling.ticks_per_rev, DrivetrainProfiling.wheel_diameter);
        
        left.configurePIDVA(DrivetrainProfiling.kp, DrivetrainProfiling.ki, DrivetrainProfiling.kd, DrivetrainProfiling.kv, DrivetrainProfiling.ka);
        right.configurePIDVA(DrivetrainProfiling.kp, DrivetrainProfiling.ki, DrivetrainProfiling.kd, DrivetrainProfiling.kv, DrivetrainProfiling.ka);
        
        return new EncoderFollower[]{
                left, // 0
                right, // 1
        };
	}
	
	public static void pathFollow(EncoderFollower[] followers, boolean reverse) {
        EncoderFollower left = followers[0];
        EncoderFollower right = followers[1];
        
        double l;
        double r;
        
        double localGp = DrivetrainProfiling.gp;
        
        if (!reverse) {
            localGp *= -1;
            l = left.calculate(-Robot.drivetrain.leftMaster.getSelectedSensorPosition(0));
            r = right.calculate(-Robot.drivetrain.rightMaster.getSelectedSensorPosition(0));
        } else {
            l = left.calculate(Robot.drivetrain.leftMaster.getSelectedSensorPosition(0));
            r = right.calculate(Robot.drivetrain.rightMaster.getSelectedSensorPosition(0));
        }

        double gyro_heading = reverse ? Robot.sensors.navX.getAngle() - DrivetrainProfiling.path_angle_offset : -Robot.sensors.navX.getAngle() + DrivetrainProfiling.path_angle_offset;

        double angle_setpoint = Pathfinder.r2d(left.getHeading());
        SmartDashboard.putNumber("Angle setpoint", angle_setpoint);
        
        double angleDifference = Pathfinder.boundHalfDegrees(angle_setpoint - gyro_heading);
        SmartDashboard.putNumber("Angle difference", angleDifference);

        double turn = localGp * angleDifference + (DrivetrainProfiling.gd *
                ((angleDifference - DrivetrainProfiling.last_gyro_error) / DrivetrainProfiling.dt));

        DrivetrainProfiling.last_gyro_error = angleDifference;


        if (left != null && !left.isFinished()) {
            SmartDashboard.putNumber("Left diff", left.getSegment().x + Robot.drivetrain.getLeftEncoderDistance());
            SmartDashboard.putNumber("Left set vel", left.getSegment().velocity);
            SmartDashboard.putNumber("Left set pos", left.getSegment().x);
            SmartDashboard.putNumber("Left calc voltage", l);
            SmartDashboard.putNumber("Commanded seg heading", left.getHeading());
            SmartDashboard.putNumber("Left + turn", l + turn);
            SmartDashboard.putNumber("Left seg acceleration", left.getSegment().acceleration);
            SmartDashboard.putNumber("Path angle offset", DrivetrainProfiling.path_angle_offset);
            SmartDashboard.putNumber("Angle offset w/ new path angle offset", angleDifference + DrivetrainProfiling.path_angle_offset);
        }
        
        if (!reverse) {
            Robot.drivetrain.setMotors(l + turn, r - turn);
        } else {
        	Robot.drivetrain.setMotors(-l + turn, -r - turn);
        }

        if (left.isFinished() && right.isFinished()) {
            DrivetrainProfiling.isProfileFinished = true;
            DrivetrainProfiling.path_angle_offset = angleDifference;
        }
}
}
