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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.util.Units;

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
    private boolean isDrivingDistance;
    private double driveDistanceTarget;

    public DriveSubsystem() { 
        leftLeader = new CANSparkMax(11, MotorType.kBrushless);
        rightLeader = new CANSparkMax(02, MotorType.kBrushless);

        leftFollower = new CANSparkMax(12, MotorType.kBrushless);
        rightFollower = new CANSparkMax(01, MotorType.kBrushless);

        leftLeader.restoreFactoryDefaults();
        leftFollower.restoreFactoryDefaults();
        rightLeader.restoreFactoryDefaults();
        rightFollower.restoreFactoryDefaults();

        leftLeader.setInverted(false);
        leftFollower.setInverted(false);
        rightLeader.setInverted(true);
        rightFollower.setInverted(true);

        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);

        leftEncoder = leftLeader.getEncoder();
        rightEncoder = rightLeader.getEncoder();
        //var leftGroup = new MotorControllerGroup(rightLeader, rightFollower);
        //var rightGroup = new MotorControllerGroup(leftLeader, leftFollower);
        diffDrive = new DifferentialDrive(leftLeader, rightLeader);

        leftEncoder.setPositionConversionFactor(0.05224526);
        rightEncoder.setPositionConversionFactor(0.05224526);
        
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);

        ahrs = new AHRS();
    } 

    public void init() {    
        rotation2d = new Rotation2d(Units.degreesToRadians(ahrs.getAngle()));

        kinematics =
            new DifferentialDriveKinematics(Units.inchesToMeters(27.0));

        odometry = new DifferentialDriveOdometry(
            rotation2d,
            0, 0,
            new Pose2d(0, 0, new Rotation2d()));
        //diffDrive.setSafetyEnabled(false);
        
    }

    @Override
    public void periodic() {
        // Update dashboard with encoder measurements
        SmartDashboard.putNumber("Left Drive Encoder", leftEncoder.getPosition());
        SmartDashboard.putNumber("Right Drive Encoder", rightEncoder.getPosition());

        // Update dashboard with angle
        SmartDashboard.putNumber("Angle", ahrs.getAngle());
        
        if (isDrivingDistance)
        {
            // Check if we've moved our intended distance
            if (rightEncoder.getPosition() >= driveDistanceTarget)
            {
                diffDrive.arcadeDrive(0, 0);
                isDrivingDistance = false;
            }
        }
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

    // Distance in meters
    public void driveDistance (double distance)
    {
        // Setup distance flag to tell the period function that we're 
        // intending to drive a certain distance.
        isDrivingDistance = true;

        // Set drive distance
        driveDistanceTarget = distance;
        
        // Set motors to move in our intended direction.
        diffDrive.arcadeDrive(distance > 0 ? 0.2 : -0.2, 0);
    }
}
