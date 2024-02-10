package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import frc.robot.commands.DefaultDriveCommand;

public class DriveSubsystem extends SubsystemBase {
    CANSparkMax leftLeader;
    CANSparkMax rightLeader;
    CANSparkMax leftFollower;
    CANSparkMax rightFollower;

    RelativeEncoder leftEncoder;
    RelativeEncoder rightEncoder;

    DifferentialDrive diffDrive;

    public DriveSubsystem() {
        //init();
    }

    public void init() {    
        leftLeader = new CANSparkMax(1, MotorType.kBrushless);
        rightLeader = new CANSparkMax(3, MotorType.kBrushless);

        leftFollower = new CANSparkMax( 2, MotorType.kBrushless);
        rightFollower = new CANSparkMax(4, MotorType.kBrushless);

        rightLeader.setInverted(true);

        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);

        leftEncoder = leftLeader.getEncoder();
        rightEncoder = rightLeader.getEncoder();


        //diffDrive.setSafetyEnabled(false);
        
        //diffDrive = new DifferentialDrive(leftLeader, rightLeader);
    }
    
    public void drive(double leftOutput, double rightOutput) {
        rightLeader.set(rightOutput);
        leftLeader.set(leftOutput);
        //diffDrive.tankDrive(leftOutput, rightOutput);
        //diffDrive.arcadeDrive(leftOutput, rightOutput);
    }

}
