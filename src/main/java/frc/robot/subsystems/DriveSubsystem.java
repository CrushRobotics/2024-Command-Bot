package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

public class DriveSubsystem extends SubsystemBase {
    CANSparkMax leftLeader;
    CANSparkMax rightLeader;
    CANSparkMax leftFollower;
    CANSparkMax rightFollower;

    RelativeEncoder leftEncoder;
    RelativeEncoder rightEncoder;

    DifferentialDrive diffDrive;
    DifferentialDriveOdometry odometry;
    DifferentialDriveKinematics kinematics;

    AHRS ahrs;

    Rotation2d rotation2d;

    public DriveSubsystem() { 
        leftLeader = new CANSparkMax(11, MotorType.kBrushless);
        rightLeader = new CANSparkMax(02, MotorType.kBrushless);

        leftFollower = new CANSparkMax(12, MotorType.kBrushless);
        rightFollower = new CANSparkMax(01, MotorType.kBrushless);

        leftLeader.restoreFactoryDefaults();
        leftFollower.restoreFactoryDefaults();
        rightLeader.restoreFactoryDefaults();
        rightFollower.restoreFactoryDefaults();

        leftLeader.setInverted(true);
        leftFollower.setInverted(true);
        rightLeader.setInverted(false);
        rightFollower.setInverted(false);
        //rightFollower.setInverted(true);

        //leftFollower.follow(leftLeader);
        //rightFollower.follow(rightLeader);

        leftEncoder = leftLeader.getEncoder();
        rightEncoder = rightLeader.getEncoder();
        var leftGroup = new MotorControllerGroup(rightLeader, rightFollower);
        var rightGroup = new MotorControllerGroup(leftLeader, leftFollower);
        diffDrive = new DifferentialDrive(leftGroup, rightGroup);
        
        //leftEncoder.setPosition(0);
        //rightEncoder.setPosition(0);
    } 

    public void init() {   
/* 
        rotation2d = new Rotation2d(Units.degreesToRadians(ahrs.getAngle()));

        kinematics =
            new DifferentialDriveKinematics(Units.inchesToMeters(27.0));

        odometry = new DifferentialDriveOdometry(
            rotation2d,
            0, 0,
            new Pose2d(0, 0, new Rotation2d()));
*/
        //diffDrive.setSafetyEnabled(false);
        
    }

    @Override
    public void periodic() {
        // Update dashboard with encoder measurements
        SmartDashboard.putNumber("Left Encoder", leftEncoder.getPosition());
        SmartDashboard.putNumber("Right Encoder", rightEncoder.getPosition());
        
    }
    
    public void drive(double leftOutput, double rightOutput) {
        //rightLeader.set(rightOutput);
        //leftLeader.set(leftOutput);
        diffDrive.tankDrive(leftOutput, rightOutput);
        //diffDrive.arcadeDrive(leftOutput, rightOutput);
    }

    public void arcadeDrive(double leftOutput, double rightOutput) {
        diffDrive.arcadeDrive(leftOutput, rightOutput);
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public void resetPose() {
        odometry.resetPosition(rotation2d, 0, 0, new Pose2d(0, 0, new Rotation2d()));
    }

    public ChassisSpeeds getCurrentSpeeds() {
        var leftVelocity = leftEncoder.getVelocity();
        var rightVelocity = rightEncoder.getVelocity();

        var wheelSpeeds = new DifferentialDriveWheelSpeeds(leftVelocity, rightVelocity);

        return kinematics.toChassisSpeeds(wheelSpeeds);
    }

}