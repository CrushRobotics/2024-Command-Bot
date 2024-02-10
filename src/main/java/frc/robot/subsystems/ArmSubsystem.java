package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

public class ArmSubsystem extends SubsystemBase {
    
    CANSparkMax armLeader;
    CANSparkMax armFollower;
    RelativeEncoder encoder;

    public void init() {
        armLeader = new CANSparkMax(5, MotorType.kBrushless);
        armFollower = new CANSparkMax(6, MotorType.kBrushless);

        armFollower.follow(armLeader);

        encoder = armLeader.getEncoder();
        encoder.setPosition(0);
    }

}
